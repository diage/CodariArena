package com.codari.arena.commands;

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

public class JoinQueueCommand implements CommandExecutor {

	/* TWO PROBLEMS: Need a method to set teams in the arena and a way to set an arena for a team. 
	 * 	--Arena.start(Team...teams) and listen for an event to fire for arena start.
	 * Right now getting a map of teams in an arena only returns a copy. Need a way to actually set teams for an arena. */
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("joinarena") && args.length == 1) {
			Player player = (Player) sender;
			Team team = Codari.getArenaManager().getCombatant(player).getTeam();
			Arena arena = Codari.getArenaManager().getArena(args[0]);
			
			if(checkIfArenaIsValid(arena, player) && checkIfPlayerHasTeam(team, player)) {  				
				//TODO - Add Team to QUEUE	

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
}
