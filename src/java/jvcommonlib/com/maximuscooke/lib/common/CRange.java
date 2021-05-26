package com.maximuscooke.lib.common;

public class CRange<T> implements IRange<T>, ISerializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3979781248419424927L;
	
	private T mMin;
	private T mMax;
		
	public CRange(T min, T max)
	{
		this.setMin(min);
		
		this.setMax(max);
	}

	@Override
	public String toString()
	{
		return "CRange [Min=" + mMin + ", Max=" + mMax + "]";
	}

	@Override
	public final T getMin()
	{
		return mMin;
	}

	@Override
	public final T getMax()
	{
		return mMax;
	}

	@Override
	public final void setMin(T min)
	{
		this.mMin = min;
	}

	@Override
	public final void setMax(T max)
	{
		this.mMax = max;
	}

}
