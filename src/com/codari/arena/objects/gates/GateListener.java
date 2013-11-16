package com.codari.arena.objects.gates;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class GateListener implements Listener {
	public void triggerEvent(PlayerInteractEvent e) {
		if(e.getClickedBlock() instanceof Gate && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			//Not sure how to get reference to the gate yet 
			//Gate gate = e.getClickedBlock();
			//gate.interact();
		}
	}
}
