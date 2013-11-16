package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.players.combatants.Combatant;

public class GoldObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 7;
	
	private int effectDuration = 200;
	private int effectAmplifier = 1;
	private PotionEffect potionEffectIncaseDamage = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, effectDuration, effectAmplifier);

	public GoldObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.GOLD_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 160;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Combatant combatant : super.getTeam().combatants() ) {
			Player player = combatant.getPlayerReference().getPlayer();
			player.addPotionEffect(this.potionEffectIncaseDamage);
		}
	}
}
