package com.codari.arena.players.roleswitch;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.Codari;
import com.codari.arena5.ArenaStartEvent;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;

/*TODO:
 * How to cancel role swap(Left click or right click?)
 */
public class RoleSwitchListener implements Listener {
	@EventHandler()
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(e.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
				if((e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equals(RoleSwitch.ROLE_SWAP_DISPLAY_NAME))) {
					Player player, teamMatePlayer;
					Combatant combatant, teamMate;

					player = (Player) e.getPlayer();
					combatant = Codari.getArenaManager().getCombatant(player);
					teamMate = combatant.getTeam().getTeamMates(combatant).get(0);

					e.setCancelled(true);

					if(teamMate != null) {
						ItemStack playerRoleSwitchItem, teamMateRoleSwitchItem;

						//Enchantment enchantment = RoleObjectEnchantment.INSTANCE;
						Enchantment enchantment = Enchantment.KNOCKBACK;

						teamMatePlayer = teamMate.getPlayer();
						playerRoleSwitchItem = player.getInventory().getItem(RoleSwitch.INVENTORY_SLOT_NUMBER);
						teamMateRoleSwitchItem = teamMatePlayer.getInventory().getItem(RoleSwitch.INVENTORY_SLOT_NUMBER);

						if(playerRoleSwitchItem.containsEnchantment(enchantment)) {	//Problem here
							Bukkit.broadcastMessage("Accepted role switch!");
							teamMate.swapRole(combatant.swapRole(teamMate.getRole())); //Using this method will fire an event. 
							if(teamMate.getRole().getName().equalsIgnoreCase("Melee")) {
								teamMate.getPlayer().getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.MELEE.getItemStack());
								player.getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.RANGED.getItemStack());
							} else {
								teamMate.getPlayer().getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.RANGED.getItemStack());
								player.getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.MELEE.getItemStack());
							}
						} else {
							String roleSwitchMessage = "Your teamate would like to switch roles with you. Right click the role switch icon on the "
									+ "first slot of your hotbar if you would like to switch.";
							//teamMateRoleSwitchItem.addEnchantment(enchantment, 1);
							teamMateRoleSwitchItem.addUnsafeEnchantment(enchantment, 1);
							Bukkit.broadcastMessage("Added enchantment to item!");
							teamMate.getPlayer().sendMessage(roleSwitchMessage);
						}
					} else {
						player.sendMessage(ChatColor.RED + "You have no teamate to switch roles with.");
					}
				}
			}
		}
	}
	@EventHandler()
	public void onPlayerJoinArena(ArenaStartEvent e) {
		for(String teamName : e.getArena().getTeams().keySet()) {	//TODO
			Team team = e.getArena().getTeams().get(teamName);			
			for(Player player : team.getPlayers()) {			
				RoleSwitch.createRoleSwitchObject(Codari.getArenaManager().getCombatant(player));
			}
		}
	}
}
