package com.codari.arena.objects.objectives.structure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
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
	private static final long serialVersionUID = -5747948050563169564L;
	//-----Fields-----//
	//---Block Configuration---//
	private int numberOfBaseBeaconBlocks = 9;
	protected transient BlockState[] beaconBaseStates = new BlockState[numberOfBaseBeaconBlocks];
	private transient BlockState beaconState;
	private transient BlockState[] glassBeam;
	private SerializableBlock serialBeaconBase;

	//---Beacon Materials---//
	private Material beaconMaterial = Material.BEACON;
	protected Material beaconBaseMaterial = Material.IRON_BLOCK;

	private boolean isSpawned;
	private transient Team team;
	private int teamSize = 2;

	//---Initialized in Constructor---//
	private AoE areaOfEffect;
	private int pointCounter = 0;
	protected int numberOfPointsToCaptureObjectivePoint = 100;

	public TemplateObjectivePoint(Player player, double radius) {
		//Block positions
		this.beaconState = player.getLocation().getBlock().getState();
		this.beaconBaseStates[0] = beaconState.getBlock().getRelative(BlockFace.DOWN).getState();
		this.beaconBaseStates[1] = beaconBaseStates[0].getBlock().getRelative(BlockFace.NORTH_WEST).getState();
		this.beaconBaseStates[2] = beaconBaseStates[0].getBlock().getRelative(BlockFace.NORTH).getState();
		this.beaconBaseStates[3] = beaconBaseStates[0].getBlock().getRelative(BlockFace.NORTH_EAST).getState();
		this.beaconBaseStates[4] = beaconBaseStates[0].getBlock().getRelative(BlockFace.WEST).getState();
		this.beaconBaseStates[5] = beaconBaseStates[0].getBlock().getRelative(BlockFace.EAST).getState();
		this.beaconBaseStates[6] = beaconBaseStates[0].getBlock().getRelative(BlockFace.SOUTH_WEST).getState();
		this.beaconBaseStates[7] = beaconBaseStates[0].getBlock().getRelative(BlockFace.SOUTH).getState();
		this.beaconBaseStates[8] = beaconBaseStates[0].getBlock().getRelative(BlockFace.SOUTH_EAST).getState();
		
		int glassHeight = this.beaconState.getWorld().getMaxHeight() - this.beaconState.getY();
		this.glassBeam = new BlockState[glassHeight];
		for (int i = 0; i < this.glassBeam.length; i++) {
			this.glassBeam[i] = this.beaconState.getBlock().getRelative(BlockFace.UP, 41).getState();
		}

		this.serialBeaconBase = new SerializableBlock(this.beaconBaseStates[0]);
		
		this.areaOfEffect = new AoE(player.getLocation(), radius, this);
		
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		World world = Bukkit.getWorld(this.serialBeaconBase.worldName);
		if (world == null) {
			throw new IllegalStateException("World named " + this.serialBeaconBase.worldName + " is not loaded");
		}
		
		this.beaconBaseStates = new BlockState[9];
		
		this.beaconBaseStates[0] = world.getBlockAt(this.serialBeaconBase.x, this.serialBeaconBase.y, this.serialBeaconBase.z).getState();
		this.beaconState = this.beaconBaseStates[0].getBlock().getRelative(BlockFace.UP).getState();
		this.beaconBaseStates[1] = beaconBaseStates[0].getBlock().getRelative(BlockFace.NORTH_WEST).getState();
		this.beaconBaseStates[2] = beaconBaseStates[0].getBlock().getRelative(BlockFace.NORTH).getState();
		this.beaconBaseStates[3] = beaconBaseStates[0].getBlock().getRelative(BlockFace.NORTH_EAST).getState();
		this.beaconBaseStates[4] = beaconBaseStates[0].getBlock().getRelative(BlockFace.WEST).getState();
		this.beaconBaseStates[5] = beaconBaseStates[0].getBlock().getRelative(BlockFace.EAST).getState();
		this.beaconBaseStates[6] = beaconBaseStates[0].getBlock().getRelative(BlockFace.SOUTH_WEST).getState();
		this.beaconBaseStates[7] = beaconBaseStates[0].getBlock().getRelative(BlockFace.SOUTH).getState();
		this.beaconBaseStates[8] = beaconBaseStates[0].getBlock().getRelative(BlockFace.SOUTH_EAST).getState();
		
		int glassHeight = this.beaconState.getWorld().getMaxHeight() - this.beaconState.getY();
		this.glassBeam = new BlockState[glassHeight];
		for (int i = 0; i < this.glassBeam.length; i++) {
			this.glassBeam[i] = this.beaconState.getBlock().getRelative(BlockFace.UP, 41).getState();
		}
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
		this.isSpawned = true;
		this.reveal();
		this.areaOfEffect.setActive();
	}

	@Override
	public void reveal() {
		this.beaconState.getBlock().setType(beaconMaterial);
		for (BlockState state : this.beaconBaseStates) {
			state.getBlock().setType(beaconBaseMaterial);
		}
		for (BlockState state : this.glassBeam) {
			if (state.getType() != Material.AIR) {
				state.getBlock().setType(Material.GLASS);
			}
		}
	}

	@Override
	public void hide() {
		this.beaconState.update(true);
		for (BlockState states : this.beaconBaseStates) {
			states.update(true);
		}
		for (BlockState state : this.glassBeam) {
			state.update(true);
		}
		this.areaOfEffect.setDeactive();	
		this.resetCapturePointProgress();
		this.isSpawned = false;
	}	
	
	@Override
	public boolean isSpawned() {
		return this.isSpawned;
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
		if(this.pointCounter >= this.numberOfPointsToCaptureObjectivePoint) {
			this.awardObjective();
			this.resetCapturePointProgress();
			this.hide();
			return true;
		}
		for(Combatant combatant : this.team.combatants()) {
			combatant.getPlayer().setExp((float)pointCounter / numberOfPointsToCaptureObjectivePoint);
		}
		return false;
	}

	private void resetCapturePointProgress() {
		if(this.team != null) {
			for(Player player : this.team.getPlayers()) {
				player.setExp(0);
			}
		}
		this.team = null;
	}
	
	private class SerializableBlock implements Serializable {
		private static final long serialVersionUID = 2328738181942000204L;
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
