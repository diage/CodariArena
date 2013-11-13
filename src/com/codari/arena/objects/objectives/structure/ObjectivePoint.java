package com.codari.arena.objects.objectives.structure;


import com.codari.arena.combatants.teams.Team;
import com.codari.arena.objects.FixedSpawnableObject;

public interface ObjectivePoint extends FixedSpawnableObject {
	public void setTeam(Team team);
	public Team getTeam();
}
