package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.JColorChooser;

import com.maximuscooke.app.soccerboss.CCompetition;
import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CDate;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.gui.CJButton;
import com.maximuscooke.lib.common.gui.CJColorChooser;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerDate;
import com.maximuscooke.lib.common.gui.CJSpinnerNumeric;
import com.maximuscooke.lib.common.gui.CJTextField;

@SuppressWarnings("serial")
public class CPropertiesDlg extends CDlgBase
{
	private CJSpinnerNumeric mMaxClubs = new CJSpinnerNumeric(CCompetition.DEFAULT_LGE_NUM_TEAMS, CCompetition.DEFAULT_LGE_MIN_TEAMS, CCompetition.DEFAULT_LGE_MAX_TEAMS, 2);
	private CJSpinnerNumeric mGap = new CJSpinnerNumeric(CCompetition.DEFAULT_FIXTURE_GAP_MIN+1, CCompetition.DEFAULT_FIXTURE_GAP_MIN, CCompetition.DEFAULT_FIXTURE_GAP_MAX, 1);
	private CJSpinnerNumeric mMaxConsec = new CJSpinnerNumeric(CCompetition.DEFAULT_HOME_AWAY_CONSECUTIVE_MIN, CCompetition.DEFAULT_HOME_AWAY_CONSECUTIVE_MIN, CCompetition.DEFAULT_HOME_AWAY_CONSECUTIVE_MAX, 1);
	private CJSpinnerNumeric mReverseGap = new CJSpinnerNumeric(90, 2, 365, 1);
	
	private CJButton mScheduleTextColor = new CJButton("Scheduled");
	private CJButton mUnScheduleTextColor = new CJButton("Un-Scheduled");
	private CJButton mErrorTextColor = new CJButton("Errors");
	private CJButton mSelectedTextColor = new CJButton("Selected Row");
	
	private CJSpinnerDate mStartDate = null;
	private CJSpinnerDate mEndDate = null;

	private CSchedule mSchedule=null;

	public CPropertiesDlg()
	{
		super("Properties", "Schedule Properties", "Modify schedule properties.", 500, 400, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png"), true);
		
		this.mSchedule = CScheduleEditor.getInstance().getSchedule();
		
		CGregorianCalendar sd = new CGregorianCalendar( this.mSchedule.getStartDate() );
		
		CGregorianCalendar ed = new CGregorianCalendar( this.mSchedule.getEndDate() );
		
		@SuppressWarnings("deprecation")
		Date startDate = new Date(sd.getYear() - CDate.DATE_OFFSET, sd.getMonth(), sd.getDayOfMonth());
				
		@SuppressWarnings("deprecation")
		Date endDate = new Date(ed.getYear() - CDate.DATE_OFFSET, ed.getMonth(), ed.getDayOfMonth());
		
		this.mStartDate = new CJSpinnerDate(startDate, startDate, startDate, CGregorianCalendar.DAY_OF_WEEK, "EEE dd:MMM:yyyy");
		
		this.mEndDate = new CJSpinnerDate(endDate, endDate, endDate, CGregorianCalendar.DAY_OF_WEEK, "EEE dd:MMM:yyyy");
		
		this.mMaxClubs.setValue((Integer)this.mSchedule.getMaxClubs());
		
		this.mGap.setValue((Integer)this.mSchedule.getFixtureGap());
		
		this.mReverseGap.setValue((Integer)this.mSchedule.getReverseFixtureGap());
		
		this.mMaxConsec.setValue((Integer)this.mSchedule.getMaxConsecutive());
		
		int rowHeight = 28;
		
		JColorChooser dlg  = new JColorChooser();
		
		this.mStartDate.setPreferredSize(300, rowHeight);
		
		this.mStartDate.setEditable(false);
		
		this.mStartDate.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mEndDate.setPreferredSize(300, rowHeight);
		
		this.mEndDate.setEditable(false);
		
		this.mEndDate.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mMaxConsec.setPreferredSize(80, rowHeight);
		
		this.mMaxConsec.setHorizontalAlignment(CJTextField.CENTER);
		
		this.mMaxConsec.setEditable(false);
		
		this.mReverseGap.setPreferredSize(80, rowHeight);
		
		this.mReverseGap.setHorizontalAlignment(CJTextField.CENTER);
		
		this.mReverseGap.setEditable(false);
		
		this.mMaxClubs.setHorizontalAlignment(CJTextField.CENTER);
		
		this.mMaxClubs.setEditable(false);
		
		this.mMaxClubs.setPreferredSize(80, rowHeight);
		
		this.mGap.setHorizontalAlignment(CJTextField.CENTER);
		
		this.mGap.setEditable(false);
		
		this.mGap.setPreferredSize(80, rowHeight);

		this.getCenterPanel().setLayout( new FlowLayout(FlowLayout.LEFT) );
				
		Dimension lblsz = new Dimension(140, rowHeight);
								
		this.getCenterPanel().add( createLabel("Max Clubs :",  lblsz) );
		
		this.getCenterPanel().add(this.mMaxClubs);
				
		this.getCenterPanel().add( createLabel("Fixture Gap :",  lblsz) );
		
		this.getCenterPanel().add(this.mGap);
				
		this.getCenterPanel().add( createLabel("Max Consecutive :",  lblsz) );
		
		this.getCenterPanel().add(this.mMaxConsec);
				
		this.getCenterPanel().add( createLabel("Reverse Gap :",  lblsz) );
		
		this.getCenterPanel().add(this.mReverseGap);
				
		this.getCenterPanel().add( createLabel("Start Date :",  lblsz) );

		this.getCenterPanel().add(this.mStartDate);
				
		this.getCenterPanel().add( createLabel("End Date :",  lblsz) );
		
		this.getCenterPanel().add(this.mEndDate);
		
		this.getCenterPanel().add(this.mScheduleTextColor);
		
		this.getCenterPanel().add(this.mUnScheduleTextColor);
		
		this.getCenterPanel().add(this.mErrorTextColor);
		
		this.getCenterPanel().add(this.mSelectedTextColor);
		
		this.mScheduleTextColor.addActionListener( e-> onScheduledTextColor() );
		
		this.mUnScheduleTextColor.addActionListener( e -> onUnScheduledTextColor() );
		
		this.mErrorTextColor.addActionListener( e -> onErrorTextColor() );
		
		this.mSelectedTextColor.addActionListener( e -> onSelectedRowTextColor() );
		
		this.getOkBtn().setEnabled(false);
		
		this.setOkButtonText("Apply");
		
		this.mStartDate.setEnabled(false);
		
		this.mEndDate.setEnabled(false);
		
		this.mMaxClubs.setEnabled(false);
		
		this.mMaxConsec.addChangeListener( e -> onContentChanged() );
		
		this.mReverseGap.addChangeListener(e -> onContentChanged());
		
		this.mGap.addChangeListener(e -> onContentChanged());
	}
	
	private void onSelectedRowTextColor()
	{
		Color clr = null;
		
		if ( (clr = CJColorChooser.showDialog(this, "Select Color", CScheduleEditor.SELECTED_ROW_COLOR)) != null)
		{
			CScheduleEditor.SELECTED_ROW_COLOR = clr;
			
			this.getOkBtn().setEnabled(true);
		}
	}
	
	private void onErrorTextColor()
	{
		Color clr = null;
		
		if ( (clr = CJColorChooser.showDialog(this, "Select Color", CScheduleEditor.ERROR_COLOR)) != null)
		{
			CScheduleEditor.ERROR_COLOR = clr;
			
			this.getOkBtn().setEnabled(true);
		}
	}
	
	private void onUnScheduledTextColor()
	{
		Color clr = null;
		
		if ( (clr = CJColorChooser.showDialog(this, "Select Color", CScheduleEditor.NOT_SCHEDULED_COLOR)) != null)
		{
			CScheduleEditor.NOT_SCHEDULED_COLOR = clr;
			
			this.getOkBtn().setEnabled(true);
		}

	}
	
	private void onScheduledTextColor()
	{
		Color clr = null;
		
		if ( (clr = CJColorChooser.showDialog(this, "Select Color", CScheduleEditor.SCHEDULED_COLOR)) != null)
		{
			CScheduleEditor.SCHEDULED_COLOR = clr;
			
			this.getOkBtn().setEnabled(true);
		}
	}
	
	private void onContentChanged()
	{
		this.getOkBtn().setEnabled(true);
	}

	@Override
	protected void onOkClicked()
	{
		this.mSchedule.setFixtureGap((Integer)this.mGap.getValue());
		
		this.mSchedule.setReverseFixtureGap((Integer)this.mReverseGap.getValue());
		
		this.mSchedule.setMaxConsecutive((Integer)this.mMaxConsec.getValue());
		
		CScheduleEditor.getInstance().onContentChanged(CScheduleEditor.REFRESH_MASK);
		
		dispose();

	}

}
