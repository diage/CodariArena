package com.codari.arena.objects.role.delegation;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.rules.ArenaRoleDeclaration;
import com.codari.arena5.players.role.Role;

public class RangedRoleDelegation extends TemplateRoleDelegation {
	private Material rangedRoleDelegationMaterial = Material.GOLD_BLOCK;
	private Role rangedRole = Codari.INSTANCE.getArenaManager().getExistingRole(ArenaRoleDeclaration.RANGED);
	
	public RangedRoleDelegation(Player player) {
		super(player);
		super.material = this.rangedRoleDelegationMaterial;
		super.role = this.rangedRole;
	}

}
