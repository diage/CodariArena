package com.codari.arena.objects.gates;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;

/**
 * TODO:
 * This needs fixed: This is the wrong kind of gate. For the arena to interact with it, it needs to be of type spawnable. 
 * Fixed by Mhenlo - doublecheck this
 * @author Ryan
 *
 */
@ArenaObjectName("Gate")
public class Gate implements FixedSpawnableObject {	
	private Block redStoneBlock;
	private BlockState redStoneBlockState;
	private Material redStoneMaterial = Material.REDSTONE_BLOCK;
	
	//-----Constructor-----//
	public Gate(Player player) {
		this.redStoneBlock = player.getLocation().getBlock();
		this.redStoneBlockState = redStoneBlock.getState();
	}
	
	//-----Constructor-----//
	public BlockState getRedStoneBlockState() {
		return this.redStoneBlockState;
	}

	//---Fixed Spawnable Methods---//
	@Override
	public void spawn() {
		this.reveal();
	}

	@Override
	public void reveal() {
		this.redStoneBlockState.setType(redStoneMaterial);
	}

	@Override
	public void hide() {
		this.redStoneBlockState.update(true);
	}
}