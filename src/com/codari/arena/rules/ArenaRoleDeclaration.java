package com.codari.arena.rules;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
import com.codari.arena.players.skills.DamageReductionSkill;
import com.codari.arena.players.skills.TeleportSkill;
import com.codari.arena5.arena.rules.roledelegation.RoleDeclaration;
import com.codari.arena5.players.role.Role;

public class ArenaRoleDeclaration implements RoleDeclaration {
	private static final long serialVersionUID = 4136022499205468835L;

	@Override
	public String initalizeRoles() {
		Role meleeRole = Codari.getArenaManager().getNewRole(ArenaStatics.MELEE).addSkill(new DamageReductionSkill());
		Role rangedRole = Codari.getArenaManager().getNewRole(ArenaStatics.RANGED).addSkill(new TeleportSkill());
		
		Codari.getArenaManager().submitRole(ArenaStatics.ARENA_NAME, meleeRole);
		Codari.getArenaManager().submitRole(ArenaStatics.ARENA_NAME, rangedRole);
		
		return ArenaStatics.ARENA_NAME;
	}
}
