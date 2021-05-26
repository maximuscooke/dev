package com.maximuscooke.lib.common.collections;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.ICollection;
import com.maximuscooke.lib.common.IDisplayString;
import com.maximuscooke.lib.common.IFind;
import com.maximuscooke.lib.common.ISerializable;

public class CHashMap<K, T> extends HashMap<K, T> implements ICollection<K>, IFind<T>, IDisplayString, ISerializable
{

	private static final long serialVersionUID = 7703050879571359552L;

	public CHashMap()
	{
		this(CCollections.DEFAULT_CAPACITY);
	}

	public CHashMap(int capacity)
	{
		super(capacity);
	}

	public CHashMap(int capacity, float loadFactor)
	{
		super(capacity, loadFactor);
	}

	public CHashMap(Map<? extends K, ? extends T> m)
	{
		super(m);
	}

	public final CArrayList<T> getValuesRandomList(int size)
	{
		CArrayList<T> list = this.getValuesToArrayList();

		return list.getRandomList(size);
	}
	
	public void runConsumer(Consumer<? super T> func)
	{
		for (T t : this.values())
		{
			func.accept( t );
		}
	}
	
	public void runConsumer(BiConsumer<K, ? super T> func)
	{
		for(K key : this.keySet())
		{
			func.accept(key, this.get(key)); 
		}
	}
	
	public CArrayList<T> runPredicate(Predicate<? super T> func)
	{
		CArrayList<T> results = new CArrayList<T>();
		
		for (T t : this.values())
		{
			if (func.test( t ))
			{
				results.add(t);
			}
		}
		
		return results;
	}

	@Override
	public CHashMap<K, T> clone()
	{
		CHashMap<K, T> clone = new CHashMap<K, T>(this.size());

		for (K key : this.keySet())
		{
			T value = this.get(key);

			clone.add(key, value);
		}

		return clone;
	}

	public final void add(K key, T value)
	{
		this.put(key, value);
	}

	public final CArrayList<T> getValuesToArrayList()
	{
		return new CArrayList<T>(this.values());
	}

	public final CArrayList<K> getKeysToArrayList()
	{
		return new CArrayList<K>(this.keySet());
	}

	public final CArrayList<K> sortKeys(Comparator<K> c)
	{
		CArrayList<K> list = new CArrayList<K>(this.keySet());

		list.sort(c);

		return list;
	}

	public final CArrayList<T> sortValues(Comparator<T> c)
	{
		CArrayList<T> list = new CArrayList<T>(this.values());

		list.sort(c);

		return list;
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

	/**
	 * Find first occurrence of object
	 */
	@Override
	public final T find(T value)
	{
		if (value != null)
		{
			for (T obj : this.values())
			{
				if (this.compareObjects(value, obj))
				{
					return obj;
				}
			}
		}

		return null;
	}

	/**
	 * Find all values between a minimum and maximum value (inclusive)
	 * 
	 * @param comparer to compare object types
	 * @param min      minimum value
	 * @param max      maximum value
	 */

	@Override
	public CArrayList<T> findInRange(Comparator<T> comparer, T min, T max)
	{
		CArrayList<T> results = null;

		for (T value : this.values())
		{
			if (comparer.compare(value, min) != CCollections.LESS_THAN && comparer.compare(value, max) != CCollections.GREATER_THAN)
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

	/**
	 * Find ALL occurrences of object
	 */
	@Override
	public CArrayList<T> findAll(T value)
	{
		CArrayList<T> results = null;

		if (value != null)
		{
			for (T obj : this.values())
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

	public CArrayList<K> KeysToList()
	{

		return new CArrayList<K>(this.KeysToList());
	}

	public CArrayList<K> KeysToList(Comparator<? super K> comparer)
	{

		CArrayList<K> list = new CArrayList<K>(this.KeysToList());

		if (comparer != null)
		{
			list.sort(comparer);
		}

		return list;
	}

	public CArrayList<T> valuesToList()
	{
		return new CArrayList<T>(this.values());
	}

	public CArrayList<T> valuesToList(Comparator<? super T> comparer)
	{
		CArrayList<T> list = new CArrayList<T>(this.values());

		if (comparer != null)
		{
			list.sort(comparer);
		}

		return list;
	}

	/**
	 * Returns size of collection
	 * 
	 * @return count of elements in collection
	 */
	public final int getSize()
	{
		return this.size();
	}

	@Override
	public String toDisplayString(int style)
	{
		return this.toString();
	}
}
