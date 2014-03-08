package com.codari.arena.objects.gate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;

import com.codari.api5.CodariI;
import com.codari.api5.annotations.ArenaObjectName;
import com.codari.api5.util.scheduler.BukkitTime;
import com.codari.arena5.assets.FixedSpawnableAsset;

@ArenaObjectName("Gate")
public class Gate extends FixedSpawnableAsset {
	private transient BlockState redStoneBlockState;
	private final SerializableBlock serialIndicator;
	private Material redStoneMaterial = Material.REDSTONE_BLOCK;
	private static final long DESPAWN_TIME = BukkitTime.SECOND.tickValueOf(5);
	private final Location location;

	//-----Constructor-----//
	public Gate(Location location) {
		this.location = location;
		this.redStoneBlockState = location.getBlock().getState();
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
	
	@Override
	public Collection<BlockState> getAffectedBlocks() {
		Collection<BlockState> affectedBlocks = new ArrayList<>();
		affectedBlocks.add(this.redStoneBlockState);
		return affectedBlocks;
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
	
	@Override
	public Location getLocation() {
		return this.location;
	}

	@Override
	public String getName() {
		return null;
	}
}	
