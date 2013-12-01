package com.codari.arena;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.codari.api5.Codari;
import com.codari.arena.objects.gates.Gate;
import com.codari.arena.objects.itemspawner.MainItemSpawner;
import com.codari.arena.objects.itemspawner.structure.ItemSpawnerListener;
import com.codari.arena.objects.objectives.*;
import com.codari.arena.objects.objectives.structure.ObjectivePointListener;
import com.codari.arena.objects.role.switchrole.RoleSwitchListener;
import com.codari.arena.objects.traps.*;
import com.codari.arena.objects.traps.structure.TrapListener;
import com.codari.arena.rules.*;

public class CodariArenaMain extends JavaPlugin {
	@Override
	public void onEnable() {
		Codari.getLibrary().registerArenaObject(Gate.class);
		Codari.getLibrary().registerArenaObject(MainItemSpawner.class);
		Codari.getLibrary().registerArenaObject(DiamondObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(EmeraldObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(GoldObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(IronObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(ExplosionTrap.class);
		Codari.getLibrary().registerArenaObject(FireTrap.class);
		Codari.getLibrary().registerArenaObject(PoisonSnareTrap.class);
		Codari.getLibrary().registerRoleDeclaration(ArenaRoleDeclaration.class);
		Codari.getLibrary().registerWinCondition(WinCondition2v2.class);
		Bukkit.getPluginManager().registerEvents(new RoleSwitchListener(), this);
		Bukkit.getPluginManager().registerEvents(new ObjectivePointListener(), this);
		Bukkit.getPluginManager().registerEvents(new TrapListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemSpawnerListener(), this);
	}
}
