package com.maximuscooke.lib.common;

public interface ICollection<T>
{
	/**
	 * Tests if collection contains any items
	 * 
	 * @return true if collection contains zero items
	 */
	boolean isEmpty();

	/**
	 * Clear collection of ALL elements
	 */
	void clear();

}
