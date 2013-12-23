package com.codari.arena.players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import com.codari.api5.util.BukkitUtils;
import com.codari.api5.util.scheduler.BukkitTime;
import com.codari.arena.ArenaStatics;
import com.codari.arena.players.roleswitch.RoleObjectItemTypes;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;

public class RoleHotbarListener implements Listener {
	
	private final static long GLOBAL_COOLDOWN = BukkitTime.SECOND.tickValueOf(1);
	
	@EventHandler
	private void hotbardom(HotbarSelectEvent e) {
		switch (e.getOption()) {
		case HOTBAR_1:
			this.activateRoleSwitch(e);
			break;
		case HOTBAR_2:
		case HOTBAR_3:
		case HOTBAR_4:
		case HOTBAR_5:
		case HOTBAR_6:
			if (e.getItem() != null) {
				Potion potion = Potion.fromItemStack(e.getItem());
				if (potion.isSplash()) {
					BukkitUtils.throwPotion(e.getCombatant().getPlayer(), e.getItem());
				} else {
					e.getCombatant().getPlayer().addPotionEffects(potion.getEffects());
				}
				e.getItem().setAmount(e.getItem().getAmount() - 1);
			}
			break;
		case HOTBAR_DROP:
			e.getCombatant().skill();
			break;
		}
		e.getCombatant().setHotbarCooldown(GLOBAL_COOLDOWN);
	}
	
	@SuppressWarnings("deprecation")
	private void activateRoleSwitch(HotbarSelectEvent e) {
		Player player, teamMatePlayer;
		Combatant combatant, teamMateCombatant;

		player = e.getCombatant().getPlayer();
		combatant = e.getCombatant();
		teamMateCombatant = combatant.getTeam().getTeamMates(combatant).get(0);

		if(teamMateCombatant != null) {
			ItemStack playerRoleSwitchItem, teamMateRoleSwitchItem;
			Enchantment enchantment = Enchantment.SILK_TOUCH;
			int sloter = e.getOption().getInventorySlot();
			Bukkit.broadcastMessage("Sloter is slot " + sloter); //TODO
			teamMatePlayer = teamMateCombatant.getPlayer();
			playerRoleSwitchItem = e.getItem();
			teamMateRoleSwitchItem = teamMatePlayer.getInventory().getItem(sloter);

			if(playerRoleSwitchItem.containsEnchantment(enchantment)) {
				teamMateCombatant.swapRole(combatant.swapRole(teamMateCombatant.getRole())); 
				if(teamMateCombatant.getRole().getName().equalsIgnoreCase(ArenaStatics.MELEE)) {
					teamMateCombatant.getPlayer().getInventory().setItem(sloter, RoleObjectItemTypes.MELEE.getItemStack());
					player.getInventory().setItem(sloter, RoleObjectItemTypes.RANGED.getItemStack());
				} else {
					teamMateCombatant.getPlayer().getInventory().setItem(sloter, RoleObjectItemTypes.RANGED.getItemStack());
					player.getInventory().setItem(sloter, RoleObjectItemTypes.MELEE.getItemStack());
				}
				teamMatePlayer.updateInventory();
				player.updateInventory();
				teamMatePlayer.sendMessage(ChatColor.GREEN + "Your role is now " + ChatColor.DARK_GREEN + teamMateCombatant.getRole().getName());
				player.sendMessage(ChatColor.GREEN + "Your role is now " + ChatColor.DARK_GREEN + combatant.getRole().getName());
			} else {
				teamMateRoleSwitchItem.addUnsafeEnchantment(enchantment, 1);
				teamMatePlayer.sendMessage(ChatColor.AQUA + "Your teammate is requesting a role switch.");
				teamMatePlayer.updateInventory();
			}
		} else {
			player.sendMessage(ChatColor.RED + "You have no teamate to switch roles with.");
		}
	}
}