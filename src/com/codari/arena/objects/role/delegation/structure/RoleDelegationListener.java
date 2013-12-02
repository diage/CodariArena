package com.codari.arena.objects.role.delegation.structure;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.codari.api5.Codari;
import com.codari.arena5.objects.persistant.RoleSelectionObject;

public class RoleDelegationListener implements Listener {
	@EventHandler() 
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() instanceof RoleSelectionObject) {
			TemplateRoleDelegation templateRoleDelegationObject = (TemplateRoleDelegation) e.getClickedBlock();
			templateRoleDelegationObject.combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
			templateRoleDelegationObject.interact();
		}
	}
}
