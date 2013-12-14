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
	private static final long serialVersionUID = 7270297227849100471L;
	private final int WEIGHT_OF_OBJECTIVE_POINT = 5;
	private final int NUMBER_OF_POINTS_AWARDED = 20;
	
	private static int effectDuration = 200;
	private static int effectAmplifier = 1;
	private static PotionEffect potionEffectFast = new PotionEffect(PotionEffectType.SPEED, effectDuration, effectAmplifier);

	public DiamondObjectivePoint(Player player) {
		super(player, ArenaStatics.RADIUS);
		super.beaconBaseMaterial = Material.DIAMOND_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 240;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Player player : super.getTeam().getPlayers() ) {
			player.addPotionEffect(potionEffectFast);
			player.setLevel(player.getLevel() + this.NUMBER_OF_POINTS_AWARDED);
			super.awardPoints(this.NUMBER_OF_POINTS_AWARDED);
		}
	}
}
