package com.codari.arena.objects.role.delegation;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
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
		Player player, teamMatePlayer;
		Combatant teamMate = combatant.getTeam().getTeamMates(combatant).get(0);
		player = combatant.getPlayerReference().getPlayer();
		teamMatePlayer = teamMate.getPlayerReference().getPlayer();
		
		combatant.setRole(role);
		
		switch(role.getName()) {
		case ArenaStatics.MELEE:
			teamMate.setRole(Codari.INSTANCE.getArenaManager().getExistingRole(null, ArenaStatics.RANGED));
			player.sendMessage("You have been assigned the melee role.");
			teamMatePlayer.sendMessage("You have been assigned the ranged role.");
			break;
		case ArenaStatics.RANGED:
			teamMate.setRole(Codari.INSTANCE.getArenaManager().getExistingRole(null, ArenaStatics.MELEE));
			player.sendMessage("You have been assigned the ranged role.");
			teamMatePlayer.sendMessage("You have been assigned the melee role.");
			break;
		default:
			return;
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
