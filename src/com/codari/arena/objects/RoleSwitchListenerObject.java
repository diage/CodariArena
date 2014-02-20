package com.codari.arena.objects;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.CodariI;
import com.codari.arena.ArenaStatics;
import com.codari.arena.players.RoleHotbarListener;
import com.codari.arena.players.roleswitch.RoleObjectItemTypes;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.objects.spawnable.ListenerFixedSpawnableObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;
import com.codari.arena5.players.role.RoleSelectEvent;

@ArenaObjectName(value = "Role Switch Listener (2v2)", links = {ArenaStatics.MELEE, ArenaStatics.RANGED})
public class RoleSwitchListenerObject implements ListenerFixedSpawnableObject {
	private static final long serialVersionUID = -1837467493766979324L;
	private final Enchantment enchantment = Enchantment.SILK_TOUCH;
	private final Map<String, String> approveRoleSwitch;

	private Location location;
	private transient BlockState glassBlockState;
	private Material glassMaterial = Material.GLASS;
	private String name, arenaName;
	private boolean enabled;

	public RoleSwitchListenerObject(Location location) {
		this.location = location;
		this.glassBlockState = location.getBlock().getState();
		ArenaObjectName objectName = this.getClass().getAnnotation(ArenaObjectName.class);
		this.name = objectName.value();
		this.enabled = false;
		this.approveRoleSwitch = new HashMap<>();
	}

	@Override
	public void spawn() {
		if(this.arenaName != null && !this.enabled) {
			Bukkit.getPluginManager().registerEvents(this, CodariI.INSTANCE);
			this.enabled = true;
			Bukkit.broadcastMessage(ChatColor.AQUA + "Role Switch Object enabled!");	//TODO
		}
	}

	@Override
	public void reveal() {
		this.glassBlockState.getBlock().setType(glassMaterial);
	}

	@Override
	public void hide() {
		this.glassBlockState.update(true);
		if(this.enabled) {
			HandlerList.unregisterAll(this);
			this.enabled = false;
			Bukkit.broadcastMessage(ChatColor.BLUE + "Role Switch Object disabled!");	//TODO
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	@Override
	public void setArenaName(String arenaName) {
		this.arenaName = arenaName;
	}

	//---Role Switching---//
	@EventHandler()
	private void playerSwapEvent(RoleSelectEvent e) {	
		if(e.getCombatant().getArenaName() != null && e.getCombatant().getArenaName().equals(this.arenaName)) {
			if(e.wasSwap()) {
				e.getCombatant().getPlayer().sendMessage(ChatColor.GREEN + "Your role is now " + ChatColor.DARK_GREEN + e.getNewRole().getName());
			} 
			if(e.getNewRole().getLink(this.name).equalsIgnoreCase(ArenaStatics.MELEE)) {	
				this.updateMeleeIcon(e.getCombatant().getPlayer());
			} else if(e.getNewRole().getLink(this.name).equals(ArenaStatics.RANGED)) {	
				this.updateRangedIcon(e.getCombatant().getPlayer());
			}	
		}
	}	

	@EventHandler
	private void hotbardom(HotbarSelectEvent e) {
		if(e.getCombatant().inArena() && e.getCombatant().getArenaName().equals(this.arenaName)) {
			switch (e.getOption()) {
			case HOTBAR_1:
				this.activateRoleSwitch(e);
				break;
			default:
				return;
			}
			e.getCombatant().setHotbarCooldown(RoleHotbarListener.GLOBAL_COOLDOWN);	
		}
	}

	@SuppressWarnings("deprecation")
	private void activateRoleSwitch(HotbarSelectEvent e) {
		if(e.getCombatant().inArena() && e.getCombatant().getArenaName().equals(this.arenaName)) {
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
				if(teamMateRoleSwitchItem != null) {
					teamMateRoleSwitchItem.addUnsafeEnchantment(this.enchantment, 1);
				}

				this.approveRoleSwitch.put(teamName, player.getName());
				teamMatePlayer.sendMessage(ChatColor.AQUA + "Your teammate is requesting a role switch.");
			}
			teamMatePlayer.updateInventory();
			player.updateInventory();
		}
	}

	@SuppressWarnings("deprecation")
	private void updateMeleeIcon(Player player) {
		player.getInventory().setItem(0, RoleObjectItemTypes.MELEE.getItemStack());
		player.updateInventory();
	}

	@SuppressWarnings("deprecation")
	private void updateRangedIcon(Player player) {
		player.getInventory().setItem(0, RoleObjectItemTypes.RANGED.getItemStack());
		player.updateInventory();
	}
}
