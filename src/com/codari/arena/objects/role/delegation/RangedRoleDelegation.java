package com.codari.arena.objects.role.delegation;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.role.delegation.structure.TemplateRoleDelegation;
import com.codari.arena5.objects.persistant.RoleSelectionObject;
import com.codari.arena5.players.role.Role;

public class RangedRoleDelegation extends TemplateRoleDelegation implements RoleSelectionObject {
	private Material rangedRoleDelegationMaterial = Material.EMERALD_BLOCK;
	
	public RangedRoleDelegation(Player player) {
		super(player);
		super.material = this.rangedRoleDelegationMaterial;
	}

	@Override
	public Role roleSelect() {
		return Codari.getArenaManager().getExistingRole(super.combatant.getArenaName(), ArenaStatics.MELEE);
	}

}
