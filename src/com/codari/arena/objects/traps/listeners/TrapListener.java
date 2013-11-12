package com.codari.arena.objects.traps.listeners;

import org.bukkit.Bukkit;

import com.codari.api.Codari;

public class TrapListener {
	public TrapListener() {
		Bukkit.getPluginManager().registerEvents(new FireTrapListener(), Codari.INSTANCE);
	}
}
