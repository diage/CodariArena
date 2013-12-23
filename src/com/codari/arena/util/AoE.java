package com.codari.arena.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitTask;

import com.codari.api5.CodariI;
import com.codari.arena5.objects.ArenaObject;

/* This class will be used to determine nearby entities around a specified location. */

public class AoE {
	//-----Fields-----//
	private List<Entity> nearbyEntities;
	private Location location;
	private double radius;
	private BukkitTask task;
	private ArenaObject arenaObject;
	
	public AoE(Location location, double radius, ArenaObject arenaObject) {
		this.location = location;
		this.radius = radius;
		this.arenaObject = arenaObject;
		this.nearbyEntities = new ArrayList<>();
	}
	
	public void setActive() {
		if (this.task == null) {
			this.task = Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, new Runnable() {
				@Override
				public void run() {
					List<Entity> nearby;
					nearby = calculate(radius);
					if(nearby.size() > 0) {
						Bukkit.getPluginManager().callEvent(new AoeTriggerEvent(location, nearby, arenaObject));
					}
				}
			}, 1, 1);
		}
	}
	
	public void setDeactive() {
		if (this.task != null) {
			Bukkit.broadcastMessage("AoE in the " + arenaObject.getClass().getSimpleName() + " is being turned off.");
			this.task.cancel();
			this.task = null;
		}
	}	
	
	private List<Entity> calculate(double radius) {
		World world = this.location.getWorld();
		Slime anchor = world.spawn(this.location, Slime.class);
		anchor.setSize(0);
		this.nearbyEntities = anchor.getNearbyEntities(this.radius, this.radius, this.radius);
		anchor.remove();
		return this.nearbyEntities;
	}
}
