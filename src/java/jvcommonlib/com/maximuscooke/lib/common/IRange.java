package com.maximuscooke.lib.common;

public interface IRange<T>
{
	T 		getMin();
	void 	setMin(T min);
	
	T 		getMax();
	void setMax(T max);
}
