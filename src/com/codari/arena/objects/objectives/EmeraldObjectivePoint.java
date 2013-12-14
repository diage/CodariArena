package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.objects.ArenaObjectName;

@ArenaObjectName("Emerald_Objective_Point")
public class EmeraldObjectivePoint extends TemplateObjectivePoint {
	private static final long serialVersionUID = -4958614955612105201L;
	private final int WEIGHT_OF_OBJECTIVE_POINT = 2;
	private final int NUMBER_OF_POINTS_AWARDED = 15;
	
	private static int effectDuration = 200;
	private static int effectAmplifier = 1;
	private static PotionEffect potionEffectRegeneration = new PotionEffect(PotionEffectType.REGENERATION, effectDuration, effectAmplifier);

	public EmeraldObjectivePoint(Player player) {
		super(player, ArenaStatics.RADIUS);
		super.beaconBaseMaterial = Material.EMERALD_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 320;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Player player : super.getTeam().getPlayers() ) {
			player.addPotionEffect(potionEffectRegeneration);
			player.setLevel(player.getLevel() + this.NUMBER_OF_POINTS_AWARDED);
			super.awardPoints(this.NUMBER_OF_POINTS_AWARDED);
		}
	}
}
