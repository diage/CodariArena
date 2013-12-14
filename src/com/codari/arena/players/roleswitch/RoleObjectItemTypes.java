package com.codari.arena.players.roleswitch;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum RoleObjectItemTypes {
	MELEE(Material.WOOD_DOOR),
	RANGED(Material.ARROW);
	
	private ItemStack roleSwapObject;
	
	private RoleObjectItemTypes(Material material) {
		this.roleSwapObject = new ItemStack(material);
		ItemMeta itemMeta = this.roleSwapObject.getItemMeta();
		itemMeta.setDisplayName(RoleSwitch.ROLE_SWAP_DISPLAY_NAME);
		this.roleSwapObject.setItemMeta(itemMeta);
	}
	
	public ItemStack getItemStack() {
		return this.roleSwapObject;
	}
}

