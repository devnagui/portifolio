package com.devnagui.model.triangle;

import com.devnagui.model.Polygon;

/**
 * @author devnagui
 *
 */
public class Triangle extends Polygon {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 6712800239517156524L;

	private static final int MAX_SIDES = 3;
	
	private TriangleType triangleType;

	public Triangle() {
		super(MAX_SIDES);
	}
	
	public Triangle(Double sideA,Double sideB, Double sideC) {
		this();
		getSides().set(0, sideA);
		getSides().set(1, sideB);
		getSides().set(2, sideC);
	}

	public TriangleType getTriangleType() {
		return triangleType;
	}

	public void setTriangleType(TriangleType triangleType) {
		this.triangleType = triangleType;
	}
	
	
	
}
