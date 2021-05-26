package com.maximuscooke.lib.common.geometry;

public class CLine<T>
{
	private CPoint<T> 	mStart;
	private CPoint<T>		mEnd;
	
	public CLine(T sx, T sy, T sz, T ex, T ey, T ez)
	{
		this.mStart.init(sx, sy, sz);
		
		this.mEnd.init(ex, ey, ez);
	}

	@Override
	public String toString()
	{
		return "CLine [Start=" + mStart + ", End=" + mEnd + "]";
	}

	public final CPoint<T> getStart()
	{
		return mStart;
	}

	public final void setStart(CPoint<T> p)
	{
		this.mStart = p;
	}

	public final CPoint<T> getEnd()
	{
		return mEnd;
	}

	public final void setEnd(CPoint<T> p)
	{
		this.mEnd = p;
	}
}
