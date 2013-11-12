package com.codari.arena.objects.traps;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;

import com.codari.arena.combatants.teams.Team;
import com.codari.arena.objects.RandomSpawnableObject;

public interface Trap extends RandomSpawnableObject {
	public void set();
	public void deactivate();
	public void trigger(List<Entity> targets);
	public Listener getListener();
	public void setTeam(Team team);
	public Team getTeam();	
}
