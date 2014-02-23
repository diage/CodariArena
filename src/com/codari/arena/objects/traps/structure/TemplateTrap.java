package com.codari.arena.objects.traps.structure;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.crypto.NoSuchMechanismException;

import net.minecraft.util.org.apache.commons.lang3.RandomStringUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.metadata.FixedMetadataValue;

import com.codari.api5.CodariI;
import com.codari.arena.objects.RandomSpawnableObjectA;
import com.codari.arena.util.AoE;
import com.codari.arena5.players.teams.Team;

public abstract class TemplateTrap extends RandomSpawnableObjectA implements Trap {
	private static final long serialVersionUID = 810954548247897220L;
	//-----Fields-----//
	//---Block Configuration---//
	private SerializableBlock serialIndicator;
	protected transient BlockState trapState;
	protected transient BlockState trapIndicatorState;

	//public static final String META_DATA_STRING = RandomStringUtils.randomAscii(69);
	public static final String RANDOM_PASS_KEY = RandomStringUtils.randomAscii(69);

	//---Design Preference---//
	protected Material revealedTrapMaterial = Material.REDSTONE_WIRE;
	protected Material setTrapMaterial = Material.REDSTONE_WIRE;

	private final Material revealedTrapIndicatorMaterial = Material.STAINED_CLAY;
	protected byte clayStoneMetaDataValue = 0;

	//---Initialized in Constructor---//
	private double radius;
	private transient AoE areaOfEffect;

	//---Initialized when trap is activated---//
	private transient Team team;
	private boolean isSpawned;

	//-----Constructor-----//
	public TemplateTrap(Location location, double radius) {
		super(location);
		//Block positions
		Block trapBlock = location.getBlock(); 
		Block indicatorBlock = trapBlock.getRelative(BlockFace.DOWN).getState().getBlock();


		//States for block positions
		this.trapState = trapBlock.getState();
		this.trapIndicatorState = indicatorBlock.getState();
		this.serialIndicator = new SerializableBlock(this.trapIndicatorState);

		//Create new AoE
		this.radius = radius;
		this.areaOfEffect = new AoE(location, radius, this);
	}

	protected void readObject() throws IOException, ClassNotFoundException {
		World world = Bukkit.getWorld(this.serialIndicator.worldName);
		if (world == null) {
			throw new IllegalStateException("World named " + this.serialIndicator.worldName + " is not loaded");
		}
		this.trapIndicatorState = world.getBlockAt(this.serialIndicator.x, this.serialIndicator.y, this.serialIndicator.z).getState();
		this.trapState = this.trapIndicatorState.getBlock().getRelative(BlockFace.UP).getState();

		this.areaOfEffect = new AoE(this.trapState.getLocation(), radius, this);
	}

	//-----Getters-----//
	public BlockState getTrapState() {
		return this.trapState;
	}

	public BlockState getTrapStateIndicator() {
		return this.trapIndicatorState;
	}


	//-----Overridden Methods-----//
	//---RandomSpawnable Object Methods---//
	@Override
	public void spawn() {
		this.isSpawned = true;
		if (this.trapState.hasMetadata(RANDOM_PASS_KEY)) {
			throw new NoSuchMechanismException("THINGS ARE HAPPENING THAT SHOULDN'T BE HAPPENING.... (traps too close)");
		}
		this.reveal();
		this.activateMetaData();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void reveal() {
		this.trapState.getBlock().setType(this.revealedTrapMaterial);
		this.trapIndicatorState.getBlock().setType(this.revealedTrapIndicatorMaterial);
		this.trapIndicatorState.getBlock().setData(this.clayStoneMetaDataValue);
	}	

	@Override
	public void hide() {
		this.trapState.update(true);
		this.trapIndicatorState.update(true);
		this.areaOfEffect.setDeactive();
		this.deactivateMetaData();
		this.isSpawned = false;
	}
	
	@Override
	public Collection<BlockState> getAffectedBlocks() {
		Collection<BlockState> affectedBlocks = new ArrayList<>();
		affectedBlocks.add(this.trapState);
		affectedBlocks.add(this.trapIndicatorState);
		return affectedBlocks;
	}

	@Override
	public boolean isSpawned() {
		return this.isSpawned;
	}

	//---Trap Methods---//
	/* When a trap is set, the adjacent blocks are colored in, the trap is
	 * no longer activatable, and the trap starts using the AoE class
	 * to check for members of the opposing team who would trigger it.  */
	@Override
	public void set() {
		this.trapState.getBlock().setType(setTrapMaterial);
		this.deactivateMetaData();
		this.startAoE();
	}

	@Override 
	public void deactivate() {
		this.hide();
		this.stopAoE();
	}

	@Override
	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public Team getTeam() {
		return this.team;
	}

	//-----Private Methods-----//
	private void startAoE() {
		this.areaOfEffect.setActive();
	}

	private void stopAoE() {
		this.areaOfEffect.setDeactive();
	}

	private void activateMetaData() {
		this.trapState.setMetadata(RANDOM_PASS_KEY, new FixedMetadataValue(CodariI.INSTANCE, this));
		//this.trapState.setMetadata(META_DATA_STRING, new FixedMetadataValue(CodariI.INSTANCE, true));
	}	

	private void deactivateMetaData() {
		if(this.trapState.hasMetadata(RANDOM_PASS_KEY)) {
			this.trapState.removeMetadata(RANDOM_PASS_KEY, CodariI.INSTANCE);
		}
		//if(this.trapState.hasMetadata(META_DATA_STRING)) {
			//this.trapState.removeMetadata(META_DATA_STRING, CodariI.INSTANCE);
		//}
	}

	private class SerializableBlock implements Serializable {
		private static final long serialVersionUID = 3405908171694915925L;
		private int x, y, z;
		private String worldName;

		public SerializableBlock(BlockState blockState) {
			this.worldName = blockState.getWorld().getName();
			this.x = blockState.getX();
			this.y = blockState.getY();
			this.z = blockState.getZ();
		}
	}
}
