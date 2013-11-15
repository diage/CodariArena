package com.codari.arena.objects.traps;

import java.util.List;

import org.bukkit.entity.Player;

import com.codari.arena.objects.traps.structure.TemplateTrap;

public class FireTrap extends TemplateTrap {
	//-----Fields-----//
	private int numberOfFireTicks = 40;

	public FireTrap(Player player, double radius) {
		super(player, radius);
	}

	//-----Private Methods-----//
	/* Sets all targets on fire  */
	private void setTargetsOnFire(List<Player> players) {
		for(int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			player.setFireTicks(this.numberOfFireTicks);
		}		
	}

	@Override
	public void trigger(List<Player> players) {
		this.setTargetsOnFire(players);
	}
}
