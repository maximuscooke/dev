package com.maximuscooke.app.soccerboss;

public class CNationalCup extends CCup
{
	private static final long serialVersionUID = -3910754382767856627L;

	public CNationalCup()
	{
	}

	public CNationalCup(String name, int countryId, int id, int maxClubs)
	{
		super(name, countryId, id, maxClubs);
		
		this.setImageName16("nd0058-16.png");
	}

	@Override
	public final int getType()
	{
		return CCompetition.TYPE_NATIONAL_CUP;
	}

}
