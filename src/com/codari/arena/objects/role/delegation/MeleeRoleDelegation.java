package com.codari.arena.objects.role.delegation;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
import com.codari.arena5.objects.persistant.RoleSelectionObject;
import com.codari.arena5.players.role.Role;

public class MeleeRoleDelegation extends TemplateRoleDelegation implements RoleSelectionObject {
	private Material meleeRoleDelegationMaterial = Material.GOLD_BLOCK;
	private Role meleeRole = Codari.getArenaManager().getExistingRole(null, ArenaStatics.MELEE);
	
	public MeleeRoleDelegation(Player player) {
		super(player);
		super.material = this.meleeRoleDelegationMaterial;
		super.role = this.meleeRole;
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reveal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Role roleSelect() {
		// TODO Auto-generated method stub
		return null;
	}
}
