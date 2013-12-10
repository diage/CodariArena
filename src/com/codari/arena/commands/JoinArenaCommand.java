package com.codari.arena.commands;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.Arena;
import com.codari.arena5.players.teams.Team;

/*
 * Need to create interim object called a 'Queue' - Simple implementation is easy, you pass in an arena to the constructor
 * and it just gets the number of teams required for that arena and potentially the team size. Once the number of teams in 
 * the queue is equal to the number required, it begins the arena and the arena can check a lot of the stuff for us. 
 * 
 * A more complicated implementation is to do a lot of the checks within the queue in which case you will either need to copy over
 * variables from the arena or save a reference to the arena. You will most likely need a queue manager within arena manager which ties
 * a queue to an ArenaName. 
 * 
 * In either case, the CommandSender ought to only check the parameters passed in to it and then do nothing else, let all the other objects
 * do those. Basically just check if a queue with the provided name exists, if so, try to add to queue and let the queue do the rest.
 * 
 * Whether you put the checks in the queue or the Arena Start is not terribly relevant at the moment. 
 */

public class JoinArenaCommand implements CommandExecutor {

	/* TWO PROBLEMS: Need a method to set teams in the arena and a way to set an arena for a team. 
	 * 	--Arena.start(Team...teams) and listen for an event to fire for arena start.
	 * Right now getting a map of teams in an arena only returns a copy. Need a way to actually set teams for an arena. */
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("joinarena") && args.length == 1) {
			Player player = (Player) sender;
			Team team = Codari.getArenaManager().getCombatant(player).getTeam();
			Arena arena = Codari.getArenaManager().getArena(args[0]);

			if(checkIfArenaIsValid(arena, player) && 					//check if arena is not null
					checkIfPlayerHasTeam(team, player) && 				//check if the player is currently in a team
					checkIfMatchIsNotInProgress(arena, player) &&		//check if the match is not already in progress
					checkTeamSize(team, arena, player) &&				//check if the player's team size matches the arena's team size
					checkIfTeamIsNotAlreadyInAnArena(team, player)) {	//check to make sure a team doesn't join two arenas at the same time

				if(arena.getTeams().size() < arena.getGameRule().getTeamSize()) {		//if there isn't already a team waiting
					arena.getTeams().put(team.getTeamName(), team);
					for(Player teamPlayer : team.getPlayers()) {
						teamPlayer.sendMessage("Waiting for a worthy opponent.");
					}
					return true;
				} else {																//if there is a team already waiting
					arena.getTeams().put(team.getTeamName(), team);	//This won't work at all. I'm sure you've figured that out...
					for(Map.Entry<String, Team> teamEntry: arena.getTeams().entrySet()) {
						Team arenaTeam = teamEntry.getValue();
						for(Player teamPlayer : arenaTeam.getPlayers()) {
							teamPlayer.sendMessage("Opponents found! The match will start soon.");
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkIfArenaIsValid(Arena arena, Player player) {
		if(arena == null) {
			player.sendMessage(ChatColor.RED + "That arena does not exist!");
		}
		return true;
	}

	private static boolean checkIfPlayerHasTeam(Team team, Player player) {
		if(team == null) {
			player.sendMessage(ChatColor.RED + "You can't join the arena if you're not on a team!");
			return false;
		}
		return true;
	}

	private static boolean checkIfMatchIsNotInProgress(Arena arena, Player player) {
		if(arena.isMatchInProgress()) {
			player.sendMessage(ChatColor.RED + "The arena you're trying to join is already in progress.");
			return false;
		}
		return true;
	}

	private static boolean checkTeamSize(Team team, Arena arena, Player player) {
		int teamSize = team.getTeamSize();
		int arenaTeamSize = arena.getGameRule().getTeamSize();
		if(teamSize != arenaTeamSize) {
			player.sendMessage(ChatColor.RED + "You're team has to have " + arena.getGameRule().getTeamSize() + " players to join that arena!");
			return false;
		}
		return true;
	}

	private static boolean checkIfTeamIsNotAlreadyInAnArena(Team team, Player player) {
		if(team.getArena() != null) {
			player.sendMessage(ChatColor.RED + "You can't join another arena if your team is already part of one!");
			return false;
		}
		return true;
	}
}
