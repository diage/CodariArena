package com.codari.arena.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ObjectListener {
	/* Filters out all non-player entities within a list. 
	 *	Next step is to convert players to combatants. 
	 */
	protected List<Player> editList(List<Entity> entities) { 
		List<Player> players = new ArrayList<Player>();
		Iterator<Entity> iterator = entities.iterator();
		while(iterator.hasNext()) {
			Entity entity = iterator.next();
			if(!(entity instanceof Player)) {
				iterator.remove();
			} else { 
				players.add((Player)entity);
			}
		}
		return players;
	}
}
