package com.codari.arena.objects.itemchooser;

import org.bukkit.inventory.ItemStack;

import com.codari.arena5.players.role.Role;

public interface ItemChooser {
	public ItemStack generateItem(Role role);
};
