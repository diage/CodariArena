package com.codari.arena.objects.role.switchrole;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.codari.arena5.players.combatants.Combatant;

public class RoleSwitch {
	public static final int INVENTORY_SLOT_NUMBER = 0;
	public static final String ROLE_SWAP_DISPLAY_NAME = "Switch role";
	
	public static void createRoleSwitchObject(Combatant combatant) {
		ItemStack roleSwitchObject;
		Player player = combatant.getPlayerReference().getPlayer();
		if(combatant.getRole().getName().equalsIgnoreCase("Melee")) {
			roleSwitchObject = RoleObjectItemTypes.MELEE.getItemStack();
		} else {
			roleSwitchObject = RoleObjectItemTypes.RANGED.getItemStack();
		}
		player.getInventory().setItem(INVENTORY_SLOT_NUMBER, roleSwitchObject);
	}
}
