package com.maximuscooke.lib.common.gui;

import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;

@SuppressWarnings("serial")
public class CJSpinnerEx extends CJSpinner
{
	public CJSpinnerEx()
	{
	}

	public CJSpinnerEx(SpinnerModel model)
	{
		super(model);
	}

	/**
	 * Return Editor object attached to spinner
	 * 
	 * @return DefaultEditor object
	 */
	public JSpinner.DefaultEditor getDefaultEditor()
	{
		return (JSpinner.DefaultEditor) this.getEditor();
	}

	/**
	 * Return TextField attached to spinner
	 * 
	 * @return JTextField
	 */
	public JTextField getDefaultTextField()
	{
		return this.getDefaultEditor().getTextField();
	}

	/**
	 * Set horizontal alignment
	 * 
	 * @param alignment JTextField.LEFT, JTextField.RIGHT, JTextField.CENTER,
	 */
	public void setHorizontalAlignment(int alignment)
	{
		this.getDefaultTextField().setHorizontalAlignment(alignment);
	}

	public void setEditable(boolean bEditable)
	{
		this.getDefaultTextField().setEditable(bEditable);
	}

	public final void setPreferredSize(int width, int height)
	{
		this.setPreferredSize(new Dimension(width, height));
	}
}
