package com.codari.arena.objects.itemspawner.structure;

import java.util.Random;

public enum ItemType {
	POTION,
	WEAPON,
	ARMOR;
	
	public static ItemType chooseItemType() {
		Random random = new Random(System.currentTimeMillis());
		switch(random.nextInt(5)){
		case 0:
		case 1:
		case 2:
			return ItemType.POTION;	
		case 3:
			return ItemType.WEAPON;	
		case 4:
			return ItemType.ARMOR;
		default:
			return ItemType.POTION;
		}
	}	
}
