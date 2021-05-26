package com.maximuscooke.lib.common.net;

import java.io.Serializable;

import com.maximuscooke.lib.common.ISerializable;
import com.maximuscooke.lib.common.collections.CHashMap;

public abstract class CPacket implements ISerializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5217086393610424912L;

	private int mRequestID = 0;
	private Object mReturnObject = null;
	private int mErrorCode = 0;
	private String mErrorMsg = null;

	private CHashMap<String, Serializable> mMap = null;

	public CPacket()
	{
	}

	public CPacket(int requestID)
	{

		this.mRequestID = requestID;
	}

	/**
	 * This method executes on the server
	 * 
	 * @return method specific return value
	 */
	public abstract Object runOnServer() throws Exception;

	/**
	 * A General purpose collection for passing objects between client/server
	 * request
	 * 
	 * @param key identifier string of object
	 * @param obj generic object
	 */
	public final void addParameters(String key, Serializable obj)
	{
		if (this.mMap == null)
		{
			this.mMap = new CHashMap<String, Serializable>();
		}

		mMap.add(key, obj);
	}

	/**
	 * Return Request ID
	 * 
	 * @return
	 */
	public final int getRequestID()
	{
		return mRequestID;
	}

	/**
	 * Return value of server function
	 * 
	 * @return Object value
	 */
	public final Object getReturnValue()
	{
		return this.mReturnObject;
	}

	/**
	 * Set return object value
	 * 
	 * @param return value object
	 */
	public void setReturnValue(Object obj)
	{
		this.mReturnObject = obj;
	}

	public boolean getRequestSuccess()
	{
		return (this.mErrorCode == 0);
	}

	public final CHashMap<String, Serializable> getMap()
	{
		return this.mMap;
	}

	public final void setMap(CHashMap<String, Serializable> map)
	{
		this.mMap = map;
	}

	public final String getErrorMessage()
	{
		return mErrorMsg;
	}

	public final void setErrorMessage(String msg)
	{
		this.mErrorMsg = msg;
	}

	public final int getErrorCode()
	{
		return mErrorCode;
	}

	public final void setErrorCode(int code)
	{
		this.mErrorCode = code;
	}
}
