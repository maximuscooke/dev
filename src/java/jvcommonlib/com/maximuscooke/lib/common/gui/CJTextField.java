package com.maximuscooke.lib.common.gui;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class CJTextField extends JTextField
{
	private Object mUserObject = null;

	public CJTextField()
	{
	}

	public CJTextField(String text)
	{
		super(text);
	}

	public CJTextField(int columns)
	{
		super(columns);
	}

	public CJTextField(String text, int columns)
	{
		super(text, columns);
	}

	public CJTextField(Document doc, String text, int columns)
	{
		super(doc, text, columns);
	}
	
	public static CJTextField createTextField(int x, int y, int width, int height)
	{
		return createTextField(x, y, width, height, CJTextField.CENTER);
	}
	
	public static CJTextField createTextField(int x, int y, int width, int height, int horizontalAlignment)
	{
		return createTextField(x, y, width, height, CJTextField.CENTER, true);
	}
	
	public static CJTextField createTextField(int x, int y, int width, int height, int horizontalAlignment, boolean bEnabled)
	{
		CJTextField tf = new CJTextField();
		
		tf.setBounds(x, y, width, height);
		
		tf.setHorizontalAlignment(horizontalAlignment);
		
		tf.setEditable(bEnabled);
		
		return tf;
	}
	
	public final boolean isBlank()
	{
		return (this.getText() != null && this.getText().length() > 0);
	}

	public final void setPreferredSize(int width, int height)
	{
		this.setPreferredSize(new Dimension(width, height));
	}

	public final Object getUserObject()
	{
		return mUserObject;
	}

	public final void setUserObject(Object obj)
	{

		this.setUserObject(obj, true);
	}

	public final void setUserObject(Object obj, boolean bSetText)
	{

		this.mUserObject = obj;

		if (bSetText && obj != null)
		{
			this.setText(obj.toString());
		}
	}
}
