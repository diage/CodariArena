package com.codari.arena.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;

public class DiamondObjectivePoint extends TemplateObjectivePoint {

	public DiamondObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.DIAMOND;
	}

}
