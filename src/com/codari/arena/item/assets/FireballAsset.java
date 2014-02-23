package com.codari.arena.item.assets;

import java.util.List;

import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;

import com.codari.arena5.item.ItemAssetInformation;
import com.codari.arena5.item.assets.Spell;
import com.codari.arena5.players.combatants.Combatant;

@ItemAssetInformation(name = "Fireball", links = {FireballAsset.DAMAGE_MULTIPLIER_ONE, FireballAsset.DAMAGE_MULTIPLIER_TWO})
public class FireballAsset implements Spell {
	
	public static final String DAMAGE_MULTIPLIER_ONE = "Damage Multiplier One";
	public static final String DAMAGE_MULTIPLIER_TWO = "Damage Multiplier Two";
	
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
	}

}
