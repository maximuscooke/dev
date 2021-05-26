package com.maximuscooke.lib.common;

public interface IDateTime extends ITime, IDate
{
	/**
	 * Initialises object to values of specified parameter
	 * 
	 * @param dt set object equal to this parameter
	 */
	void init(IDateTime dt);

	/**
	 * Compares current value with specified parameter
	 * 
	 * @param dt Date and time object to compare
	 * @return comparison result
	 */
	int compareToDateTime(IDateTime dt);

}
