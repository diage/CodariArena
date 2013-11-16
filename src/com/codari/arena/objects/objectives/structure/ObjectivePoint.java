package com.codari.arena.objects.objectives.structure;


import java.util.List;

import org.bukkit.entity.Player;

import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.teams.Team;

public interface ObjectivePoint extends RandomSpawnableObject {
	public void combatantOn(List<Player> players);
	public void combatantOff();
	public void setTeam(Team team);
	public Team getTeam();
	public void awardObjective();
}
