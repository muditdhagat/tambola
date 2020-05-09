package org.mudit.tambola.core;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class TicketHelper {
	
	static int generateRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	static int getNumberOfElementsInSet(List<List<Integer>> set) {
		int count = 0;
		for (List<Integer> li : set)
			count += li.size();
		return count;
	}

	public static int getNumberOfElementsInDataset(Map<Integer, List<Integer>> dataset) {
		int count = 0;
		for(List<Integer> column : dataset.values()) {
			count += column.size();
		}
		return count;
	}

}
