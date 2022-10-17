package com.project1.map.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Map {
	
	private int gridSize;
	private int[][] grid;
	private List<Coordinates> finalPathToGoal;
	private List<Coordinates> allReachableNodes;
	private HashMap<String, List<Coordinates>> reachableNodeWisePathToGoal;
	private String mapId;
	
	public Map() {
		this.mapId = UUID.randomUUID().toString();

	}
	
	//Required to duplicate an object of this class
	public Map(Map map) {
		this.gridSize = map.getGridSize();
		this.grid = map.getGrid();
		this.finalPathToGoal = new ArrayList<>(map.getFinalPathToGoal());
		this.allReachableNodes = new ArrayList<>(map.getAllReachableNodes());
	}

	public int getGridSize() {
		return gridSize;
	}
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
	public int[][] getGrid() {
		return grid;
	}
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}
	public List<Coordinates> getFinalPathToGoal() {
		return finalPathToGoal;
	}
	public void setFinalPathToGoal(List<Coordinates> finalPathToGoal) {
		this.finalPathToGoal = finalPathToGoal;
	}
	public List<Coordinates> getAllReachableNodes() {
		return allReachableNodes;
	}
	public void setAllReachableNodes(List<Coordinates> allReachableNodes) {
		this.allReachableNodes = allReachableNodes;
	}

	public HashMap<String, List<Coordinates>> getReachableNodeWisePathToGoal() {
		return reachableNodeWisePathToGoal;
	}

	public void setReachableNodeWisePathToGoal(HashMap<String, List<Coordinates>> reachableNodeWisePathToGoal) {
		this.reachableNodeWisePathToGoal = reachableNodeWisePathToGoal;
	}	
	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

}
