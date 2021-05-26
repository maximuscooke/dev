package com.maximuscooke.lib.common;

import java.util.List;

public abstract class CString
{
	public static String toString(Object... objects)
	{
		StringBuilder sb = new StringBuilder(1024);

		for (Object o : objects)
		{
			sb.append(o.toString());
		}

		return sb.toString();
	}

	public static String capitalize(String str)
	{
		return new StringBuilder().append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
	}

	public static void capitalize(List<String> strings)
	{
		for (int i = 0; i < strings.size(); i++)
		{
			strings.set(i, CString.capitalize(strings.get(i)));
		}
	}

	public static String findAndReplace(String source, String search, String replacement)
	{
		return source.replaceAll(search, replacement);
	}

	public static void findAndReplace(List<String> strings, String search, String replacement)
	{
		for (int i = 0; i < strings.size(); i++)
		{
			strings.set(i, CString.findAndReplace(strings.get(i), search, replacement));
		}
	}

	public static String abbreviate(String src, int maxLength)
	{

		assert (src != null && maxLength > 0) : "[CString::abbreviate] string must not be null and length must be greater than zero";

		if (src.length() > maxLength)
		{
			String appendStr = (maxLength == 1 || maxLength == 2) ? "." : "..";

			return src.substring(0, maxLength - (appendStr.length())) + appendStr;
		}

		return src;
	}

	public static String reverse(String src)
	{
		assert (src != null) : "[CString::reverse] string is null";

		int j = src.length();

		char[] newWord = new char[j];

		for (int i = 0; i < src.length(); i++)
		{
			newWord[--j] = src.charAt(i);
		}
		
		return new String(newWord);
	}

}
