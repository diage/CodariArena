package com.codari.arena.objects.role.delegation;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.codari.api5.Codari;
import com.codari.arena.rules.ArenaRoleDeclaration;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;

public abstract class TemplateRoleDelegation implements Listener {
	private Block roleDelegationBlock;
	protected Material material;
	protected Role role;

	public TemplateRoleDelegation(Player player) {
		this.roleDelegationBlock = player.getLocation().getBlock();
		this.roleDelegationBlock.setType(material);
	}
	
	public void assignRole(Combatant combatant, Role role) {
		combatant.setRole(role);
		Combatant teamate = combatant.getTeam().getTeamMates(combatant).get(0);
		switch(role.getName()) {
		case ArenaRoleDeclaration.MELEE:
			teamate.setRole(Codari.INSTANCE.getArenaManager().getExistingRole(ArenaRoleDeclaration.RANGED));
			break;
		case ArenaRoleDeclaration.RANGED:
			teamate.setRole(Codari.INSTANCE.getArenaManager().getExistingRole(ArenaRoleDeclaration.MELEE));
			break;
		default:
			teamate.setRole(Codari.INSTANCE.getArenaManager().getExistingRole(ArenaRoleDeclaration.RANGED));
			break;
		}
	}
	
	@EventHandler() 
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() instanceof TemplateRoleDelegation) {
			Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
			this.assignRole(combatant, role);
		}
	}
}
