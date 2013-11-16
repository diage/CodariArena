package com.codari.arena.objects.gates;

import javax.xml.crypto.NoSuchMechanismException;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.codari.arena5.objects.persistant.ImmediatePersistentObject;

public class Gate implements ImmediatePersistentObject {
	//-----Fields-----//
	private int gateWidth = 5;
	private int gateHeight = 5;	
	private boolean isGateOpen = false;
	
	private Block[] gateBlock;
	private Block[] fenceBlock;
	
	//-----Constructor-----//
	public Gate(Player player, int gateWidth, int gateHeight, boolean isGateOpen) {
		if(gateWidth < 3) {
			gateWidth = 3;
			throw new NoSuchMechanismException("The gate width must be at least three blocks!");
		} 
		if (gateHeight < 3) {
			gateHeight = 3;
			throw new NoSuchMechanismException("The gate height must be at least three blocks!");
		}
		
		/* Constructor of this object must set a player, a gate width, a gate height, and whether the gate is open or not. */
		this.gateWidth = gateWidth;
		this.gateHeight = gateHeight;
		this.isGateOpen = isGateOpen;
		
		gateBlock = new Block[gateHeight * 2 + gateWidth - 2];
		fenceBlock = new Block[gateWidth - 2];
	}

	//----Immediate Persistent Object Methods---//
	@Override
	public void interact() {
		if(!isGateOpen) {
			openGate();
		} else {
			closeGate();
		}
	}

	@Override
	public void reveal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	private void openGate() {
		
	}
	
	private void closeGate() {
		
	}

}
