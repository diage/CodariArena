package com.codari.arena.players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import com.codari.api5.Codari;
import com.codari.api5.util.BukkitUtils;
import com.codari.api5.util.scheduler.BukkitTime;
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
	
	private void activateRoleSwitch(HotbarSelectEvent e) {
		Player player, teamMatePlayer;
		Combatant combatant, teamMate;

		player = e.getCombatant().getPlayer();
		combatant = Codari.getArenaManager().getCombatant(player);
		teamMate = combatant.getTeam().getTeamMates(combatant).get(0);

		if(teamMate != null) {
			ItemStack playerRoleSwitchItem, teamMateRoleSwitchItem;

			Enchantment enchantment = Enchantment.SILK_TOUCH;
			
			int sloter = e.getOption().getInventorySlot();

			teamMatePlayer = teamMate.getPlayer();
			playerRoleSwitchItem = e.getItem();
			teamMateRoleSwitchItem = teamMatePlayer.getInventory().getItem(sloter);

			if(playerRoleSwitchItem.containsEnchantment(enchantment)) {
				Bukkit.broadcastMessage("Accepted role switch!");
				teamMate.swapRole(combatant.swapRole(teamMate.getRole())); 
				if(teamMate.getRole().getName().equalsIgnoreCase("Melee")) {
					teamMate.getPlayer().getInventory().setItem(sloter, RoleObjectItemTypes.MELEE.getItemStack());
					player.getInventory().setItem(sloter, RoleObjectItemTypes.RANGED.getItemStack());
				} else {
					teamMate.getPlayer().getInventory().setItem(sloter, RoleObjectItemTypes.RANGED.getItemStack());
					player.getInventory().setItem(sloter, RoleObjectItemTypes.MELEE.getItemStack());
				}
			} else {
				String roleSwitchMessage = "Your teamate would like to switch roles with you. Right click the role switch icon on the "
						+ "first slot of your hotbar if you would like to switch.";
				teamMateRoleSwitchItem.addUnsafeEnchantment(enchantment, 1);
				Bukkit.broadcastMessage("Added enchantment to item!");
				teamMate.getPlayer().sendMessage(roleSwitchMessage);
			}
		} else {
			player.sendMessage(ChatColor.RED + "You have no teamate to switch roles with.");
		}
	}
}