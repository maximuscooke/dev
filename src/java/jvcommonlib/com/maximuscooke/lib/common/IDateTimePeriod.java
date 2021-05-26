package com.maximuscooke.lib.common;

public interface IDateTimePeriod
{
	/**
	 * Returns period start date and time
	 * 
	 * @return start date and time
	 */
	IDateTime getPeriodStart();

	/**
	 * Set period start date and time
	 * 
	 * @param dt period start date
	 */
	void setPeriodStart(IDateTime dt);

	/**
	 * Returns period end date and time
	 * 
	 * @return end date and time
	 */
	IDateTime getPeriodEnd();

	/**
	 * Set period end date and time
	 * 
	 * @param dt period end date
	 */
	void setPeriodEnd(IDateTime dt);

}
