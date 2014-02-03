package com.codari.arena;

import java.util.Random;

import net.minecraft.util.org.apache.commons.lang3.RandomStringUtils;

public final class RandomClass implements Comparable<RandomClass>, Runnable {
	private static final Random random = new Random(System.currentTimeMillis());
	
	@Override
	public String toString() {
		return RandomStringUtils.randomAscii(random.nextInt());
	}
	
	@Override
	public int hashCode() {
		return random.nextInt();
	}
	
	@Override
	public boolean equals(Object ignore) {
		return random.nextBoolean();
	}

	@Override
	public int compareTo(RandomClass arg0) {
		return random.nextInt();
	}

	@Override
	public void run() {
		System.out.println(RandomStringUtils.randomAscii(random.nextInt()));
	}
}
