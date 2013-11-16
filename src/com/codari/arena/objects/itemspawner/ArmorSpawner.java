package com.codari.arena.objects.itemspawner;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena.objects.itemspawner.structure.TemplateItemSpawner;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.RoleType;

public class ArmorSpawner extends TemplateItemSpawner {

	public ArmorSpawner(Player player) {
		super(player);
		super.itemSpawnerMaterial = Material.GOLD_BLOCK;
	}

	@Override
	public void spawnItem(Combatant combatant) {
		RoleType role = super.checkRole(combatant);
	}
	
	

}
