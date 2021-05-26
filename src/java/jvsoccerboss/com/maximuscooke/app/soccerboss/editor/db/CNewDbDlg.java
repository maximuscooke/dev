package com.maximuscooke.app.soccerboss.editor.db;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CDate;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.gui.CJComboBox;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerDate;
import com.maximuscooke.lib.common.gui.CJTextField;

@SuppressWarnings("serial")
public class CNewDbDlg extends CDlgBase
{
	private DefaultComboBoxModel<CDbSizeRowItem> mComboBoxModel = new DefaultComboBoxModel<CDbSizeRowItem>();
	
	private CJComboBox<CDbSizeRowItem> mDbSizeCB = new CJComboBox<CDbSizeRowItem>(mComboBoxModel);
	
	private CJSpinnerDate mDate;
	
	private CJLabel mNameLbl = new CJLabel("Name : ");
	private CJLabel mSizeLbl = new CJLabel("Size : ");
	private CJLabel mDateLbl = new CJLabel("Season Date : ");
	private CJTextField mNameTF = new CJTextField("New Database");
	
	public CNewDbDlg(CDatabase db)
	{
		super("New Database", "Create database.", "Create a new database.", 400, 300, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0006-48.png"), true);
				
		this.getCenterPanel().setLayout( new FlowLayout(FlowLayout.LEFT) );
		
		this.mNameLbl.setFont(this.mDefaultFont);
		
		this.getCenterPanel().add(this.mNameLbl);
		
		this.getCenterPanel().add(this.mNameTF);
		
		this.getCenterPanel().add( this.mSizeLbl );
		
		this.getCenterPanel().add( this.mDbSizeCB );
		
		this.getCenterPanel().add( this.mDateLbl );
		
		CGregorianCalendar dt = new CGregorianCalendar();
		
		@SuppressWarnings("deprecation")
		Date minDate = new Date(dt.getYear() - CDate.DATE_OFFSET - 100, dt.getMonth(), dt.getDayOfMonth());
				
		@SuppressWarnings("deprecation")
		Date maxDate = new Date(dt.getYear() - CDate.DATE_OFFSET + 1 + 100, dt.getMonth(), dt.getDayOfMonth());
		
		@SuppressWarnings("deprecation")
		Date date = new Date(dt.getYear() - CDate.DATE_OFFSET + 1, dt.getMonth(), dt.getDayOfMonth());
		
		this.mDate = new CJSpinnerDate(date, minDate, maxDate, CGregorianCalendar.DAY_OF_WEEK, "EEE dd:MMM:yyyy");
		
		this.mDate.setPreferredSize(new Dimension(250, 22) );
		
		this.mDate.setHorizontalAlignment(CJLabel.CENTER);
				
		this.mNameLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mNameLbl.setPreferredSize(100, 30);
						
		this.mNameTF.setPreferredSize(250, 22);
				
		this.mSizeLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mSizeLbl.setPreferredSize(100, 30);
				
		this.mDbSizeCB.setPreferredSize(new Dimension(250, 22) );
		
		this.mDateLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mDateLbl.setPreferredSize(100, 30);
		
		this.getCenterPanel().add( this.mDate );
				
		this.mComboBoxModel.addElement( new CDbSizeRowItem("VERY_SMALL", CDatabase.DB_SIZE_VERY_SMALL) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("SMALL", CDatabase.DB_SIZE_SMALL) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("MEDIUM", CDatabase.DB_SIZE_MEDIUM) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("LARGE", CDatabase.DB_SIZE_LARGE) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("VERY_LARGE", CDatabase.DB_SIZE_VERY_LARGE) );
		
		this.mComboBoxModel.addElement( new CDbSizeRowItem("ULTRA_LARGE", CDatabase.DB_SIZE_ULTRA_LARGE) );
		
		this.mNameTF.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				onContentChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				onContentChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				onContentChanged();
			}

		});
	}
	
	private void onContentChanged()
	{
		this.getOkBtn().setEnabled( (this.mNameTF.getText() != null && this.mNameTF.getText().length() > 0) );
	}

	@Override
	protected void onOkClicked()
	{
		CDatabase db = new CDatabase(this.mNameTF.getText(), CEditor.getInstance());
		
		CDbSizeRowItem rowItem = (CDbSizeRowItem)this.mDbSizeCB.getSelectedItem();
		
		if (rowItem != null)
		{
			db.setDbSize(rowItem.getSize());
		}
		
		db.setStartDate( this.mDate.getDateValue() );
				
		CEditor.getInstance().setDB( db );
		
		this.dispose();
	}
}

class CDbSizeRowItem
{
	private String mName=null;
	private int mSize=0;
	
	public CDbSizeRowItem(String nm, int sz)
	{
		this.mName = nm;
		
		this.mSize = sz;
	}
	
	@Override
	public String toString()
	{
		return this.mName;
	}
	
	public final int getSize()
	{
		return this.mSize;
	}

	public final String getName()
	{
		return this.mName;
	}
}

