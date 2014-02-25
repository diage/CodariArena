package com.codari.arena.objects.itemspawner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.annotations.ArenaObjectName;
import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.RandomSpawnableObjectA;
import com.codari.arena.objects.itemspawner.chooser.ItemChooser;
import com.codari.arena.objects.itemspawner.structure.ItemSpawner;
import com.codari.arena.objects.itemspawner.structure.ItemSpawnerListener;
import com.codari.arena.objects.itemspawner.structure.ItemType;
import com.codari.arena.util.AoE;
import com.codari.arena5.players.combatants.Combatant;

@ArenaObjectName(value = MainItemSpawner.NAME, links = {ArenaStatics.MELEE, ArenaStatics.RANGED})
public class MainItemSpawner extends RandomSpawnableObjectA implements ItemSpawner {
	private static final long serialVersionUID = 5092060018825234373L;
	//-----Fields-----//
	private transient BlockState itemSpawnerBlockState;
	private final SerializableBlock serialIndicator;
	protected Material itemSpawnerMaterial;
	private transient ItemStack meleeItem;
	private transient ItemStack rangedItem;
	private transient ItemType typer;
	private boolean isSpawned;
	public static ItemChooser itemChooser = new ItemChooser();
	
	public static final String NAME = "Item_Spawner";
	
	//---Initialized in Constructor---//
	private transient AoE areaOfEffect;

	public MainItemSpawner(Location location) {
		super(location);
		this.itemSpawnerBlockState = location.getBlock().getRelative(BlockFace.UP, 4).getState();
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
		this.areaOfEffect = new AoE(this.itemSpawnerBlockState.getBlock().getLocation(), 1, this);
	}

	//-----Getters-----//
	public BlockState getBeaconState() {
		return this.itemSpawnerBlockState;
	}

	@Override
	public void spawn() {
		this.isSpawned = true;
		this.reveal();	
		this.areaOfEffect.setActive();
	}

	@Override
	public void reveal() {
		ItemSpawnerListener.stopPhysics(this.itemSpawnerBlockState.getBlock());
		this.spawnItem();
		this.itemSpawnerBlockState.getBlock().setType(itemSpawnerMaterial);
	}

	@Override
	public void hide() {
		this.areaOfEffect.setDeactive();
		this.itemSpawnerBlockState.update(true);
		ItemSpawnerListener.resumePhysics(this.itemSpawnerBlockState.getBlock());
		this.isSpawned = false;
	}
	
	@Override
	public Collection<BlockState> getAffectedBlocks() {
		Collection<BlockState> affectedBlocks = new ArrayList<>();
		affectedBlocks.add(this.itemSpawnerBlockState);
		return affectedBlocks;
	}
	
	@Override
	public boolean isSpawned() {
		return this.isSpawned;
	}		

	@Override
	public void addItem(Combatant combatant) {
		switch(this.typer) {
		case POTION:
			switch (combatant.getRole().getLink(NAME)) {
			case ArenaStatics.MELEE:
				this.addPotionToInventory(combatant.getPlayer(), this.meleeItem);
				break;
			case ArenaStatics.RANGED:
				this.addPotionToInventory(combatant.getPlayer(), this.rangedItem);
				break;
			}
			break;
		case WEAPON:
			switch (combatant.getRole().getLink(NAME)) {
			case ArenaStatics.MELEE:
				this.addWeaponToInventory(combatant.getPlayer(), this.meleeItem);
				break;
			case ArenaStatics.RANGED:
				this.addWeaponToInventory(combatant.getPlayer(), this.rangedItem);
				break;
			}
			break;
		case ARMOR:
			switch (combatant.getRole().getLink(NAME)) {
			case ArenaStatics.MELEE:
				this.equipArmor(combatant.getPlayer(), this.meleeItem);
				break;
			case ArenaStatics.RANGED:
				this.equipArmor(combatant.getPlayer(), this.rangedItem);
				break;
			}
			break;			
		}
		this.hide();
	}
	
	private void spawnItem() {
		this.typer = ItemType.chooseItemType();
		this.meleeItem = itemChooser.generateItem(ArenaStatics.MELEE, this.typer);
		this.rangedItem = itemChooser.generateItem(ArenaStatics.RANGED, this.typer);
		switch(this.typer) {
		case POTION: 
			this.itemSpawnerMaterial = Material.BREWING_STAND;
			break;
		case WEAPON:
			this.itemSpawnerMaterial = Material.ENCHANTMENT_TABLE;
			break;
		case ARMOR:
			this.itemSpawnerMaterial = Material.CHEST;
			break;			
		}
	}

	private void addPotionToInventory(Player player, ItemStack itemStack) {
		ItemStack[] items = player.getInventory().getContents();
		for(int i = 1; i < 6; i++) {
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
		player.getInventory().setItem(7, itemStack);
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
