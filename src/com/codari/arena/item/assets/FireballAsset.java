package com.codari.arena.item.assets;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;

import com.codari.api5.CodariI;
import com.codari.arena5.item.ItemAssetName;
import com.codari.arena5.item.assets.Spell;
import com.codari.arena5.players.combatants.Combatant;

@ItemAssetName(name = "Fireball")
public class FireballAsset implements Spell {
	
	@Override
	public String serializeToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cast(Combatant combatant) {
		final Player player = combatant.getPlayer();
		player.launchProjectile(LargeFireball.class, player.getLocation().getDirection().multiply(2));
		Bukkit.getScheduler().runTask(CodariI.INSTANCE, new Runnable() {
			@Override
			public void run() {
				player.launchProjectile(Fireball.class, player.getLocation().getDirection().multiply(2));
				Bukkit.getScheduler().runTask(CodariI.INSTANCE, new Runnable() {
					@Override
					public void run() {
						player.launchProjectile(SmallFireball.class, player.getLocation().getDirection().multiply(2));
					}
				});
			}
		});
	}

}
