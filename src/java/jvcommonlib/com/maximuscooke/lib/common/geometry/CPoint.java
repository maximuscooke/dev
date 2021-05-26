package com.maximuscooke.lib.common.geometry;

import com.maximuscooke.lib.common.ISerializable;

public class CPoint<T> implements ISerializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2972793649429270238L;
	
	private T mX;
	private T mY;
	private T mZ;
	
	public CPoint(T x, T y, T z)
	{
		this.init(x, y, z);
	}
	
	public final void init(T x, T y, T z)
	{
		this.setX(x);
		
		this.setY(y);
		
		this.setZ(z);
	}
	
	public final T getX()
	{
		return mX;
	}

	public final void setX(T x)
	{
		this.mX = x;
	}

	public final T getY()
	{
		return mY;
	}

	public final void setY(T y)
	{
		this.mY = y;
	}

	public final T getZ()
	{
		return mZ;
	}

	public final void setZ(T z)
	{
		this.mZ = z;
	}

	@Override
	public String toString()
	{
		return "CPoint [X=" + mX + ", Y=" + mY + ", Z=" + mZ + "]";
	}

}
