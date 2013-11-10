package com.codari.arena.objects.traps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import com.codari.api.Codari;
import com.codari.arena.util.AoeTriggerEvent;

public class FireTrap extends TemplateTrap {
	//-----Fields-----//
	private int numberOfFireTicks = 40;

	public FireTrap(Player player, double radius) {
		super(player, radius, RandomStringUtils.randomAscii(25));
	}

	//-----Events-----//
	@EventHandler
	public void triggerAoEEvent(AoeTriggerEvent e) {
		Bukkit.broadcastMessage("Something is triggering the Fire Trap!");
		List<Entity> targets = new ArrayList<>(e.getEntities());

		//Has to check if opposing team triggered the trap
		if(true /*e.getEntities().contains*/) {
			editList(targets);
		}
		
		//Effects Of Trap
		setTargetsOnFire(targets);
		
		//Deactivate the Trap (Not sure if this is right)
		super.deactivate();
	}

	@EventHandler
	//Check for activation
	public void triggerInteractEvent(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			if (block.hasMetadata(super.META_DATA_STRING)) {
				MetadataValue value = null;
				for (MetadataValue possibleValue : block.getMetadata(super.META_DATA_STRING)) {
					if (Codari.INSTANCE.equals(possibleValue.getOwningPlugin())) {
						value = possibleValue;
						break;
					}
				}
				if (value != null && value.asBoolean()) {
					super.set();
				}
			}
		}
	}

	//-----Private Methods-----//
	/* Sets all targets on fire  */
	private void setTargetsOnFire(List<Entity> targets) {
		int currentNumberOfFireTicks;
		for(int i = 0; i < targets.size(); i++) {
			Player player = (Player)targets.get(i);
			currentNumberOfFireTicks = player.getFireTicks();
			player.setFireTicks(currentNumberOfFireTicks + numberOfFireTicks);
		}		
	}
	
	
	/* Filters out all non-player entities within a list. */
	private void editList(List<Entity> entities) {
		Iterator<Entity> iterator = entities.iterator();
		while(iterator.hasNext()) {
			if(!(iterator.next() instanceof Player)) {
				iterator.remove();
			}
		}
	}
}
