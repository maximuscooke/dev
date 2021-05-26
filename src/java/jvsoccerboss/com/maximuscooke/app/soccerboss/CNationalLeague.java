package com.maximuscooke.app.soccerboss;

public class CNationalLeague extends CLeague
{
	private static final long serialVersionUID = 8423777144492597824L;

	public CNationalLeague()
	{
	}

	public CNationalLeague(String name, int countryId, int id, int maxClubs, int precedenceOrder)
	{
		super(name, countryId, id, maxClubs, precedenceOrder);
				
		this.setImageName16("ac0047-16.png");

	}

	@Override
	public final int getType()
	{
		return CCompetition.TYPE_NATIONAL_LEAGUE;
	}
}
