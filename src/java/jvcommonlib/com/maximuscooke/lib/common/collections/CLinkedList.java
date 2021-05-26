package com.maximuscooke.lib.common.collections;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.ICollection;
import com.maximuscooke.lib.common.IDisplayString;
import com.maximuscooke.lib.common.IFind;
import com.maximuscooke.lib.common.ISerializable;

public class CLinkedList<T> extends LinkedList<T> implements ICollection<T>, IFind<T>, IDisplayString, ISerializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7960151937244409806L;

	public CLinkedList()
	{
	}

	public final int getSize()
	{
		return this.size();
	}

	public final T getAt(int index)
	{
		return this.get(index);
	}

	public final void insertAt(int index, T element)
	{
		this.add(index, element);
	}

	public final void removeAt(int index)
	{
		this.remove(index);
	}

	@Override
	public String toDisplayString(int style)
	{
		return this.toString();
	}

	@Override
	public boolean compareObjects(T obj1, T obj2)
	{
		if (obj1 != null)
		{
			return obj1.equals(obj2);
		}

		return false;
	}

	@Override
	public T find(T value)
	{
		if (value != null)
		{
			for (T obj : this)
			{
				if (this.compareObjects(value, obj))
				{
					return obj;
				}
			}
		}

		return null;
	}
	
	public void runConsumer(Consumer<? super T> func)
	{
		for (T t : this)
		{
			func.accept( t );
		}
	}
	
	public CArrayList<T> runPredicate(Predicate<? super T> func)
	{
		CArrayList<T> results = new CArrayList<T>();
		
		for (T t : this)
		{
			if (func.test( t ))
			{
				results.add(t);
			}
		}
		
		return results;
	}

	@Override
	public CArrayList<T> findInRange(Comparator<T> c, T min, T max)
	{
		CArrayList<T> results = null;

		for (T value : this)
		{
			if (c.compare(value, min) != CCollections.LESS_THAN && c.compare(value, max) != CCollections.GREATER_THAN)
			{
				if (results == null)
				{
					results = new CArrayList<T>();
				}

				results.add(value);
			}
		}

		return results;
	}

	@Override
	public CArrayList<T> findAll(T value)
	{
		CArrayList<T> results = null;

		if (value != null)
		{
			for (T obj : this)
			{
				if (this.compareObjects(value, obj))
				{
					if (results == null)
					{
						results = new CArrayList<T>();
					}

					results.add(obj);
				}
			}
		}

		return results;
	}
}
