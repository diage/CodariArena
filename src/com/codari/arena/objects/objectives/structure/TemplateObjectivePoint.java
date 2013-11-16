package com.codari.arena.objects.objectives.structure;

import java.util.List;

import javax.xml.crypto.NoSuchMechanismException;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.objects.RandomSpawnableObjectA;
import com.codari.arena.util.AoE;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;

public abstract class TemplateObjectivePoint extends RandomSpawnableObjectA implements ObjectivePoint{
	//-----Fields-----//
	//---Block Configuration---//
	private int numberOfBaseBeaconBlocks = 9;
	private Block[] beaconBaseBlock = new Block[numberOfBaseBeaconBlocks];
	protected BlockState[] beaconBaseStates = new BlockState[numberOfBaseBeaconBlocks];
	private Block beaconBlock;
	private BlockState beaconState;

	//---Beacon Materials---//
	private Material beaconMaterial = Material.BEACON;
	protected Material beaconBaseMaterial = Material.IRON_BLOCK; 

	public static final String RANDOM_PASS_KEY = RandomStringUtils.randomAscii(69);
	private Team team;
	private int teamSize = 2;

	//---Initialized in Constructor---//
	private AoE areaOfEffect;
	private int pointCounter = 0;
	protected int numberOfPointsToCaptureObjectivePoint = 100;

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

		areaOfEffect = new AoE(player.getLocation(), radius, this);
	}

	//-----Getters-----//
	public BlockState getBeaconState() {
		return this.beaconState;
	}

	public BlockState[] getTrapStateIndicator() {
		return this.beaconBaseStates;
	}

	//---Fixed Spawnable Object Methods---//
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
		this.areaOfEffect.setDeactive();	
		this.resetCapturePointProgress();
	}	

	//---Objective Point Methods---//
	@Override
	public void combatantOff() {
		this.pointCounter = 0;
		this.resetCapturePointProgress();
	}

	@Override
	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public Team getTeam() {
		return this.team;
	}

	@Override
	public void combatantOn(List<Player> players) {
		Team team = Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(players.get(0)));	
		
		if(!(this.checkSameTeam(players))) {
			return;
		}
		
		if(this.getTeam() == null || !this.getTeam().equals(team)) {
			this.combatantOff();
			this.setTeam(team);
		} else {
			if(players.size() == this.teamSize) {
				this.incrementCapturePoint();
			}	
		}
	}

	private boolean checkSameTeam(List<Player> players) {
		Team compareTeam;
		Team teamOfFirstPlayer = Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(players.get(0)));
		if(teamOfFirstPlayer == null) {
			throw new NullPointerException("Player: " + 
					Codari.INSTANCE.getArenaManager().getCombatant(players.get(0)).toString() + 
					" is trying to capture an objective point but is not part of any team!");
		}
		for(Player player : players) {
			compareTeam = Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(player));
			if(!(compareTeam.equals(teamOfFirstPlayer))) {
				return false;
			}
		}
		return true;
	}

	private boolean incrementCapturePoint() {
		this.pointCounter++;
		if(this.pointCounter == this.numberOfPointsToCaptureObjectivePoint) {
			this.awardObjective();
			this.hide();
			return true;
		}
		for(Combatant combatant : this.team.combatants()) {
			combatant.getPlayerReference().getPlayer().setExp((float)pointCounter / numberOfPointsToCaptureObjectivePoint);
		}
		return false;
	}

	private void resetCapturePointProgress() {
		if(this.team != null) {
			for(Combatant combatant : this.team.combatants()) {
				combatant.getPlayerReference().getPlayer().setExp(0);
			}
		}
		this.team = null;
	}
}
