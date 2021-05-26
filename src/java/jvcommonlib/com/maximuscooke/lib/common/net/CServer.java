package com.maximuscooke.lib.common.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.maximuscooke.lib.common.IVersion;
import com.maximuscooke.lib.common.collections.CHashMap;

public class CServer implements IVersion
{

	public static final int DEFAULT_SERVER_PORT = 4657;
	public static final int DEFAULT_POOLSIZE = 64;
	public static final int DEFAULT_SOCKET_TIMEOUT = 1;
	public static final String DEFAULT_SERVER_NAME = "localhost";

	public static final int REQUEST_SERVER_SHUTDOWN = -10;
	public static final int REQUEST_SERVER_INFO = -20;
	public static final int REQUEST_SERVER_CONTEXT = -30;

	public static final int REQUEST_CODE_SUCCESSFUL = 0;
	public static final int REQUEST_CODE_FAILED = -1;

	private ExecutorService mPool;

	private int mPort = DEFAULT_SERVER_PORT;
	private int mTimeOut = DEFAULT_SOCKET_TIMEOUT;
	private int mPoolSize = DEFAULT_POOLSIZE;

	private final int mReleaseVer = 1;
	private final int mBuildVer = 0;
	private final int mRevisionVer = 0;

	private boolean mLog = false;
	private static volatile boolean mRun = false;

	public CServer()
	{
		this(CServer.DEFAULT_SERVER_PORT);
	}

	public CServer(int port)
	{
		this(port, CServer.DEFAULT_POOLSIZE);
	}

	public CServer(int port, int poolSize)
	{
		this(port, poolSize, CServer.DEFAULT_SOCKET_TIMEOUT);
	}

	public CServer(int port, int poolSize, int socketTimeout)
	{

		this.mPort = port;

		this.mPoolSize = poolSize;

		this.mTimeOut = socketTimeout;
	}

	public CServer(CHashMap<String, String> map)
	{

		if (map.containsKey(CNetRunner.PORT_PARAM))
		{
			this.mPort = Integer.parseInt(map.get(CNetRunner.PORT_PARAM));
		}

		if (map.containsKey(CNetRunner.POOL_SIZE_PARAM))
		{
			this.mPoolSize = Integer.parseInt(map.get(CNetRunner.POOL_SIZE_PARAM));
		}

		if (map.containsKey(CNetRunner.TIMEOUT_PARAM))
		{
			this.mTimeOut = Integer.parseInt(map.get(CNetRunner.TIMEOUT_PARAM));
		}

		if (map.containsKey(CNetRunner.LOG_PARAM))
		{
			this.mLog = true;
		}
	}

	public void start()
	{

		CServer.mRun = true;

		System.out.println(this.toString());

		if (mPoolSize <= 0)
		{
			if (mLog)
			{
				System.out.println("[SERVER] Using cached thread pool.");
			}
			mPool = Executors.newCachedThreadPool();

		} 
		else
		{
			if (mLog)
			{
				System.out.println("[SERVER] Using fixed thread pool.");
			}

			mPool = Executors.newFixedThreadPool(mPoolSize);
		}

		ServerSocket serverSocket = null;

		try
		{
			serverSocket = new ServerSocket(mPort);

			serverSocket.setSoTimeout(mTimeOut * 1000);

			while (mRun)
			{

				try
				{
					Socket s = serverSocket.accept();

					mPool.submit(new CServerThread(s));

				} 
				catch (SocketTimeoutException e)
				{
					if (mLog)
					{
						System.out.println("[SERVER] Request timed out...");
					}
				} 
				catch (IOException e)
				{
					System.out.println(e.getMessage());
				}
			}

			serverSocket.close();

		} catch (IOException e1)
		{
			System.out.println(e1.getMessage());
		} 
		finally
		{

			mPool.shutdown();

			try
			{
				System.out.print("[SERVER] Waiting for Threads to finish...");

				mPool.awaitTermination(30, TimeUnit.MINUTES);

				System.out.println("Completed\n[SERVER] Shutdown...Completed");

			} catch (InterruptedException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}

	public synchronized static void shutdowServer()
	{
		System.out.println("[SERVER] Shutdown...Starting");

		mRun = false;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("[SERVER=Version (");

		sb.append(this.getReleaseVersion());

		sb.append(".");

		sb.append(this.getBuildVersion());

		sb.append(".");

		sb.append(this.getRevisionVersion());

		sb.append(")]\n");

		sb.append("[STATUS=");

		sb.append((CServer.mRun == true) ? "Running, " : "Not Running, ");

		sb.append("PORT=");

		sb.append(this.mPort);

		sb.append(", ");

		sb.append("POOL-SIZE=");

		sb.append(this.mPoolSize);

		sb.append(", TIMEOUT=");

		sb.append(this.mTimeOut);

		sb.append(", LOG=");

		sb.append(this.mLog == true ? "On]" : "Off]");

		return sb.toString();
	}

	@Override
	public int getReleaseVersion()
	{
		return this.mReleaseVer;
	}

	@Override
	public int getBuildVersion()
	{
		return this.mBuildVer;
	}

	@Override
	public int getRevisionVersion()
	{
		return this.mRevisionVer;
	}
}
