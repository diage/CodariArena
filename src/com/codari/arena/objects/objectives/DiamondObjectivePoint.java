package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.players.combatants.Combatant;

@ArenaObjectName("Diamond_Objective_Point")
public class DiamondObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 5;
	//private final int NUMBER_OF_POINTS_AWARDED = 25;
	
	private int effectDuration = 200;
	private int effectAmplifier = 1;
	private PotionEffect potionEffectFast = new PotionEffect(PotionEffectType.SPEED, effectDuration, effectAmplifier);

	public DiamondObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.DIAMOND_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 240;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Combatant combatant : super.getTeam().combatants() ) {
			Player player = combatant.getPlayerReference().getPlayer();
			player.addPotionEffect(this.potionEffectFast);
			//TODO - Award the points to the team
		}
	}
}
