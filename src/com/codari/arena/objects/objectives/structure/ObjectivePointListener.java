package com.codari.arena.objects.objectives.structure;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena.objects.ObjectListener;
import com.codari.arena.util.AoeTriggerEvent;

public class ObjectivePointListener extends ObjectListener implements Listener {

	//-----Events-----//
	@EventHandler
	public void triggerAoEEvent(AoeTriggerEvent e) {
		if(e.getArenaObject() instanceof ObjectivePoint) {
			ObjectivePoint objectivePoint = (ObjectivePoint)e.getArenaObject();
			List<Player> players = this.editList(e.getEntities());
			if(players.size() > 0) {
				objectivePoint.combatantOn(players);
			} else {
				objectivePoint.combatantOff();
			}
		}
	}
}
