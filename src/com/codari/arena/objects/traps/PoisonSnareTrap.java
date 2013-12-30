package com.codari.arena.objects.traps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena.ArenaStatics;
import com.codari.arena.objects.traps.structure.TemplateTrap;
import com.codari.arena5.objects.ArenaObjectName;

@ArenaObjectName("Poison_Snare_Trap")
public class PoisonSnareTrap extends TemplateTrap{
	private static final long serialVersionUID = -8207530364005755280L;

	//-----Fields-----//
	private final static int WEIGHT_OF_OBJECTIVE_POINT = 5;
	
	private static int slowEffectDuration = 55;
	private static int slowEffectAmplifier = 2;
	private static PotionEffect potionEffectSlow = new PotionEffect(PotionEffectType.SLOW, slowEffectDuration, slowEffectAmplifier);
	
	private static int poisonEffectDuration = 110;
	private static int poisonEffectAmplifier = 3;
	private static PotionEffect potionEffectPoison = new PotionEffect(PotionEffectType.POISON, poisonEffectDuration, poisonEffectAmplifier);

	public PoisonSnareTrap(Location location) {
		super(location, ArenaStatics.RADIUS);
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
	
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		super.readObject();
		super.weight = WEIGHT_OF_OBJECTIVE_POINT;
	}
}
