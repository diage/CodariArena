package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;

public class IronObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 10;
	
	public IronObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.IRON_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 80;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		
	}
}
