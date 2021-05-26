package com.maximuscooke.lib.common;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class CSerialize
{

	public static final int SERIALIZE_FORMAT_XML = 100;
	public static final int SERIALIZE_FORMAT_BINARY = 200;

	public static void serializeObjects(String dir, String fName, int format, Object... objects) throws IOException
	{
		if (format == CSerialize.SERIALIZE_FORMAT_XML)
		{
			serializeObjectsToXml(dir, fName, objects);
		} 
		else if (format == CSerialize.SERIALIZE_FORMAT_BINARY)
		{
			serializeObjects(dir, fName, objects);
		}
	}

	public static Object deSerializeObjects(String dir, String fName, int format) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Object obj = null;

		if (format == CSerialize.SERIALIZE_FORMAT_XML)
		{
			obj = deSerializeObjectFromXml(dir, fName);
		} 
		else if (format == CSerialize.SERIALIZE_FORMAT_BINARY)
		{
			obj = deSerializeObject(dir, fName);
		}

		return obj;
	}

	private static void serializeObjects(File file, Object... objects) throws IOException
	{
		FileOutputStream f = new FileOutputStream(file);

		ObjectOutputStream s = new ObjectOutputStream(f);

		for (Object obj : objects)
		{
			s.writeObject(obj);

			s.flush();
		}

		f.close();

		s.close();
	}

	private static void serializeObjects(String dir, String fName, Object... objects) throws IOException
	{
		serializeObjects(new File(dir, fName), objects);
	}

	private static void serializeObjectsToXml(File file, Object... objects) throws IOException
	{
		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));

		for (Object o : objects)
		{
			e.writeObject(o);

			e.flush();
		}

		e.close();
	}

	private static void serializeObjectsToXml(String dir, String fName, Object... objects) throws IOException
	{
		serializeObjectsToXml(new File(dir, fName), objects);
	}

	private static Object deSerializeObject(File file) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		FileInputStream f = new FileInputStream(file);

		ObjectInputStream s = new ObjectInputStream(f);

		Object o = s.readObject();

		f.close();

		s.close();

		return o;
	}

	private static Object deSerializeObject(String dir, String fName) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		return deSerializeObject(new File(dir, fName));
	}

	private static Object deSerializeObjectFromXml(File file) throws IOException, FileNotFoundException
	{
		XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));

		Object o = d.readObject();

		d.close();

		return o;
	}

	private static Object deSerializeObjectFromXml(String dir, String fName) throws IOException, FileNotFoundException
	{
		return deSerializeObjectFromXml(new File(dir, fName));
	}
}
