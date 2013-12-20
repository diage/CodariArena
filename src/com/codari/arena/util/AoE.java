package com.codari.arena.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;

import com.codari.api5.CodariI;
import com.codari.arena5.objects.ArenaObject;

/* This class will be used to determine nearby entities around a specified location. */

public class AoE implements Serializable{
	private static final long serialVersionUID = -5025568447102872535L;
	//-----Fields-----//
	private transient List<Entity> nearbyEntities;
	private transient Location location;
	private double radius;
	private boolean active;
	private ArenaObject arenaObject;
	private SerializableLocation serialLocation;
	
	public AoE(Location location, double radius, ArenaObject arenaObject) {
		this.location = location;
		this.radius = radius;
		this.arenaObject = arenaObject;
		this.nearbyEntities = new ArrayList<>();
		
		this.serialLocation = new SerializableLocation(this.location);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		World world = Bukkit.getWorld(this.serialLocation.worldName);
		if (world == null) {
			throw new IllegalStateException("World named " + this.serialLocation.worldName + " is not loaded");
		}
		this.location = world.getBlockAt(this.serialLocation.x, this.serialLocation.y, this.serialLocation.z).getLocation();
	}
	
	public void setActive() {
		this.active = true;
		this.run();
	}
	
	public void setDeactive() {
		Bukkit.broadcastMessage("AoE in the " + arenaObject.getClass().getSimpleName() + " is being turned off.");
		this.active = false;
	}	
	
	private List<Entity> calculate(double radius) {
		World world = this.location.getWorld();
		Slime anchor = world.spawn(this.location, Slime.class);
		anchor.setSize(0);
		this.nearbyEntities = anchor.getNearbyEntities(this.radius, this.radius, this.radius);
		anchor.remove();
		return this.nearbyEntities;
	}
	
	private void run() {
		BukkitRunnable runner = new BukkitRunnable() {
			@Override
			public void run() {
				List<Entity> nearby;
				nearby = calculate(radius);
				if(nearby.size() > 0) {
					Bukkit.getPluginManager().callEvent(new AoeTriggerEvent(location, nearby, arenaObject));
				}
				if(!active) {
					super.cancel();
				}
			}
		};
		
		runner.runTaskTimer(CodariI.INSTANCE, 1, 1);
	}
	
	private class SerializableLocation implements Serializable {
		private static final long serialVersionUID = 3405908171694915925L;
		private int x, y, z;
		private String worldName;
		
		public SerializableLocation(Location location) {
			this.worldName = location.getWorld().getName();
			this.x = location.getBlock().getX();
			this.y = location.getBlock().getY();
			this.z = location.getBlock().getZ();
		}
	}
}
