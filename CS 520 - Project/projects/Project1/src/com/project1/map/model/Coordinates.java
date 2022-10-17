package com.project1.map.model;

import java.util.Objects;

public class Coordinates{
	
	private int x;
	private int y;
	
	public Coordinates() {}
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordinates(Coordinates c1) {
		this.x = c1.x;
		this.y = c1.y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		return x == other.x && y == other.y;
	}
	
	@Override
	public String toString() {
		return "X:" + x + ",Y:" + y;
	}
	
	

}
