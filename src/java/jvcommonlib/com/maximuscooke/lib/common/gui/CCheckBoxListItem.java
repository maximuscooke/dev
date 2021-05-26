package com.maximuscooke.lib.common.gui;

import com.maximuscooke.lib.common.ISerializable;

@SuppressWarnings("serial")
public class CCheckBoxListItem implements  ISerializable
{
   private Object		mUserObject= null;
   private boolean 	mIsSelected = false;
 
   public CCheckBoxListItem(Object obj) 
   {
   	this.setUserObject(obj);
   }
 
   public String toString() 
   {
   	return this.mUserObject.toString();
   }

   public boolean isSelected() 
   {
      return this.mIsSelected;
   }
 
   public void setSelected(boolean bSelected) 
   {
      this.mIsSelected = bSelected;
   }

	public final Object getUserObject()
	{
		return this.mUserObject;
	}

	public final void setUserObject(Object obj)
	{
		this.mUserObject = obj;
	}

}
