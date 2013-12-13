package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.objects.ArenaObjectName;

@ArenaObjectName("Diamond_Objective_Point")
public class DiamondObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 5;
	private final int NUMBER_OF_POINTS_AWARDED = 20;
	
	private int effectDuration = 200;
	private int effectAmplifier = 1;
	private PotionEffect potionEffectFast = new PotionEffect(PotionEffectType.SPEED, effectDuration, effectAmplifier);

	public DiamondObjectivePoint(Player player) {
		super(player, ArenaStatics.RADIUS);
		super.beaconBaseMaterial = Material.DIAMOND_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 240;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Player player : super.getTeam().getPlayers() ) {
			player.addPotionEffect(this.potionEffectFast);
			super.awardPoints(NUMBER_OF_POINTS_AWARDED);
		}
	}
}
