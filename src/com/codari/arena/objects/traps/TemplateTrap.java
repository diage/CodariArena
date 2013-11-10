package com.codari.arena.objects.traps;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.codari.api.Codari;
import com.codari.arena.util.AoE;
import com.codari.arena.util.TeamColor;

public abstract class TemplateTrap implements Trap {
	//-----Fields-----//
	//---Block Configuration---//
	private final BlockState[] trapStates = new BlockState[5];
	public final String META_DATA_STRING;
	
	//---Design Preference---//
	protected Material revealedBlockMaterial = Material.SANDSTONE;
	protected Material setBlockMaterial = Material.WOOL;
	
	//---Initialized in Constructor---//
	private TeamColor teamColor;
	private AoE areaOfEffect;

	//-----Constructor-----//
	public TemplateTrap(Player player, double radius, String metaData) {
		//Block positions
		Block baseBlock = player.getLocation().getBlock();
		Block baseBlockNorth = baseBlock.getRelative(BlockFace.NORTH, 1);
		Block baseBlockSouth = baseBlock.getRelative(BlockFace.SOUTH, 1);
		Block baseBlockWest = baseBlock.getRelative(BlockFace.WEST, 1);
		Block baseBlockEast = baseBlock.getRelative(BlockFace.EAST, 1);
		
		//States for block positions
		this.trapStates[0] = baseBlock.getState();
		this.trapStates[1] = baseBlockNorth.getState();
		this.trapStates[2] = baseBlockSouth.getState();
		this.trapStates[3] = baseBlockWest.getState();
		this.trapStates[4] = baseBlockEast.getState();
		
		//Create new AoE
		this.areaOfEffect = new AoE(player.getLocation(), radius);
		
		//Setting metadata
		META_DATA_STRING = metaData;
	}
	
	//-----Getters-----//
	public BlockState[] getPadState() {
		return this.trapStates;
	}
	
	public TeamColor getTeamColor() {
		return this.teamColor;
	}		
		
	//-----Overridden Methods-----//
	//---RandomSpawnable Object Methods---//
	@Override
	public void spawn() {
		this.reveal();
		this.setActivatable();
	}
	
	@Override
	public void reveal() {
		trapStates[0].getBlock().setType(revealedBlockMaterial);
	}	

	@Override
	public void hide() {
		for(int i = 0; i < trapStates.length; i++) {
			trapStates[i].update(true);
		}
		this.areaOfEffect.setDeactive();
	}
	
	//---Trap Methods---//
	/* When a trap is set, the adjacent blocks are colored in, the trap is
	 * no longer activatable, and the trap starts using the AoE class
	 * to check for members of the opposing team who would trigger it.  */
	@Override
	public void set() {
		for(int i = 0; i < trapStates.length; i++) {
			trapStates[i].getBlock().setType(setBlockMaterial);
		}
		this.setDeactivateable();
		this.startAoE();
	}
	
	@Override 
	public void deactivate() {
		this.hide();
		this.stopAoE();
	}
	
	//-----Private Methods-----//
	private void startAoE() {
		this.areaOfEffect.setActive();
	}
	
	private void stopAoE() {
		this.areaOfEffect.setDeactive();
	}
	
	private void setActivatable() {
		this.trapStates[0].setMetadata(META_DATA_STRING, new FixedMetadataValue(Codari.INSTANCE, true));
	}	
	
	private void setDeactivateable() {
		this.trapStates[0].setMetadata(META_DATA_STRING, new FixedMetadataValue(Codari.INSTANCE, false));
	}
}
