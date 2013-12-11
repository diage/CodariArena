package com.codari.arena.objects.traps;

import java.util.List;

import org.bukkit.entity.Player;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.traps.structure.TemplateTrap;
import com.codari.arena5.objects.ArenaObjectName;

@ArenaObjectName("Fire_Trap")
public class FireTrap extends TemplateTrap {
	//-----Fields-----//
	private final int WEIGHT_OF_OBJECTIVE_POINT = 5;
	
	private int numberOfFireTicks = 40;

	public FireTrap(Player player) {
		super(player, ArenaStatics.RADIUS);
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
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
