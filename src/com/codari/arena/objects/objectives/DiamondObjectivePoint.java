package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.players.combatants.Combatant;

public class DiamondObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 5;
	private int slowEffectDuration = 55;
	private int slowEffectAmplifier = 10;
	private PotionEffect potionEffectSlow = new PotionEffect(PotionEffectType.SLOW, slowEffectDuration, slowEffectAmplifier);

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
			player.addPotionEffect(this.potionEffectSlow);
		}
	}
}
