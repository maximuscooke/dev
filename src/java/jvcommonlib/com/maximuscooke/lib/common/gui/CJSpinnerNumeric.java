package com.maximuscooke.lib.common.gui;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class CJSpinnerNumeric extends CJSpinnerEx
{
	private SpinnerNumberModel mModel = null;

	public CJSpinnerNumeric(int value, int min, int max, int stepSize)
	{
		this(new SpinnerNumberModel(value, min, max, stepSize));
	}

	public CJSpinnerNumeric(double value, double min, double max, double stepSize)
	{
		this(new SpinnerNumberModel(value, min, max, stepSize));
	}

	public CJSpinnerNumeric(SpinnerNumberModel model)
	{
		super((SpinnerModel) model);

		this.mModel = model;
	}

	/**
	 * Return SpinnerNumberModel object attached to this component
	 */
	public final SpinnerNumberModel getModelNumeric()
	{
		return this.mModel;
	}
}
