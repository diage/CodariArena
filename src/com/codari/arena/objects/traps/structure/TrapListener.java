package com.codari.arena.objects.traps.structure;

import java.util.Iterator;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.arena.objects.ObjectListener;
import com.codari.arena.util.AoeTriggerEvent;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;

public class TrapListener extends ObjectListener implements Listener {

	//-----Events-----//
	@EventHandler
	public void triggerAoEEvent(AoeTriggerEvent e) {
		if(e.getArenaObject() instanceof Trap) {
			Trap trap = (Trap)e.getArenaObject();
			List<Player> players = this.editList(e.getEntities());		
			this.clearTeams(players, trap.getTeam());

			if(players.size() > 0) {
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
				if (interiorValue.getOwningPlugin().equals(CodariI.INSTANCE)) {
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
					if (CodariI.INSTANCE.equals(possibleValue.getOwningPlugin())) {
						trapValue = possibleValue;
						break;
					}
				}
				if (trapValue != null && trapValue.asBoolean()) {
					Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
					trap.setTeam(combatant.getTeam());
					trap.set();
				}
			}
		}
	}

	//FIXME - redstone activation has to be added to traps
	@EventHandler
	public void noPoweredRunes(BlockRedstoneEvent e) {
		Block block = e.getBlock();
		if (block.hasMetadata(TemplateTrap.META_DATA_STRING)) {
			MetadataValue trapValue = null;
			for (MetadataValue possibleValue : block.getMetadata(TemplateTrap.META_DATA_STRING)) {
				if (CodariI.INSTANCE.equals(possibleValue.getOwningPlugin())) {
					trapValue = possibleValue;
					break;
				}
			}
			if (trapValue != null && trapValue.asBoolean()) {
				e.setNewCurrent(15);
			}
		}
	}

	private void clearTeams(List<Player> players, Team team) {
		for(Iterator<Player> pI = players.iterator();pI.hasNext();) {
			Player player = pI.next();
			Combatant combatant = Codari.getArenaManager().getCombatant(player);
			if(Codari.getArenaManager().getTeam(combatant).equals(team)) {
				pI.remove();
			}
		}
	}
}
