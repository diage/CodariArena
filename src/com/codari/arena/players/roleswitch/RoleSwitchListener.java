package com.codari.arena.players.roleswitch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
import com.codari.arena5.arena.events.ArenaStartEvent;
import com.codari.arena5.players.teams.Team;

public class RoleSwitchListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinArena(ArenaStartEvent e) {
		for(String teamName : e.getArena().getTeams().keySet()) {	//FIXME - fix the iteration through the keyset
			Team team = e.getArena().getTeams().get(teamName);	
			team.combatants().get(0).setRole(Codari.getArenaManager().getExistingRole(ArenaStatics.ARENA_NAME, ArenaStatics.MELEE));
			team.combatants().get(1).setRole(Codari.getArenaManager().getExistingRole(ArenaStatics.ARENA_NAME, ArenaStatics.RANGED));			
			for(Player player : team.getPlayers()) {			
				RoleSwitch.createRoleSwitchObject(Codari.getArenaManager().getCombatant(player));
			}
		}
	}
}

