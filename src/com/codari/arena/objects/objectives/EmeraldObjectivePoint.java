package com.codari.arena.objects.objectives;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena.objects.objectives.structure.TemplateObjectivePoint;

public class EmeraldObjectivePoint extends TemplateObjectivePoint {

	public EmeraldObjectivePoint(Player player, double radius) {
		super(player, radius);
		super.beaconBaseMaterial = Material.EMERALD_BLOCK;
	}

	@Override
	public void combatantOn(List<Player> players) {
		// TODO Auto-generated method stub
		
	}
}
