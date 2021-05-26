package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.app.soccerboss.CScheduleValidationException;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CJComboBox;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerDate;

@SuppressWarnings("serial")
public class CSchReverseRowDlg extends CDlgBase
{
	private CJSpinnerDate mDate;
	
	private DefaultComboBoxModel<CMoveRowItem> mComboBoxModel = new DefaultComboBoxModel<CMoveRowItem>();
	
	private CJComboBox<CMoveRowItem> mDateCB = new CJComboBox<CMoveRowItem>(mComboBoxModel);
	
	private CSchedule mSchedule=null;

	public CSchReverseRowDlg()
	{
		super("Schedule Reverse Row", "Schedule Reverse Row", "Schedule the reverse fixture row to a new date", 450, 250, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0047-48.png"), true);
		
		this.mSchedule = CScheduleEditor.getInstance().getSchedule();
		
		@SuppressWarnings("deprecation")
		Date startDate = new Date(this.mSchedule.getStartDate().getYear() - 1900, this.mSchedule.getStartDate().getMonth(), this.mSchedule.getStartDate().getDayOfMonth());
		
		@SuppressWarnings("deprecation")
		Date endDate = new Date(this.mSchedule.getEndDate().getYear() - 1900, this.mSchedule.getEndDate().getMonth(), this.mSchedule.getEndDate().getDayOfMonth());
		
		this.mDate = new CJSpinnerDate(startDate, startDate, endDate, CGregorianCalendar.DAY_OF_WEEK, "EEE dd:MMM:yyyy");
		
		this.mDate.setHorizontalAlignment( CJLabel.CENTER );
		
		this.mDate.setEditable(false);
		
		this.mDateCB.setPreferredSize( new Dimension(200, 28) );
		
		this.getCenterPanel().setLayout( new FlowLayout(FlowLayout.LEFT) );
		
		CJLabel selRowLbl = new CJLabel("Select Row : ");
		
		selRowLbl.setPreferredSize(150,  28);
		
		selRowLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.getCenterPanel().add( selRowLbl  );
		
		this.getCenterPanel().add( this.mDateCB);
		
		CJLabel selDestLbl = new CJLabel("Select Destination : ");
		
		selDestLbl.setPreferredSize(150,  28);
		
		selDestLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.getCenterPanel().add( selDestLbl );
		
		this.getCenterPanel().add( this.mDate );
		
		CArrayList<CGregorianCalendar> dates = this.mSchedule.getFixtureDates();
		
		dates.sort(CCollections.mDefaultSortDateTime);
		
		for(CGregorianCalendar dt : dates)
		{
			this.mComboBoxModel.addElement( new CMoveRowItem(dt) );
		}
		
		this.mDate.addChangeListener( e -> onContentChanged() );
		
		this.mDateCB.addActionListener( e -> onContentChanged() );

	}
	
	private void onContentChanged()
	{
		this.getOkBtn().setEnabled(true);
	}

	@Override
	protected void onOkClicked()
	{
		CMoveRowItem selItem = (CMoveRowItem)this.mDateCB.getSelectedItem();

		try
		{
			this.mSchedule.scheduleReverse(selItem.getDate(), this.mDate.getDateValue());
		} 
		catch (CScheduleValidationException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "Schedule Reverse Row Error", JOptionPane.ERROR_MESSAGE);
		}
				
		dispose();

	}

}
