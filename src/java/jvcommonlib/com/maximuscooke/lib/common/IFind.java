package com.maximuscooke.lib.common;

import java.util.Comparator;

import com.maximuscooke.lib.common.collections.CArrayList;

public interface IFind<T>
{
	boolean compareObjects(T o1, T o2);

	T find(T value);

	CArrayList<T> findInRange(Comparator<T> c, T min, T max);

	CArrayList<T> findAll(T value);

}
