package com.codari.arena.objects.traps;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.codari.arena.objects.traps.structure.TemplateTrap;

public class ExplosionTrap extends TemplateTrap {
	//-----Fields-----//
	private float radiusExplosion = 2.0f;
	private Listener listener;

	public ExplosionTrap(Player player, double radius) {
		super(player, radius);
	}

	@Override
	public void trigger(List<Entity> targets) {
		for(Entity explosionTargets : targets) {
			 explosionTargets.getWorld().createExplosion(explosionTargets.getLocation(), radiusExplosion);
		} 
		
	}

	@Override
	public Listener getListener() {
		return this.listener;
	}

}
