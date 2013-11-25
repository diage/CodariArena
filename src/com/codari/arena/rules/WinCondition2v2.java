package com.codari.arena.rules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.rules.WinConditionTemplate;

public class WinCondition2v2 extends WinConditionTemplate {
	//-----Fields------//
	private final int NUMBER_OF_POINTS_TO_WIN;
	private Map<String, TeamPoint> teamPoints;
	private boolean isInitialized = false;
	private Team winnerTeam;

	public WinCondition2v2(int numberOfPointsToWin) {
		this.NUMBER_OF_POINTS_TO_WIN = numberOfPointsToWin;
		this.teamPoints = new HashMap<String, TeamPoint>();
	}

	public void incrementPoints(Arena arena, Team team, int points) {
		if(isInitialized) {
			this.teamPoints.get(team.getTeamName()).incrementPoints(points);
			if(this.teamPoints.get(team.getTeamName()).getPoints() >= NUMBER_OF_POINTS_TO_WIN) {
				this.conditionMet = true;
				this.winnerTeam = team;
			}
		}
	}

	public void decrementPoints(Arena arena, Team team, int points) {
		if(isInitialized) {
			this.teamPoints.get(team.getTeamName()).decrementPoints(points);
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

	private class TeamPoint {
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

