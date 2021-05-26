package com.maximuscooke.lib.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.maximuscooke.lib.common.collections.CArrayList;

public abstract class CCollections
{

	public static final int EQUAL = 0;
	public static final int LESS_THAN = -1;
	public static final int GREATER_THAN = 1;
	
	public static final int FIND_FIRST = 10;
	public static final int FIND_LAST = 20;
	public static final int FIND_ALL = 30;

	public static final int NOT_FOUND = -1;

	public static final int SORT_ASCENDING = 100;
	public static final int SORT_DESCENDING = 200;
	
	public static final int MOVE_UP = 10;
	public static final int MOVE_DOWN = 20;
	public static final int SHIFT_LEFT = 10;
	public static final int SHIFT_RIGHT = 20;

	public static final int DEFAULT_CAPACITY = 16;

	public static final Comparator<IDateTimeStamp> mDefaultSortDateTimeStamp;
	public static final Comparator<IDateTime> mDefaultSortTime;
	public static final Comparator<IDateTime> mDefaultSortDate;
	public static final Comparator<IDateTime> mDefaultSortDateTime;
	public static final Comparator<IName> mDefaultSortName;
	public static final Comparator<ILongID> mDefaultSortLongID;
	public static final Comparator<Integer> mDefaultSortInteger;
	public static final Comparator<Object> mDefaultSortObject;
	public static final Comparator<IPrecedenceOrder> mDefaultSortPrecedenceOrder;

	/**
	 * Default static sort routines
	 */
	static
	{

		mDefaultSortDate = new Comparator<IDateTime>() 
		{
			public int compare(IDateTime dt1, IDateTime dt2)
			{

				if (dt1 == null && dt2 == null)
				{
					return CCollections.EQUAL;
				}

				if (dt1 != null && dt2 == null)
				{
					return CCollections.LESS_THAN;
				}

				if (dt1 == null && dt2 != null)
				{
					return CCollections.GREATER_THAN;
				}

				if (dt1.getYear() == dt2.getYear())
				{
					if (dt1.getMonth() == dt2.getMonth())
					{
						if (dt1.getDayOfMonth() == dt2.getDayOfMonth())
						{
							return CCollections.EQUAL;
						} 
						else if (dt1.getDayOfMonth() < dt2.getDayOfMonth())
						{
							return CCollections.LESS_THAN;
						} else
						{
							return CCollections.GREATER_THAN;
						}
					} 
					else if (dt1.getMonth() < dt2.getMonth())
					{
						return CCollections.LESS_THAN;
					} 
					else
					{
						return CCollections.GREATER_THAN;
					}
				} 
				else if (dt1.getYear() < dt2.getYear())
				{
					return CCollections.LESS_THAN;
				} 
				else
				{
					return CCollections.GREATER_THAN;
				}
			}
		};

		mDefaultSortTime = new Comparator<IDateTime>() 
		{

			public int compare(IDateTime dt1, IDateTime dt2)
			{
				if (dt1 == null && dt2 == null)
				{
					return CCollections.EQUAL;
				}

				if (dt1 != null && dt2 == null)
				{
					return CCollections.LESS_THAN;
				}

				if (dt1 == null && dt2 != null)
				{
					return CCollections.GREATER_THAN;
				}

				if (dt1.getHourOfDay() == dt2.getHourOfDay())
				{
					if (dt1.getMinutes() == dt2.getMinutes())
					{
						if (dt1.getSeconds() == dt2.getSeconds())
						{
							return CCollections.EQUAL;
						} 
						else if (dt1.getSeconds() < dt2.getSeconds())
						{
							return CCollections.LESS_THAN;
						} 
						else
						{
							return CCollections.GREATER_THAN;
						}
					} 
					else if (dt1.getMinutes() < dt2.getMinutes())
					{
						return CCollections.LESS_THAN;
					} 
					else
					{
						return CCollections.GREATER_THAN;
					}
				} 
				else if (dt1.getHourOfDay() < dt2.getHourOfDay())
				{
					return CCollections.LESS_THAN;
				} 
				else
				{
					return CCollections.GREATER_THAN;
				}
			}
		};

		mDefaultSortDateTime = new Comparator<IDateTime>() {

			public int compare(IDateTime dt1, IDateTime dt2)
			{

				int result = CCollections.mDefaultSortDate.compare(dt1, dt2);

				if (result == CCollections.EQUAL)
				{
					return CCollections.mDefaultSortTime.compare(dt1, dt2);
				}

				return result;
			}
		};

		mDefaultSortPrecedenceOrder = new Comparator<IPrecedenceOrder>() {

			@Override
			public int compare(IPrecedenceOrder o1, IPrecedenceOrder o2)
			{
				if (o1 == null && o2 == null)
				{
					return CCollections.EQUAL;
				}

				if (o1.getPrecedenceOrder() < o2.getPrecedenceOrder())
				{
					return CCollections.LESS_THAN;
				}

				if (o1.getPrecedenceOrder() > o2.getPrecedenceOrder())
				{
					return CCollections.GREATER_THAN;
				}

				return CCollections.EQUAL;
			}

		};

		mDefaultSortObject = new Comparator<Object>() {

			public int compare(Object o1, Object o2)
			{
				if (o1 == null && o2 == null)
				{
					return CCollections.EQUAL;
				}

				if (o1 != null && o2 == null)
				{
					return CCollections.LESS_THAN;
				}

				if (o1 == null && o2 != null)
				{
					return CCollections.GREATER_THAN;
				}

				return o1.toString().compareTo(o2.toString());
			}
		};

		mDefaultSortDateTimeStamp = new Comparator<IDateTimeStamp>() {

			public int compare(IDateTimeStamp o1, IDateTimeStamp o2)
			{
				if (o1 == null && o2 == null)
				{
					return CCollections.EQUAL;
				}

				if (o1 != null && o2 == null)
				{
					return CCollections.LESS_THAN;
				}

				if (o1 == null && o2 != null)
				{
					return CCollections.GREATER_THAN;
				}

				if (o1.getDateTime() == null && o2.getDateTime() == null)
				{
					return CCollections.EQUAL;
				}

				if (o1.getDateTime() != null && o2.getDateTime() == null)
				{
					return CCollections.LESS_THAN;
				}

				if (o1.getDateTime() == null && o2.getDateTime() != null)
				{
					return CCollections.GREATER_THAN;
				}

				return o1.getDateTime().compareToDateTime(o2.getDateTime());
			}

		};

		mDefaultSortName = new Comparator<IName>() {

			@Override
			public int compare(IName o1, IName o2)
			{
				if (o1 == null && o2 == null)
				{
					return CCollections.EQUAL;
				}

				if (o1 != null && o2 == null)
				{
					return CCollections.LESS_THAN;
				}

				if (o1 == null && o2 != null)
				{
					return CCollections.GREATER_THAN;
				}

				if (o1 != null && o2 != null && o1.getName() == null && o2.getName() == null)
				{
					return CCollections.EQUAL;
				}

				if (o1.getName() != null && o2.getName() == null)
				{
					return CCollections.LESS_THAN;
				}

				if (o2.getName() != null && o1.getName() == null)
				{
					return CCollections.GREATER_THAN;
				}

				return o1.getName().compareTo(o2.getName());
			}
		};

		mDefaultSortLongID = new Comparator<ILongID>() {

			@Override
			public int compare(ILongID o1, ILongID o2)
			{
				if (o1 == null && o2 == null)
				{
					return CCollections.EQUAL;
				}

				if (o1 != null && o2 == null)
				{
					return CCollections.LESS_THAN;
				}

				if (o2 != null && o1 == null)
				{
					return CCollections.GREATER_THAN;
				}

				if (o1.getLongID() < o2.getLongID())
				{
					return CCollections.LESS_THAN;
				} 
				else if (o1.getLongID() > o2.getLongID())
				{
					return CCollections.GREATER_THAN;
				}

				return CCollections.EQUAL;
			}
		};

		mDefaultSortInteger = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2)
			{
				if (o1 < o2)
				{
					return CCollections.LESS_THAN;
				} 
				else if (o1 > o2)
				{
					return CCollections.GREATER_THAN;
				}

				return CCollections.EQUAL;
			}

		};
	}
			
//	public static void doExport(String dir, String fName, String separator, String marker, Collection<? extends IExportObject> list, int options) throws Exception
//	{
//		if ((options & IExportObject.EXPORT_OPT_CREATE) != 0)
//		{
//			CFile.deleteIfExists(dir, fName);			
//		}
//		
//		CTextFile tf = new CTextFile(dir, fName);
//				
//		tf.addLine(marker);
//
//		list.forEach( value -> tf.addLine( value.exportObject(separator) ) );
//		
//		tf.addLine(marker);
//				
//		tf.save(dir, fName, (options & IExportObject.EXPORT_OPT_APPEND) != 0);
//	}
//	
//	public static void doImport(String dir, String fName, String separator, String marker, Consumer<String> func) throws Exception
//	{
//		CTextFile tf = new CTextFile(dir, fName);
//		
//		tf.read();
//		
//		CRange<Long> range = tf.findMarkerRange(marker);
//		
//		for(long i = range.getMin()+1; i < range.getMax(); i++)
//		{
//			func.accept( tf.getLineAt( (int)i) );			
//		}
//	}

	/**
	 * Sort any list that implements the IUniqueID interface
	 * 
	 * @param list target list to sort
	 */
	public static void sortByUniqueID(List<? extends ILongID> list)
	{
		sortByUniqueID(list, CCollections.SORT_ASCENDING);
	}

	/**
	 * Sort any list that implements the IUniqueID interface
	 * 
	 * @param list      target list to sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 */
	public static void sortByUniqueID(List<? extends ILongID> list, int sortOrder)
	{
		sortByUniqueID(list, mDefaultSortLongID, sortOrder);
	}

	/**
	 * Sort any list that implements the IUniqueID interface
	 * 
	 * @param list      target list to sort
	 * @param comparer  object to use for sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 */
	public static void sortByUniqueID(List<? extends ILongID> list, Comparator<? super ILongID> comparer, int sortOrder)
	{
		Collections.sort(list, comparer);

		if (sortOrder == CCollections.SORT_DESCENDING)
		{
			Collections.reverse(list);
		}
	}

	/**
	 * Sort any set that implements the IName interface and return the results as a
	 * CArrayList object (Default Sort is ASCENDING)
	 * 
	 * @param set target set to sort
	 * @return sorted set
	 */
	public static CArrayList<? extends IName> sortByName(Set<? extends IName> set)
	{
		return sortByName(set, CCollections.SORT_ASCENDING);
	}

	/**
	 * Sort any set that implements the IName interface and return the results as a
	 * CArrayList object (Default Sort is ASCENDING)
	 * 
	 * @param set       target set to sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 * @return sorted set
	 */
	public static CArrayList<? extends IName> sortByName(Set<? extends IName> set, int sortOrder)
	{
		return sortByName(set, mDefaultSortName, sortOrder);
	}

	/**
	 * Sort any set that implements the IName interface and return the results as a
	 * CArrayList object (Default Sort is ASCENDING)
	 * 
	 * @param set       target list to sort
	 * @param comparer  object to use for sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 */

	/**
	 * Sort any set that implements the IName interface and return the results as a
	 * CArrayList object (Default Sort is ASCENDING)
	 * 
	 * @param set       target list to sort
	 * @param comparer  object to use for sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 * @return sorted list of objects
	 */

	public static CArrayList<? extends IName> sortByName(Set<? extends IName> set, Comparator<? super IName> comparer, int sortOrder)
	{
		CArrayList<? extends IName> list = new CArrayList<IName>(set);

		sortByName(list, comparer, sortOrder);

		return list;
	}

	/**
	 * Sort any list that implements the IName interface
	 * 
	 * @param list target list to sort
	 */
	public static void sortByName(List<? extends IName> list)
	{
		sortByName(list, CCollections.SORT_ASCENDING);
	}

	/**
	 * Sort any list that implements the IName interface
	 * 
	 * @param list      target list to sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 */
	public static void sortByName(List<? extends IName> list, int sortOrder)
	{
		sortByName(list, mDefaultSortName, sortOrder);
	}

	/**
	 * Sort any set that implements the IName interface and return the results as a
	 * CArrayList object (Default Sort is ASCENDING)
	 * 
	 * @param list      target list to sort
	 * @param comparer  object to use for sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 */
	public static void sortByName(List<? extends IName> list, Comparator<? super IName> comparer, int sortOrder)
	{
		Collections.sort(list, comparer);

		if (sortOrder == CCollections.SORT_DESCENDING)
		{
			Collections.reverse(list);
		}
	}

	/**
	 * Sort any set that implements the IDateTimeStamp interface and return the
	 * results as a CArrayList object (Default Sort is ASCENDING)
	 * 
	 * @param set target list to sort
	 * @return sorted list of objects
	 */
	public static CArrayList<? extends IDateTimeStamp> sortByDateTimeStamp(Set<? extends IDateTimeStamp> set)
	{
		return CCollections.sortByDateTimeStamp(set, mDefaultSortDateTimeStamp, CCollections.SORT_ASCENDING);
	}

	/**
	 * Sort any set that implements the IDateTimeStamp interface and return the
	 * results as a CArrayList object (Default Sort is ASCENDING)
	 * 
	 * @param set       target list to sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 * @return sorted list of objects
	 */
	public static CArrayList<? extends IDateTimeStamp> sortByDateTimeStamp(Set<? extends IDateTimeStamp> set, int sortOrder)
	{
		return CCollections.sortByDateTimeStamp(set, mDefaultSortDateTimeStamp, sortOrder);
	}

	/**
	 * Sort any set that implements the IDateTimeStamp interface and return the
	 * results as a CArrayList object (Default Sort is ASCENDING)
	 * 
	 * @param set       target list to sort
	 * @param comparer  object to use for sort
	 * @param sortOrder should be either ICollection.SORT_ASCENDING or
	 *                  ICollection.SORT_DESCENDING
	 * @return sorted list of objects
	 */
	public static CArrayList<? extends IDateTimeStamp> sortByDateTimeStamp(Set<? extends IDateTimeStamp> set, Comparator<? super IDateTimeStamp> comparer, int sortOrder)
	{

		CArrayList<? extends IDateTimeStamp> list = new CArrayList<IDateTimeStamp>(set);

		sortByDateTimeStamp(list, comparer, sortOrder);

		return list;
	}

	/**
	 * Sorts a list of objects that implement the IDateTimeStamp interface
	 * 
	 * @param list target list to be sorted
	 */
	public static void sortByDateTimeStamp(List<? extends IDateTimeStamp> list)
	{
		sortByDateTimeStamp(list, CCollections.SORT_ASCENDING);
	}

	/**
	 * Sorts a list of objects that implement the IDateTimeStamp interface
	 * 
	 * @param list      target list to be sorted
	 * @param sortOrder sorting order either ICollection.SORT_DESCENDING or
	 *                  ICollection.SORT_ASCENDING
	 */
	public static void sortByDateTimeStamp(List<? extends IDateTimeStamp> list, int sortOrder)
	{
		sortByDateTimeStamp(list, mDefaultSortDateTimeStamp, sortOrder);
	}

	/**
	 * Sorts a list of objects that implement the IDateTimeStamp interface
	 * 
	 * @param list      target list to be sorted
	 * @param comparer  sort comparator
	 * @param sortOrder sorting order either ICollection.SORT_DESCENDING or
	 *                  ICollection.SORT_ASCENDING
	 */
	public static void sortByDateTimeStamp(List<? extends IDateTimeStamp> list, Comparator<? super IDateTimeStamp> comparer, int sortOrder)
	{
		Collections.sort(list, comparer);

		if (sortOrder == CCollections.SORT_DESCENDING)
		{
			Collections.reverse(list);
		}
	}

	/**
	 * Sorts a list of objects that implement the IPrecedenceOrder interface
	 * 
	 * @param list      target list to be sorted
	 * @param sortOrder sorting order either ICollection.SORT_DESCENDING or
	 *                  ICollection.SORT_ASCENDING
	 */
	public static void sortByPrecedenceOrder(List<? extends IPrecedenceOrder> list, int sortOrder)
	{
		sortByPrecedenceOrder(list, CCollections.mDefaultSortPrecedenceOrder, sortOrder);
	}

	/**
	 * Sorts a list of objects that implement the IPrecedenceOrder interface
	 * 
	 * @param list      target list to be sorted
	 * @param comparer  sort comparator
	 * @param sortOrder sorting order either ICollection.SORT_DESCENDING or
	 *                  ICollection.SORT_ASCENDING
	 */
	public static void sortByPrecedenceOrder(List<? extends IPrecedenceOrder> list, Comparator<IPrecedenceOrder> comparer, int sortOrder)
	{
		Collections.sort(list, comparer);

		if (sortOrder == CCollections.SORT_DESCENDING)
		{
			Collections.reverse(list);
		}
	}

	/**
	 * Remove an object from a collection by using its unique ID as search condition
	 * criteria
	 * 
	 * @param collection source collection
	 * @param key        ID to remove
	 * @return count of objects removed
	 */
	@SuppressWarnings("unchecked")
	public static int removeByLongID(Collection<? extends ILongID> collection, Long key)
	{
		int count = 0;

		for (Iterator<ILongID> iter = (Iterator<ILongID>) collection.iterator(); iter.hasNext();)
		{
			ILongID obj = iter.next();

			if (obj.getLongID() == key)
			{
				iter.remove();

				++count;
			}
		}

		return count;
	}

	/**
	 * Remove an object from a collection by using its unique ID as search condition
	 * criteria
	 * 
	 * @param collection source collection
	 * @param value      ID to remove
	 * @return count of objects removed
	 */
	public static int removeByLongID(Collection<? extends ILongID> collection, ILongID value)
	{
		return removeByLongID(collection, value.getLongID());
	}

	/**
	 * Create string of all objects in collection
	 * 
	 * @param separator Separator string
	 * @param list      collection list
	 * @return String of objects separated by specific separator string
	 */
	public static final String toSeparatedString(String separator, List<? extends Object> list)
	{
		StringBuilder sb = new StringBuilder((list.size() + 1 * 16));

		if (list.size() == 1)
		{
			sb.append(list.get(0));
		} 
		else if (list.size() > 1)
		{
			int sz = list.size() - 1;

			for (int i = 0; i < sz; i++)
			{
				sb.append(list.get(i).toString());

				sb.append(separator);
			}

			sb.append(list.get(sz).toString());
		}

		return sb.toString();
	}

	/**
	 * Create string of all objects in collection
	 * 
	 * @param separator Separator string
	 * @param objects   object array
	 * @return String of objects separated by specific separator string
	 */
	public static final String toSeparatedString(String separator, Object... objects)
	{
		CArrayList<Object> list = new CArrayList<Object>(objects.length + 1);

		for (Object o : objects)
		{
			list.add(o);
		}

		return toSeparatedString(separator, list);
	}

	/**
	 * Create string of all object values in collection
	 * 
	 * @param separator Separator string
	 * @param map       map collection
	 * @return String of objects separated by specific separator string
	 */
	public static final String toSeparatedString(String separator, Map<?, ? extends Object> map)
	{
		return toSeparatedString(separator, map.values().toArray());
	}

	/**
	 * Returns the next available integer id within a collection
	 * 
	 * @param collection objects collection
	 * @return next available id
	 */
	public static int getNextIntegerID(Collection<? extends IIntegerID> collection)
	{
		return getNextIntegerID((Integer.MIN_VALUE + 1), collection);
	}

	/**
	 * Returns the next available integer id within a collection
	 * 
	 * @param startID    starting ID
	 * @param collection objects collection
	 * @return next available id
	 */
	public static int getNextIntegerID(int startID, Collection<? extends IIntegerID> collection)
	{
		int nextID = startID;

		for (IIntegerID o : collection)
		{
			nextID = Math.max(nextID, o.getIntegerID());
		}

		return (nextID + 1);
	}

	/**
	 * Returns the next available long id within a collection
	 * 
	 * @param startID    starting ID
	 * @param collection of objects
	 * @return next available id
	 */
	public static long getNextLongID(long startID, Collection<? extends ILongID> collection)
	{
		long nextID = startID;

		for (ILongID o : collection)
		{
			nextID = Math.max(nextID, o.getLongID());
		}

		return (nextID + 1);

	}

	/**
	 * Returns the next available long id within a collection
	 * 
	 * @param collection of objects
	 * @return next available id
	 */
	public static long getNextLongID(Collection<? extends ILongID> collection)
	{
		return getNextLongID((Long.MIN_VALUE + 1), collection);
	}

}
