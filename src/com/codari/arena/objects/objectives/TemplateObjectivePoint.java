package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.codari.api.Codari;
import com.codari.arena.combatants.teams.TeamColor;
import com.codari.arena.util.AoE;

public abstract class TemplateObjectivePoint implements ObjectivePoint {
	//-----Fields-----//
	private final BlockState[] objectivePointStates = new BlockState[5];
	
	//---Initialized in Constructor---//
	private TeamColor teamColor;
	private AoE areaOfEffect;
	private double radius;
	
	//---Coder Preference---//
	private final String META_DATA_STRING = "SOME_STRING";
	private Material revealedBlockMaterial = Material.SANDSTONE;
	private Material setBlockMaterial = Material.WOOL;

	//-----Constructor-----//
	public TemplateObjectivePoint(Player player, TeamColor teamcolor, double radius) {
		//Block positions
		Block baseBlock = player.getLocation().getBlock();
		Block baseBlockNorth = baseBlock.getRelative(BlockFace.NORTH, 1);
		Block baseBlockSouth = baseBlock.getRelative(BlockFace.SOUTH, 1);
		Block baseBlockWest = baseBlock.getRelative(BlockFace.WEST, 1);
		Block baseBlockEast = baseBlock.getRelative(BlockFace.EAST, 1);
		
		//States for block positions
		this.objectivePointStates[0] = baseBlock.getState();
		this.objectivePointStates[1] = baseBlockNorth.getState();
		this.objectivePointStates[2] = baseBlockSouth.getState();
		this.objectivePointStates[3] = baseBlockWest.getState();
		this.objectivePointStates[4] = baseBlockEast.getState();
		
		//Set radius size
		this.radius = radius;
	}
	
	//-----Getters-----//
	public BlockState[] getPadState() {
		return this.objectivePointStates;
	}
	
	public TeamColor getTeamColor() {
		return this.teamColor;
	}		
		
	//-----Overridden Methods-----//
	@Override
	public void spawn() {
		this.reveal();
		this.setActivatable();
	}
	
	@Override
	public void reveal() {
		objectivePointStates[0].getBlock().setType(revealedBlockMaterial);
	}	

	@Override
	public void hide() {
		for(int i = 0; i < objectivePointStates.length; i++) {
			objectivePointStates[i].update(true);
		}
		this.areaOfEffect.setDeactive();
	}
	
	@Override
	public void set() {
		for(int i = 0; i < objectivePointStates.length; i++) {
			objectivePointStates[i].getBlock().setType(setBlockMaterial);
		}
		this.setDeactivateable();
		this.startAoE();
	}
	
	@Override 
	public void activate() {
		this.hide();
		this.stopAoE();
	}
	
	//-----Private Methods-----//
	private void startAoE() {
		this.areaOfEffect = new AoE(this.objectivePointStates[0].getLocation(), this.radius);
		this.areaOfEffect.setActive();
	}
	
	private void stopAoE() {
		this.areaOfEffect.setDeactive();
	}
	
	private void setActivatable() {
		this.objectivePointStates[0].setMetadata(META_DATA_STRING, new FixedMetadataValue(Codari.INSTANCE, true));
	}	
	
	private void setDeactivateable() {
		this.objectivePointStates[0].setMetadata(META_DATA_STRING, new FixedMetadataValue(Codari.INSTANCE, false));
	}
}
