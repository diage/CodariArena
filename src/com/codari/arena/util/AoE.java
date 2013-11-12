package com.codari.arena.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;

import com.codari.api.Codari;
import com.codari.arena.objects.traps.Trap;

/* This class will be used to determine nearby entities around a specified location. */

public class AoE {
	//-----Fields-----//
	private List<Entity> nearbyEntities;
	private Location location;
	private double radius;
	private boolean active;
	private Trap trap;
	
	public AoE(Location location, double radius, Trap trap) {
		this.location = location;
		this.radius = radius;
		this.trap = trap;
	}
	
	public void setActive() {
		this.active = true;
		this.run();
	}
	
	public void setDeactive() {
		this.active = false;
	}	
	
	private void calculate(double radius) {
		World world = this.location.getWorld();
		Slime anchor = world.spawn(this.location, Slime.class);
		anchor.setSize(0);
		this.nearbyEntities = anchor.getNearbyEntities(this.radius, this.radius, this.radius);
		anchor.remove();
	}
	
	private void run() {
		BukkitRunnable runner = new BukkitRunnable() {
			@Override
			public void run() {
				calculate(radius);
				if(nearbyEntities.size() > 0) {
					Bukkit.getPluginManager().callEvent(new AoeTriggerEvent(location, nearbyEntities, trap));
				}
				if(!active) {
					super.cancel();
				}
			}
		};
		
		runner.runTaskTimer(Codari.INSTANCE, 1, 1);
	}
}
