package com.codari.arena.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveQueueCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("leavequeue") && args.length == 0) {
			//Player player = (Player) sender;
			//TODO - Remove team from queue
		}
		return false;
	}
}
