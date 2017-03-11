/**
 * 
 */
package com.devnagui.business;

import java.io.Serializable;

import com.devnagui.exception.InvalidPolygonException;
import com.devnagui.model.Polygon;

/**
 * Business Object base class for for all Polygon types 
 * 
 * @author devnagui
 *
 */
public abstract class PolygonIdentifactorBO<T extends Polygon> implements Serializable {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -669701300828887520L;
	
	
	public abstract void validate(T polygon) throws InvalidPolygonException;
	
	public void identify(T polygon) throws InvalidPolygonException{
		validate(polygon);
		classificate(polygon);
	}
	
	public abstract void classificate(T polygon);
}
