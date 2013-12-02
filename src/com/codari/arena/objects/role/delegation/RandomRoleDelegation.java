package com.codari.arena.objects.role.delegation;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.role.delegation.structure.TemplateRoleDelegation;
import com.codari.arena5.objects.persistant.RoleSelectionObject;
import com.codari.arena5.players.role.Role;

public class RandomRoleDelegation extends TemplateRoleDelegation implements RoleSelectionObject {
	private Material randomRoleDelegationMaterial = Material.DIAMOND_BLOCK;

	public RandomRoleDelegation(Player player) {
		super(player);
		super.material = this.randomRoleDelegationMaterial;
	}
	
	private Role chooseRandomRole() {
		Role tempRole;
		Random random = new Random(System.currentTimeMillis());
		String arenaName = super.combatant.getArenaName();
		switch(random.nextInt(2)) {
		case 0:
			tempRole = Codari.getArenaManager().getExistingRole(arenaName, ArenaStatics.MELEE);
			break;
		case 1:
			tempRole = Codari.getArenaManager().getExistingRole(arenaName, ArenaStatics.RANGED);
			break;
		default:
			tempRole = Codari.getArenaManager().getExistingRole(arenaName, ArenaStatics.MELEE);
			break;
		}
		return tempRole;
	}

	@Override
	public Role roleSelect() {
		return this.chooseRandomRole();
	}
}
