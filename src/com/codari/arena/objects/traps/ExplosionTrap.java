package com.codari.arena.objects.traps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.codari.api5.annotations.ArenaObjectName;
import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.traps.structure.TemplateTrap;

@ArenaObjectName("Explosion_Trap")
public class ExplosionTrap extends TemplateTrap {
	//-----Fields-----//
	private final static int WEIGHT_OF_OBJECTIVE_POINT = 5;
	
	private float powerExplosion = 2.0f;
	

	public ExplosionTrap(Location location) {
		super(location, ArenaStatics.RADIUS);
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
	
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		super.readObject();
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}
}
