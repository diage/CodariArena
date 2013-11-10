package com.codari.arena.objects.traps;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.entity.Player;


public class ExplosionTrap extends TemplateTrap {

	public ExplosionTrap(Player player, double radius) {
		super(player, radius, RandomStringUtils.randomAscii(25));
	}
}
