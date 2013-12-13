package com.codari.arena.objects.objectives.structure;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.objects.RandomSpawnableObjectA;
import com.codari.arena.rules.WinCondition2v2;
import com.codari.arena.util.AoE;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.rules.wincondition.WinConditionTemplate;

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
		this.beaconState = this.beaconBlock.getState();
		for(int i = 0; i < beaconBaseStates.length; i++) {
			this.beaconBaseStates[i] = this.beaconBaseBlock[i].getState();
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
		Team team = Codari.getArenaManager().getTeam(Codari.getArenaManager().getCombatant(players.get(0)));	
		
		if(!(this.checkSameTeam(players))) {
			Bukkit.broadcastMessage("Players on different teams are trying to capture the capture point!");
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
	
	@Override
	public void awardPoints(int points) {
		if(this.team == null) {	//TODO
			Bukkit.broadcastMessage(ChatColor.RED + "Objective point is trying to assign points to a null team!");
			return;
		}
		Bukkit.broadcastMessage(this.team.getTeamName() + " is being awarded with " + points + " points!");
		Collection<WinConditionTemplate> winConditions = this.getTeam().getArena().getGameRule().getWinConditions();
		for(WinConditionTemplate winCondition : winConditions) {
			if(winCondition instanceof WinCondition2v2) {
				((WinCondition2v2) winCondition).incrementPoints(this.team.getArena(), this.team, points);
			}
		}
	}

	private boolean checkSameTeam(List<Player> players) {
		Team compareTeam, teamOfFirstPlayer;
		teamOfFirstPlayer = Codari.getArenaManager().getTeam(Codari.getArenaManager().getCombatant(players.get(0)));
		if(teamOfFirstPlayer == null) {
			throw new NullPointerException(ChatColor.RED + "Player: " + players.get(0).toString() + 
					" is trying to capture an objective point but is not part of any team!");
		}
		for(Player player : players) {
			compareTeam = Codari.getArenaManager().getTeam(Codari.getArenaManager().getCombatant(player));
			if(!(compareTeam.equals(teamOfFirstPlayer))) {	//TODO - correct equals needs to be implemented
				return false;
			}
		}
		return true;
	}

	private boolean incrementCapturePoint() {
		this.pointCounter++;
		if(this.pointCounter == this.numberOfPointsToCaptureObjectivePoint) {
			this.resetCapturePointProgress();
			this.awardObjective();
			this.hide();
			return true;
		}
		for(Combatant combatant : this.team.combatants()) {
			Bukkit.broadcastMessage("Setting progress bar capture point.");
			combatant.getPlayer().setExp((float)pointCounter / numberOfPointsToCaptureObjectivePoint);
		}
		return false;
	}

	private void resetCapturePointProgress() {
		if(this.team != null) {
			for(Combatant combatant : this.team.combatants()) {
				combatant.getPlayer().setExp(0);
			}
		}
		this.team = null;
	}
}
