package com.codari.arena.objects.role.delegation;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
import com.codari.arena5.objects.persistant.RoleSelectionObject;
import com.codari.arena5.players.role.Role;

public class RangedRoleDelegation extends TemplateRoleDelegation implements RoleSelectionObject {
	private Material rangedRoleDelegationMaterial = Material.EMERALD_BLOCK;
	private Role rangedRole = Codari.getArenaManager().getExistingRole(null, ArenaStatics.RANGED);
	
	public RangedRoleDelegation(Player player) {
		super(player);
		super.material = this.rangedRoleDelegationMaterial;
		super.role = this.rangedRole;
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
