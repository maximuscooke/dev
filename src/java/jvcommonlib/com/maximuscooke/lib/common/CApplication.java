package com.maximuscooke.lib.common;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class CApplication
{
	private static final Random mRandom = new Random();

	public static String FILE_SEPARATOR = File.separator;
	public static final String NEW_LINE_STRING = "\n";
	public static String DEFAULT_FONT_FAMILY = "Helvetica";
	public static final String EMPTY_STRING = "";
	public static final String PIPE_SEPARATOR = "|";
	public static final String NULL_PTR_STRING = "<nullptr>";

	public static String getLineSeparator()
	{
		return System.getProperty("line.separator");
	}

	public static void setAppleUseScreenMenuBar(boolean bUse)
	{
		System.setProperty("apple.laf.useScreenMenuBar", new Boolean(bUse).toString());
	}
	/**
	 * Get Random object
	 * 
	 * @return random object
	 */
	public static Random getRandom()
	{
		return mRandom;
	}

	/**
	 * Get Random integer
	 * 
	 * @return random integer
	 */
	public static int getRandomInteger()
	{
		return mRandom.nextInt();
	}

	/**
	 * Get random integer between zero and max bounds
	 * 
	 * @param max maximum value
	 * @return random integer
	 */
	public static int getRandomInteger(int max)
	{
		return mRandom.nextInt(Math.max(1, max));
	}

	/**
	 * Get random integer between minimum and maximum bounds
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 * @return random integer between minimum and max
	 */
	public static int getRandomInteger(int min, int max)
	{
		return min + getRandomInteger((max - min));
	}

	/**
	 * Get random boolean value
	 * 
	 * @return random boolean
	 */
	public static boolean getRandomBoolean()
	{
		return mRandom.nextBoolean();
	}

	/**
	 * Get random float value
	 * 
	 * @return random float value
	 */
	public static float getRandomFloat()
	{
		return mRandom.nextFloat();
	}

	/**
	 * Get random long value
	 * 
	 * @return random long value
	 */
	public static long getRandomLong()
	{
		return mRandom.nextLong();
	}

	/**
	 * Get random double value
	 * 
	 * @return random double value
	 */
	public static double getRandomDouble()
	{
		return mRandom.nextDouble();
	}

	/**
	 * Return true if object is null
	 * 
	 * @param obj object to test
	 * @return true if object is null, else false
	 */
	public static boolean isNull(Object obj)
	{
		return (obj == null);
	}

	/**
	 * Return true if object is NOT null
	 * 
	 * @param obj object to test
	 * @return true if object is NOT null, else false
	 */
	public static boolean isNotNull(Object obj)
	{
		return (obj != null);
	}

	/**
	 * Run garbage collector
	 */
	public static void runGC()
	{
		System.gc();
	}

	/**
	 * Limits a value to be within a given range 0 to max
	 * 
	 * @param value to limit
	 * @param max   maximum range value
	 * @return value limited to range
	 */
	public static byte limit(byte value, byte max)
	{
		return (byte) limit(value, (byte) 0, max);
	}

	/**
	 * Limits a value to be within a given range minimum and maximum
	 * 
	 * @param value to limit
	 * @param min   minimum range value
	 * @param max   maximum range value
	 * @return value limited to range
	 */
	public static byte limit(byte value, byte min, byte max)
	{
		return (byte) Math.max((int) min, Math.min((int) value, (int) max));
	}

	/**
	 * Limits a value to be within a given range 0 to max
	 * 
	 * @param value to limit
	 * @param max   maximum range value
	 * @return value limited to range
	 */
	public static short limit(short value, short max)
	{
		return (short) limit(value, 0, max);
	}

	/**
	 * Limits a value to be within a given range minimum and maximum
	 * 
	 * @param value to limit
	 * @param min   minimum range value
	 * @param max   maximum range value
	 * @return value limited to range
	 */
	public static short limit(short value, short min, short max)
	{
		return (short) Math.max(min, Math.min(value, max));
	}

	/**
	 * Limits a value to be within a given range 0 to max
	 * 
	 * @param value to limit
	 * @param max   maximum range value
	 * @return value limited to range
	 */
	public static int limit(int value, int max)
	{
		return limit(value, 0, max);
	}

	/**
	 * Limits a value to be within a given range minimum and maximum
	 * 
	 * @param value to limit
	 * @param min   minimum range value
	 * @param max   maximum range value
	 * @return value limited to range
	 */
	public static int limit(int value, int min, int max)
	{
		return Math.max(min, Math.min(value, max));
	}

	/**
	 * Limits a value to be within a given range 0 to max
	 * 
	 * @param value to limit
	 * @param max   maximum range value
	 * @return value limited to range
	 */
	public static long limit(long value, long max)
	{
		return limit(value, 0, max);
	}

	/**
	 * Limits a value to be within a given range minimum and maximum
	 * 
	 * @param value to limit
	 * @param min   minimum range value
	 * @param max   maximum range value
	 * @return value limited to range
	 */
	public static long limit(long value, long min, long max)
	{
		return Math.max(min, Math.min(value, max));
	}

	/**
	 * Set bit in target
	 * 
	 * @param bit    bit position to set
	 * @param target target value
	 * @return original value with bit position set
	 */
	public static int setBit(byte bit, int target)
	{
		return target | (1 << bit);
	}

	/**
	 * Set bit in target value
	 * 
	 * @param bit    bit position to set
	 * @param target target value
	 * @return original value with bit position set
	 */
	public static long setBit(byte bit, long target)
	{
		return target | (1 << bit);
	}

	/**
	 * Set bit in target value
	 * 
	 * @param bit    bit position to set
	 * @param target target value
	 * @return original value with bit position set
	 */
	public static short setBit(byte bit, short target)
	{
		return (short) (target | (1 << bit));
	}

	/**
	 * Set bit in target value
	 * 
	 * @param bit    bit position to set
	 * @param target target value
	 * @return original value with bit position set
	 */
	public static byte setBit(byte bit, byte target)
	{
		return (byte) (target | (1 << bit));
	}

	/**
	 * Clear bit in target value
	 * 
	 * @param bit    bit position to clear
	 * @param target target value
	 * @return original value with bit position cleared
	 */
	public static int clearBit(byte bit, int target)
	{
		return target & ~(1 << bit);
	}

	/**
	 * Clear bit in target value
	 * 
	 * @param bit    bit position to clear
	 * @param target target value
	 * @return original value with bit position cleared
	 */
	public static long clearBit(byte bit, long target)
	{
		return target & ~(1L << bit);
	}

	/**
	 * Clear bit in target value
	 * 
	 * @param bit    bit position to clear
	 * @param target target value
	 * @return original value with bit position cleared
	 */
	public static short clearBit(byte bit, short target)
	{
		return (short) (target & ~(1 << bit));
	}

	/**
	 * Clear bit in target value
	 * 
	 * @param bit    bit position to clear
	 * @param target target value
	 * @return original value with bit position cleared
	 */
	public static byte clearBit(byte bit, byte target)
	{
		return (byte) (target & ~(1 << bit));
	}

	/**
	 * Toggle/Flip bit in target value
	 * 
	 * @param bit    bit position to toggle
	 * @param target target value
	 * @return original value with bit position toggled
	 */
	public static int toggleBit(byte bit, int target)
	{
		return (target ^ (1 << bit));
	}

	/**
	 * Toggle/Flip bit in target value
	 * 
	 * @param bit    bit position to toggle
	 * @param target target value
	 * @return original value with bit position toggled
	 */
	public static long toggleBit(byte bit, long target)
	{
		return (target ^ (1L << bit));
	}

	/**
	 * Toggle/Flip bit in target value
	 * 
	 * @param bit    bit position to toggle
	 * @param target target value
	 * @return original value with bit position toggled
	 */
	public static short toggleBit(byte bit, short target)
	{
		return (short) (target ^ (1L << bit));
	}

	/**
	 * Toggle/Flip bit in target value
	 * 
	 * @param bit    bit position to toggle
	 * @param target target value
	 * @return original value with bit position toggled
	 */
	public static byte toggleBit(byte bit, byte target)
	{
		return (byte) (target ^ (1 << bit));
	}

	/**
	 * Test if bit is set in target value
	 * 
	 * @param bit    bit position to test
	 * @param target target value
	 * @return true if bit set, else false
	 */
	public static boolean isBitSet(byte bit, int target)
	{
		return ((target & (1 << bit)) != 0);
	}

	/**
	 * Test if bit is set in target value
	 * 
	 * @param bit    bit position to test
	 * @param target target value
	 * @return true if bit set, else false
	 */
	public static boolean isBitSet(byte bit, long target)
	{
		return ((target & (1L << bit)) != 0);
	}

	/**
	 * Test if bit is set in target value
	 * 
	 * @param bit    bit position to test
	 * @param target target value
	 * @return true if bit set, else false
	 */
	public static boolean isBitSet(byte bit, short target)
	{
		return ((target & (1 << bit)) != 0);
	}

	/**
	 * Test if bit is set in target value
	 * 
	 * @param bit    bit position to test
	 * @param target target value
	 * @return true if bit set, else false
	 */
	public static boolean isBitSet(byte bit, byte target)
	{
		return ((target & (1 << bit)) != 0);
	}

	/**
	 * Convert target to binary string for display
	 * 
	 * @param target target value
	 * @return String representation of target in binary format
	 */
	public static String bitsToString(long target)
	{
		return formatBinaryString(target, (Long.BYTES << 3));
	}

	/**
	 * Convert target to binary string for display
	 * 
	 * @param target target value
	 * @return String representation of target in binary format
	 */
	public static String bitsToString(short target)
	{
		return formatBinaryString((long) target, (Short.BYTES << 3));
	}

	/**
	 * Convert target to binary string for display
	 * 
	 * @param target target value
	 * @return String representation of target in binary format
	 */
	public static String bitsToString(byte target)
	{
		return formatBinaryString((long) target, (Byte.BYTES << 3));
	}

	/**
	 * Convert target to binary string for display
	 * 
	 * @param target target value
	 * @return String representation of target in binary format
	 */
	public static String bitsToString(int target)
	{
		return formatBinaryString((long) target, (Integer.BYTES << 3));
	}

	/**
	 * Format binary string with padding zeros
	 * 
	 * @param value  target value to convert to binary string
	 * @param length number of bits
	 * @return Binary formatted string
	 */
	private static String formatBinaryString(Long value, int length)
	{
		StringBuilder sb = new StringBuilder(String.format(("%0" + length + "d"), 0));

		sb.append(Long.toBinaryString(value));

		String s = sb.toString();

		return String.format("%1$" + length + "s", s).substring(s.length() - length, s.length());
	}
	
	public static void doExport(String dir, String fName, IExportFile obj, int options) throws Exception
	{
		if ((options & IExportObject.EXPORT_OPT_CREATE) != 0)
		{
			CFile.deleteIfExists(dir, fName);			
		}
		
		CTextFile tf = new CTextFile(dir, fName);
		
		obj.doExport(tf);
						
		tf.save(dir, fName, (options & IExportObject.EXPORT_OPT_APPEND) != 0);
	}
	
	public static void doImport(String dir, String fName, IExportFile obj) throws Exception
	{
		CTextFile tf = new CTextFile(dir, fName);
		
		tf.read();
		
		obj.doImport(tf, 0L);		
	}
	
	public static String fromNullPtrString(String str)
	{
		return (str != null && str.equalsIgnoreCase(CApplication.NULL_PTR_STRING)) ? null : str;
	}
	
	public static String toNullPtrString(String str)
	{
		return (str == null) ? CApplication.NULL_PTR_STRING : str;
	}
	
	public static boolean isNullPtrString(String str)
	{
		return str.equalsIgnoreCase(CApplication.NULL_PTR_STRING);
	}
	
	public static List<String> splitExportString(String ln, String separator)
	{
		String split = separator.equalsIgnoreCase(CApplication.PIPE_SEPARATOR) ? "\\|" : separator;
		
		return Arrays.asList(ln.split(split));
	}
}
