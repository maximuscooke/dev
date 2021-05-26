package com.maximuscooke.lib.common;

public class CTextTranslator implements ISerializable
{
	private static final long serialVersionUID = 2360318462412781420L;

	public CTextTranslator()
	{
	}

	public static String translate(String str)
	{
		return translate(str, str);
	}

	public static String translate(String str, String defaulStr)
	{
		return str;
	}
}
