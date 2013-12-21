package com.codari.arena.rules;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.codari.arena5.arena.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.rules.wincondition.WinConditionTemplate;

public class WinCondition2v2 extends WinConditionTemplate {
	private static final long serialVersionUID = 1394895801767255729L;
	//-----Fields------//
	private final int NUMBER_OF_POINTS_TO_WIN;
	private Map<String, TeamPoint> teamPoints;
	private boolean isInitialized = false;
	private transient Team winnerTeam;

	public WinCondition2v2(int numberOfPointsToWin) {
		this.NUMBER_OF_POINTS_TO_WIN = numberOfPointsToWin;
		this.teamPoints = new HashMap<String, TeamPoint>();
	}

	public void incrementPoints(Arena arena, Team team, int points) {
		if(isInitialized) {
			this.teamPoints.get(team.getTeamName()).incrementPoints(points);
			List<Player> players = team.getPlayers();
			for(Player player : players) {
				player.sendMessage("Your team is at " + teamPoints.get(team.getTeamName()).getPoints() + " points!");
			}
			if(this.teamPoints.get(team.getTeamName()).getPoints() >= NUMBER_OF_POINTS_TO_WIN) {
				this.conditionMet = true;
				this.winnerTeam = team;
				for(Player player: players) {
					player.sendMessage("Congratulations. You guys have won the match!");
				}
			}
		}
	}

	public void decrementPoints(Arena arena, Team team, int points) {
		if(isInitialized) {
			this.teamPoints.get(team.getTeamName()).decrementPoints(points);
			List<Player> players = team.getPlayers();
			for(Player player : players) {
				player.sendMessage("Your team is at " + teamPoints.get(team.getTeamName()).getPoints() + " points!");
			}
			if(this.teamPoints.get(team.getTeamName()).getPoints() < NUMBER_OF_POINTS_TO_WIN) {
				this.conditionMet = false;
				this.winnerTeam = null;
			}
		}
	}

	@Override
	public void initialize(Arena arena) {
		this.isInitialized = true;
		Map<String, Team> teams = arena.getTeams();

		for(String teamName : teams.keySet()) {
			this.teamPoints.put(teamName, new TeamPoint());
		}
	}

	@Override
	public Collection<Combatant> getWinners() {
		if(this.conditionMet) {
			return winnerTeam.combatants();
		}
		return null;
	}

	private class TeamPoint implements Serializable {
		private static final long serialVersionUID = -8564239362109356955L;
		private int points;

		public TeamPoint() {
			this.points = 0;
		}

		public int getPoints() {
			return this.points;
		}

		public void incrementPoints(int points) {
			this.points += points;
		}

		public void decrementPoints(int points) {
			this.points -= points;
		}
	}
}

