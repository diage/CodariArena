package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.players.combatants.Combatant;

public class IronObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 10;
	
	private int effectDuration = 200;
	private int effectAmplifier = 1;
	private PotionEffect potionEffectJump = new PotionEffect(PotionEffectType.JUMP, effectDuration, effectAmplifier);
	
	public IronObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.IRON_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 80;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Combatant combatant : super.getTeam().combatants() ) {
			Player player = combatant.getPlayerReference().getPlayer();
			player.addPotionEffect(this.potionEffectJump);
		}		
	}
}
