package com.codari.arena.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.Potion;

import com.codari.api5.events.HotbarSelectEvent;
import com.codari.api5.util.BukkitUtils;
import com.codari.api5.util.scheduler.BukkitTime;

public class RoleHotbarListener implements Listener {
	public final static long GLOBAL_COOLDOWN = BukkitTime.SECOND.tickValueOf(1);

	@EventHandler
	private void hotbardom(HotbarSelectEvent e) {
		if (e.getCombatant().inArena()) {
			switch (e.getOption()) {
			case HOTBAR_1:
				return;	//Moved to RoleSwitchListenerObject
			case HOTBAR_2:
			case HOTBAR_3:
			case HOTBAR_4:
			case HOTBAR_5:
			case HOTBAR_6:
				if (e.getItem() != null) {
					Potion potion = Potion.fromItemStack(e.getItem());
					if (potion.isSplash()) {
						BukkitUtils.throwPotion(e.getCombatant().getPlayer(), e.getItem());
					} else {
						e.getCombatant().getPlayer().addPotionEffects(potion.getEffects());
					}
					e.getItem().setAmount(e.getItem().getAmount() - 1);
				}
				break;
			case HOTBAR_DROP:
				e.getCombatant().skill();
				break;
			}
			e.getCombatant().setHotbarCooldown(GLOBAL_COOLDOWN);
		}
	}

}