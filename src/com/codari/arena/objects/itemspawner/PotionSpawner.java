package com.codari.arena.objects.itemspawner;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena.objects.itemspawner.structure.TemplateItemSpawner;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.RoleType;

public class PotionSpawner extends TemplateItemSpawner {

	public PotionSpawner(Player player) {
		super(player);
		super.itemSpawnerMaterial = Material.EMERALD_BLOCK;
	}

	@Override
	public void spawnItem(Combatant combatant) {
		// TODO Auto-generated method stub
		
	}

}
