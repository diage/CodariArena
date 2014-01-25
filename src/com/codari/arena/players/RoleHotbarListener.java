package com.codari.arena.players;

import java.util.HashMap;
import java.util.Map;

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
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;

public class RoleHotbarListener implements Listener {
	private final static long GLOBAL_COOLDOWN = BukkitTime.SECOND.tickValueOf(1);
	private final Enchantment enchantment = Enchantment.SILK_TOUCH;
	private final Map<String, String> approveRoleSwitch;

	public RoleHotbarListener() {
		this.approveRoleSwitch = new HashMap<>();
	}

	@EventHandler
	private void hotbardom(HotbarSelectEvent e) {
		if (e.getCombatant().inArena()) {
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
	}

	@SuppressWarnings("deprecation")
	private void activateRoleSwitch(HotbarSelectEvent e) {
		Player player, teamMatePlayer;
		Combatant combatant, teamMateCombatant;
		int slotNumber = e.getOption().getInventorySlot();

		combatant = e.getCombatant();
		teamMateCombatant = combatant.getTeam().getTeamMates(combatant).get(0);
		if(teamMateCombatant != null) {
			player = e.getCombatant().getPlayer();
			teamMatePlayer = teamMateCombatant.getPlayer();
		} else {
			Bukkit.broadcastMessage(ChatColor.DARK_RED + "INVALID TEAMS!");
			return;
		}

		String teamName = combatant.getTeam().getTeamName();
		if(this.approveRoleSwitch.containsKey(teamName)) {
			if(player.getName() != this.approveRoleSwitch.get(teamName)) {
				teamMateCombatant.swapRole(combatant.swapRole(teamMateCombatant.getRole())); 
				this.approveRoleSwitch.remove(teamName);
			} else {
				player.sendMessage(ChatColor.AQUA + "You have already requested a switch.");
			}
		} else {
			ItemStack teamMateRoleSwitchItem;
			teamMateRoleSwitchItem = teamMatePlayer.getInventory().getItem(slotNumber);
			teamMateRoleSwitchItem.addUnsafeEnchantment(this.enchantment, 1);

			this.approveRoleSwitch.put(teamName, player.getName());
			teamMatePlayer.sendMessage(ChatColor.AQUA + "Your teammate is requesting a role switch.");
		}
		teamMatePlayer.updateInventory();
		player.updateInventory();
	}
}