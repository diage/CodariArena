package com.codari.arena.players.skills;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.codari.api5.annotations.SkillName;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;

@SkillName(value = "Teleport")
public class TeleportSkill implements Skill {
	//-----Fields-----//
	private int numberOfBlocksAPlayerCanTeleport = 25;

	@Override
	public void activateSkill(Combatant combatant) {
		Bukkit.broadcastMessage(ChatColor.GREEN + combatant.getPlayer().getName() + " is activating the TeleportSkill.");
		Player player = combatant.getPlayer();
		@SuppressWarnings("deprecation")
		List<Block> lastTwoBlocks = player.getLastTwoTargetBlocks(null, this.numberOfBlocksAPlayerCanTeleport);
		player.teleport(lastTwoBlocks.get(1).getLocation(), TeleportCause.PLUGIN);
		/*Location location = player.getLocation();
		BlockIterator blockIterator = new BlockIterator(location, numberOfBlocksAPlayerCanTeleport);
		Block target = null;
		while(blockIterator.hasNext()) {
			Block next = blockIterator.next();
			if(next.getType() != Material.AIR) {
				break;
			} else {
				target = next;
			}
		}
		if(target != null) {
			player.teleport(target.getLocation());
		}*/
	}

	@Override
	public SkillActivation getSkillActivation() {
		return SkillActivation.SKILL;
	}

}
