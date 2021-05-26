package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CCheckBoxListItem;
import com.maximuscooke.lib.common.gui.CJCheckBoxList;
import com.maximuscooke.lib.common.gui.CJScrollPane;

@SuppressWarnings("serial")
public class CAutoNameDlg extends CDlgBase
{
	private DefaultListModel<CAutoNameRowItem> mModel = new DefaultListModel<CAutoNameRowItem>();
	
	private CJCheckBoxList<CAutoNameRowItem> mList = new CJCheckBoxList<CAutoNameRowItem>(mModel);

	public CAutoNameDlg()
	{
		super("Auto Name", "Auto Name", "Automatically name each club.", 500, 400, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0165-48.png"), true);
		
		this.getCenterPanel().setLayout(new FlowLayout(FlowLayout.CENTER) );
		
		CJScrollPane sp = new CJScrollPane(this.mList);
		
		sp.setPreferredSize( new Dimension(400,200));
		
		this.getCenterPanel().add( sp );
		
		this.setResizable(true);
				
		this.fillList();
		
		this.getOkBtn().setEnabled(true);
	}
	
	private void fillList()
	{
		CArrayList<String> clubs = new CArrayList<String>(new String[]  { "Arsenal",
																								"Bournemouth", 
																								"Brighton", 
																								"Burnley",
																								"Cardiff City",
																								"Chelsea",
																								"Crystal Palace", 
																								"Everton", 
																								"Fulham",
																								"Huddersfield",
																								"Leicester",
																								"Liverpool",
																								"Man City",
																								"Man United",
																								"Newcastle United",
																								"Southampton",
																								"Tottenham",
																								"Watford",
																								"West Ham United",
																								"Wolves",
																								"Aston Villa",
																								"Birmingham City",
																								"Blackburn Rovers",
																								"Bolton Wanderers",
																								"Brentford", 
																								"Bristol City",
																								"Derby County",
																								"Ipswich Town", 
																								"Leeds United", 
																								"Hull City",
																								"Middlesbrough",
																								"Millwall",
																								"Norwich City",
																								"Nottingham Forest", 
																								"Preston North End",
																								"Queens Park Rangers",
																								"Reading",
																								"Rotherham", 
																								"Sheffield United",
																								"Sheffield Wednesday",
																								"Stoke City",
																								"Swansea City",
																								"West Bromwich Albion",
																								"Wigan Athletic" });
		
		Collections.sort(clubs);

		for(String s : clubs)
		{
			this.mModel.addElement( new CAutoNameRowItem( s )  );			
		}
	}
	
	@Override
	protected void onOkClicked()
	{
		CSchedule sch = CScheduleEditor.getInstance().getSchedule();
		
		if (sch != null)
		{
			CArrayList<String> clubNames = new CArrayList<String>();
			
			for(int i = 0; i < this.mModel.getSize(); i++)
			{
				CAutoNameRowItem row = this.mModel.getElementAt(i);
				
				if (row.isSelected())
				{
					clubNames.add(row.getName());
				}
			}

			if (clubNames.getSize() < sch.getMaxClubs())
			{
				JOptionPane.showMessageDialog(this, String.format("%d items selected, must select minimum of %d items", clubNames.getSize(), sch.getMaxClubs()), "Auto Name", JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				sch.autoName(clubNames);
				
				this.dispose();				
			}
		}
	}
}

class CAutoNameRowItem extends CCheckBoxListItem
{
	private static final long serialVersionUID = -4402463304017676767L;
	
	private String mName = null;
	
	public CAutoNameRowItem(String name) 
   {
   	super(name);
   	
   	this.mName = name;
    }	
	
	public String getName()
	{
		return this.mName;
	}
	
	@Override
	public String toString()
	{
		return this.getName();
	}
}
