package com.codari.arena.objects.role.delegation;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.role.delegation.structure.TemplateRoleDelegation;
import com.codari.arena5.objects.persistant.RoleSelectionObject;
import com.codari.arena5.players.role.Role;

public class MeleeRoleDelegation extends TemplateRoleDelegation implements RoleSelectionObject {
	private Material meleeRoleDelegationMaterial = Material.GOLD_BLOCK;
	
	public MeleeRoleDelegation(Player player) {
		super(player);
		super.material = this.meleeRoleDelegationMaterial;
	}
	
	@Override
	public Role roleSelect() {
		return Codari.getArenaManager().getExistingRole(super.combatant.getArenaName(), ArenaStatics.MELEE);
	}
}	
