package com.project1.map.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.project1.map.utility.Constants;

public class Node {

	private Coordinates curPosition;
	private List<Coordinates> path;
	private List<Coordinates> actualPathTaken;
	private int priorityValue;

	public Node() {
	}

	public Node(int x, int y) {
		Coordinates k = new Coordinates(x, y);
		List<Coordinates> path = new ArrayList<>();
		this.curPosition = k;
		this.priorityValue = getManhattanDistanceToGoal(k);
		path.add(k);
		this.path = path;
	}

	public Node(int x, int y, Node parent) {
		Coordinates child = new Coordinates(x, y);
		List<Coordinates> path = new ArrayList<>(parent.path);
		this.curPosition = child;
		path.add(child);
		this.path = path;
		this.priorityValue = getManhattanDistanceToGoal(child) + this.path.size();
	}
	
	public Node(Node n1) {
		this.actualPathTaken = n1.actualPathTaken != null && !n1.actualPathTaken.isEmpty() ? new ArrayList<>(n1.actualPathTaken): new ArrayList<>();
		this.curPosition = new Coordinates(n1.curPosition);
		this.path = n1.path != null && !n1.path.isEmpty() ? new ArrayList<>(n1.path): new ArrayList<>();
		this.priorityValue = n1.priorityValue;
	}

	public Coordinates getCurPosition() {
		return curPosition;
	}

	public void setCurPosition(Coordinates curPosition) {
		this.curPosition = curPosition;
	}

	public List<Coordinates> getPath() {
		return path;
	}

	public void setPath(List<Coordinates> path) {
		this.path = path;
	}

	public int getPriorityValue() {
		return priorityValue;
	}

	public void setPriorityValue(int priorityValue) {
		this.priorityValue = priorityValue;
	}

	public void printNodeCoordinates() {
		System.out.println("Printing NODE Coordinates: " + "X: " + curPosition.getX() + " Y: " + curPosition.getY());
	}

	public void printNodePath() {
		System.out.println("Printing NODE Path: ");
		for (Coordinates a : path) {
			System.out.println("X: " + a.getX() + " Y: " + a.getY());
		}
	}

	public List<Coordinates> getActualPathTaken() {
		return actualPathTaken;
	}

	public void setActualPathTaken(List<Coordinates> actualPathTaken) {
		this.actualPathTaken = actualPathTaken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(curPosition, path, priorityValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return Objects.equals(curPosition, other.curPosition) && Objects.equals(path, other.path)
				&& priorityValue == other.priorityValue;
	}

	public static int getManhattanDistanceToGoal(Coordinates currPos) {
		Integer distance = 0;
		Integer x = currPos.getX();
		Integer y = currPos.getY();
		Coordinates goal = new Coordinates(Constants.GRIDSIZE - 1, Constants.GRIDSIZE - 1);
		distance = Math.abs(x - goal.getX()) + Math.abs(y - goal.getY());
		return distance.intValue();
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.curPosition);
	}

}
