package com.codari.arena.objects.objectives.structure;

import javax.xml.crypto.NoSuchMechanismException;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.codari.arena.combatants.teams.Team;

public abstract class TemplateObjectivePoint implements ObjectivePoint{
	//-----Fields-----//
	//---Block Configuration---//
	private int numberOfBaseBeaconBlocks = 9;
	private Block[] beaconBaseBlock = new Block[numberOfBaseBeaconBlocks];
	protected BlockState[] beaconBaseStates = new BlockState[numberOfBaseBeaconBlocks];
	private Block beaconBlock;
	private BlockState beaconState;
	
	//---Beacon Materials---//
	private Material beaconMaterial = Material.BEACON;
	protected Material beaconBaseMaterial = Material.EMERALD; //Can be gold, diamond, emerald, or iron

	public static final String RANDOM_PASS_KEY = RandomStringUtils.randomAscii(69);
	private Team team;

	//---Initialized in Constructor---//
	//private AoE areaOfEffect;

	public TemplateObjectivePoint(Player player, double radius) {
		//Block positions
		this.beaconBlock = player.getLocation().getBlock();
		this.beaconBaseBlock[0] = beaconBlock.getRelative(BlockFace.DOWN);
		this.beaconBaseBlock[1] = beaconBaseBlock[0].getRelative(BlockFace.NORTH_WEST);
		this.beaconBaseBlock[2] = beaconBaseBlock[0].getRelative(BlockFace.NORTH);
		this.beaconBaseBlock[3] = beaconBaseBlock[0].getRelative(BlockFace.NORTH_EAST);
		this.beaconBaseBlock[4] = beaconBaseBlock[0].getRelative(BlockFace.WEST);
		this.beaconBaseBlock[5] = beaconBaseBlock[0].getRelative(BlockFace.EAST);
		this.beaconBaseBlock[6] = beaconBaseBlock[0].getRelative(BlockFace.SOUTH_WEST);
		this.beaconBaseBlock[7] = beaconBaseBlock[0].getRelative(BlockFace.SOUTH);
		this.beaconBaseBlock[8] = beaconBaseBlock[0].getRelative(BlockFace.SOUTH_EAST);

		//States for block positions
		this.beaconState = beaconBlock.getState();
		for(int i = 0; i < beaconBaseStates.length; i++) {
			beaconBaseStates[i] = beaconBaseBlock[i].getState();
		}

		//areaOfEffect = new AoE(player.getLocation(), radius, this);
	}

	//-----Getters-----//
	public BlockState getBeaconState() {
		return this.beaconState;
	}

	public BlockState[] getTrapStateIndicator() {
		return this.beaconBaseStates;
	}

	@Override
	public void spawn() {		
		for(BlockState states : this.beaconBaseStates) {
			if(states.hasMetadata(RANDOM_PASS_KEY)) {
				throw new NoSuchMechanismException("The Objective Base Blocks are too close!");
			}
		}
		this.reveal();
	}

	@Override
	public void reveal() {
		this.beaconState.getBlock().setType(beaconMaterial);
		for(BlockState states : this.beaconBaseStates) {
			states.getBlock().setType(beaconBaseMaterial);
		}	
	}
	
	@Override
	public void hide() {
		this.beaconState.update(true);
		for(BlockState states : this.beaconBaseStates) {
			states.update(true);
		}
		//this.areaOfEffect.setDeactive();		
	}	

	@Override
	public void setTeam(Team team) {
		this.team = team;

	}

	@Override
	public Team getTeam() {
		return this.team;
	}

}
