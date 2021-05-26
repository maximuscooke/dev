package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.IDateTime;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CCheckBoxListItem;
import com.maximuscooke.lib.common.gui.CJCheckBoxList;
import com.maximuscooke.lib.common.gui.CJScrollPane;

@SuppressWarnings("serial")
public class CClearRowDlg extends CDlgBase
{
	private DefaultListModel<CClearRowItem> mModel = new DefaultListModel<CClearRowItem>();
	
	private CJCheckBoxList<CClearRowItem> mList = new CJCheckBoxList<CClearRowItem>(mModel);
	
	private CSchedule mSchedule=null;

	public CClearRowDlg()
	{
		super("Clear Rows", "Clear selected Rows", "Select Rows in list to clear", 400, 400, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0047-48.png"), true);
			
		this.mSchedule = CScheduleEditor.getInstance().getSchedule();
		
		CArrayList<CGregorianCalendar> dates = this.mSchedule.getFixtureDates();
		
		dates.sort(CCollections.mDefaultSortDateTime);
		
		for(int i = 0; i < dates.getCount(); i++)
		{
			CGregorianCalendar dt = dates.getAt(i);
				
			this.mModel.addElement( new CClearRowItem( dt ) );				
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
		if (JOptionPane.showConfirmDialog(this, "Confirm clear schedule row, continue (Y/N) ?", "Clear Row", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)

		{			
			CArrayList<IDateTime> dates = new CArrayList<IDateTime>();
			
			for(int i = 0; i < this.mModel.getSize(); i++)
			{
				CClearRowItem rowItem = this.mModel.getElementAt(i);
				
				if (rowItem.isSelected())
				{								
					dates.add((CGregorianCalendar)rowItem.getUserObject());
				}
			}
			
			this.mSchedule.unSchedule(dates);
			
			this.dispose();
		}

	}
}

class CClearRowItem extends CCheckBoxListItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4402463304017676767L;

	public CClearRowItem(Object obj) 
   {
   	super(obj);
   }
	
	@Override
	public String toString()
	{
		return CGregorianCalendar.toString((CGregorianCalendar)this.getUserObject(), "EEE dd MMM yyyy");
	}
}
