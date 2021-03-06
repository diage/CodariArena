package com.codari.arena.players.skills;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.api5.Codari;
import com.codari.api5.annotations.SkillName;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;
import com.codari.arena5.players.teams.Team;

@SkillName(value = "Damage Reduction")
public class DamageReductionSkill implements Skill {
	//-----Fields-----//
	private int effectDuration = 40;
	private int effectAmplification = 10;
	
	private int damageRadius = 5;
	private int damageDoneToNearbyEnemies = 10;

	@Override
	public void activateSkill(Combatant combatant) {
		Bukkit.broadcastMessage(ChatColor.GREEN + combatant.getPlayer().getName() + " is activating the DamageReductionSkill.");
		//Apply Potion Effect for damage resistance
		PotionEffect damageResistancePotionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectDuration, effectAmplification);
		damageResistancePotionEffect.apply(combatant.getPlayer());
		
		//Apply damage to nearby enemies
		Player player = combatant.getPlayer();
		Team team = Codari.getArenaManager().getTeam(combatant);
		List<Entity> entities = player.getNearbyEntities(damageRadius, damageRadius, damageRadius);
		List<Player> players = this.editList(entities);
		if(players.size() > 1) {
			for(Player targetPlayer: players) {
				if(!(Codari.getArenaManager().getTeam(Codari.getArenaManager().getCombatant(targetPlayer)).getTeamName().equals(team.getTeamName()))) {
					targetPlayer.damage(damageDoneToNearbyEnemies, player);
				} 
			}
		}
	}

	@Override
	public SkillActivation getSkillActivation() {
		return SkillActivation.SKILL;
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
