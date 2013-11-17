package com.codari.arena.objects.itemspawner;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.arena.objects.ObjectListener;
import com.codari.arena.util.AoeTriggerEvent;
import com.codari.arena5.players.combatants.Combatant;

public class ItemSpawnerListener extends ObjectListener implements Listener {
	//-----Events-----//
	@EventHandler
	public void triggerAoEEvent(AoeTriggerEvent e) {
		if(e.getArenaObject() instanceof ItemSpawner) {
			ItemSpawner itemSpawner = (ItemSpawner) e.getArenaObject();
			List<Player> players = this.editList(e.getEntities());
			if(players.size() == 1) {
				Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(players.get(0));
				itemSpawner.spawnItem(combatant);
			}
		}
	}
}
