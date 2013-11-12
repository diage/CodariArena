package com.codari.arena.objects.traps;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.codari.arena.objects.traps.structure.TemplateTrap;

public class FireTrap extends TemplateTrap {
	//-----Fields-----//
	private int numberOfFireTicks = 40;
	private Listener listener;

	public FireTrap(Player player, double radius) {
		super(player, radius);
	}

	//-----Private Methods-----//
	/* Sets all targets on fire  */
	private void setTargetsOnFire(List<Entity> targets) {
		for(int i = 0; i < targets.size(); i++) {
			Player player = (Player)targets.get(i);
			player.setFireTicks(this.numberOfFireTicks);
		}		
	}

	@Override
	public void trigger(List<Entity> targets) {
		this.setTargetsOnFire(targets);
	}

	@Override
	public Listener getListener() {
		return this.listener;
	}
}
