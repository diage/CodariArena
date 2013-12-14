package com.codari.arena.objects;

import com.codari.arena5.objects.spawnable.RandomSpawnableObject;

public abstract class RandomSpawnableObjectA implements RandomSpawnableObject {
	private static final long serialVersionUID = 8992108532551692962L;
	protected int weight = 10;
	
	@Override
	public int getWeight() {
		return this.weight;
	}
}
