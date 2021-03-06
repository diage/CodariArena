package com.codari.arena.objects;

import org.bukkit.Location;

import com.codari.api5.annotations.ArenaObjectName;
import com.codari.arena5.assets.RandomSpawnableAsset;

public abstract class RandomSpawnableObjectA extends RandomSpawnableAsset {
	protected int weight = 10;
	private final Location location;
	private final String name;
	
	public RandomSpawnableObjectA(Location location) {
		this.location = location;
		ArenaObjectName objectName = this.getClass().getAnnotation(ArenaObjectName.class);
		this.name = objectName.value();
	}
	
	@Override
	public int getWeight() {
		return this.weight;
	}
	
	@Override
	public final String getName() {
		return this.name;
	}
	
	@Override
	public final Location getLocation() {
		return this.location;
	}
}
