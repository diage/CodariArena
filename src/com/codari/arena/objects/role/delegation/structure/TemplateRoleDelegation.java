package com.codari.arena.objects.role.delegation.structure;

import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena.ArenaStatics;
import com.codari.arena5.Arena;
import com.codari.arena5.objects.persistant.RoleSelectionObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.teams.Team;
/**
 * These need complete reworks to implement the correct ArenaObject type!
 * @author Ryan
 *
 */
public abstract class TemplateRoleDelegation implements RoleSelectionObject {
	private Block roleDelegationBlock;
	protected Material material;
	protected Combatant combatant;
	
	private final String MELEE_ROLE_MESSAGE = "You have been assigned the melee role.";
	private final String RANGED_ROLE_MESSAGE = "You have been assigned the ranged role.";

	public TemplateRoleDelegation(Player player) {
		this.roleDelegationBlock = player.getLocation().getBlock();
		this.roleDelegationBlock.setType(material);
	}

	@Override
	public void interact() {
		Player player, teamMatePlayer;
		Combatant teamMate = combatant.getTeam().getTeamMates(combatant).get(0);
		player = combatant.getPlayer();
		teamMatePlayer = teamMate.getPlayer();
		String arenaName = teamMate.getArenaName();
		Role role = this.roleSelect();
		this.combatant.setRole(role);
		switch(role.getName()) {
		case ArenaStatics.MELEE:
			teamMate.setRole(Codari.getArenaManager().getExistingRole(arenaName, ArenaStatics.RANGED));
			player.sendMessage(MELEE_ROLE_MESSAGE);
			teamMatePlayer.sendMessage(RANGED_ROLE_MESSAGE);
			break;
		case ArenaStatics.RANGED:
			teamMate.setRole(Codari.getArenaManager().getExistingRole(arenaName, ArenaStatics.MELEE));
			player.sendMessage(RANGED_ROLE_MESSAGE);
			teamMatePlayer.sendMessage(MELEE_ROLE_MESSAGE);
			break;
		default:
			return;
		}	
		
		if(this.checkIfAllPlayersHaveARole()) {
			this.hide();
		}
	}

	@Override
	public void reveal() {
		this.roleDelegationBlock.setType(material);
	}

	@Override
	public void hide() {
		this.roleDelegationBlock.getState().update(true);
	}
	
	private boolean checkIfAllPlayersHaveARole() {
		Arena arena = Codari.getArenaManager().getArena(combatant.getArenaName());
		/*Check if all the players have selected a role and if they have...de-spawn role delegation objects*/
		for(Entry<String, Team> teamKey : arena.getTeams().entrySet()) {
			Team team = arena.getTeams().get(teamKey);
			for(Player player : team.getPlayers()) {
				Combatant combatant = Codari.getArenaManager().getCombatant(player);
				if(combatant.getRole() == null) {
					return false;
				}
			}
		}	
		return true;
	}
}
