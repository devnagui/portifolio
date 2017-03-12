package com.devnagui.business.triangle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.devnagui.exception.InvalidPolygonException;
import com.devnagui.exception.InvalidTriangleException;
import com.devnagui.model.triangle.Triangle;
import com.devnagui.model.triangle.TriangleType;

import org.junit.Assert;

/**
 * @author devnagui
 */
@RunWith(MockitoJUnitRunner.class)
public class TriangleBOTest {
	
	@Mock
	TriangleIdentificatorBO triangleBO;
	
	@Test(expected=InvalidTriangleException.class)
	public void testInvalidTriangleByTriangleNull() throws InvalidTriangleException{
		Mockito.doCallRealMethod().when(triangleBO).validate(null);
		triangleBO.validate(null);
	}
	@Test(expected=InvalidTriangleException.class)
	public void testInvalidTriangleBySideNull() throws InvalidTriangleException{
		Triangle triangle = new Triangle(null, 1.0, 0.0);
		Mockito.doCallRealMethod().when(triangleBO).validate(triangle);
		triangleBO.validate(triangle);
	}
	
	@Test(expected=InvalidTriangleException.class)
	public void testInvalidTriangleBySideZero() throws InvalidTriangleException{
		Triangle triangle = new Triangle(3.0, 1.0, 0.0);
		Mockito.doCallRealMethod().when(triangleBO).validate(triangle);
		triangleBO.validate(triangle);
	}
	
	@Test(expected=InvalidTriangleException.class)
	public void testInvalidTriangleBySideLessZero() throws InvalidTriangleException{
		Triangle triangle = new Triangle(3.0, -1.0, 0.0);
		Mockito.doCallRealMethod().when(triangleBO).validate(triangle);
		triangleBO.validate(triangle);
	}
	
	@Test(expected=InvalidTriangleException.class)
	public void testInvalidTriangle() throws InvalidTriangleException{
		Triangle triangle = new Triangle(3.0, 1.0, 1.0);
		Mockito.doCallRealMethod().when(triangleBO).validate(triangle);
		triangleBO.validate(triangle);
	}

	@Test()
	public void testValidTriangle() throws InvalidTriangleException{
		Triangle triangle = new Triangle(3.0, 3.0, 3.0);
		Mockito.doCallRealMethod().when(triangleBO).validate(triangle);
		triangleBO.validate(triangle);
	}
	
	@Test
	public void testEquilateralClassification() throws InvalidPolygonException{
		Triangle triangle = new Triangle(3.0, 3.0, 3.0);
		Mockito.doCallRealMethod().when(triangleBO).classificate(triangle);
		triangleBO.classificate(triangle);
		Assert.assertEquals( TriangleType.EQUILATERAL,triangle.getTriangleType());
	}

	@Test
	public void testIsoscelesMinSideClassification() throws InvalidPolygonException{
		Triangle triangle = new Triangle(3.0, 3.0, 4.0);
		Mockito.doCallRealMethod().when(triangleBO).classificate(triangle);
		triangleBO.classificate(triangle);
		Assert.assertEquals(TriangleType.ISOSCELES,triangle.getTriangleType() );
	}
	
	@Test
	public void testIsoscelesMaxSideClassification() throws InvalidPolygonException{
		Triangle triangle = new Triangle(3.0, 4.0, 4.0);
		Mockito.doCallRealMethod().when(triangleBO).classificate(triangle);
		triangleBO.classificate(triangle);
		Assert.assertEquals(TriangleType.ISOSCELES,triangle.getTriangleType() );
	}
	
	@Test
	public void testScaneleClassification() throws InvalidPolygonException{
		Triangle triangle = new Triangle(4.0, 3.0, 2.0);
		Mockito.doCallRealMethod().when(triangleBO).classificate(triangle);
		triangleBO.classificate(triangle);
		Assert.assertEquals(TriangleType.SCALENE, triangle.getTriangleType() );
	}
	
	@Test
	public void testCorrectWholeStackEquilateralIdentificationSuccesss() throws InvalidPolygonException{
		Triangle triangle = new Triangle(4.0, 4.0, 4.0);
		Mockito.doCallRealMethod().when(triangleBO).identify(triangle);
		Mockito.doCallRealMethod().when(triangleBO).validate(triangle);
		Mockito.doCallRealMethod().when(triangleBO).classificate(triangle);
		triangleBO.identify(triangle);
		Assert.assertEquals(TriangleType.EQUILATERAL,triangle.getTriangleType() );
	}
	
	@Test
	public void testCorrectWholeStackIsocelesIdentificationSuccesss() throws InvalidPolygonException{
		Triangle triangle = new Triangle(3.0, 2.0, 2.0);
		Mockito.doCallRealMethod().when(triangleBO).identify(triangle);
		Mockito.doCallRealMethod().when(triangleBO).validate(triangle);
		Mockito.doCallRealMethod().when(triangleBO).classificate(triangle);
		triangleBO.identify(triangle);
		Assert.assertEquals(TriangleType.ISOSCELES,triangle.getTriangleType() );
	}
	
	@Test
	public void testCorrectWholeStackScaleneIdentificationSuccesss() throws InvalidPolygonException{
		Triangle triangle = new Triangle(3.0, 2.0, 1.0);
		Mockito.doCallRealMethod().when(triangleBO).identify(triangle);
		Mockito.doCallRealMethod().when(triangleBO).validate(triangle);
		Mockito.doCallRealMethod().when(triangleBO).classificate(triangle);
		triangleBO.identify(triangle);
		Assert.assertEquals(TriangleType.SCALENE,triangle.getTriangleType() );
	}
}
