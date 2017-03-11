package com.devnagui.model.triangle;

public enum TriangleType {
	EQUILATERAL(3),
	ISOSCELES(2),
	SCALENE(0);
	
	private Integer numberOfequalSides;
	
	private TriangleType(Integer equalSides) {
		this.numberOfequalSides = equalSides;
	}
	
	public Integer getNumberOfequalSides() {
		return numberOfequalSides;
	}
	
	public static TriangleType getType(Integer numberOfEqualSides){
		if(EQUILATERAL.getNumberOfequalSides().equals(numberOfEqualSides)){
			return EQUILATERAL;
		}else if(ISOSCELES.getNumberOfequalSides().equals(numberOfEqualSides)){
			return ISOSCELES;
		}
		return SCALENE;
		
	}
}
