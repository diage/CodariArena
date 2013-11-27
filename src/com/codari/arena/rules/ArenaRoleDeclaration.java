package com.codari.arena.rules;

import com.codari.api5.Codari;
import com.codari.arena.players.skills.DamageReductionSkill;
import com.codari.arena.players.skills.TeleportSkill;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.rules.roles.RoleDeclaration;

public class ArenaRoleDeclaration implements RoleDeclaration {
	public final static String MELEE = "Melee";
	public final static String RANGED = "Ranged";
	@Override
	public void initalizeRoles() {
		Role meleeRole = Codari.INSTANCE.getArenaManager().getNewRole(MELEE);
		Role rangedRole = Codari.INSTANCE.getArenaManager().getNewRole(RANGED);
		
		meleeRole.addSkill(new DamageReductionSkill());
		rangedRole.addSkill(new TeleportSkill());
		
		Codari.INSTANCE.getArenaManager().submitRole(meleeRole);
		Codari.INSTANCE.getArenaManager().submitRole(rangedRole);
	}
}
