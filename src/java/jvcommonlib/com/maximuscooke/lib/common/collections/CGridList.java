package com.maximuscooke.lib.common.collections;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.maximuscooke.lib.common.CString;

public class CGridList<T> extends CLinkedList<CArrayList<T>>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2072992261148512180L;

	private CArrayList<String> mColumnHeaders = null;

	public CGridList()
	{
	}
	
	public void addColumnHeader(List<String> headings)
	{
		if (mColumnHeaders == null)
		{
			mColumnHeaders = new CArrayList<String>();
		}

		for (String h : headings)
		{
			mColumnHeaders.add(h);
		}
	}

	public void addColumnHeader(String... headings)
	{
		this.addColumnHeader(new CArrayList<String>(headings));
	}

	public void clearHeader()
	{
		if (this.mColumnHeaders != null)
		{
			this.mColumnHeaders.clear();
		}
	}

	public final void addRow(CArrayList<T> row)
	{
		this.add(row);
	}

	public final int getRowCount()
	{
		return this.getSize();
	}

	public final CArrayList<T> getRowAt(int index)
	{
		return this.getAt(index);
	}

	@Override
	public boolean isEmpty()
	{
		return this.getSize() > 0;
	}

	public final void setObjectAt(int row, int col, T val)
	{
		this.getAt(row).setAt(col, val);
	}

	public final T getObjectAt(int row, int col)
	{
		return this.getAt(row).getAt(col);
	}

	public int getMaxColWidth()
	{
		int result = 1;

		for (CArrayList<T> row : this)
		{
			for (int i = 0; i < row.getSize(); i++)
			{
				T ele = row.getAt(i);

				result = Math.max(result, (ele == null) ? 4 : ele.toString().length());
			}
		}
		
		return result;
	}

	public final int getColumnCount()
	{
		return (this.mColumnHeaders != null) ? this.mColumnHeaders.getSize() : 0;
	}

	public final boolean hasHeaders()
	{
		return (this.mColumnHeaders != null && this.mColumnHeaders.getSize() > 0);
	}

	public final CArrayList<String> getColumnHeaders()
	{
		return mColumnHeaders;
	}
	
	public final void forEachCell(Consumer<T> func)
	{
		runConsumerForGrid(func);
	}

	public final void setColumnHeaders(CArrayList<String> headers)
	{
		this.mColumnHeaders = headers;
	}
	
	public void runConsumerForGrid(Consumer<T> func)
	{
		this.forEach( row -> { row.forEach(e -> { func.accept( e ); } ); } );		
	}
	
	public CArrayList<T> runPredicateForGrid(Predicate<T> func)
	{
		CArrayList<T> results = new CArrayList<T>();
		
		this.forEach( row -> { row.forEach(e -> { if (func.test( e )) { results.add( e ); } } ); } );

		return results;
	}
	
	@Override
	public String toString()
	{
		int maxColWidth = this.getMaxColWidth();

		String formatStr = "%-" + maxColWidth + "s";

		StringBuilder sb = new StringBuilder();

		if (this.hasHeaders())
		{			
			this.mColumnHeaders.forEach(hdr -> {sb.append("["); String tmp = CString.abbreviate(String.format(formatStr, hdr), maxColWidth); sb.append(tmp); sb.append("] ");} );

			sb.append("\n");
		}

		this.forEach(row -> { row.forEach( e -> { sb.append("["); String strVal = (e == null) ? "null" : e.toString(); sb.append(String.format(formatStr, strVal)); sb.append("] "); } ); sb.append("\n"); } );
			
		

		return sb.toString();
	}

}
