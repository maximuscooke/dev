package com.maximuscooke.lib.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CHashMap;

public class CTextFile extends CFile implements IEncryption
{
	private static final long serialVersionUID = 2501079505115700192L;
	
	public static final int OP_SORT = (1<<0);
	public static final int OP_CAPITALIZE = (1<<1);
	public static final int OP_REMOVE_DUPLICATES = (1<<2);
	public static final int OP_ENCRYPT = (1<<3);
	public static final int OP_REMOVE_BLANK_LINES = (1<<4);

	private CArrayList<String> mLines = null;
	private int mCapacity = CCollections.DEFAULT_CAPACITY;

	public CTextFile(String fileName)
	{
		this(null, fileName);
	}

	public CTextFile(String dir, String fName)
	{
		this(dir, fName, CCollections.DEFAULT_CAPACITY);
	}

	public CTextFile(String dir, String fName, int capacity)
	{
		super(dir, fName);

		this.setCapacity(capacity);

		this.mLines = new CArrayList<String>(capacity);
	}
		
	public static void write(String fName, List<String> lines, boolean bAppend) throws Exception
	{
		CTextFile.write(new File(fName), lines, bAppend);
	}

	public static void write(String dir, String fName, List<String> lines, boolean bAppend) throws Exception
	{
		CTextFile.write(new File(dir, fName), lines, bAppend);
	}

	public static void write(File f, List<String> lines, boolean bAppend) throws Exception
	{
		FileWriter fw = new FileWriter(f, bAppend);

		BufferedWriter bw = new BufferedWriter(fw);

		PrintWriter pw = new PrintWriter(bw, true);

		for (String ln : lines)
		{
			pw.println(ln);
		}

		fw.close();

		pw.close();

		bw.close();
	}

	public static CArrayList<String> read(String fPath) throws Exception
	{
		return CTextFile.read(fPath, CCollections.DEFAULT_CAPACITY);
	}

	public static CArrayList<String> read(String fPath, int capacity) throws Exception
	{
		return CTextFile.read(new File(fPath), capacity);
	}

	public static CArrayList<String> read(String dir, String fName, int capacity) throws Exception
	{
		return CTextFile.read(new File(dir, fName), capacity);
	}

	public static CArrayList<String> read(File f, int capacity) throws Exception
	{
		if (!f.exists())
		{
			throw new Exception("File <" + f.getAbsolutePath() + "> not found.");
		}

		CArrayList<String> lines = new CArrayList<String>(capacity);

		FileReader fr = new FileReader(f);

		BufferedReader br = new BufferedReader(fr);

		String line;

		boolean bEof = false;

		while (bEof == false)
		{
			try
			{
				line = br.readLine();

				if (line == null)
				{
					break;
				}

				lines.add(line);
			} 
			catch (Exception e)
			{
				bEof = true;
			}
		}

		fr.close();

		br.close();

		lines.trimToSize();

		return lines;
	}

	public static void sort(String dir, String fName, Comparator<String> c) throws Exception
	{
		CTextFile f = new CTextFile(dir, fName);

		f.open();

		if (c != null)
		{
			f.sort(c);
		}

		f.save();
	}

	public static void sort(String dir, String fName) throws Exception
	{
		CTextFile.sort(dir, fName, null);
	}

	public static void encrypt(String dir, String fName, String key, int iteration) throws Exception
	{
		CTextFile f = new CTextFile(dir, fName);

		f.open();

		f.encrypt(key, iteration);

		f.save();
	}

	public static void decrypt(String dir, String fName, String key, int iteration) throws Exception
	{
		CTextFile f = new CTextFile(dir, fName);

		f.open();

		f.decrypt(key, iteration);

		f.save();
	}

	public static void capitalize(List<String> lines)
	{
		for (int i = 0; i < lines.size(); i++)
		{
			lines.set(i, CString.capitalize(lines.get(i)));
		}
	}
	
//	public void capitalize()
//	{
//	}
	
	public static CRange<Long> findMarkerRange(String marker, List<String> list)
	{
		return findMarkerRange(marker, marker, list);
	}
	
	public static CRange<Long> findMarkerRange(String markerStart, String markerEnd, List<String> list)
	{
		CRange<Long> results = new CRange<Long>(-1L, -1L);
		
		boolean bFoundStart=false;
		
		for(int i = 0; i < list.size(); i++)
		{
			String row = list.get(i);
			
			if (row != null)
			{
				if (!bFoundStart && row.equals(markerStart))
				{
					results.setMin( (long)i );
					
					bFoundStart = true;
				}
				else if (bFoundStart && row.equals(markerEnd))
				{
					results.setMax( (long)i );
					
					break;
				}
			}
		}
		
		return results;
	}
	
	public CRange<Long> findMarkerRange(String marker)
	{
		return findMarkerRange(marker, marker);
	}
	
	public CRange<Long> findMarkerRange(String markerStart, String markerEnd)
	{
		return CTextFile.findMarkerRange(markerStart, markerEnd, this.mLines);
	}

	public final void capitalize()
	{
		CTextFile.capitalize(this.mLines);
	}

	public final String set(int index, String value)
	{
		return this.mLines.set(index, value);
	}

	public final String replaceAt(int index, String value)
	{
		return set(index, value);
	}

	public final void save(String dir, String fName) throws Exception
	{
		save(dir, fName, false);
	}

	public final void save(String dir, String fName, boolean bAppend) throws Exception
	{
		CTextFile.write(dir, fName, this.mLines, bAppend);
	}

	public void save() throws Exception
	{
		this.save(this.getDirectory(), this.getFileName());
	}

	public final void save(String key, int iteration) throws Exception
	{
		this.encrypt(key, iteration);

		this.save();
	}

	public void load(String key, int iteration) throws Exception
	{
		this.open();

		this.decrypt(key, iteration);
	}

	public void open() throws Exception
	{
		this.open(this.getDirectory(), this.getFileName());
	}

	public void open(String dir, String fName) throws Exception
	{
		this.readFile(dir, fName);
	}
	
	public void read() throws Exception
	{
		this.open();
	}

	public void read(String dir, String fName) throws Exception
	{
		this.open(dir, fName);
	}

	public final void sort()
	{
		Collections.sort(this.mLines);
	}

	public final void sort(Comparator<String> c)
	{
		Collections.sort(this.mLines, c);
	}

	public final void findAndReplace(String pattern, String replacement)
	{
		this.findAndReplace(0, pattern, replacement);
	}

	public final void findAndReplace(int lineStart, String pattern, String replacement)
	{
		for (int i = lineStart; i < this.getLineCount(); i++)
		{
			this.replaceAt(i, CString.findAndReplace(this.getLineAt(i), pattern, replacement));
		}
	}
	
	public void performOp(int opMask)
	{
		if ((opMask & CTextFile.OP_REMOVE_BLANK_LINES) != 0)
		{
			this.removeBlankLines();
		}

		if ((opMask & CTextFile.OP_REMOVE_DUPLICATES) != 0)
		{
			this.removeDuplicateLines();
		}

		if ((opMask & CTextFile.OP_CAPITALIZE) != 0)
		{
			this.capitalize();
		}

		if ((opMask & CTextFile.OP_SORT) != 0)
		{
			this.sort();
		}
	}

	public final void addLine(String line)
	{
		mLines.add(line);
	}

	public final void addLine(String... lines)
	{
		if (lines != null)
		{
			for (String ln : lines)
			{
				addLine(ln);
			}
		}
	}
	
	public final int removeBlankLines()
	{
		int count = 0;

		CHashMap<String, String> map = new CHashMap<String, String>(this.getSize());
		
		for (int i = 0; i < this.getSize(); i++)
		{
			String key = this.getLineAt(i);
			
			if (key.trim().length() > 0)
			{
				map.add(key, key);
			}
			else
			{
				++count;
			}
		}
		
		this.mLines.clear();
		
		for(String ln : map.values())
		{
			this.mLines.add(ln);
		}

		return count;
	}

	public final int removeDuplicateLines()
	{
		int count = 0;

		CHashMap<String, String> map = new CHashMap<String, String>(this.getSize());
		
		for (int i = 0; i < this.getSize(); i++)
		{
			String key = this.getLineAt(i);
			
			if (!map.containsKey(key))
			{
				map.add(key, key);
			}
			else
			{
				++count;
			}
		}
		
		this.mLines.clear();
		
		for(String ln : map.values())
		{
			this.mLines.add(ln);
		}

		return count;
	}

	public final CArrayList<String> getLines()
	{
		return this.mLines;
	}

	public final CArrayList<String> getLines(int lnStart, int lnEnd)
	{
		int min = Math.min(lnStart, lnEnd);

		int max = Math.max(lnStart, lnEnd);

		CArrayList<String> lines = new CArrayList<String>(max - min + 1);

		for (int i = min; i <= max; i++)
		{
			lines.add(this.getLineAt(i));
		}

		return lines;
	}

	public final String getLineAt(int index)
	{
		return this.getLines().get(index);
	}

	public final int getLineCount()
	{
		return this.getSize();
	}

	public final int getSize()
	{
		return getLines().size();
	}

	public final void clear()
	{
		this.mLines.clear();
	}

	public final void importFile(String dir, String fName) throws Exception
	{
		CTextFile tf = new CTextFile(dir, fName);

		tf.open();

		this.importFile(tf);
	}

	public void importFile(CTextFile file)
	{
		for (String ln : file.mLines)
		{
			this.addLine(ln);
		}
	}

	protected void readFile(String dir, String fName) throws Exception
	{
		mLines = CTextFile.read(new File(dir, fName), this.getCapacity());
	}

	public final void reverse()
	{
		Collections.reverse(this.mLines);
	}

	public final int getCapacity()
	{
		return mCapacity;
	}

	public final void setCapacity(int capacity)
	{
		this.mCapacity = capacity;
	}

	public final void removeLine(String value)
	{
		this.mLines.remove(value);
	}

	public final void removeLineAt(int index)
	{
		this.mLines.remove(index);
	}

	public final void swapLines(int index1, int index2)
	{
		String tmp = this.mLines.get(index1);

		this.mLines.set(index1, this.mLines.get(index2));

		this.mLines.set(index2, tmp);
	}

	public final void randomize()
	{
		this.randomize(this.getSize());
	}

	public final void randomize(int count)
	{
		int max = this.getSize();

		for (int i = 0; i < count; i++)
		{
			this.swapLines(CApplication.getRandomInteger(max), CApplication.getRandomInteger(max));
		}
	}

	/**
	 * Returns a random line in the file
	 * 
	 * @return random line in text file
	 */
	public final String getRandomLine()
	{
		return this.mLines.getRandom();
	}

	@Override
	public void encrypt(String key, int iteration)
	{
		for (int i = 0; i < this.mLines.size(); i++)
		{
			try
			{
				this.mLines.set(i, CEncryption.encrypt(key, this.mLines.get(i), iteration));
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void decrypt(String key, int iteration)
	{
		for (int i = 0; i < this.mLines.size(); i++)
		{
			try
			{
				this.mLines.set(i, CEncryption.decrypt(key, this.mLines.get(i), iteration));
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(super.toString());

		sb.append(" ");

		if (this.mLines != null && this.mLines.size() > 0)
		{
			sb.append(this.mLines);
		} 
		else
		{
			sb.append("File is empty.");
		}

		return sb.toString();
	}

}
