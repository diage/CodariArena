package com.codari.arena.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

import com.codari.api5.CodariI;
import com.codari.arena.ArenaStatics;
import com.codari.arena.players.roleswitch.RoleObjectItemTypes;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.objects.spawnable.ListenerFixedSpawnableObject;
import com.codari.arena5.players.role.RoleSelectEvent;

@ArenaObjectName(value = "Role Switch Listener (2v2)", links = {ArenaStatics.MELEE, ArenaStatics.RANGED})
public class RoleSwitchListenerObject implements ListenerFixedSpawnableObject {
	private static final long serialVersionUID = -1837467493766979324L;

	private Location location;
	private transient BlockState glassBlockState;
	private Material glassMaterial = Material.GLASS;
	private String name, arenaName;

	public RoleSwitchListenerObject(Location location) {
		this.location = location;
		this.glassBlockState = location.getBlock().getState();
		ArenaObjectName objectName = this.getClass().getAnnotation(ArenaObjectName.class);
		this.name = objectName.value();
	}

	@SuppressWarnings("deprecation")
	@EventHandler()
	private void playerSwapEvent(RoleSelectEvent e) {
		
		Bukkit.broadcastMessage(ChatColor.GREEN + "Role Select Event heard!");	//TODO
		if(e.wasSwap()) {
			e.getCombatant().getPlayer().sendMessage(ChatColor.GREEN + "Your role is now " + ChatColor.DARK_GREEN + e.getNewRole().getName());
		} 
		if(e.getNewRole().getLink(this.name).equalsIgnoreCase(ArenaStatics.MELEE)) {	
			e.getCombatant().getPlayer().getInventory().setItem(0, RoleObjectItemTypes.MELEE.getItemStack());
			e.getCombatant().getPlayer().updateInventory();
		} else if(e.getNewRole().getLink(this.name).equals(ArenaStatics.RANGED)) {	
			e.getCombatant().getPlayer().getInventory().setItem(0, RoleObjectItemTypes.RANGED.getItemStack());
			e.getCombatant().getPlayer().updateInventory();
		}
		
	}
	
	@Override
	public void spawn() {
		if(this.arenaName != null) {
			Bukkit.getPluginManager().registerEvents(this, CodariI.INSTANCE);
		}
	}

	@Override
	public void reveal() {
		this.glassBlockState.getBlock().setType(glassMaterial);
	}

	@Override
	public void hide() {
		this.glassBlockState.update(true);
		HandlerList.unregisterAll(this);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}


	public void setArenaName(String arenaName) {
		this.arenaName = arenaName;
	}
}
