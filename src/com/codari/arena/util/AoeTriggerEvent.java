package com.codari.arena.util;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.codari.arena.objects.traps.Trap;

public class AoeTriggerEvent extends Event {
	//-----Static Fields-----//
	private static final HandlerList handlers = new HandlerList();
	
	//-----Static Methods-----//
	public static HandlerList getHandlerList() {
		return AoeTriggerEvent.handlers;
	}
	
	//-----Fields-----//
	private Location location;
	private List<Entity> entities;
	private Trap trap;
	
	public AoeTriggerEvent(Location location, List<Entity> entities, Trap trap) {
		this.location = location;
		this.entities = entities;
		this.trap = trap;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public List<Entity> getEntities() {
		return this.entities;
	}
	
	public Trap getTrap() {
		return this.trap;
	}	

	@Override
	public HandlerList getHandlers() {
		return AoeTriggerEvent.handlers;
	}	
}
