package com.maximuscooke.app.soccerboss.editor.db;

import java.util.Date;

import javax.swing.DefaultComboBoxModel;

import com.maximuscooke.app.soccerboss.CCompetition;
import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CGameObject;
import com.maximuscooke.app.soccerboss.CLeague;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CDate;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.gui.CJComboBox;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerDate;
import com.maximuscooke.lib.common.gui.CJSpinnerNumeric;

@SuppressWarnings("serial")
public class CTabPgLeague extends CTabPgGameObject
{
	private CJLabel mClubCountLbl = new CJLabel("Club Count :");
	
	private CJSpinnerNumeric mClubCount = new CJSpinnerNumeric(100, 4, 300, 1);
	
	private CJLabel mStartDateLbl = new CJLabel("Start Date :");
	private CJLabel mEndDateLbl = new CJLabel("End Date :");
	private CJLabel mWinPtsLbl = new CJLabel("Win Points :");
	private CJLabel mDrawPtsLbl = new CJLabel("Draw Points :");
	private CJLabel mLossPtsLbl = new CJLabel("Loss Points :");
	private CJLabel mSchTmplateLbl = new CJLabel("Schedule Template :");
	private CJLabel mPromotionCountLbl = new CJLabel("Promotion Count :");
	private CJLabel mRelegationCountLbl = new CJLabel("Relegation Count :");

	private CJSpinnerNumeric mWinPts = new CJSpinnerNumeric(CCompetition.DEFAULT_LGE_POINTS_WIN, 1, 10, 1);
	private CJSpinnerNumeric mDrawPts = new CJSpinnerNumeric(CCompetition.DEFAULT_LGE_POINTS_DRAW, 1, 10, 1);
	private CJSpinnerNumeric mLossPts = new CJSpinnerNumeric(0, 0, 10, 1);
	
	private CJSpinnerNumeric mPromotionCount = new CJSpinnerNumeric(3, 0, 6, 1);
	private CJSpinnerNumeric mRelegationCount = new CJSpinnerNumeric(3, 0, 6, 1);
	
	private DefaultComboBoxModel<String> mScheduleTemplateModel = new DefaultComboBoxModel<String>();
	private CJComboBox<String> mScheduleTemplateCB = new CJComboBox<String>(mScheduleTemplateModel);	

	private CJSpinnerDate mStartDate=null;
	private CJSpinnerDate mEndDate=null;
	
	public CTabPgLeague()
	{
		super("Leagues");
		
		CGregorianCalendar dt = new CGregorianCalendar();
		
		@SuppressWarnings("deprecation")
		Date minDate = new Date(dt.getYear() - CDate.DATE_OFFSET - 100, dt.getMonth(), dt.getDayOfMonth());
				
		@SuppressWarnings("deprecation")
		Date maxDate = new Date(dt.getYear() - CDate.DATE_OFFSET + 1 + 100, dt.getMonth(), dt.getDayOfMonth());

		@SuppressWarnings("deprecation")
		Date date = new Date(dt.getYear() - CDate.DATE_OFFSET + 1, dt.getMonth(), dt.getDayOfMonth());
		
		this.mStartDate = new CJSpinnerDate(date, minDate, maxDate, CGregorianCalendar.DAY_OF_WEEK, "EEE dd:MMM:yyyy");
		
		this.mStartDate.setHorizontalAlignment(CJLabel.CENTER);

		this.mEndDate = new CJSpinnerDate(date, minDate, maxDate, CGregorianCalendar.DAY_OF_WEEK, "EEE dd:MMM:yyyy");
		
		this.mEndDate.setHorizontalAlignment(CJLabel.CENTER);
		
		int rowHeight = 28;
		
		this.add(this.mClubCountLbl);
		
		this.add(this.mClubCount);
		
		this.add(this.mStartDateLbl);
		
		this.add(this.mEndDateLbl);
		
		this.add(this.mStartDate);
		
		this.add(this.mEndDate);
		
		this.add(this.mWinPtsLbl);
		
		this.add(this.mDrawPtsLbl);
		
		this.add(this.mLossPtsLbl);
		
		this.add(this.mWinPts);
		
		this.add(this.mDrawPts);
		
		this.add(this.mLossPts);
		
		this.add(this.mSchTmplateLbl);
		
		this.add(this.mScheduleTemplateCB);
		
		this.add(this.mPromotionCountLbl);
		
		this.add(this.mRelegationCountLbl);
		
		this.add(this.mPromotionCount);
		
		this.add(this.mRelegationCount);
		
		this.mClubCountLbl.setBounds(8, 110, 150, rowHeight);
				
		this.mClubCountLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mClubCount.setBounds(158, 110, 100, rowHeight);
		
		this.mClubCount.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mSchTmplateLbl.setBounds(8, 146, 150, rowHeight);
		
		this.mSchTmplateLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mScheduleTemplateCB.setBounds(158, 146, 250, rowHeight);
		
		this.mStartDateLbl.setBounds(8, 178, 150, rowHeight);
		
		this.mStartDateLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mStartDate.setBounds(158,  178, 250, rowHeight);

		this.mEndDateLbl.setBounds(8, 210, 150, rowHeight);
		
		this.mEndDateLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mEndDate.setBounds(158,  210, 250, rowHeight);
		
		this.mLossPtsLbl.setBounds(8, 242, 150, rowHeight);
		
		this.mLossPtsLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mLossPts.setBounds(158, 242, 100, rowHeight);
		
		this.mLossPts.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mDrawPtsLbl.setBounds(8, 274, 150, rowHeight);
		
		this.mDrawPtsLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mDrawPts.setBounds(158, 274, 100, rowHeight);
		
		this.mDrawPts.setHorizontalAlignment(CJLabel.CENTER);

		this.mWinPtsLbl.setBounds(8, 306, 150, rowHeight);
		
		this.mWinPtsLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mWinPts.setBounds(158, 306, 100, rowHeight);
		
		this.mWinPts.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mPromotionCountLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mPromotionCountLbl.setBounds(8, 338, 150, rowHeight);
		
		this.mPromotionCount.setBounds(158, 338, 100, rowHeight);
		
		this.mPromotionCount.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mPromotionCount.setEditable(false);
		
		this.mRelegationCountLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mRelegationCountLbl.setBounds(8, 370, 150, rowHeight);
		
		this.mRelegationCount.setBounds(158, 370, 100, rowHeight);
		
		this.mRelegationCount.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mRelegationCount.setEditable(false);

		this.mClubCountLbl.setEnabled(false);
		
		this.mClubCount.setEnabled(false);
		
		this.mStartDateLbl.setEnabled(false);
		
		this.mEndDateLbl.setEnabled(false);
		
		this.mStartDate.setEnabled(false);
		
		this.mEndDate.setEnabled(false);
		
		this.mWinPtsLbl.setEnabled(false);
		
		this.mDrawPtsLbl.setEnabled(false);
		
		this.mLossPtsLbl.setEnabled(false);
		
		this.mWinPts.setEnabled(false);
		
		this.mDrawPts.setEnabled(false);
		
		this.mLossPts.setEnabled(false);
		
		this.mSchTmplateLbl.setEnabled(false);
		
		this.mScheduleTemplateCB.setEnabled(false);
		
		this.mDrawPts.setEditable(false);
		
		this.mLossPts.setEditable(false);
		
		this.mWinPts.setEditable(false);
		
		this.mPromotionCountLbl.setEnabled(false);
		
		this.mPromotionCount.setEnabled(false);
		
		this.mRelegationCountLbl.setEnabled(false);
		
		this.mRelegationCount.setEnabled(false);
				
		CTabPgGameObject.fillComboBoxes(this.mScheduleTemplateModel, CSoccerBoss.SCH_TMPL_DIR, CSoccerBoss.SCH_TMPL_EXT);

	}

	@Override
	public void initTabPage(CDatabase db, CGameObject gmobj, int options)
	{
		this.setIsInitializing(true);
		
		boolean isLeague = (gmobj instanceof CLeague);

		this.initGameObjectTabPg(db, gmobj, isLeague);
		
		this.mClubCountLbl.setEnabled(isLeague);
		
		this.mClubCount.setEnabled(isLeague);
		
		this.mStartDate.setEnabled(isLeague);
		
		this.mStartDateLbl.setEnabled(isLeague);
		
		this.mEndDate.setEnabled(isLeague);
		
		this.mEndDateLbl.setEnabled(isLeague);
		
		this.mDrawPts.setEnabled(isLeague);
		
		this.mDrawPtsLbl.setEnabled(isLeague);
		
		this.mWinPts.setEnabled(isLeague);
		
		this.mWinPtsLbl.setEnabled(isLeague);
		
		this.mLossPts.setEnabled(isLeague);
		
		this.mLossPtsLbl.setEnabled(isLeague);
		
		this.mScheduleTemplateCB.setEnabled(isLeague);
		
		this.mSchTmplateLbl.setEnabled(isLeague);
		
		this.mPromotionCountLbl.setEnabled(isLeague);
		
		this.mPromotionCount.setEnabled(isLeague);
		
		this.mRelegationCountLbl.setEnabled(isLeague);
		
		this.mRelegationCount.setEnabled(isLeague);
		
		if (isLeague)
		{
			CLeague lg = (CLeague)gmobj;
			
			this.mClubCount.setValue( (Integer)lg.getClubCount() );
			
			if (lg.getStartDate() != null)
			{
				this.mStartDate.setDateValue( lg.getStartDate() );				
			}
			
			if (lg.getEndDate() != null)
			{
				this.mEndDate.setDateValue( lg.getEndDate() );				
			}
						
			this.mWinPts.setValue( (Integer)lg.getPointsForWin() );
			
			this.mLossPts.setValue( (Integer)lg.getPointsForDefeat() );
			
			this.mDrawPts.setValue( (Integer)lg.getPointsForDraw() );
			
			this.mPromotionCount.setValue( (Integer)lg.getPromotionCount() );
			
			this.mRelegationCount.setValue( (Integer)lg.getRelegationCount() );
			
			if (lg.getScheduleTemplate() != null)
			{
				this.mScheduleTemplateCB.setSelectedItem(lg.getScheduleTemplate());
			}
			else
			{
				this.mScheduleTemplateCB.setSelectedIndex(-1);
			}
		}
										
		this.setIsInitializing(false);
	}

	@Override
	protected void onContentChanged()
	{

	}

}
