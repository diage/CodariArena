package com.codari.arena.objects.traps.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import com.codari.api.Codari;
import com.codari.arena.combatants.teams.Team;
import com.codari.arena.util.AoeTriggerEvent;

public class TrapListener implements Listener {
	
	//-----Events-----//
	@EventHandler
	public void triggerAoEEvent(AoeTriggerEvent e) {
		Bukkit.broadcastMessage("Something is triggering the Fire Trap!");
		List<Entity> targets = new ArrayList<>(e.getEntities());
		this.editList(targets);
		this.clearTeams(targets, e.getTrap().getTeam());
		//Has to check if opposing team triggered the trap
		if(targets.size() > 0) {
			e.getTrap().trigger(targets);
			e.getTrap().deactivate();
		}
	}

	
	@EventHandler
	//Check for activation
	public void triggerInteractEvent(PlayerInteractEvent e) { //TODO - Add Team when set trap
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			List<MetadataValue> values = block.getMetadata(TemplateTrap.RANDOM_PASS_KEY);
			MetadataValue metaValue = null;
			for (MetadataValue interiorValue : values) {
				if (interiorValue.getOwningPlugin().equals(Codari.INSTANCE)) {
					metaValue = interiorValue;
				}
			}
			if (metaValue == null) {
				return;
			}
			Trap Trap = (Trap) metaValue.value();
			if (block.hasMetadata(TemplateTrap.META_DATA_STRING)) {
				MetadataValue trapValue = null;
				for (MetadataValue possibleValue : block.getMetadata(TemplateTrap.META_DATA_STRING)) {
					if (Codari.INSTANCE.equals(possibleValue.getOwningPlugin())) {
						trapValue = possibleValue;
						break;
					}
				}
				if (trapValue != null && trapValue.asBoolean()) {
					Trap.set();
				}
			}
		}
	}
	
	/* Filters out all non-player entities within a list. */
	private void editList(List<Entity> entities) { //TODO
		Iterator<Entity> iterator = entities.iterator();
		while(iterator.hasNext()) {
			if(!(iterator.next() instanceof Player)) {
				iterator.remove();
			}
		}
	}
	
	//TODO
	private void clearTeams(List<Entity> entities, Team team) {
		//Iterator<>
	}
}
