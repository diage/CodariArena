package com.codari.arena.objects.itemspawner.chooser;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.itemspawner.structure.ItemType;
import com.codari.arena5.players.role.Role;

public class ItemChooser {
	//-----Fields------//
	private final int MELEE_WEAPON = 0;
	private final int RANGED_WEAPON = 1;
	private final int MELEE_ARMOR = 2;
	private final int RANGED_ARMOR = 3;
	
	private final int MELEE_POTION = 0;
	private final int RANGED_POTION = 1;
	
	private Enchantment[][] itemEnchantments;
	private ItemStack[][] potions;
	
	//----Constructor-----//
	public ItemChooser() {
		//---Item Enchantments---//
		this.itemEnchantments = new Enchantment[4][3];
		
		this.itemEnchantments[MELEE_WEAPON][0] = Enchantment.DAMAGE_ALL;
		this.itemEnchantments[MELEE_WEAPON][1] = Enchantment.FIRE_ASPECT;
		this.itemEnchantments[MELEE_WEAPON][2] = Enchantment.KNOCKBACK;
		
		this.itemEnchantments[RANGED_WEAPON][0] = Enchantment.ARROW_DAMAGE;
		this.itemEnchantments[RANGED_WEAPON][1] = Enchantment.ARROW_FIRE;
		this.itemEnchantments[RANGED_WEAPON][2] = Enchantment.ARROW_KNOCKBACK;
		
		this.itemEnchantments[MELEE_ARMOR][0] = Enchantment.PROTECTION_EXPLOSIONS;
		this.itemEnchantments[MELEE_ARMOR][1] = Enchantment.PROTECTION_FIRE;
		
		this.itemEnchantments[RANGED_ARMOR][0] = Enchantment.PROTECTION_FALL;
		this.itemEnchantments[RANGED_ARMOR][1] = Enchantment.THORNS;
		
		//---Potions---//
		this.potions = new ItemStack[2][3];
		
		this.potions[MELEE_POTION][0] = (new Potion(PotionType.SLOWNESS)).splash().toItemStack(1);
		this.potions[MELEE_POTION][1] = (new Potion(PotionType.REGEN)).toItemStack(1);
		this.potions[MELEE_POTION][2] = (new Potion(PotionType.STRENGTH)).toItemStack(1);
		
		this.potions[RANGED_POTION][0] = (new Potion(PotionType.WEAKNESS)).splash().toItemStack(1);
		this.potions[RANGED_POTION][1] = (new Potion(PotionType.SPEED)).toItemStack(1);
		this.potions[RANGED_POTION][2] = (new Potion(PotionType.POISON)).splash().toItemStack(1);
	}	
	
	public ItemStack generateItem(Role role, ItemType itemType) {
		switch(itemType) {
		case POTION:
			return this.generatePotion(role);
		case WEAPON:
			return this.generateWeapon(role);
		case ARMOR:
			return this.generateArmor(role);
		}
		return null;
	}
	
	//-----Generate Potion-----//
	private ItemStack generatePotion(Role role) {
		switch(role.getName()) {
		case ArenaStatics.MELEE:
			return generateMeleePotion();
		case ArenaStatics.RANGED:
			return generateRangedPotion();
		default:
			return null;
		}
	}
	
	private ItemStack generateMeleePotion() {
		Random random = new Random(System.currentTimeMillis());
		switch(random.nextInt(10)){
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			return this.potions[RANGED_POTION][1];
		case 6:
		case 7:
		case 8: 
			return this.potions[RANGED_POTION][2];
		case 9: 
			return this.potions[RANGED_POTION][0];
		default:
			return null;
		}	
	}
	
	private ItemStack generateRangedPotion() {
		Random random = new Random(System.currentTimeMillis());
		switch(random.nextInt(10)){
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			return this.potions[MELEE_POTION][1];
		case 6:
		case 7:
		case 8: 
			return this.potions[MELEE_POTION][0];
		case 9: 
			return this.potions[MELEE_POTION][2];
		default:
			return null;
		}	
	}
	
	//-----Generate Weapon-----//
	private ItemStack generateWeapon(Role role) {
		switch(role.getName()) {
		case ArenaStatics.MELEE:
			return generateMeleeWeapon();
		case ArenaStatics.RANGED:
			return generateRangedWeapon();
		default:
			return null;
		}			
	}
	
	private ItemStack generateMeleeWeapon() {
		ItemStack itemStack;
		Random random = new Random(System.currentTimeMillis());	
		Random randomItemStack = new Random(System.currentTimeMillis());
		
		switch(random.nextInt(10)){
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			itemStack = new ItemStack(Material.IRON_SWORD);
			break;
		case 9: 
			itemStack = new ItemStack(Material.DIAMOND_SWORD);
			break;
		default:
			itemStack = new ItemStack(Material.IRON_SWORD);
		}	
		
		switch(randomItemStack.nextInt(5)){
		case 0:
		case 1:
		case 2:
			itemStack.addEnchantment(this.itemEnchantments[MELEE_WEAPON][2], 1);
			break;
		case 3:
			itemStack.addEnchantment(this.itemEnchantments[MELEE_WEAPON][0], 1);
			break;
		case 4:
			itemStack.addEnchantment(this.itemEnchantments[MELEE_WEAPON][1], 1);
		default:
		}
		
		return itemStack;
	}
	
	private ItemStack generateRangedWeapon() {
		ItemStack itemStack = new ItemStack(Material.BOW);
		Random randomItemStack = new Random(System.currentTimeMillis());
		
		itemStack.addEnchantment(Enchantment.ARROW_INFINITE, 1);

		switch(randomItemStack.nextInt(5)){
		case 0:
		case 1:
			itemStack.addEnchantment(this.itemEnchantments[RANGED_WEAPON][0], 1);
			break;
		case 2:
		case 3:
			itemStack.addEnchantment(this.itemEnchantments[RANGED_WEAPON][1], 1);
			break;
		case 4:
			itemStack.addEnchantment(this.itemEnchantments[RANGED_WEAPON][2], 1);
		default:
		}
		
		return itemStack;
	}
	
	//-----Generate Armor-----//
	private ItemStack generateArmor(Role role) {
		switch(role.getName()) {
		case ArenaStatics.MELEE:
			return generateMeleeArmor();
		case ArenaStatics.RANGED:
			return generateRangedArmor();
		default:
			return null;
		}			
	}
	
	private ItemStack generateMeleeArmor() {
		ItemStack itemStack;
		Random randomItemStack = new Random(System.currentTimeMillis());
		Random randomItemEnchantment = new Random(System.currentTimeMillis());
		
		switch(randomItemStack.nextInt(14)) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			itemStack = new ItemStack(Material.IRON_HELMET);
			break;
		case 5:
		case 6:
		case 7:
		case 8:	
		case 9:
			itemStack = new ItemStack(Material.IRON_BOOTS);
			break;
		case 10:
		case 11:
		case 12:
			itemStack = new ItemStack(Material.IRON_LEGGINGS);
			break;
		case 13:
			itemStack = new ItemStack(Material.IRON_CHESTPLATE);
			break;
		default:
			itemStack = new ItemStack(Material.IRON_HELMET);
		}
		
		switch(randomItemEnchantment.nextInt(5)){
		case 0:
		case 1:
		case 2:
			itemStack.addEnchantment(this.itemEnchantments[MELEE_ARMOR][0], 1);
			break;			
		case 3:
		case 4:
			itemStack.addEnchantment(this.itemEnchantments[MELEE_ARMOR][1], 1);
		default:
		}
		
		return itemStack;
	}
	
	private ItemStack generateRangedArmor() {
		ItemStack itemStack;
		Random randomItemStack = new Random(System.currentTimeMillis());
		Random randomItemEnchantment = new Random(System.currentTimeMillis());
		
		switch(randomItemStack.nextInt(14)) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			itemStack = new ItemStack(Material.LEATHER_HELMET);
			break;
		case 5:
		case 6:
		case 7:
		case 8:	
		case 9:
			itemStack = new ItemStack(Material.LEATHER_BOOTS);
			break;
		case 10:
		case 11:
		case 12:
			itemStack = new ItemStack(Material.LEATHER_LEGGINGS);
			break;
		case 13:
			itemStack = new ItemStack(Material.LEATHER_CHESTPLATE);
			break;
		default:
			itemStack = new ItemStack(Material.LEATHER_HELMET);
		}
		
		switch(randomItemEnchantment.nextInt(5)){
		case 0:
		case 1:
		case 2:
			itemStack.addUnsafeEnchantment(this.itemEnchantments[RANGED_ARMOR][0], 1);	
			break;			
		case 3:
		case 4:
			itemStack.addEnchantment(this.itemEnchantments[RANGED_ARMOR][1], 1);
		default:
		}
		
		return itemStack;
	}
}
