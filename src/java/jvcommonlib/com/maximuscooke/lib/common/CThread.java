package com.maximuscooke.lib.common;

public abstract class CThread extends Thread implements INotify
{

	public static final int THREAD_STATUS_COMPLETED = 0;
	public static final int THREAD_STATUS_INTERRUPTED = 30;
	public static final int THREAD_STATUS_NOT_STARTED = 40;
	public static final int THREAD_STATUS_RUNNING = 50;

	private volatile int mStatus = THREAD_STATUS_NOT_STARTED;
	private volatile INotifyListener mListener = null;

	public CThread()
	{
	}

	public CThread(Runnable target)
	{
		super(target);
	}

	public CThread(String name)
	{
		super(name);
	}

	public CThread(ThreadGroup group, Runnable target)
	{
		super(group, target);
	}

	public CThread(ThreadGroup group, String name)
	{
		super(group, name);
	}

	public CThread(Runnable target, String name)
	{
		super(target, name);
	}

	public CThread(ThreadGroup group, Runnable target, String name)
	{
		super(group, target, name);
	}

	public CThread(ThreadGroup group, Runnable target, String name, long stackSize)
	{
		super(group, target, name, stackSize);
	}

	@Override
	public abstract void run();

	public synchronized final int getStatus()
	{
		return this.mStatus;
	}

	public synchronized final void setStatus(int state)
	{
		this.mStatus = state;
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
