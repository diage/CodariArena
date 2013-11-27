package com.codari.arena.objects.role.switchrole;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;

/*TODO:
 * How to cancel role swap(Left click or right click?)
 */
public class RoleSwitchListener implements Listener {
	@EventHandler()
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if((e.getItem().getItemMeta().getDisplayName().equals(RoleSwitch.ROLE_SWAP_DISPLAY_NAME))) {
				Player player, teamMatePlayer;
				Combatant combatant, teamMate;
				
				player = (Player) e.getPlayer();
				combatant = Codari.INSTANCE.getArenaManager().getCombatant(player);
				teamMate = combatant.getTeam().getTeamMates(combatant).get(0);

				if(teamMate != null) {
					ItemStack playerRoleSwitchItem, teamMateRoleSwitchItem;
					Role playerRole, teamMateRole;
					
					Enchantment enchantment = RoleObjectEnchantment.INSTANCE;
					
					teamMatePlayer = teamMate.getPlayerReference().getPlayer();
					playerRoleSwitchItem = player.getInventory().getItem(RoleSwitch.INVENTORY_SLOT_NUMBER);
					teamMateRoleSwitchItem = teamMatePlayer.getInventory().getItem(RoleSwitch.INVENTORY_SLOT_NUMBER);
					playerRole = combatant.getRole();
					teamMateRole = teamMate.getRole();
					
					if(playerRoleSwitchItem.containsEnchantment(enchantment)) {
						teamMateRole.swapRole(playerRole.swapRole(teamMateRole));
						if(teamMate.getRole().getName().equalsIgnoreCase("Melee")) {
							teamMate.getPlayerReference().getPlayer().getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.MELEE.getItemStack());
							player.getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.RANGED.getItemStack());
						} else {
							teamMate.getPlayerReference().getPlayer().getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.RANGED.getItemStack());
							player.getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.MELEE.getItemStack());
						}
					} else {
						String roleSwitchMessage = "Your teamate would like to switch roles with you. Right click the role switch icon on the "
								+ "first slot of your hotbar if you would like to switch.";
						teamMateRoleSwitchItem.addEnchantment(enchantment, 1);
						teamMate.getPlayerReference().getPlayer().sendMessage(roleSwitchMessage);
					}
				} else {
					player.sendMessage(ChatColor.RED + "You have no teamate to switch roles with.");
				}
			}
		}
	}
}