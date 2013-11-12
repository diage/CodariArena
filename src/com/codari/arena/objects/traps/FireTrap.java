package com.codari.arena.objects.traps;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.codari.arena.objects.traps.listeners.FireTrapListener;

public class FireTrap extends TemplateTrap {
	//-----Fields-----//
	private int numberOfFireTicks = 200;
	private Listener listener;

	public FireTrap(Player player, double radius) {
		super(player, radius, RandomStringUtils.randomAscii(25));
		this.listener = new FireTrapListener(this);
	}

	//-----Private Methods-----//
	/* Sets all targets on fire  */
	private void setTargetsOnFire(List<Entity> targets) {
		this.editList(targets);
		for(int i = 0; i < targets.size(); i++) {
			Player player = (Player)targets.get(i);
			player.setFireTicks(this.numberOfFireTicks);
		}		
	}
	
	
	/* Filters out all non-player entities within a list. */
	private void editList(List<Entity> entities) {
		Iterator<Entity> iterator = entities.iterator();
		while(iterator.hasNext()) {
			if(!(iterator.next() instanceof Player)) {
				iterator.remove();
			}
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
