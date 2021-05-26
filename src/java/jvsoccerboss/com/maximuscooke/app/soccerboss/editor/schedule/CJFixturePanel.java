package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import com.maximuscooke.app.soccerboss.CClub;
import com.maximuscooke.app.soccerboss.CFixture;
import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CCheckBoxListItem;
import com.maximuscooke.lib.common.gui.CJCheckBoxList;
import com.maximuscooke.lib.common.gui.CJComboBox;
import com.maximuscooke.lib.common.gui.CJScrollPane;
import com.maximuscooke.lib.common.gui.CJTabPage;

@SuppressWarnings("serial")
public class CJFixturePanel extends CJTabPage
{
	private CSchedule mSchedule=null;
	
	private DefaultListModel<CFixturePanelItem> mFixtureModel = new DefaultListModel<CFixturePanelItem>();
	
	private CJCheckBoxList<CFixturePanelItem> mList = new CJCheckBoxList<CFixturePanelItem>(mFixtureModel);

	private DefaultComboBoxModel<CClub> mComboBoxModel = new DefaultComboBoxModel<CClub>();
	
	private CJComboBox<CClub> mClubCB = new CJComboBox<CClub>(mComboBoxModel);

	public CJFixturePanel()
	{
		super("Fixture List");
				
		this.setLayout( new BorderLayout() );
		
		this.add( this.mClubCB, BorderLayout.NORTH );
		
		this.add( new CJScrollPane(this.mList), BorderLayout.CENTER);
		
		this.mClubCB.addActionListener( e -> onSelectionChanged());
	}
	
	public void refresh(CSchedule sch)
	{
		this.mSchedule = sch;
		
		this.mComboBoxModel.removeAllElements();
		
		if (sch != null)
		{
			CArrayList<CClub> clubs = sch.getClubs(-1, -1);
			
			CCollections.sortByName(clubs);
			
			for(CClub c : clubs)
			{
				this.mComboBoxModel.addElement(c);
			}			
		}
		
		onSelectionChanged();
	}
	
	private void onSelectionChanged()
	{
		this.mFixtureModel.removeAllElements();
		
		if (this.mSchedule != null)
		{
			CClub selClub = (CClub)this.mComboBoxModel.getSelectedItem();
			
			if(selClub != null)
			{				
				CArrayList<CFixture> fixtures = this.mSchedule.getFixtureSchedule(selClub.getIntegerID());
				
				if(fixtures != null)
				{
					CCollections.sortByDateTimeStamp(fixtures);
					
					for(CFixture fxt : fixtures)
					{
						this.mFixtureModel.addElement( new CFixturePanelItem(selClub.getIntegerID(), fxt) );
					}				
				}
			}
		}
	}
}

class CFixturePanelItem extends CCheckBoxListItem
{
	private static final long serialVersionUID = 9159980259265787168L;
	
	private int mClubID=-1;
	private CFixture mFixture=null;

	public CFixturePanelItem(int clubID, CFixture fxt) 
   {
   	super(fxt);
   	
   	this.mFixture = fxt;
   	
   	this.mClubID = clubID;
   }
	
	public String toString()
	{
		return mFixture.toShortString(this.mClubID);
	}
}
