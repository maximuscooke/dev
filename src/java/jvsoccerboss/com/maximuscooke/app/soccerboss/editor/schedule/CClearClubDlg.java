package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.exceptions.CKeyNotFoundException;
import com.maximuscooke.lib.common.gui.CCheckBoxListItem;
import com.maximuscooke.lib.common.gui.CJCheckBoxList;
import com.maximuscooke.lib.common.gui.CJScrollPane;

@SuppressWarnings("serial")
public class CClearClubDlg extends CDlgBase
{
	private DefaultListModel<CClearClubRowItem> mModel = new DefaultListModel<CClearClubRowItem>();
	
	private CJCheckBoxList<CClearClubRowItem> mList = new CJCheckBoxList<CClearClubRowItem>(mModel);
	
	private CSchedule mSchedule=null;

	public CClearClubDlg()
	{
		super("Clear Club", "Clear Club Fixtures", "Clear all fixtures for specified clubs", 400, 400, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0047-48.png"), true);
		
		this.mSchedule = CScheduleEditor.getInstance().getSchedule();
		
		CArrayList<String> names = this.mSchedule.getClubNames();
		
		Collections.sort(names);
		
		for(int i = 0; i < names.getCount(); i++)
		{
			this.mModel.addElement( new CClearClubRowItem( names.getAt(i) ) );				
		}
		
		this.getCenterPanel().setLayout( new BorderLayout() );

		CJScrollPane sp = new CJScrollPane( mList );
				
		this.getCenterPanel().add( sp );
				
		this.mList.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event)
			{
				onContentChanged();
			}
		});
	}
	
	private void onContentChanged()
	{
		boolean bEnable = false;
		
		for(int i = 0; i < this.mModel.getSize(); i++)
		{			
			if (this.mModel.getElementAt(i).isSelected())
			{
				bEnable = true;
				
				break;
			}
		}
		
		this.getOkBtn().setEnabled(bEnable);
	}

	@Override
	protected void onOkClicked()
	{
		if (JOptionPane.showConfirmDialog(this, "Confirm clear schedule for club(s), continue (Y/N) ?", "Clear Club", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)
		{
			CArrayList<Integer> ids = new CArrayList<Integer>();
			
			for(int i = 0; i < this.mModel.getSize(); i++)
			{
				CClearClubRowItem rowItem = this.mModel.getElementAt(i);
				
				if (rowItem.isSelected())
				{			
					try
					{
						int id = this.mSchedule.getIDFromName((String)rowItem.getUserObject());
						
						if (id >= 0)
						{
							ids.add(id);
						}
					} 
					catch (CKeyNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			}
			this.mSchedule.unScheduleClubs(ids);
		}
		dispose();
	}

}

class CClearClubRowItem extends CCheckBoxListItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4402463304017676767L;

	public CClearClubRowItem(Object obj) 
   {
   	super(obj);
   }
	
	@Override
	public String toString()
	{
		return (String)this.getUserObject();
	}
}
