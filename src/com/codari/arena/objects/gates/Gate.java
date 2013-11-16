package com.codari.arena.objects.gates;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.codari.arena5.objects.persistant.ImmediatePersistentObject;

public class Gate implements ImmediatePersistentObject {	
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

	//----Immediate Persistent Object Methods---//
	@Override
	public void interact() {
		
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
