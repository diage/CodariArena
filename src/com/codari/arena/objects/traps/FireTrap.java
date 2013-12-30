package com.codari.arena.objects.traps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.traps.structure.TemplateTrap;
import com.codari.arena5.objects.ArenaObjectName;

@ArenaObjectName("Fire_Trap")
public class FireTrap extends TemplateTrap {
	private static final long serialVersionUID = 6383764538907844812L;

	//-----Fields-----//
	private final static int WEIGHT_OF_OBJECTIVE_POINT = 5;
	
	private int numberOfFireTicks = 40;

	public FireTrap(Location location) {
		super(location, ArenaStatics.RADIUS);
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	//-----Private Methods-----//
	/* Sets all targets on fire  */
	private void setTargetsOnFire(List<Player> players) {
		for(int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			player.setFireTicks(this.numberOfFireTicks);
		}		
	}

	@Override
	public void trigger(List<Player> players) {
		this.setTargetsOnFire(players);
	}
	
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		super.readObject();
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}
}
