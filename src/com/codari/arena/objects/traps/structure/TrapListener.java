package com.codari.arena.objects.traps.structure;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import com.codari.api5.Codari;
import com.codari.arena.objects.ObjectListener;
import com.codari.arena.util.AoeTriggerEvent;
import com.codari.arena5.players.teams.Team;

public class TrapListener extends ObjectListener implements Listener {

	//-----Events-----//
	@EventHandler
	public void triggerAoEEvent(AoeTriggerEvent e) {
		if(e.getArenaObject() instanceof Trap) {
			List<Player> players = this.editList(e.getEntities());
			
			//this.clearTeams(targets, e.getTrap().getTeam());
			//Has to check if opposing team triggered the trap
			if(players.size() > 0) {
				Trap trap = (Trap)e.getArenaObject();
				trap.trigger(players);
				trap.deactivate();
			}

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
			Trap trap = (Trap) metaValue.value();
			if (block.hasMetadata(TemplateTrap.META_DATA_STRING)) {
				MetadataValue trapValue = null;
				for (MetadataValue possibleValue : block.getMetadata(TemplateTrap.META_DATA_STRING)) {
					if (Codari.INSTANCE.equals(possibleValue.getOwningPlugin())) {
						trapValue = possibleValue;
						break;
					}
				}
				if (trapValue != null && trapValue.asBoolean()) {
					trap.set();
				}
			}
		}
	}

	@EventHandler
	public void noPoweredRunes(BlockRedstoneEvent e) {
		Block block = e.getBlock();
		if (block.hasMetadata(TemplateTrap.META_DATA_STRING)) {
			MetadataValue trapValue = null;
			for (MetadataValue possibleValue : block.getMetadata(TemplateTrap.META_DATA_STRING)) {
				if (Codari.INSTANCE.equals(possibleValue.getOwningPlugin())) {
					trapValue = possibleValue;
					break;
				}
			}
			if (trapValue != null && trapValue.asBoolean()) {
				e.setNewCurrent(15);
			}
		}
	}

	//TODO
	@SuppressWarnings("unused")
	private void clearTeams(List<Entity> entities, Team team) {
		//Iterator<>
	}
}
