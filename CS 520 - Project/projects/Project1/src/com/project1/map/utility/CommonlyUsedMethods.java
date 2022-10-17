package com.project1.map.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.project1.map.model.Coordinates;
import com.project1.map.model.Map;
import com.project1.map.model.Node;

public class CommonlyUsedMethods {
	
	public static Random randVar = new Random();
	
	public static boolean compareCoordinateWithListOfCoordinates(Coordinates a, ArrayList<Coordinates> b) {				
		if(b.contains(a)) {
			return true;
		}
		return false;
	}
	
	public static Boolean containsAny(List<Coordinates> l1, List<Coordinates> l2) {
        for (Coordinates cood : l1) {
            if (l2.contains(cood)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
	
	public static void printMap(List<Map> newMaps, String methodName) {
		for(Map single: newMaps) {
			int gridSize = single.getGridSize();
			for(int i = 0; i < gridSize; i++) {
				String row = "";
				for (int j = 0; j < gridSize; j++) {
					row = row.concat(single.getGrid()[i][j] + " ");
				}
			}
		}
	}
	
	public static String Array2DToString(int[][] mapGrid) {
		return Arrays.deepToString(mapGrid);
	}
	
	public static Integer getManhattanDistance(Node a, Node b) {
		Coordinates A = a.getCurPosition();
		Coordinates B = b.getCurPosition();
		return Math.abs(A.getX() - B.getX()) + Math.abs(A.getY() - B.getY());
	}
	
	/**
	 * Check if the values of x and y are out of map bounds
	 * @param x
	 * @param y
	 * @param gridSize
	 * @return true if out of bounds
	 */
	public static Boolean checkForOutOfBounds(int x, int y, int gridSize) {
		if(x < 0 || y < 0 || x >= gridSize || y >= gridSize) {
			return true;
		}
		return false;
	}
	
	public static int getMaxValueIndex(int[] a) {
		int maxIndex = 0;
		int len = a.length;
		for(int i = 1; i < len; i++) {
			if(a[i] > a[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	public static Coordinates getCoordinatesFromString(String cood) {
		List<String> c = new ArrayList<>(Arrays.asList(cood.split(",")));
		Integer x = Integer.parseInt(c.get(0).split(":")[1]);
		Integer y = Integer.parseInt(c.get(1).split(":")[1]);
		return new Coordinates(x, y);
	}
	
	/**
	 * Makes a deep copy of a hashmap
	 * @param map
	 * @return
	 */
	public static HashMap<String, List<Coordinates>> copyHashMap(Map map) {
		HashMap<String, List<Coordinates>> copy = new HashMap<>();
		if(map.getReachableNodeWisePathToGoal() != null && map.getReachableNodeWisePathToGoal().isEmpty()) {
			for(Entry<String, List<Coordinates>> pair: map.getReachableNodeWisePathToGoal().entrySet()) {
				copy.put(pair.getKey(), new ArrayList<Coordinates>(pair.getValue()));
			}
			return copy;
		}
		return null;
	}
	
	public static ArrayList<Node> createDeepCopy(ArrayList<Node> a) {
		ArrayList<Node> output = new ArrayList<>();
		for(Node b: a) {
			output.add(new Node(b));
		}
		
		return output;
	}
	
}
