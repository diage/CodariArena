package com.codari.arena.objects.traps.structure;

import java.util.List;

import org.bukkit.entity.Entity;

import com.codari.arena.combatants.teams.Team;
import com.codari.arena.objects.RandomSpawnableObject;

public interface Trap extends RandomSpawnableObject {
	public void set();
	public void deactivate();
	public void trigger(List<Entity> targets);
	public void setTeam(Team team);
	public Team getTeam();	
}
