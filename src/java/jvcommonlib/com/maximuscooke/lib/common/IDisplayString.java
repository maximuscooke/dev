package com.maximuscooke.lib.common;

public interface IDisplayString
{
	/**
	 * Display object using a long style
	 */
	public static final int STYLE_LONG = 10;

	/**
	 * Display object using a short style
	 */
	public static final int STYLE_SHORT = 20;

	/**
	 * Returns string representation of object
	 * 
	 * @param style of display string
	 * @return String representation of object
	 */
	String toDisplayString(int style);

}
