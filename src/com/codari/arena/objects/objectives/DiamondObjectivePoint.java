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

@ArenaObjectName("Diamond_Objective_Point")
public class DiamondObjectivePoint extends TemplateObjectivePoint {
	private static final long serialVersionUID = 7270297227849100471L;
	private static final int WEIGHT_OF_OBJECTIVE_POINT = ArenaStatics.DIAMOND_WEIGHT;
	private static final int NUMBER_OF_POINTS_AWARDED = ArenaStatics.POINT_MULTIPLIER / (WEIGHT_OF_OBJECTIVE_POINT + ArenaStatics.POINT_OFFSET);
	private static final int CAPTURE_TIME = (int) (ArenaStatics.BASE_TIME * (((float) ArenaStatics.DESIRED_WEIGHT) / (WEIGHT_OF_OBJECTIVE_POINT + ArenaStatics.TIME_OFFSET )));
	
	private static int effectDuration = 2000;
	private static int effectAmplifier = 1;
	private static PotionEffect potionEffectFast = new PotionEffect(PotionEffectType.SPEED, effectDuration, effectAmplifier);

	public DiamondObjectivePoint(Location location) {
		super(location, ArenaStatics.RADIUS);
		super.beaconBaseMaterial = Material.DIAMOND_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = CAPTURE_TIME;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Player player : super.getTeam().getPlayers() ) {
			player.addPotionEffect(potionEffectFast);
			player.setLevel(player.getLevel() + NUMBER_OF_POINTS_AWARDED);
		}
		super.awardPoints(NUMBER_OF_POINTS_AWARDED);
	}
	
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		super.readObject();
		super.beaconBaseMaterial = Material.DIAMOND_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = CAPTURE_TIME;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}
}
