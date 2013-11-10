package com.codari.arena.objects.traps;

import org.bukkit.event.Listener;

import com.codari.arena.objects.RandomSpawnableObject;

public interface Trap extends RandomSpawnableObject, Listener {
	public void set();
	public void deactivate();
}
