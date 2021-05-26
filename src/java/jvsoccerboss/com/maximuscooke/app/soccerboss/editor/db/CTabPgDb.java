package com.maximuscooke.app.soccerboss.editor.db;

import java.util.Date;

import javax.swing.DefaultComboBoxModel;

import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CGameObject;
import com.maximuscooke.lib.common.CDate;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.gui.CJComboBox;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerDate;
import com.maximuscooke.lib.common.gui.CJTreeEx;

@SuppressWarnings("serial")
public class CTabPgDb extends CTabPgGameObject
{
	private DefaultComboBoxModel<CDbSizeRowItem> mComboBoxModel = new DefaultComboBoxModel<CDbSizeRowItem>();
	
	private CJComboBox<CDbSizeRowItem> mDbSizeCB = null;
	
	private CJSpinnerDate mDate=null;
	
	private CJLabel mDbizeLbl = new CJLabel("Size : ");;
	private CJLabel mDateLbl = new CJLabel("Season Start : ");
		
	public CTabPgDb()
	{
		super("Database");
		
		int rowHeight = 28;
		
		int col1  = 8;
		int col2  = col1+150;
								
		this.mDbizeLbl.setBounds(col1, 110, 120, rowHeight);	
		
		this.mDbizeLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mDbSizeCB = new CJComboBox<CDbSizeRowItem>(this.mComboBoxModel);
		
		this.mDbSizeCB.setBounds(col2, 110, 250, rowHeight);	
		
		this.mDateLbl.setBounds(col1, 146, 120, rowHeight);
		
		this.mDateLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		CGregorianCalendar dt = new CGregorianCalendar();
		
		@SuppressWarnings("deprecation")
		Date minDate = new Date(dt.getYear() - CDate.DATE_OFFSET - 100, dt.getMonth(), dt.getDayOfMonth());
				
		@SuppressWarnings("deprecation")
		Date maxDate = new Date(dt.getYear() - CDate.DATE_OFFSET + 1 + 100, dt.getMonth(), dt.getDayOfMonth());
		
		@SuppressWarnings("deprecation")
		Date date = new Date(dt.getYear() - CDate.DATE_OFFSET + 1, dt.getMonth(), dt.getDayOfMonth());
		
		this.mDate = new CJSpinnerDate(date, minDate, maxDate, CGregorianCalendar.DAY_OF_WEEK, "EEE dd:MMM:yyyy");
		
		this.mDate.addChangeListener( e -> onContentChanged() );
				
		this.mDate.setEditable(false);

		this.add(this.mDbizeLbl);
		
		this.add(this.mDbSizeCB);
		
		this.add(this.mDateLbl);
		
		this.add(this.mDate);
				
		this.mDate.setBounds(col2, 146, 250, rowHeight);
		
		this.mDate.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("VERY_SMALL", CDatabase.DB_SIZE_VERY_SMALL) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("SMALL", CDatabase.DB_SIZE_SMALL) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("MEDIUM", CDatabase.DB_SIZE_MEDIUM) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("LARGE", CDatabase.DB_SIZE_LARGE) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("VERY_LARGE", CDatabase.DB_SIZE_VERY_LARGE) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("ULTRA_LARGE", CDatabase.DB_SIZE_ULTRA_LARGE) );
		
		this.mDbSizeCB.addActionListener( e -> onContentChanged() );
		
		this.mDateLbl.setEnabled(false);
		
		this.mDate.setEnabled(false);
		
		this.mDbizeLbl.setEnabled(false);
		
		this.mDbSizeCB.setEnabled(false);
	}
			
	@Override
	public void initTabPage(CDatabase db, CGameObject gmobj, int options)
	{				
		this.setIsInitializing(true);
		
		boolean isDatabase = (gmobj instanceof CDatabase);

		this.initGameObjectTabPg(db, gmobj, isDatabase);
						
		this.mDate.setEnabled(isDatabase);
		
		this.mDateLbl.setEnabled(isDatabase);
		
		this.mDbizeLbl.setEnabled(isDatabase);
				
		this.mDbSizeCB.setEnabled(isDatabase);
		
		this.setIsInitializing(false);
	}
		
	@Override
	protected void onUpdate()
	{
		super.onUpdate();
		
		CDatabase db = CEditor.getInstance().getDB();
		
		CDbSizeRowItem rowItem = (CDbSizeRowItem) this.mDbSizeCB.getSelectedItem();

		if (rowItem != null)
		{
			db.setDbSize(rowItem.getSize());
		}

		db.setStartDate(this.mDate.getDateValue());
		
		CEditor.getInstance().getExplorer().reload(CJTreeEx.OPT_RELOAD | CJTreeEx.OPT_SORT | CJTreeEx.OPT_SAVE_EXPANDED_STATE | CJTreeEx.OPT_SAVE_SELECTED_NODES);
	}
	
	@Override
	protected void onContentChanged()
	{
		if (!this.isInitializing())
		{
			this.getRemoveBtn().setEnabled(false);
			
			boolean bEnable = this.getNameTextField().getText().length() > 0;
			
			this.getUpdateBtn().setEnabled(bEnable);	
		}
	}	
}
