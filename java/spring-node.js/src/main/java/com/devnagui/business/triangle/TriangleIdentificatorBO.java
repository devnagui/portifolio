/**
 * 
 */
package com.devnagui.business.triangle;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.devnagui.business.PolygonIdentifactorBO;
import com.devnagui.exception.InvalidTriangleException;
import com.devnagui.model.triangle.Triangle;
import com.devnagui.model.triangle.TriangleType;

/**
 * Business Object base class for for all Triangles types 
 * @author devnagui
 */
@Service
public class TriangleIdentificatorBO extends PolygonIdentifactorBO<Triangle> {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 3531422247247707467L;

	@Override
	public void validate(Triangle triangle) throws InvalidTriangleException {
		if(Collections.frequency(triangle.getSides(), null)!=0){
			throw new InvalidTriangleException("One or more of the sides are null.");
		}
		if(Collections.frequency(triangle.getSides(), Double.valueOf(0.0))!=0){
			throw new InvalidTriangleException("One or more of the sides are zero.");
		}
		
		if(!isValidByConditionOfInequalityOfSides(triangle)){
			throw new InvalidTriangleException("One of the sides is bigger than the sum of the others.");
		}
	}


	@Override
	public void classificate(Triangle triangle) {
		//From now on we only have 2 options - Equilateral or Isosceles
		//We now that we have a repeated 2 or 3 repeated sides, if we get the max and min of the sides we can do a Math.max(maxSide,minSide) 
		//to obtain the total of sides's frequency without do a "for" to count the frequency of each side.
		Double biggerSide = Collections.max(triangle.getSides());
		Double smallestSide = Collections.min(triangle.getSides());
		int smallestSideFrequency= Collections.frequency(triangle.getSides(), smallestSide);
		int biggerSideFrequency  = Collections.frequency(triangle.getSides(), biggerSide);
		triangle.setTriangleType(TriangleType.getType(Math.max(smallestSideFrequency, biggerSideFrequency)));	
	}


	/**
	 * @param triangle
	 * @return True if the sum of the lengths of any two sides is equal or less than to the length of the remaining side.
	 */
	private Boolean isValidByConditionOfInequalityOfSides(Triangle triangle) {
		Double a = triangle.getSides().get(0);
		Double b = triangle.getSides().get(1);
		Double c = triangle.getSides().get(2);
		return isSideValid(a, b, c) && isSideValid(b, a, c) && isSideValid(c, a, b);
	}
	private boolean isSideValid(Double sideToTest, Double otherSide1, Double otherSide2) {
		return sideToTest <= otherSide1 + otherSide2;
	}

}
