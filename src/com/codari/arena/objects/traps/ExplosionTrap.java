package com.codari.arena.objects.traps;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.codari.arena.objects.traps.structure.TemplateTrap;

public class ExplosionTrap extends TemplateTrap {
	//-----Fields-----//
	private float powerExplosion = 2.0f;

	public ExplosionTrap(Player player, double radius) {
		super(player, radius);
	}

	@Override
	public void trigger(List<Entity> targets) {
		for(Entity explosionTargets : targets) {
			double locationX = explosionTargets.getLocation().getX();
			double locationY = explosionTargets.getLocation().getY();
			double locationZ = explosionTargets.getLocation().getZ();
			explosionTargets.getWorld().createExplosion(locationX, locationY, locationZ, powerExplosion, false, false);
		} 
	}
}
