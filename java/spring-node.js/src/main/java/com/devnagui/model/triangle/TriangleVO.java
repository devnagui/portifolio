/**
 * 
 */
package com.devnagui.model.triangle;

/**
 * @author devnagui
 *
 */
public class TriangleVO  {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -6156063160863052785L;
	
	private Double sideA;
	
	private Double sideB;
	
	private Double sideC;
	
	public TriangleVO() {
	}
	
	public Double getSideA() {
		return sideA;
	}



	public void setSideA(Double sideA) {
		this.sideA = sideA;
	}



	public Double getSideB() {
		return sideB;
	}



	public void setSideB(Double sideB) {
		this.sideB = sideB;
	
	}
	
	public void setSideC(Double sideC) {
		this.sideC = sideC;
	}
	
	public Double getSideC() {
		return sideC;
	}


	public Triangle getAsTriangle() {
		return new Triangle(sideA,sideB,sideC);
	}
	
	
}
