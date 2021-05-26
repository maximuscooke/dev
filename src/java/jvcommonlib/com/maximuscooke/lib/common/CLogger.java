package com.maximuscooke.lib.common;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.maximuscooke.lib.common.collections.CArrayList;

public abstract class CLogger
{
	public static final int LOG_TO_SCREEN = (1 << 0);
	public static final int LOG_TO_FILE = (1 << 1);

	private static Locale locale = new Locale("en");
	private static String mFilename = "log.txt";

	private static CArrayList<String> mLines = new CArrayList<String>();
	private static int mOptions = CLogger.LOG_TO_SCREEN;

	private static boolean mClearFileOnFirstLog = false;
	private static boolean mFileCleared = false;
	private static boolean mAddTimeStamp = true;

	public static boolean log(String... msgs) throws Exception
	{

		for (String m : msgs)
		{
			doLog(m);
		}

		return true;
	}

	public static boolean log(Exception e) throws Exception
	{
		doLog(e.getMessage());

		return true;
	}

	private static void doLog(String msg) throws Exception
	{
		if (mClearFileOnFirstLog && !mFileCleared)
		{
			CFile.deleteIfExists(mFilename);

			mFileCleared = true;
		}

		GregorianCalendar cal = new GregorianCalendar(locale);

		String logMsg = (mAddTimeStamp == true) ? (String.format("[%02d:%02d:%02d] ", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + msg) : msg;

		if ((mOptions & LOG_TO_SCREEN) != 0)
		{
			System.out.println(logMsg);
		}

		if ((mOptions & LOG_TO_FILE) != 0)
		{
			mLines.clear();

			mLines.add(logMsg);

			CTextFile.write(mFilename, mLines, true);
		}
	}

	public static void logDate() throws Exception
	{
		GregorianCalendar cal = new GregorianCalendar(locale);

		doLog(String.format("[%02d:%02d:%04d]", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)));

	}

	public static boolean getAddTimeStamp()
	{
		return mAddTimeStamp;
	}

	public static void setAddTimeStamp(boolean bAdd)
	{
		mAddTimeStamp = bAdd;
	}

	public static boolean getClearFileOnFirstLog()
	{
		return mClearFileOnFirstLog;
	}

	public static void setCleaFileOnFirstLog(boolean bClear)
	{
		mClearFileOnFirstLog = bClear;
	}

	public static void setFilename(String fn)
	{
		mFilename = fn;
	}

	public static String getFilename()
	{
		return mFilename;
	}

	public static int getOptions()
	{
		return mOptions;
	}

	public static void setOptions(int options)
	{
		mOptions = options;
	}
}
