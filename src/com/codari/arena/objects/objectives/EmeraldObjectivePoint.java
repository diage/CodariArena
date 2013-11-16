package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;

public class EmeraldObjectivePoint extends TemplateObjectivePoint {
	private final int WEIGHT_OF_OBJECTIVE_POINT = 2;

	public EmeraldObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.EMERALD_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 320;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void awardObjective() {
		// TODO Auto-generated method stub
		
	}
}
