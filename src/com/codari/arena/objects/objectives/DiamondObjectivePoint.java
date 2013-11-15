package com.codari.arena.objects.objectives;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.players.teams.Team;

public class DiamondObjectivePoint extends TemplateObjectivePoint {

	public DiamondObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.DIAMOND_BLOCK;
	}

	@Override
	public void combatantOn(List<Player> players) {
		int teamCounter = 0;
		Team team = null;	
		/*
		 *check if the objective point team is null.
		 *if it is null, check players in the list to see if theyre all on the same team
		 *if yes, set team and increment
		 *if not, do nothing
		 *
		 * if it is not null, check to see if at least one player from the team is in the list
		 * if no player from the team is on the list, call combatantOff. 
		 */
		if(!(checkSameTeam(players))) {
			return;
		} else {
			team = Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(players.get(0)));
		}
		
		if(super.getTeam() == null) {
			super.setTeam(team);
			super.pointCounter++;
			super.setCapturePointProgress();
		} else {	//If the objective point is set to a certain team
			for(Player player : players) {
				if(Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(player)).equals(super.getTeam())) {
					teamCounter++;
				} else {
					super.combatantOff();
				}
			}
		}
		if(teamCounter == 2) {
			super.pointCounter++;
			super.setCapturePointProgress();
		}
	}

	private boolean checkSameTeam(List<Player> players) {
		boolean sameTeam = true;
		Team compareTeam;
		Team team = Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(players.get(0)));
		for(Player player : players) {
			compareTeam = Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(player));
			if(team == null) {
				throw new NullPointerException("Player: " + 
								Codari.INSTANCE.getArenaManager().getCombatant(players.get(0)).toString() + 
								" is trying to capture an objective point but is not part of any team!");
			} else {
				if(!(compareTeam.equals(team))) {
					sameTeam = false;
				}
			}
		}	
		return sameTeam;
	}
}
