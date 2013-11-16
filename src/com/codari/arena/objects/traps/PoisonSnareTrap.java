package com.codari.arena.objects.traps;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.objects.traps.structure.TemplateTrap;

public class PoisonSnareTrap extends TemplateTrap{
	//-----Fields-----//
	private final int WEIGHT_OF_OBJECTIVE_POINT = 5;
	
	private int slowEffectDuration = 55;
	private int slowEffectAmplifier = 10;
	private PotionEffect potionEffectSlow = new PotionEffect(PotionEffectType.SLOW, slowEffectDuration, slowEffectAmplifier);
	
	private int poisonEffectDuration = 519;
	private int poisonEffectAmplifier = 10;
	private PotionEffect potionEffectPoison = new PotionEffect(PotionEffectType.POISON, poisonEffectDuration, poisonEffectAmplifier);

	public PoisonSnareTrap(Player player, double radius) {
		super(player, radius);
		super.clayStoneMetaDataValue = 5;
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}

	@Override
	public void trigger(List<Player> players) {
		for(Player triggerTarget: players) {
			potionEffectSlow.apply(triggerTarget);
			potionEffectPoison.apply(triggerTarget);
		}	
	}
}
