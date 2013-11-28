package com.codari.arena.objects.itemspawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.codari.arena.objects.RandomSpawnableObjectA;
import com.codari.arena.util.AoE;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.players.combatants.Combatant;

@ArenaObjectName("Item_Spawner")
public class TemplateItemSpawner extends RandomSpawnableObjectA implements ItemSpawner {
	//-----Fields-----//
	private Block itemSpawnerBlock;
	private BlockState itemSpawnerBlockState;
	protected Material itemSpawnerMaterial = Material.DIAMOND_BLOCK;
	
	public static ItemChooser itemChooser = new ItemChooser();
	
	//---Initialized in Constructor---//
	private AoE areaOfEffect;

	public TemplateItemSpawner(Player player) {
		this.itemSpawnerBlock = player.getLocation().getBlock().getRelative(BlockFace.UP, 4);
		this.itemSpawnerBlockState = this.itemSpawnerBlock.getState();

		this.areaOfEffect = new AoE(player.getLocation(), 1, this);
	}

	//-----Getters-----//
	public BlockState getBeaconState() {
		return this.itemSpawnerBlockState;
	}

	@Override
	public void spawn() {
		this.reveal();	
	}

	@Override
	public void reveal() {
		itemSpawnerBlockState.getBlock().setType(itemSpawnerMaterial);
		this.areaOfEffect.setActive();
	}

	@Override
	public void hide() {
		this.areaOfEffect.setDeactive();
		this.itemSpawnerBlockState.update(true);
	}
	
	@Override
	public void spawnItem(Combatant combatant) {
		ItemStack spawnedItem;
		ItemType itemType = ItemType.chooseItemType();
		spawnedItem = itemChooser.generateItem(combatant.getRole(), itemType);
		
		switch(itemType) {
		case POTION: 
			this.addPotionToInventory(combatant.getPlayer(), spawnedItem);
			break;
		case WEAPON:
			this.addWeaponToInventory(combatant.getPlayer(), spawnedItem);
			break;
		case ARMOR:
			this.equipArmor(combatant.getPlayer(), spawnedItem);
			break;			
		}
		
	}
	
	private void addPotionToInventory(Player player, ItemStack itemStack) {
		player.getInventory().addItem(itemStack);
	}
	
	private void equipArmor(Player player, ItemStack itemStack) {
		switch(itemStack.getType()) {
		case CHAINMAIL_HELMET: 
		case LEATHER_HELMET:
		case DIAMOND_HELMET:
		case GOLD_HELMET:
		case IRON_HELMET:
			player.getInventory().setHelmet(itemStack);
			break;
		case CHAINMAIL_CHESTPLATE: 
		case LEATHER_CHESTPLATE:
		case DIAMOND_CHESTPLATE:
		case GOLD_CHESTPLATE:
		case IRON_CHESTPLATE:
			player.getInventory().setChestplate(itemStack);	
			break;
		case CHAINMAIL_LEGGINGS: 
		case LEATHER_LEGGINGS:
		case DIAMOND_LEGGINGS:
		case GOLD_LEGGINGS:
		case IRON_LEGGINGS:
			player.getInventory().setLeggings(itemStack);
			break;
		case CHAINMAIL_BOOTS: 
		case LEATHER_BOOTS:
		case DIAMOND_BOOTS:
		case GOLD_BOOTS:
		case IRON_BOOTS:
			player.getInventory().setBoots(itemStack);	
			break;
		default:
		}
	} 
	
	private void addWeaponToInventory(Player player, ItemStack itemStack) {
		player.getInventory().setItem(1, itemStack);
	}
}
