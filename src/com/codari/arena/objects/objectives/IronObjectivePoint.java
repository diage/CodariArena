package com.codari.arena.objects.objectives;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.api5.annotations.ArenaObjectName;
import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;

@ArenaObjectName("Iron_Objective_Point")
public class IronObjectivePoint extends TemplateObjectivePoint {
	private static final long serialVersionUID = 3638585052909346303L;
	private static final int WEIGHT_OF_OBJECTIVE_POINT = ArenaStatics.IRON_WEIGHT;
	private static final int NUMBER_OF_POINTS_AWARDED = ArenaStatics.POINT_MULTIPLIER / (WEIGHT_OF_OBJECTIVE_POINT + ArenaStatics.POINT_OFFSET);
	private static final int CAPTURE_TIME = (int) (ArenaStatics.BASE_TIME * (((float) ArenaStatics.DESIRED_WEIGHT) / (WEIGHT_OF_OBJECTIVE_POINT + ArenaStatics.TIME_OFFSET )));
	
	private static int effectDuration = 2000;
	private static int effectAmplifier = 1;
	private static PotionEffect potionEffectJump = new PotionEffect(PotionEffectType.JUMP, effectDuration, effectAmplifier);
	
	public IronObjectivePoint(Location location) {
		super(location, ArenaStatics.RADIUS);
		super.beaconBaseMaterial = Material.IRON_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = CAPTURE_TIME;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Player player : super.getTeam().getPlayers()) {
			player.addPotionEffect(potionEffectJump);
			player.setLevel(player.getLevel() + NUMBER_OF_POINTS_AWARDED);
		}		
		super.awardPoints(NUMBER_OF_POINTS_AWARDED);
	}
	
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		super.readObject();
		super.beaconBaseMaterial = Material.IRON_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = CAPTURE_TIME;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}		
}
