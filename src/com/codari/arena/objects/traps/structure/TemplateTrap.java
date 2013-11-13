package com.codari.arena.objects.traps.structure;


import javax.xml.crypto.NoSuchMechanismException;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.codari.api.Codari;
import com.codari.arena.combatants.teams.Team;
import com.codari.arena.util.AoE;

public abstract class TemplateTrap implements Trap {
	//-----Fields-----//
	//---Block Configuration---//
	protected BlockState trapState;
	protected BlockState trapIndicatorState;
	public static final String META_DATA_STRING = RandomStringUtils.randomAscii(69);
	public static final String RANDOM_PASS_KEY = RandomStringUtils.randomAscii(69);

	//---Design Preference---//
	protected Material revealedTrapMaterial = Material.REDSTONE_WIRE;
	protected Material setTrapMaterial = Material.REDSTONE_WIRE;
	
	private final Material revealedTrapIndicatorMaterial = Material.HARD_CLAY;
	protected byte clayStoneMetaDataValue = 0;

	//---Initialized in Constructor---//
	private AoE areaOfEffect;
	private Team team;

	//-----Constructor-----//
	public TemplateTrap(Player player, double radius) {
		//Block positions
		Block trapBlock = player.getLocation().getBlock(); 
		Block indicatorBlock = trapBlock.getRelative(BlockFace.DOWN).getState().getBlock();


		//States for block positions
		this.trapState = trapBlock.getState();
		this.trapIndicatorState = indicatorBlock.getState();

		//Create new AoE
		this.areaOfEffect = new AoE(player.getLocation(), radius, this);
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
		if (this.trapState.hasMetadata(RANDOM_PASS_KEY)) {
			throw new NoSuchMechanismException("THINGS ARE HAPPENING THAT SHOULDN'T BE HAPPENING.... (traps too close)");
		}
		this.reveal();
		this.setActivatable();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void reveal() {
		this.trapState.getBlock().setType(revealedTrapMaterial);
		this.trapIndicatorState.getBlock().setType(revealedTrapIndicatorMaterial);
		this.trapIndicatorState.getBlock().setData(this.clayStoneMetaDataValue);
	}	

	@Override
	public void hide() {
		this.trapState.update(true);
		this.trapIndicatorState.update(true);
		this.areaOfEffect.setDeactive();
	}

	//---Trap Methods---//
	/* When a trap is set, the adjacent blocks are colored in, the trap is
	 * no longer activatable, and the trap starts using the AoE class
	 * to check for members of the opposing team who would trigger it.  */
	@Override
	public void set() {
		this.trapState.getBlock().setType(setTrapMaterial);
		this.setDeactivateable();
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

	private void setActivatable() {
		this.trapState.setMetadata(RANDOM_PASS_KEY, new FixedMetadataValue(Codari.INSTANCE, this));
		this.trapState.setMetadata(META_DATA_STRING, new FixedMetadataValue(Codari.INSTANCE, true));
	}	

	private void setDeactivateable() {
		this.trapState.removeMetadata(RANDOM_PASS_KEY, Codari.INSTANCE);
		this.trapState.removeMetadata(META_DATA_STRING, Codari.INSTANCE);
	}
}
