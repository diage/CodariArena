package com.codari.arena.objects.objectives;

import org.bukkit.event.Listener;

import com.codari.arena.objects.FixedSpawnableObject;

public interface ObjectivePoint extends FixedSpawnableObject, Listener {
	public void set();
	public void activate();
}
