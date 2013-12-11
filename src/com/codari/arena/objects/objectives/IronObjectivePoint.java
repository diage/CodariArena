package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.players.combatants.Combatant;

@ArenaObjectName("Iron_Objective_Point")
public class IronObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 10;
	private final int NUMBER_OF_POINTS_AWARDED = 5;
	
	private int effectDuration = 200;
	private int effectAmplifier = 1;
	private PotionEffect potionEffectJump = new PotionEffect(PotionEffectType.JUMP, effectDuration, effectAmplifier);
	
	public IronObjectivePoint(Player player) {
		super(player, ArenaStatics.RADIUS);
		super.beaconBaseMaterial = Material.IRON_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 80;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Combatant combatant : super.getTeam().combatants() ) {
			Player player = combatant.getPlayer();
			player.addPotionEffect(this.potionEffectJump);
			super.awardPoints(NUMBER_OF_POINTS_AWARDED);
		}		
	}
}
