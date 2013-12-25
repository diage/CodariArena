package com.codari.arena;

public class OldRoleSwitchAttempts {
	/** TRY ONE! **/
//	@EventHandler()
//	public void onPlayerRightClick(PlayerInteractEvent e) {
//		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//			if(e.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
//				if((e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equals(RoleSwitch.ROLE_SWAP_DISPLAY_NAME))) {
//					Player player, teamMatePlayer;
//					Combatant combatant, teamMate;
//
//					player = (Player) e.getPlayer();
//					combatant = Codari.getArenaManager().getCombatant(player);
//					teamMate = combatant.getTeam().getTeamMates(combatant).get(0);
//
//					e.setCancelled(true);
//
//					if(teamMate != null) {
//						ItemStack playerRoleSwitchItem, teamMateRoleSwitchItem;
//
//						//Enchantment enchantment = RoleObjectEnchantment.INSTANCE;
//						Enchantment enchantment = Enchantment.KNOCKBACK;
//
//						teamMatePlayer = teamMate.getPlayer();
//						playerRoleSwitchItem = player.getInventory().getItem(RoleSwitch.INVENTORY_SLOT_NUMBER);
//						teamMateRoleSwitchItem = teamMatePlayer.getInventory().getItem(RoleSwitch.INVENTORY_SLOT_NUMBER);
//
//						if(playerRoleSwitchItem.containsEnchantment(enchantment)) {	//Problem here
//							Bukkit.broadcastMessage("Accepted role switch!");
//							teamMate.swapRole(combatant.swapRole(teamMate.getRole())); //Using this method will fire an event. 
//							if(teamMate.getRole().getName().equalsIgnoreCase("Melee")) {
//								teamMate.getPlayer().getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.MELEE.getItemStack());
//								player.getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.RANGED.getItemStack());
//							} else {
//								teamMate.getPlayer().getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.RANGED.getItemStack());
//								player.getInventory().setItem(RoleSwitch.INVENTORY_SLOT_NUMBER, RoleObjectItemTypes.MELEE.getItemStack());
//							}
//						} else {
//							String roleSwitchMessage = "Your teamate would like to switch roles with you. Right click the role switch icon on the "
//									+ "first slot of your hotbar if you would like to switch.";
//							//teamMateRoleSwitchItem.addEnchantment(enchantment, 1);
//							teamMateRoleSwitchItem.addUnsafeEnchantment(enchantment, 1);
//							Bukkit.broadcastMessage("Added enchantment to item!");
//							teamMate.getPlayer().sendMessage(roleSwitchMessage);
//						}
//					} else {
//						player.sendMessage(ChatColor.RED + "You have no teamate to switch roles with.");
//					}
//				}
//			}
//		}
//	}
	/**TRY TWO!!**/
//	Player player, teamMatePlayer;
//	Combatant combatant, teamMateCombatant;
//
//	player = e.getCombatant().getPlayer();
//	combatant = e.getCombatant();
//	teamMateCombatant = combatant.getTeam().getTeamMates(combatant).get(0);
//	
//	if(teamMateCombatant != null) {
//		Bukkit.broadcastMessage(ChatColor.BLUE + "Your teamate is " + teamMateCombatant.getPlayer().getName());
//		ItemStack playerRoleSwitchItem, teamMateRoleSwitchItem;
//		Enchantment enchantment = Enchantment.SILK_TOUCH;
//		int sloter = e.getOption().getInventorySlot();
//		teamMatePlayer = teamMateCombatant.getPlayer();
//		playerRoleSwitchItem = e.getItem();
//		teamMateRoleSwitchItem = teamMatePlayer.getInventory().getItem(sloter);
//
//		if(playerRoleSwitchItem.containsEnchantment(enchantment)) {
//			teamMateCombatant.swapRole(combatant.swapRole(teamMateCombatant.getRole())); 
//			if(teamMateCombatant.getRole().getName().equalsIgnoreCase(ArenaStatics.MELEE)) {
//				teamMateCombatant.getPlayer().getInventory().setItem(sloter, RoleObjectItemTypes.MELEE.getItemStack());
//				player.getInventory().setItem(sloter, RoleObjectItemTypes.RANGED.getItemStack());
//			} else {
//				teamMateCombatant.getPlayer().getInventory().setItem(sloter, RoleObjectItemTypes.RANGED.getItemStack());
//				player.getInventory().setItem(sloter, RoleObjectItemTypes.MELEE.getItemStack());
//			}
//			teamMatePlayer.updateInventory();
//			player.updateInventory();
//			teamMatePlayer.sendMessage(ChatColor.GREEN + "Your role is now " + ChatColor.DARK_GREEN + teamMateCombatant.getRole().getName());
//			player.sendMessage(ChatColor.GREEN + "Your role is now " + ChatColor.DARK_GREEN + combatant.getRole().getName());
//		} else {
//			teamMateRoleSwitchItem.addUnsafeEnchantment(enchantment, 1);
//			teamMatePlayer.sendMessage(ChatColor.AQUA + "Your teammate is requesting a role switch.");
//			teamMatePlayer.updateInventory();
//		}
//	} else {
//		player.sendMessage(ChatColor.RED + "You have no teamate to switch roles with.");
//	}
	/**STRIKE THREE!!!**/
//	private final static Enchantment ROLL_SWCH = Enchantment.SILK_TOUCH;/*new CustomEnchantment(2525) {
//	@Override
//	public boolean isVisible() {
//		return true;
//	}
//
//	@Override
//	public boolean canEnchantItem(ItemStack ignore) {
//		return true;
//	}
//
//	@Override
//	public boolean conflictsWith(Enchantment ignore) {
//		return false;
//	}
//
//	@Override
//	public EnchantmentTarget getItemTarget() {
//		return EnchantmentTarget.ALL;
//	}
//
//	@Override
//	public int getMaxLevel() {
//		return 0;
//	}
//
//	@Override
//	public String getName() {
//		return ";";
//	}
//
//	@Override
//	public int getStartLevel() {
//		return 0;
//	}
//};
//static {
//	Codari.getEnchantmentManager().registerEnchantment(ROLL_SWCH);
//}*/
//	Combatant requester = e.getCombatant();
//	Combatant requeste = requester.getTeam().getTeamMates(requester).get(0);
//	int slot = e.getOption().getInventorySlot();
//	if (requester.inArena()) {
//		ItemStack item = e.getItem();
//		//TODO DOTO
//		Bukkit.broadcastMessage("" + item.containsEnchantment(ROLL_SWCH));
//		if (item.containsEnchantment(ROLL_SWCH)) {
//			requester.swapRole(requeste.swapRole(requester.getRole()));
//			ItemStack requesterItem = requester.getPlayer().getInventory().getItem(slot);
//			ItemStack requesteItem = requeste.getPlayer().getInventory().getItem(slot);
//			requesterItem.removeEnchantment(ROLL_SWCH);
//			requesteItem.removeEnchantment(ROLL_SWCH);
//			requester.getPlayer().getInventory().setItem(slot, requesteItem);
//			requeste.getPlayer().getInventory().setItem(slot, requesterItem);
//			requester.getPlayer().updateInventory();
//			requeste.getPlayer().updateInventory();
//			requester.getPlayer().sendMessage(ChatColor.GREEN + "Your role is now " + ChatColor.DARK_GREEN + requester.getRole().getName());
//			requeste.getPlayer().sendMessage(ChatColor.GREEN + "Your role is now " + ChatColor.DARK_GREEN + requeste.getRole().getName());
//		} else {
//			ItemStack requesteItem = requeste.getPlayer().getInventory().getItem(e.getOption().getInventorySlot());
//			requesteItem.addEnchantment(ROLL_SWCH, 0);
//			requeste.getPlayer().getInventory().setItem(e.getOption().getInventorySlot(), requesteItem);
//			requeste.getPlayer().sendMessage(ChatColor.AQUA + "Your teammate is requesting a role switch.");
//		}
//	}
}
