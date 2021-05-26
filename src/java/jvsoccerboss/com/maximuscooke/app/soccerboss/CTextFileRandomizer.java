package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.CTextFile;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CHashMap;

public class CTextFileRandomizer
{
	private CArrayList<String> mList1 = null;
	
	private CArrayList<String> mList2 = null;
	
	private int mIndex1=0;
	
	private String mSeparator = " ";
	
	private CHashMap<String, String> mDuplicateMap = null;
	
	public CTextFileRandomizer(String dir1, String file1, String dir2, String file2) throws Exception
	{
		this(dir1, file1, dir2, file2, 512, true);
	}

	public CTextFileRandomizer(String dir1, String file1, String dir2, String file2, int defaultSize, boolean bSuppressDuplicates) throws Exception
	{
		this.mList1 = CTextFile.read(dir1, file1, defaultSize);
		
		this.mList2 = CTextFile.read(dir2, file2, defaultSize);
		
		this.mList1.shuffle();
		
		this.mList2.shuffle();
		
		if (bSuppressDuplicates)
		{
			mDuplicateMap = new CHashMap<String, String>();
		}
	}
	
	public CTextFileRandomizerItem getRandomItem()
	{
		return getRandomItem(0);
	}
	
	public CTextFileRandomizerItem getRandomItem(int options)
	{
		if (this.mIndex1 >= this.mList1.getCount())
		{
			this.mIndex1 = 0;
		}
				
		CTextFileRandomizerItem item = null;
		
		if (this.mDuplicateMap != null)
		{
			int index = this.mIndex1;	
			
			String str = null;
			
			String rnd1 = null;
			
			String rnd2 = null;
			
			do
			{
				rnd1 = this.mList1.getAt(index++);
				
				rnd2 = this.mList2.getRandom();
				
				str = String.format("%s%s%s", rnd1, this.getSeparator(), rnd2);

			}
			while(this.mDuplicateMap.containsKey(str));
			
			this.mDuplicateMap.add(str, str);
			
			item = new CTextFileRandomizerItem(rnd1, rnd2);
		}
		else
		{
			item = new CTextFileRandomizerItem(this.mList1.getAt(mIndex1), this.mList2.getRandom());
		}
		
		this.mIndex1++;
				
		return item;
	}
	
	public String getRandomName()
	{
		CTextFileRandomizerItem rndItem = getRandomItem();
		
		return String.format("%s%s%s", rndItem.getName1(), this.getSeparator(), rndItem.getName2());
	}

	public final void setIndex1(int index)
	{
		this.mIndex1 = index;
	}

	public final String getSeparator()
	{
		return this.mSeparator;
	}

	public final void setSeparator(String separator)
	{
		this.mSeparator = separator;
	}
}

class CTextFileRandomizerItem
{
	private String mName1=null;
	private String mName2=null;
	
	public CTextFileRandomizerItem(String name1, String name2)
	{
		this.mName1=name1;
		
		this.mName2=name2;
	}

	public final String getName1()
	{
		return this.mName1;
	}

	public final String getName2()
	{
		return this.mName2;
	}
}