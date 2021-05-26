package com.maximuscooke.app.soccerboss.editor.db;

import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CGameObject;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CFile;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CJButton;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJTabPage;
import com.maximuscooke.lib.common.gui.CJTextField;
import com.maximuscooke.lib.common.gui.CJTreeEx;

@SuppressWarnings("serial")
public abstract class CTabPgGameObject extends CJTabPage
{	
	public static final byte IS_INITIALIZING_BIT = 0;

	private CJButton mUpdateBtn = new CJButton("Update");
	private CJButton mAddBtn = new CJButton("Add");
	private CJButton mRemoveBtn = new CJButton("Remove");

//	private DefaultComboBoxModel<String> mComboBoxModel = new DefaultComboBoxModel<String>();
	
//	private CJComboBox<String> mImageCB = new CJComboBox<String>(mComboBoxModel);

	private CJLabel mNameLbl = new CJLabel("Name : ");
//	private CJLabel mImageLbl = new CJLabel("Image (16x16) : ");
	private CJTextField mNameTF = new CJTextField();
	private CJLabel mIDLbl = new CJLabel("ID : ");
	private CJTextField mIDTF = new CJTextField();
		
	private CGameObject mGameObject=null;
	
	private boolean mIsInitializing=false;
	
	private int mStatus=0;
	
	public CTabPgGameObject(String title)
	{
		super(title);
	
		int rowHeight = 28;
		
		int row = 8;
		int row2 = row+32;
		int row3 = row2+32;
		int col1 = 8;
		int col2 = col1+150;
		int col3 = col2+250;

		this.setLayout( null );

		this.mNameLbl.setBounds(col1, row, 150, rowHeight);
		
		this.mNameLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mNameTF.setBounds(col2, row, 250, rowHeight);
		
		this.mNameTF.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mIDLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mIDLbl.setBounds(col1, row2, 150, rowHeight);
		
		this.mIDTF.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mIDTF.setBounds(col2, row2, 100, rowHeight);
				
		this.add(this.mAddBtn);	
		
		this.mAddBtn.setBounds(col3, row, 100, rowHeight);
		
		this.add(this.mRemoveBtn);
		
		this.mRemoveBtn.setBounds(col3 + 100, row, 100, rowHeight);
		
		this.add(this.mUpdateBtn);
		
		this.mUpdateBtn.setBounds(col3 + 200, row, 100, rowHeight);
				
		this.add(this.mIDLbl);
		
		this.add(this.mIDTF);

		this.add( this.mNameLbl );
		
		this.add( this.mNameTF );
				
		this.getUpdateBtn().addActionListener( e -> onUpdateClicked() );
		
		this.getAddBtn().addActionListener( e -> onAddClicked() );
		
		this.getRemoveBtn().addActionListener( e -> onRemoveClicked());
				
		this.mNameTF.getDocument().addDocumentListener( new DocumentListener() {

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
				
		this.mIDLbl.setEnabled(false);
		
		this.mIDTF.setEnabled(false);
		
		this.mNameLbl.setEnabled(false);
		
		this.mNameTF.setEnabled(false);

		this.mAddBtn.setEnabled(false);
		
		this.mRemoveBtn.setEnabled(false);

		this.mUpdateBtn.setEnabled(false);
	}
	
	protected void initGameObjectTabPg(CDatabase db, CGameObject gmobj, boolean bEnable)
	{
		this.setGameObject(gmobj);
		
		this.mAddBtn.setEnabled(false);
		
		this.mRemoveBtn.setEnabled(bEnable);
		
		this.mUpdateBtn.setEnabled(false);
		
//		this.mImageCB.setEnabled(bEnable);
//		
//		this.mImageLbl.setEnabled(bEnable);
		
		this.mNameLbl.setEnabled(bEnable);
		
		this.mNameTF.setEnabled(bEnable);
		
		this.mIDLbl.setEnabled(bEnable);

		if (bEnable && gmobj != null)
		{			
			this.mNameTF.setText(gmobj.getName());
			
			this.mIDTF.setText(Integer.toString(gmobj.getIntegerID()));
			
//			this.mImageCB.setSelectedItem(gmobj.getImageName16());			
		}
		else
		{
			this.mNameTF.setText(CApplication.EMPTY_STRING);
			
			this.mIDTF.setText(CApplication.EMPTY_STRING);
			
//			this.mImageCB.setSelectedIndex(-1);
		}
	}
	
	public abstract void initTabPage(CDatabase db, CGameObject gmobj, int options);
			
	protected boolean doConfirm(String msg1, String msg2)
	{
		if (JOptionPane.showConfirmDialog(this, msg1, msg2, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0006-48.png")) == JOptionPane.YES_OPTION)

		{
			return true;
		}
		
		return false;
	}
	
	protected void updateDb(CGameObject gmobj)
	{
		gmobj.setName( this.mNameTF.getText() );
		
//		if (this.mImageCB.getSelectedIndex() >= 0)
//		{
//			gmobj.setImageName16( (String)this.mImageCB.getSelectedItem() );				
//		}

		CEditor.getInstance().updateExplorer(CJTreeEx.OPT_RELOAD | CJTreeEx.OPT_SAVE_EXPANDED_STATE | CJTreeEx.OPT_SAVE_SELECTED_NODES, true);					
		
		this.getUpdateBtn().setEnabled(false);
	}
		
	private void onUpdateClicked()
	{
		if (doConfirm("Confirm Update, continue (Y/N) ?", "Update"))
		{
			onUpdate();	
		}
	}
	
	private void onAddClicked()
	{
		if (doConfirm("Confirm add, continue (Y/N) ?", "Add"))
		{
			onAdd();	
		}
	}
	
	private void onRemoveClicked()
	{
		if (doConfirm("Confirm remove, continue (Y/N) ?", "Remove"))
		{
			onRemove();	
		}
	}
	
	protected void onUpdate()
	{		
		this.mAddBtn.setEnabled(false);
		
		this.mRemoveBtn.setEnabled(false);
		
		this.mUpdateBtn.setEnabled(false);
		
		this.getGameObject().setName(this.mNameTF.getText());
		
//		this.getGameObject().setImageName16((String)this.mComboBoxModel.getSelectedItem());
	}
	
	protected void onAdd()
	{		
		this.mAddBtn.setEnabled(true);
		
		this.mRemoveBtn.setEnabled(false);
		
		this.mUpdateBtn.setEnabled(false);				
	}
	
	protected void onRemove()
	{		
		this.mAddBtn.setEnabled(false);
		
		this.mRemoveBtn.setEnabled(true);
		
		this.mUpdateBtn.setEnabled(false);
	}
	
	protected void onRefresh()
	{
//		if (CEditor.getInstance() != null)
//		{			
//			CDatabase db = CEditor.getInstance().getDB();
//			
//			this.mComboBoxModel.removeAllElements();
//			
//			if (db != null)
//			{
//				CArrayList<String> list = new CArrayList<String>(CDatabase.getImageMap().keySet());
//				
//				Collections.sort(list);
//				
//				for(String fn : list)
//				{
//					this.mComboBoxModel.addElement( fn );
//				}			
//			}
//		}
	}
			
	protected abstract void onContentChanged();
	
	public static void fillComboBoxes(DefaultComboBoxModel<String> model, String dir, String ext)
	{		
		CArrayList<String> list = CFile.getListOfFilePaths(dir, ext, true);
				
		Collections.sort(list);
		
		for(String fn : list)
		{
			model.addElement(fn);
		}
	}
		
	protected final CJButton getUpdateBtn()
	{
		return this.mUpdateBtn;
	}
	
	protected final CJButton getAddBtn()
	{
		return this.mAddBtn;
	}
	
	protected final CJButton getRemoveBtn()
	{
		return this.mRemoveBtn;
	}

	public final CGameObject getGameObject()
	{
		return this.mGameObject;
	}

	public final void setGameObject(CGameObject obj)
	{
		this.mGameObject = obj;
	}

//	public final CJComboBox<String> getImageComboBox()
//	{
//		return this.mImageCB;
//	}

	public final CJTextField getNameTextField()
	{
		return this.mNameTF;
	}
	
	public final boolean isInitializing()
	{
		return CApplication.isBitSet(CTabPgGameObject.IS_INITIALIZING_BIT, this.mStatus);		
	}
	
	public final void setIsInitializing(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CTabPgGameObject.IS_INITIALIZING_BIT, this.mStatus) : CApplication.clearBit(CTabPgGameObject.IS_INITIALIZING_BIT, this.mStatus);
	}

}
