package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.objects.ArenaObjectName;

@ArenaObjectName("Gold_Objective_Point")
public class GoldObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 7;
	private final int NUMBER_OF_POINTS_AWARDED = 10;
	
	private int effectDuration = 200;
	private int effectAmplifier = 1;
	private PotionEffect potionEffectIncaseDamage = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, effectDuration, effectAmplifier);

	public GoldObjectivePoint(Player player) {
		super(player, ArenaStatics.RADIUS);
		super.beaconBaseMaterial = Material.GOLD_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 160;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Player player : super.getTeam().getPlayers()) {
			player.addPotionEffect(this.potionEffectIncaseDamage);
			player.setLevel(player.getLevel() + this.NUMBER_OF_POINTS_AWARDED);
			super.awardPoints(this.NUMBER_OF_POINTS_AWARDED);
		}
	}
}
