package com.codari.arena.objects.gate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.api5.util.scheduler.BukkitTime;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;

@ArenaObjectName("Gate")
public class Gate implements FixedSpawnableObject {
	private static final long serialVersionUID = 502299764798303853L;
	private transient BlockState redStoneBlockState;
	private final SerializableBlock serialIndicator;
	private Material redStoneMaterial = Material.REDSTONE_BLOCK;
	private final static long DESPAWN_TIME = BukkitTime.SECOND.tickValueOf(5); 

	//-----Constructor-----//
	public Gate(Player player) {
		this.redStoneBlockState = player.getLocation().getBlock().getState();
		this.serialIndicator = new SerializableBlock(this.redStoneBlockState);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		World world = Bukkit.getWorld(this.serialIndicator.worldName);
		if (world == null) {
			throw new IllegalStateException("World named " + this.serialIndicator.worldName + " is not loaded");
		}
		this.redStoneBlockState = world.getBlockAt(this.serialIndicator.x, this.serialIndicator.y, this.serialIndicator.z).getState();
	}

	//-----Constructor-----//
	public BlockState getRedStoneBlockState() {
		return this.redStoneBlockState;
	}

	//---Fixed Spawnable Methods---//
	@Override
	public void spawn() {
		this.reveal();
		this.countDown();
	}

	@Override
	public void reveal() {
		this.redStoneBlockState.getBlock().setType(redStoneMaterial);
	}

	@Override
	public void hide() {
		this.redStoneBlockState.update(true);
	}

	private class SerializableBlock implements Serializable {
		private static final long serialVersionUID = -684579128017548484L;
		private int x, y, z;
		private String worldName;

		public SerializableBlock(BlockState blockState) {
			this.worldName = blockState.getWorld().getName();
			this.x = blockState.getX();
			this.y = blockState.getY();
			this.z = blockState.getZ();
		}
	}

	private void countDown() {
		Bukkit.getScheduler().runTaskLater(CodariI.INSTANCE, new Runnable() {
			@Override
			public void run() {
				hide();
			}	
		}, DESPAWN_TIME);
	}
}	
