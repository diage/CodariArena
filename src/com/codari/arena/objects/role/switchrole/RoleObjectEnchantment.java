package com.codari.arena.objects.role.switchrole;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class RoleObjectEnchantment extends Enchantment {
	//----Fields-----//
	public final static Enchantment INSTANCE = new RoleObjectEnchantment();
	
	private RoleObjectEnchantment() {
		super(13371337);	
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ALL;
	}

	@Override
	public int getMaxLevel() {
		return Integer.MAX_VALUE;
	}

	@Override
	public String getName() {
		return "SUPAH DUPAH COOL STRING";
	}

	@Override
	public int getStartLevel() {
		return Integer.MIN_VALUE;
	}

	
	
}
