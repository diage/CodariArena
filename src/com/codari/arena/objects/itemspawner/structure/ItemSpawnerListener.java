package com.codari.arena.objects.itemspawner.structure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import com.codari.api5.Codari;
import com.codari.arena.objects.ObjectListener;
import com.codari.arena.util.AoeTriggerEvent;
import com.codari.arena5.players.combatants.Combatant;

public class ItemSpawnerListener extends ObjectListener implements Listener {
	private final static Set<Block> physics = new HashSet<>();
	
	public static void stopPhysics(Block block) {
		physics.add(block);
	}
	
	public static void resumePhysics(Block block) {
		physics.remove(block);
	}
	
	//-----Events-----//
	@EventHandler
	public void triggerAoEEvent(AoeTriggerEvent e) {
		if(e.getArenaObject() instanceof ItemSpawner) {
			ItemSpawner itemSpawner = (ItemSpawner) e.getArenaObject();
			List<Player> players = this.editList(e.getEntities());
			if(players.size() == 1) {
				Combatant combatant = Codari.getArenaManager().getCombatant(players.get(0));
				itemSpawner.addItem(combatant);
			}
		}
	}
	
	@EventHandler
	public void operationFlotyFloats(BlockPhysicsEvent e) {
		if (physics.contains(e.getBlock())) {
			e.setCancelled(true);
		}
	}
}
