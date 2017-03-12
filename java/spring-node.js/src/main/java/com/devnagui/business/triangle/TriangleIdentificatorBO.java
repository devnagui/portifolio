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
@Service()
public class TriangleIdentificatorBO extends PolygonIdentifactorBO<Triangle> {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 3531422247247707467L;

	@Override
	public void validate(Triangle triangle) throws InvalidTriangleException {
		
		if(triangle ==null){
			throw new InvalidTriangleException("Triangle object is null.");
		}
		if(triangle.getSides()==null){
			throw new InvalidTriangleException("One or more of the sides are null.");
		}
		if(Collections.frequency(triangle.getSides(), null)!=0){
			throw new InvalidTriangleException("One or more of the sides are null.");
		}
		
		if(Collections.frequency(triangle.getSides(), Double.valueOf(0.0))!=0){
			throw new InvalidTriangleException("One or more of the sides are zero.");
		}

		if( Collections.min(triangle.getSides())<0){
			throw new InvalidTriangleException("One or more of the sides are less than zero.");
		}

		if(!isValidByConditionOfInequalityOfSides(triangle)){
			throw new InvalidTriangleException("One of the sides is bigger than the sum of the others.");
		}
	}


	@Override
	public void classificate(Triangle triangle) {
		//We can could go check the frequency of each element but we could do this with only two, like that:
		Double biggerSide = Collections.max(triangle.getSides());
		Double smallestSide = Collections.min(triangle.getSides());
		int biggerSideFrequency  = Collections.frequency(triangle.getSides(), biggerSide);
		int smallestSideFrequency= Collections.frequency(triangle.getSides(), smallestSide);
		//If the sides are equal and the frequency is 1, that means that each side is repeated only 1 time
		if(biggerSideFrequency == smallestSideFrequency && biggerSideFrequency == 1){
			//Scalene
			triangle.setTriangleType(TriangleType.SCALENE);
			//We could ask for TriangleType.getType(0) but that would be just redundancy...
		}else{
			//Isosceles or Equilateral, max of both will decide..
			triangle.setTriangleType(TriangleType.getType(Math.max(biggerSideFrequency, smallestSideFrequency)));
		}
		
			
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
		return (Math.max(otherSide1,otherSide2) - Math.min(otherSide1,otherSide2)) < sideToTest  
				&& sideToTest < (otherSide1 + otherSide2);
	}

}
