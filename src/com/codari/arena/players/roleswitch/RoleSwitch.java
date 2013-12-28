package com.codari.arena.players.roleswitch;

import org.bukkit.inventory.ItemStack;

import com.codari.arena.ArenaStatics;
import com.codari.arena5.players.combatants.Combatant;

public class RoleSwitch {
	public static final int INVENTORY_SLOT_NUMBER = 0;
	public static final String ROLE_SWAP_DISPLAY_NAME = "Switch role";
	
	@SuppressWarnings("deprecation")
	public static void createRoleSwitchObject(Combatant combatant) {
		ItemStack roleSwitchObject;
		if(combatant.getRole().getName().equalsIgnoreCase(ArenaStatics.MELEE)) {
			roleSwitchObject = RoleObjectItemTypes.MELEE.getItemStack();
		} else {
			roleSwitchObject = RoleObjectItemTypes.RANGED.getItemStack();
		}
		combatant.getPlayer().getInventory().setItem(INVENTORY_SLOT_NUMBER, roleSwitchObject);
		combatant.getPlayer().updateInventory();
	}
}
