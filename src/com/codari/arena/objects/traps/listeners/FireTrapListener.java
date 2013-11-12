package com.codari.arena.objects.traps.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import com.codari.api.Codari;
import com.codari.arena.objects.traps.FireTrap;
import com.codari.arena.util.AoeTriggerEvent;

public class FireTrapListener implements Listener {
	
	private FireTrap fireTrap;
	
	public FireTrapListener(FireTrap fireTrap) {
		this.fireTrap = fireTrap;
	}
	
	//-----Events-----//
	@EventHandler
	public void triggerAoEEvent(AoeTriggerEvent e) {
		Bukkit.broadcastMessage("Something is triggering the Fire Trap!");
		List<Entity> targets = new ArrayList<>(e.getEntities());

		//Has to check if opposing team triggered the trap
		if(true /*e.getEntities().contains*/) {
			e.getTrap().trigger(targets);
		}
	}

	
	@EventHandler
	//Check for activation
	public void triggerInteractEvent(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			if (block.hasMetadata(this.fireTrap.META_DATA_STRING)) {
				MetadataValue value = null;
				for (MetadataValue possibleValue : block.getMetadata(this.fireTrap.META_DATA_STRING)) {
					if (Codari.INSTANCE.equals(possibleValue.getOwningPlugin())) {
						value = possibleValue;
						break;
					}
				}
				if (value != null && value.asBoolean()) {
					this.fireTrap.set();
				}
			}
		}
	}
}
