package com.maximuscooke.lib.common.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.ICollection;
import com.maximuscooke.lib.common.IDisplayString;
import com.maximuscooke.lib.common.IFind;
import com.maximuscooke.lib.common.ISerializable;

public class CArrayList<T> extends ArrayList<T> implements ICollection<T>, IFind<T>, IDisplayString, ISerializable
{
	private static final long serialVersionUID = 8432218742113073429L;

	public CArrayList()
	{
		this(CCollections.DEFAULT_CAPACITY);
	}

	public CArrayList(int capacity)
	{
		super(capacity);
	}

	public CArrayList(Collection<? extends T> c)
	{
		super(c);
	}

	public CArrayList(Enumeration<? extends T> c)
	{
		while (c.hasMoreElements())
		{
			this.add(c.nextElement());
		}
	}
	
	public CArrayList(T[] array)
	{
		super(array != null ? array.length : CCollections.DEFAULT_CAPACITY);
		
		for(T value : array)
		{
			this.add(value);
		}
	}

	public final void inflate(int count)
	{
		int max = (count - this.getSize());

		for (int i = 0; i < max; i++)
		{
			this.add((T) null);
		}
		
	}

	public final void insertAt(int index, T element)
	{
		this.add(index, element);
	}

	public final void shift(int numRotations, int shiftDirection)
	{
		for (int i = 0; i < numRotations; i++)
		{
			shift(shiftDirection);
		}
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
	
	public final int getCount()
	{
		return this.getSize();
	}

	public final void shift(int shiftDirection)
	{

		if (this.getSize() > 1)
		{

			int lastIndex = this.getSize() - 1;

			if (shiftDirection == CCollections.SHIFT_LEFT)
			{

				T saveEle = this.getAt(0);

				for (int i = 0; i < lastIndex; i++)
				{
					this.setAt(i, this.getAt(i + 1));
				}

				this.setAt(lastIndex, saveEle);

			} 
			else if (shiftDirection == CCollections.SHIFT_RIGHT)
			{

				T saveEle = this.getLast();

				for (int i = lastIndex; i > 0; i--)
				{
					this.setAt(i, this.getAt(i - 1));
				}

				this.setAt(0, saveEle);
			}
		}
	}

	/**
	 * Adds/merge values from given collection to this collection
	 * 
	 * @param collection to add/merge
	 */
	public final void add(Iterable<? extends T> collection)
	{
		for (T value : collection)
		{
			this.add(value);
		}
	}

	@SafeVarargs
	public final void addArray(T... items)
	{
		for (T i : items)
		{
			this.add(i);
		}
	}

	/**
	 * removes values from given collection
	 * 
	 * @param collection to add/merge
	 */
	public final void remove(Iterable<? extends T> collection)
	{
		assert (collection != null) : "[CArrayList::remove] collection is null";
		for (T value : collection)
		{
			this.remove(value);
		}
	}

	/**
	 * Sets value at given position
	 * 
	 * @param index of object to set
	 * @param value of object to set
	 * @return old value of object
	 */
	public final T setAt(int index, T value)
	{
		assert (index >= 0 && index < (this.getSize() - 1)) : "[CArrayList::setAt] index out of range";
		return this.set(index, value);
	}

	/**
	 * Replaces value at given position
	 * 
	 * @param index of object to set
	 * @param value of object to set
	 * @return old value of object
	 */
	public final T replaceAt(int index, T value)
	{
		return setAt(index, value);
	}

	/**
	 * Return object at given index position
	 * 
	 * @param index position
	 * @return object at index position
	 */
	public final T getAt(int index)
	{
		assert (index >= 0 && index < (this.getSize() - 1)) : "[CArrayList::getAt] index out of range";
		return this.get(index);
	}

	/**
	 * Returns a random object from collection
	 * 
	 * @return random object
	 */
	public final T getRandom()
	{
		return this.getRandom(0, this.getSize()-1);
	}

	/**
	 * Returns a random list of objects from collection
	 * 
	 * @param size number of elements in list
	 * @return random list
	 */
	public final CArrayList<T> getRandomList(int size)
	{
		CArrayList<T> list = new CArrayList<T>(size);

		for (int i = 0; i < size; i++)
		{
			list.add(getRandom());
		}

		return list;
	}

	/**
	 * Returns a random object from within a given range
	 * 
	 * @param rangeStart first index position
	 * @param rangeEnd   last index position
	 * @return random object
	 */
	public final T getRandom(int rangeStart, int rangeEnd)
	{
		return this.get(CApplication.getRandomInteger(rangeStart, rangeEnd));
	}

	/**
	 * Remove a range of objects from collection, based upon index position
	 * 
	 * @param rangeStart first index position
	 * @param rangeEnd   last index position
	 */
	public final void removeRange(int rangeStart, int rangeEnd)
	{
		super.removeRange(rangeStart, rangeEnd);
	}

	/**
	 * Remove first occurrence of value from collection
	 * 
	 * @param value to be removed
	 */
	public final void removeValue(T value)
	{
		super.remove(value);
	}

	/**
	 * Remove all occurrences of object from collection
	 * 
	 * @param value to be removed
	 */
	public final void removeAll(T value)
	{
		this.removeAll(Arrays.asList(value));
	}

	/**
	 * Remove element at index position
	 * 
	 * @param index of object to remove
	 */
	public final void removeAt(int index)
	{
		this.remove(index);
	}

	/**
	 * Reverse the order of the collection
	 */
	public final void reverseOrder()
	{
		Collections.reverse(this);
	}

	/**
	 * Randomises the order of the collection
	 */
	public final void randomize()
	{
		Collections.shuffle(this, CApplication.getRandom());
	}

	/**
	 * Randomises the order of the collection
	 */
	public final void shuffle()
	{
		randomize();
	}

	/**
	 * Fill the collection with a specific capacity and initial value
	 * 
	 * @param capacity size of collection
	 * @param value    initial value
	 */
	public final void fill(int capacity, T value)
	{
		this.clear();

		for (int i = 0; i < capacity; i++)
		{
			this.add(value);
		}
	}

	/**
	 * Clone Collection
	 * 
	 * @return clone of collection
	 */
	@Override
	public CArrayList<T> clone()
	{
		CArrayList<T> clone = new CArrayList<T>(this.size());

		for (T value : this)
		{
			clone.add(value);
		}

		return clone;
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

	/**
	 * Returns true if collection is empty else false
	 * 
	 * @return true if object is empty, else false
	 */

	@Override
	public final boolean isEmpty()
	{
		return (this.size() == 0);
	}

	/**
	 * Compare objects for equality, elements in list must implement equals method
	 * 
	 * @param obj1 first object to be compared
	 * @param obj2 second object to be compared
	 * @return true if equal else false
	 */
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
	 * Returns the index position of target object
	 * 
	 * @param value to search for
	 * @return index position of object, else -1 if object is not found
	 */
	public final int findIndexOf(T value)
	{
		if (value != null)
		{
			for (int i = 0; i < this.size(); i++)
			{
				T obj = this.getAt(i);

				if (this.compareObjects(obj, obj))
				{
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * Find first occurrence of object
	 * 
	 * @param value to search for
	 * @return return target/search object, else null if object not found
	 */

	@Override
	public final T find(T value)
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

	/**
	 * Find last occurrence of object, else null if object not found
	 * 
	 * @param value to search for
	 * @return target object else null
	 */
	public final T findLast(T value)
	{
		if (value != null)
		{
			for (int i = (this.size() - 1); i >= 0; --i)
			{
				T obj = this.getAt(i);

				if (this.compareObjects(value, obj))
				{
					return obj;
				}
			}
		}

		return null;
	}

	/**
	 * Find ALL occurrences of object
	 */
	@Override
	public final CArrayList<T> findAll(T value)
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

	/**
	 * Find all values between a minimum and maximum range (inclusive)
	 * 
	 * @param comparer to compare object types
	 * @param min      minimum value
	 * @param max      maximum value
	 */

	@Override
	public final CArrayList<T> findInRange(Comparator<T> comparer, T min, T max)
	{
		CArrayList<T> results = null;

		for (T value : this)
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
	 * Return the last element in the array
	 * 
	 * @return last element in the array
	 */
	public final T getLast()
	{
		return this.getAt((this.size() - 1));
	}

	/**
	 * Returns index of object in list
	 * 
	 * @param obj Target object
	 * @return index of object, else -1 if object not found
	 */
	public final int getIndexOf(T obj)
	{
		return this.indexOf(obj);
	}

	/**
	 * Swap two values in a list
	 * 
	 * @param i first value
	 * @param j second value
	 */
	public final void swap(int i, int j)
	{
		Collections.swap(this, i, j);
	}

	/**
	 * Moves the value at index position up the list
	 * 
	 * @param index Target index
	 */
	public final void moveUp(int index)
	{
		if (index > 0)
		{
			this.swap(index, index - 1);
		}
	}

	/**
	 * Moves the value at index position down the list
	 * 
	 * @param index
	 */
	public final void moveDown(int index)
	{
		if (index < (this.getSize() - 1))
		{
			this.swap(index, index + 1);
		}
	}

	/**
	 * Moves a value in list up or down the collection
	 * 
	 * @param index          target object index position
	 * @param upOrDownOption MOVE_UP_LIST or MOVE_DOWN_LIST
	 */
	public final void moveUpOrDown(int index, int upOrDownOption)
	{
		if (upOrDownOption == CCollections.MOVE_UP)
		{
			this.moveUp(index);
		} 
		else if (upOrDownOption == CCollections.MOVE_DOWN)
		{
			this.moveDown(index);
		}
	}

	/**
	 * Returns string representation of collection
	 */
	@Override
	public String toDisplayString(int style)
	{
		return this.toString();
	}

}
