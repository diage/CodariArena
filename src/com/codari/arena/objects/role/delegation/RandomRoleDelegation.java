package com.codari.arena.objects.role.delegation;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.rules.ArenaRoleDeclaration;
import com.codari.arena5.players.role.Role;

public class RandomRoleDelegation extends TemplateRoleDelegation{
	private Material randomRoleDelegationMaterial = Material.GOLD_BLOCK;
	private Role randomRole = this.chooseRandomRole();

	public RandomRoleDelegation(Player player) {
		super(player);
		super.material = this.randomRoleDelegationMaterial;
		super.role = this.randomRole;
	}
	
	private Role chooseRandomRole() {
		Role tempRole;
		Random random = new Random(System.currentTimeMillis());
		switch(random.nextInt(2)) {
		case 0:
			tempRole = Codari.INSTANCE.getArenaManager().getExistingRole(ArenaRoleDeclaration.MELEE);
			break;
		case 1:
			tempRole = Codari.INSTANCE.getArenaManager().getExistingRole(ArenaRoleDeclaration.RANGED);
			break;
		default:
			tempRole = Codari.INSTANCE.getArenaManager().getExistingRole(ArenaRoleDeclaration.MELEE);
			break;
		}
		return tempRole;
	}

}
