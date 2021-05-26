package com.maximuscooke.lib.common.gui;

import com.maximuscooke.lib.common.collections.CHashMap;

@SuppressWarnings("serial")
public abstract class CJWizardPage extends CJPanel
{
	private String mDesc = null;
	private String mTitle = null;

	private CJWizardDialog mParent = null;

	public CJWizardPage(String title, String desc)
	{
		super(true);

		this.mTitle = title;

		this.mDesc = desc;
	}

	// Abstract methods
	public abstract boolean doButtonValidation(CJWizardDialog d, int btn);

	protected abstract void onNextOrFinish(CJWizardDialog dlg, CHashMap<String, Object> collection);

	protected void onPrevious(CJWizardDialog dlg, CHashMap<String, Object> collection)
	{
		// Override in derived class
	}

	public void valuesChanged()
	{
		this.getWizardParent().validateWizardPage(this);
	}

	public final String getTitle()
	{
		return mTitle;
	}

	public final String getDescription()
	{
		return mDesc;
	}

	public final CJWizardDialog getWizardParent()
	{
		return mParent;
	}

	public final void setWizardParent(CJWizardDialog parent)
	{
		this.mParent = parent;
	}
}
