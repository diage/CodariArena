package com.codari.arena.objects;

import com.codari.arena5.objects.spawnable.RandomSpawnableObject;

public abstract class RandomSpawnableObjectA implements RandomSpawnableObject {
	protected int weight = 10;
	
	@Override
	public int getWeight() {
		return this.weight;
	}
}
