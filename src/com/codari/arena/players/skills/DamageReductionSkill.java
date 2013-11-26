package com.codari.arena.players.skills;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;
import com.codari.arena5.players.teams.Team;

public class DamageReductionSkill implements Skill {
	//-----Fields-----//
	private int effectDuration = 40;
	private int effectAmplification = 10;
	
	private int damageRadius = 5;
	private int damageDoneToNearbyEnemies = 10;

	@Override
	public void activateSkill(Combatant combatant) {
		//Apply Potion Effect for damage resistance
		PotionEffect damageResistancePotionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectDuration, effectAmplification);
		damageResistancePotionEffect.apply(combatant.getPlayerReference().getPlayer());
		
		//Apply damage to nearby enemies
		Player player = combatant.getPlayerReference().getPlayer();
		Team team = Codari.INSTANCE.getArenaManager().getTeam(combatant);
		List<Entity> entities = player.getNearbyEntities(damageRadius, damageRadius, damageRadius);
		List<Player> players = this.editList(entities);
		if(players.size() > 1) {
			for(Player targetPlayer: players) {
				if(!(Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(targetPlayer)).equals(team))) {
					double currentHealth = targetPlayer.getHealth();
					targetPlayer.setHealth(currentHealth - damageDoneToNearbyEnemies);
				} 
			}
		}
	}

	@Override
	public SkillActivation getSkillActivation() {
		return SkillActivation.BLOCK;
	}
	
	private List<Player> editList(List<Entity> entities) { 
		List<Player> players = new ArrayList<Player>();
		Iterator<Entity> iterator = entities.iterator();
		while(iterator.hasNext()) {
			Entity entity = iterator.next();
			if(entity instanceof Player) {
				players.add((Player)entity);
			} 
		}
		return players;
	}
}
