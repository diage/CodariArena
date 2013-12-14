package com.codari.arena.objects.itemspawner.structure;

import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.combatants.Combatant;

public interface ItemSpawner extends RandomSpawnableObject {
	public void addItem(Combatant combatant);
}
