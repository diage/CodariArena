package com.codari.arena.util;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
	
	public AoeTriggerEvent(Location location, List<Entity> entities) {
		this.location = location;
		this.entities = entities;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public List<Entity> getEntities() {
		return this.entities;
	}

	@Override
	public HandlerList getHandlers() {
		return AoeTriggerEvent.handlers;
	}	
}
