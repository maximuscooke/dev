package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.JOptionPane;

import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CDate;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerDate;

@SuppressWarnings("serial")
public class CExtendDlg extends CDlgBase
{
	private CJSpinnerDate mDate;
	
	private CGregorianCalendar mStartDate = null;

	private CSchedule mSchedule=null;
	
	public CExtendDlg()
	{
		super("Extend Schedule", "Extend Schedule", "Extend schedule end date.", 400, 200, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR+ "nd0047-48.png"), true);
		
		this.mSchedule = CScheduleEditor.getInstance().getSchedule();
		
		this.mStartDate = new CGregorianCalendar( this.mSchedule.getEndDate() );
				
		@SuppressWarnings("deprecation")
		Date startDate = new Date(this.mStartDate.getYear() - CDate.DATE_OFFSET, this.mStartDate.getMonth(), this.mStartDate.getDayOfMonth());
				
		@SuppressWarnings("deprecation")
		Date endDate = new Date(this.mStartDate.getYear() - CDate.DATE_OFFSET + 1, this.mStartDate.getMonth(), this.mStartDate.getDayOfMonth());
		
		this.mDate = new CJSpinnerDate(startDate, startDate, endDate, CGregorianCalendar.DAY_OF_WEEK, "EEE dd:MMM:yyyy");
		
		CJLabel lbl = new CJLabel("End Date : ");
				
		lbl.setBounds(32, 60, 120, 28);
				
		this.mDate.setHorizontalAlignment(CJLabel.CENTER);
		
		this.getCenterPanel().setLayout( new FlowLayout(FlowLayout.CENTER) );
		
		this.getCenterPanel().add(lbl);
		
		this.getCenterPanel().add(this.mDate);
		
		this.mDate.addChangeListener( e -> onContentChanged() );
	}
	
	private void onContentChanged()
	{
		CGregorianCalendar dt = this.mDate.getDateValue();
		
		this.getOkBtn().setEnabled(dt.getYear() != this.mStartDate.getYear() ||
											dt.getMonth() != this.mStartDate.getMonth() || 
											dt.getDayOfMonth() != this.mStartDate.getDayOfMonth());
	}

	@Override
	protected void onOkClicked()
	{
		if (JOptionPane.showConfirmDialog(this, "Confirm extend schedule, continue (Y/N) ?", "Unschedule Fixture", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)

		{
			this.mSchedule.setEndDate(this.mDate.getDateValue());
			
			CScheduleEditor.getInstance().extendSchedule(this.mDate.getDateValue());
			
			this.dispose();			
		}
	}
}
