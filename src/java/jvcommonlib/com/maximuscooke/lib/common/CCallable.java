package com.maximuscooke.lib.common;

import java.util.concurrent.Callable;

public abstract class CCallable<T> implements Callable<T>, INotify
{
	private volatile INotifyListener mListener = null;

	public CCallable()
	{
	}

	@Override
	public synchronized void notify(int action, INotifyMessage msg)
	{
		if (this.mListener != null)
		{
			mListener.notifyListeners(this, action, msg);
		}
	}

	@Override
	public synchronized void register(INotifyListener listener)
	{
		mListener = listener;
	}

	@Override
	public synchronized void unregister(INotifyListener listener)
	{
		mListener = null;
	}
}
