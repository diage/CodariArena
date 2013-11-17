package com.codari.arena.players.skills;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;

public class DamageReductionSkill implements Skill {
	//-----Fields-----//
	private int effectDuration = 40;
	private int effectAmplification = 10;

	//TODO - Deal damage to enemies nearby
	@Override
	public void activateSkill(Combatant combatant) {
		PotionEffect damageResistancePotionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectDuration, effectAmplification);
		damageResistancePotionEffect.apply(combatant.getPlayerReference().getPlayer());
	}

	@Override
	public SkillActivation getSkillActivation() {
		return SkillActivation.BLOCK;
	}
}
