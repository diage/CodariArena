package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;
import com.codari.arena5.objects.ArenaObjectName;

@ArenaObjectName("Iron_Objective_Point")
public class IronObjectivePoint extends TemplateObjectivePoint {
	private static final long serialVersionUID = 3638585052909346303L;
	private final int WEIGHT_OF_OBJECTIVE_POINT = 10;
	private final int NUMBER_OF_POINTS_AWARDED = 5;
	
	private static int effectDuration = 200;
	private static int effectAmplifier = 1;
	private static PotionEffect potionEffectJump = new PotionEffect(PotionEffectType.JUMP, effectDuration, effectAmplifier);
	
	public IronObjectivePoint(Player player) {
		super(player, ArenaStatics.RADIUS);
		super.beaconBaseMaterial = Material.IRON_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 80;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		for(Player player : super.getTeam().getPlayers()) {
			player.addPotionEffect(potionEffectJump);
			player.setLevel(player.getLevel() + this.NUMBER_OF_POINTS_AWARDED);
			super.awardPoints(this.NUMBER_OF_POINTS_AWARDED);
		}		
	}
}
