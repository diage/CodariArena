package com.codari.arena.objects.traps.structure;

import java.util.List;

import org.bukkit.entity.Player;

import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.teams.Team;

public interface Trap extends RandomSpawnableObject {
	public void set();
	public void deactivate();
	public void trigger(List<Player> players);
	public void setTeam(Team team);
	public Team getTeam();	
}
