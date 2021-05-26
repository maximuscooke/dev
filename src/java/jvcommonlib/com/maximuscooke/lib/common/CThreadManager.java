package com.maximuscooke.lib.common;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.maximuscooke.lib.common.collections.CHashMap;
import com.maximuscooke.lib.common.exceptions.CDuplicateKeyException;
import com.maximuscooke.lib.common.exceptions.CKeyNotFoundException;

public class CThreadManager
{

	private ExecutorService mThreadPool;
	private CHashMap<String, Future<?>> mFutureMap = new CHashMap<String, Future<?>>();
	private CHashMap<String, CThread> mThreadMap = new CHashMap<String, CThread>();

	public CThreadManager()
	{
		this(-1);
	}

	public CThreadManager(int threadCount)
	{
		if (threadCount <= 0)
		{
			mThreadPool = Executors.newCachedThreadPool();

		} else
		{
			mThreadPool = Executors.newFixedThreadPool(Math.max(threadCount, 2));
		}
	}

	public <T> Future<T> submit(String key, CCallable<T> task, INotifyListener listener) throws Exception
	{

		assert (key != null) : "[CThreadManager::submit] Key cannot be null";
		assert (task != null) : "[CThreadManager::submit] Name cannot be null";

		if (this.mFutureMap.containsKey(key))
		{
			throw new CDuplicateKeyException(key + " already exist");
		}

		task.register(listener);

		Future<T> future = mThreadPool.submit(task);

		this.mFutureMap.add(key, future);

		return future;
	}

	public <T> Future<T> submit(String key, CCallable<T> task) throws Exception
	{
		return submit(key, task, null);
	}

	public CThread submit(String key, CThread task)
	{
		return submit(key, task, null);
	}

	public CThread submit(String key, CThread task, INotifyListener listener)
	{
		assert (key != null) : "[CThreadManager::submit] Key cannot be null";
		assert (task != null) : "[CThreadManager::submit] Thread task cannot be null";

		task.register(listener);

		this.mThreadPool.submit(task);

		this.mThreadMap.add(key, task);

		return task;
	}

	public void shutdown(boolean bWait) throws InterruptedException
	{
		shutdown(bWait, 1, TimeUnit.DAYS);
	}

	public void shutdown(boolean bWait, long timeout, TimeUnit unit) throws InterruptedException
	{
		this.mThreadPool.shutdown();

		if (bWait)
		{
			awaitTermination(timeout, unit);
		}
	}

	public void shutdownNow()
	{
		this.mThreadPool.shutdownNow();
	}

	public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException
	{
		this.mThreadPool.awaitTermination(timeout, unit);
	}

	/**
	 * Wait for all futures to complete
	 */
	public void waitForFutures()
	{
		int count;

		do
		{
			count = 0;

			for (Future<?> f : this.mFutureMap.values())
			{
				if (!f.isDone())
				{
					++count;
				}
			}
		} while (count > 0);
	}

	/**
	 * Wait for all threads to complete
	 */
	public void waitForThreads()
	{
		int count;

		do
		{
			count = 0;

			for (CThread t : this.mThreadMap.values())
			{
				if (t.getStatus() != CThread.THREAD_STATUS_COMPLETED)
				{
					++count;
				}
			}
		} while (count > 0);
	}

	/**
	 * Wait for futures and threads to finish
	 */
	public void waitForAll()
	{
		this.waitForFutures();

		this.waitForThreads();
	}

	public Future<?> getFuture(String key) throws CKeyNotFoundException
	{
		assert (key != null) : "[CThreadManager::getFuture] Key cannot be null";

		if (!this.mFutureMap.containsKey(key))
		{
			throw new CKeyNotFoundException("key <" + key + "> not found");
		}

		return this.mFutureMap.get(key);
	}

	/**
	 * Returns the value calculated by the running thread
	 * 
	 * @param key named of thread
	 * @return Thread calculated value
	 * @throws CKeyNotFoundException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Object getFutureValue(String key) throws CKeyNotFoundException, InterruptedException, ExecutionException
	{
		assert (key != null) : "[CThreadManager::getValue] Key cannot be null";

		if (!this.mFutureMap.containsKey(key))
		{
			throw new CKeyNotFoundException("key <" + key + "> not found");
		}

		return getFuture(key).get();
	}

	/**
	 * Returns the value calculated by the running thread
	 * 
	 * @param <T>  Type of value to return
	 * @param key  named of thread
	 * @param task class type, calling code should look like Integer.class,
	 *             Long.class or String.class
	 * @return Thread calculated value
	 * @throws CKeyNotFoundException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public <T> T getFutureValue(String key, Class<T> task) throws CKeyNotFoundException, InterruptedException, ExecutionException
	{
		return task.cast(getFutureValue(key));
	}

	public CThread getThread(String key) throws CKeyNotFoundException
	{
		assert (key != null) : "[CThreadManager::getThread] Key cannot be null";

		if (!this.mThreadMap.containsKey(key))
		{
			throw new CKeyNotFoundException("key <" + key + "> not found");
		}

		return this.mThreadMap.get(key);
	}

}
