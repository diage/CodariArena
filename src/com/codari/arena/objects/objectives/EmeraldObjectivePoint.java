package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;

public class EmeraldObjectivePoint extends TemplateObjectivePoint {

	public EmeraldObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.EMERALD_BLOCK;
		super.numberOfPointsToCaptureObjectivePoint = 320;
	}

	@Override
	public void awardObjective() {
		// TODO Auto-generated method stub
		
	}
}
