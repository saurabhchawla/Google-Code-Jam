package com.org.question008;

import java.util.ArrayList;
import java.util.List;

public class TestGround {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		List<Checker> checkerList = new ArrayList<Checker>();
		int s=4;
		checkerList.add(new Checker(new int[] {3, 1, 1, 4}));
		checkerList.add(new Checker(new int[] {4, 2, 2 ,5}));
		checkerList.add(new Checker(new int[] {5, 3, 4, 8}));
		checkerList.add(new Checker(new int[] {4, 2, 5, 9}));
		// TODO Auto-generated method stub
		HexagonGame hexagonGame = new HexagonGame();
		int minimum = hexagonGame.minimizedBipartiteMatching(checkerList, s, "horizontal");
		System.out.println("Minimum: " + minimum);
	}

}
