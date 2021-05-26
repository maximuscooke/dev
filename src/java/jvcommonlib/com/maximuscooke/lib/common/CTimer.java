package com.maximuscooke.lib.common;

public class CTimer
{
	public static final double MILLI_SECOND = 1000.0;
	public static final double NANO_SECOND = 1000000000.0;

	private long mStart = 0;
	private long mStop = 0;

	public CTimer()
	{
	}

	public static long getCurrentTimeInMillis()
	{
		return System.currentTimeMillis();
	}

	public static long getCurrentTimeInNano()
	{
		return System.nanoTime();
	}

	public static long getElapsedCpuInMillis(long start)
	{
		return CTimer.getCurrentTimeInMillis() - start;
	}

	public static double getElapsedTimeInMillis(long start)
	{
		return CTimer.getElapsedCpuInMillis(start) / CTimer.MILLI_SECOND;
	}

	public static long getElapsedCpuInNano(long start)
	{
		return CTimer.getCurrentTimeInNano() - start;
	}

	public static double getElapsedTimeInNano(long start)
	{
		return CTimer.getElapsedCpuInNano(start) / CTimer.NANO_SECOND;
	}

	public final void start()
	{
		this.mStart = CTimer.getCurrentTimeInNano();
	}

	public final void stop()
	{
		this.mStop = CTimer.getCurrentTimeInNano();
	}

	public final double getElapsedCpu()
	{
		return (this.mStop - this.mStart);
	}

	public final double getElapsedTime()
	{
		return (this.mStop - this.mStart) / CTimer.NANO_SECOND;
	}

	public final long getStart()
	{
		return this.mStart;
	}

	public final void setStart(long start)
	{
		this.mStart = start;
	}

	public final void setStop(long stop)
	{
		this.mStop = stop;
	}

	public final long getStop()
	{
		return this.mStop;
	}
}
