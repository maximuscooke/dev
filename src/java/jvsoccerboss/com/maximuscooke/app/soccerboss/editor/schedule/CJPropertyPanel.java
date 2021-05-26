package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Collections;

import javax.swing.DefaultListModel;

import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.IDateTime;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJList;
import com.maximuscooke.lib.common.gui.CJScrollPane;
import com.maximuscooke.lib.common.gui.CJTabPage;

@SuppressWarnings("serial")
public class CJPropertyPanel extends CJTabPage
{
	private CSchedule mSchedule=null;
	
	private DefaultListModel<String> mModel = new DefaultListModel<String>();
	
	private CJList<String> mList = new CJList<String>(mModel);
	
	private CJLabel mLabel = new CJLabel();
	
	public CJPropertyPanel()
	{
		super("Play List");
		
		this.setLayout( new BorderLayout() );
		
		this.mLabel = new CJLabel("N/A");
		
		this.mLabel.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mLabel.setFont(new Font("helvetica", Font.PLAIN, 14));
		
		this.add( this.mLabel, BorderLayout.NORTH );
		
		this.add( new CJScrollPane(this.mList), BorderLayout.CENTER);
	}
	
	public void refresh(CSchedule sch, IDateTime dt)
	{
		this.mSchedule = sch;
		
		this.mModel.removeAllElements();
		
		if (this.mSchedule != null && dt != null)
		{
			CArrayList<String> clublist = this.mSchedule.getClubsNotPlayingOn(dt);
			
			CGregorianCalendar cgdt = (CGregorianCalendar)dt;
			
			this.mLabel.setText(String.format("Clubs Not Playing on %s", CGregorianCalendar.toString(cgdt, "EEE, d MMM yyyy")));
			
			Collections.sort(clublist);
			
			for(String s : clublist)
			{
				this.mModel.addElement(s);
			}
		}
	}
}
