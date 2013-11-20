package com.codari.arena.objects.traps;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.codari.arena.objects.traps.structure.TemplateTrap;
import com.codari.arena5.objects.ArenaObjectName;

@ArenaObjectName("Explosion Trap")
public class ExplosionTrap extends TemplateTrap {
	//-----Fields-----//
	private final int WEIGHT_OF_OBJECTIVE_POINT = 5;
	
	private float powerExplosion = 2.0f;
	

	public ExplosionTrap(Player player, double radius) {
		super(player, radius);
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void trigger(List<Player> players) {
		for(Entity explosionTargets : players) {
			double locationX = super.trapState.getLocation().getX();
			double locationY = super.trapState.getLocation().getY();
			double locationZ = super.trapState.getLocation().getZ();
			explosionTargets.getWorld().createExplosion(locationX, locationY, locationZ, powerExplosion, false, false);
		} 
	}
}
