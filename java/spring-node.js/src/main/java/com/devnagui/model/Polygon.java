package com.devnagui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author devnagui
 */
public abstract class Polygon implements Serializable {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -3652054465129564700L;
	
	/**
	 * Arrays.asList enforces the amount of sizes, for instance:
	 * 
	 * Triangles(3)
	 * Rectangles(4)
	 * etc.
	 * 
	 * @param numberOfSides
	 */
	public Polygon(Integer numberOfSides) {
		sides = Arrays.asList(new Double[numberOfSides]);
	}
	
	/**
	 * For super polygons ;)
	 */
	public Polygon(){
		sides = new ArrayList<Double>();
	}
	
	private List<Double> sides;

	/**
	 * @return An unmodifiable list of sides
	 */
	public List<Double> getSides() {
		return sides;
	}

}
