package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.Dimension;
import java.awt.FlowLayout;

import com.maximuscooke.app.soccerboss.CCompetition;
import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerDate;
import com.maximuscooke.lib.common.gui.CJSpinnerNumeric;
import com.maximuscooke.lib.common.gui.CJTextField;

@SuppressWarnings("serial")
public class CNewDlg extends CDlgBase
{
	private CJSpinnerNumeric mMaxClubs = new CJSpinnerNumeric(CCompetition.DEFAULT_LGE_NUM_TEAMS, CCompetition.DEFAULT_LGE_MIN_TEAMS, CCompetition.DEFAULT_LGE_MAX_TEAMS, 2);
	private CJSpinnerNumeric mGap = new CJSpinnerNumeric(CCompetition.DEFAULT_FIXTURE_GAP_MIN+1, CCompetition.DEFAULT_FIXTURE_GAP_MIN, CCompetition.DEFAULT_FIXTURE_GAP_MAX, 1);
	private CJSpinnerNumeric mMaxConsec = new CJSpinnerNumeric(CCompetition.DEFAULT_HOME_AWAY_CONSECUTIVE_MIN, CCompetition.DEFAULT_HOME_AWAY_CONSECUTIVE_MIN, CCompetition.DEFAULT_HOME_AWAY_CONSECUTIVE_MAX, 1);
	private CJSpinnerNumeric mReverseGap = new CJSpinnerNumeric(90, 2, 365, 1);

	private CJSpinnerDate mStartDate = new CJSpinnerDate("EEE dd:MMM:yyyy");
	private CJSpinnerDate mEndDate = new CJSpinnerDate("EEE dd:MMM:yyyy");
	
	public CNewDlg()
	{
		super("New Schedule", "Create New Schedule", "Create a new fixture schedule for clubs", 500, 400, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png"), true);
		
		int rowHeight = 28;
		
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
		
		this.getOkBtn().setEnabled(true);
		
	}
		
	@Override
	protected void onOkClicked()
	{
		CSchedule sch = new CSchedule();
		
		sch.init((Integer)this.mMaxClubs.getValue(), 
				   (Integer)this.mGap.getValue(), 
				   (Integer)this.mMaxConsec.getValue(), 
				   (Integer)this.mReverseGap.getValue(), 
				   this.mStartDate.getDateValue(), 
				   this.mEndDate.getDateValue());
		
		CScheduleEditor.getInstance().setSchedule(sch);
		
		dispose();
	}
}
