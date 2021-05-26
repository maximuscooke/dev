package com.maximuscooke.app.soccerboss.editor.db;

import com.maximuscooke.app.soccerboss.CClub;
import com.maximuscooke.app.soccerboss.CCompetition;
import com.maximuscooke.app.soccerboss.CCountry;
import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CGameObject;
import com.maximuscooke.app.soccerboss.CRegion;
import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CDefaultMutableTreeNode;
import com.maximuscooke.lib.common.gui.CJTreeEx;

@SuppressWarnings("serial")
public class CDbExplorer extends CJTreeEx
{	
	public CDbExplorer(Object root)
	{
		super(root);
	}
	
	public final void refresh(CDatabase db, int options)
	{		
		this.removeAllTreeNodes();

		if (db != null)
		{
			if ((options & CEditor.BUILD_DB_MASK) != 0)
			{
				build(db);
			}
			
			if ((options & CEditor.REFRESH_DB_MASK) != 0)
			{
				this.reload();
			}
		}
	}

	private void build(CDatabase db)
	{		
		this.init(db);
		
		CArrayList<CRegion> list = new CArrayList<CRegion>(db.getRegionMap().values());
		
		CCollections.sortByName(list);
		
		list.forEach( r -> buildRegion(db, r)  );
	}
	
	private void buildRegion(CDatabase db, CRegion rg)
	{
		buildCountries(db, rg, this.addRegion(rg, 0));
	}
	
	private void buildCountries(CDatabase db, CRegion rg, CDefaultMutableTreeNode parent)
	{
		CArrayList<CCountry> list = new CArrayList<CCountry>(db.getCountryMap().values());
		
		CCollections.sortByName(list);
		
		for(CCountry c : list) 
		{
			if (rg.getIntegerID() == c.getRegionID())
			{
				CDefaultMutableTreeNode tn = this.addTreeNode(parent, c);
				
				tn.setType(CDatabase.COUNTRY_TYPE);
				
				this.buildClubs(db, c, tn);
				
				this.buildCompetitions(db,  c, tn);
				
			}
		}
	}
	
	private void buildCompetitions(CDatabase db, CCountry country, CDefaultMutableTreeNode parent)
	{
		this.buildCompetitions(db, country, parent, CCompetition.TYPE_NATIONAL_LEAGUE,  CDatabase.LEAGUE_TYPE, "Leagues");
		
		this.buildCompetitions(db, country, parent, CCompetition.TYPE_NATIONAL_CUP,  CDatabase.CUP_TYPE, "Cups");
				
		this.reload();
	}
	
	private void buildCompetitions(CDatabase db, CCountry country, CDefaultMutableTreeNode parent, int mask, int type, String title)
	{
		CArrayList<CCompetition> comps  = db.getCompetitionsFor(country, mask);
		
		if (comps.getSize() > 0)
		{			
			CDefaultMutableTreeNode compRoot = new CDefaultMutableTreeNode(title);
			
			parent.add(compRoot);
			
//			CCollections.sortByPrecedenceOrder(comps, CCollections.SORT_ASCENDING);
						
			for(CCompetition c : comps)
			{
				CDefaultMutableTreeNode tn = this.addTreeNode(compRoot, c);
				
				tn.setType(type);
				
				for(Integer id : c.getClubIds())
				{
					CClub club = db.getClub(id);
					
					CDefaultMutableTreeNode clubNode = this.addTreeNode(tn, club, CJTreeEx.OPT_SORT);
					
					clubNode.setType(CDatabase.CLUB_TYPE);				
				}
			}
		}
	}
		
	private void buildClubs(CDatabase db, CCountry country, CDefaultMutableTreeNode parent)
	{
		CArrayList<CClub> clubs  = db.getClubsFor(country);
				
		if (clubs.getSize() > 0)
		{
			CDefaultMutableTreeNode clubRoot = new CDefaultMutableTreeNode("Clubs");
			
			parent.add(clubRoot);

			CCollections.sortByName(clubs);
			
			for(CClub club : clubs)
			{
				CDefaultMutableTreeNode tn = this.addTreeNode(clubRoot, club);
				
				tn.setType(CDatabase.CLUB_TYPE);				
			}
		}
	}
	
	public final CDefaultMutableTreeNode addRegion(CRegion rg, int option)
	{
		CDefaultMutableTreeNode tn = this.addTreeNode(rg, option);
		
		tn.setType(CDatabase.REGION_TYPE);

		return tn;
	}

	public final CDefaultMutableTreeNode addClub(CClub club, int options)
	{
		CDefaultMutableTreeNode tn = this.addTreeNode(club, options);
		
		tn.setType(CDatabase.CLUB_TYPE);
		
		return tn;
	}
	
	public final void deleteRegion(CRegion rg, int options)
	{
		this.deleteTreeNode(rg, options);
	}
	
	public final CDefaultMutableTreeNode addCountry(CCountry c, int option)
	{
		CDefaultMutableTreeNode parent = this.findTreeNode( tn -> { if (tn.getType() == CDatabase.REGION_TYPE && ((CRegion)tn.getUserObject()).getIntegerID() == c.getRegionID() ) { return true; } return false; } );
		
		this.addTreeNode(parent, c, option);
		
		return parent;
	}
	
	public final void deleteCountry(CCountry country, int options)
	{
		this.deleteTreeNode(country, options);
	}
	
	public final void deleteTreeNode(CGameObject gmobj, int options)
	{
		this.removeTreeNode(tn -> { if (tn.getUserObject() == gmobj) { return true; } return false; }, options );
	}


//	public final void setDB(CDatabase db)
//	{
//		this.build(db);
//	}
}
