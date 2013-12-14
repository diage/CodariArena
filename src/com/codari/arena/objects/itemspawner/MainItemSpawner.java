package com.codari.arena.objects.itemspawner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.codari.arena.objects.RandomSpawnableObjectA;
import com.codari.arena.objects.itemspawner.chooser.ItemChooser;
import com.codari.arena.objects.itemspawner.structure.ItemSpawner;
import com.codari.arena.objects.itemspawner.structure.ItemType;
import com.codari.arena.util.AoE;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.players.combatants.Combatant;

@ArenaObjectName("Item_Spawner")
public class MainItemSpawner extends RandomSpawnableObjectA implements ItemSpawner {
	private static final long serialVersionUID = 5092060018825234373L;
	//-----Fields-----//
	private transient BlockState itemSpawnerBlockState;
	private final SerializableBlock serialIndicator;
	protected Material itemSpawnerMaterial = Material.DIAMOND_BLOCK;
	private boolean isSpawned;
	public static ItemChooser itemChooser = new ItemChooser();
	
	//---Initialized in Constructor---//
	private AoE areaOfEffect;

	public MainItemSpawner(Player player) {
		this.itemSpawnerBlockState = player.getLocation().getBlock().getRelative(BlockFace.UP, 4).getState();
		this.serialIndicator = new SerializableBlock(this.itemSpawnerBlockState);
		this.areaOfEffect = new AoE(this.itemSpawnerBlockState.getBlock().getLocation(), 1, this);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		World world = Bukkit.getWorld(this.serialIndicator.worldName);
		if (world == null) {
			throw new IllegalStateException("World named " + this.serialIndicator.worldName + " is not loaded");
		}
		this.itemSpawnerBlockState = world.getBlockAt(this.serialIndicator.x, this.serialIndicator.y, this.serialIndicator.z).getState();
	}

	//-----Getters-----//
	public BlockState getBeaconState() {
		return this.itemSpawnerBlockState;
	}

	@Override
	public void spawn() {
		this.isSpawned = true;
		this.reveal();	
	}

	@Override
	public void reveal() {
		itemSpawnerBlockState.getBlock().setType(itemSpawnerMaterial); //TODO - set material based on item being spawned
		this.areaOfEffect.setActive();
	}

	@Override
	public void hide() {
		this.areaOfEffect.setDeactive();
		this.itemSpawnerBlockState.update(true);
		this.isSpawned = false;
	}
	
	@Override
	public boolean isSpawned() {
		return this.isSpawned;
	}		

	@Override
	public void spawnItem(Combatant combatant) {
		ItemStack spawnedItem;
		ItemType itemType = ItemType.chooseItemType();
		spawnedItem = itemChooser.generateItem(combatant.getRole(), itemType);

		switch(itemType) {
		case POTION: 
			this.itemSpawnerMaterial = Material.BREWING_STAND;
			this.addPotionToInventory(combatant.getPlayer(), spawnedItem);
			break;
		case WEAPON:
			this.itemSpawnerMaterial = Material.ANVIL;
			this.addWeaponToInventory(combatant.getPlayer(), spawnedItem);
			break;
		case ARMOR:
			this.itemSpawnerMaterial = Material.CHEST;
			this.equipArmor(combatant.getPlayer(), spawnedItem);
			break;			
		}

		this.hide();
	}

	private void addPotionToInventory(Player player, ItemStack itemStack) {
		ItemStack[] items = player.getInventory().getContents();
		for(int i = 0; i < 5; i++) {
			if(items[i] == null) {
				player.getInventory().setItem(i, itemStack/*=)*/);
				return;
			} else if (items[i].isSimilar(itemStack)) { 
				items[i].setAmount(items[i].getAmount() + 1);
				player.getInventory().setItem(i, items[i]);
				return;
			}
		}
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
	
	@SuppressWarnings("serial")
	private class SerializableBlock implements Serializable {
		private int x, y, z;
		private String worldName;
		
		public SerializableBlock(BlockState blockState) {
			this.worldName = blockState.getWorld().getName();
			this.x = blockState.getX();
			this.y = blockState.getY();
			this.z = blockState.getZ();
		}
	}
}
