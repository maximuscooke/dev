package com.maximuscooke.app.soccerboss.editor.utilities;

import java.io.File;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CFile;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.CTextFile;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CCheckBoxListItem;
import com.maximuscooke.lib.common.gui.CJButton;
import com.maximuscooke.lib.common.gui.CJCheckBoxList;
import com.maximuscooke.lib.common.gui.CJFileChooser;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJScrollPane;
import com.maximuscooke.lib.common.gui.CJTextField;

@SuppressWarnings("serial")
public class CSetupFilePg extends CTabPgBase
{
	private CJTextField mWorkingDirTF = new CJTextField();
	
	private CJButton mWorkingDirBtn = new CJButton("Browse...");
	private CJButton mSelectAllBtn = new CJButton("Select All");
	private CJButton mClearAllBtn = new CJButton("Clear All");
	private CJButton mConvertBtn = new CJButton("Convert");
	
	private DefaultListModel<CFileRowItem> mModel = new DefaultListModel<CFileRowItem>();
	
	private CJCheckBoxList<CFileRowItem> mList = new CJCheckBoxList<CFileRowItem>(mModel);

	
	public CSetupFilePg()
	{
		super("Setup Files");
		
		this.setLayout(null);
		
		CJLabel wdlbl = new CJLabel("Working Directory :");
		
		wdlbl.setBounds(12, 7, 130, 28);
				
		this.add( wdlbl );
		
		this.mWorkingDirTF.setBounds(16, 32, 600, 28);
		
		this.add( this.mWorkingDirTF );
		
		this.mWorkingDirBtn.setBounds(620, 33, 100, 28);
		
		this.add(this.mWorkingDirBtn);
		
		CJLabel fllbl = new CJLabel("File(s) :");
		
		fllbl.setBounds(12, 64, 60, 28);
				
		this.add(fllbl);
		
		CJScrollPane sp = new CJScrollPane(this.mList);
		
		this.add( sp );
		
		sp.setBounds(16, 94, 300, 400);
		
		this.add(this.mSelectAllBtn);
		
		this.mSelectAllBtn.setBounds(320, 100, 100, 28);
		
		this.mSelectAllBtn.addActionListener( e -> onSelectAll() );
		
		this.mClearAllBtn.setBounds(320, 130, 100, 28);
		
		this.mClearAllBtn.addActionListener( e -> onClearAll() );
		
		this.add(this.mClearAllBtn);
		
		this.add(this.mConvertBtn);
		
		this.mConvertBtn.setBounds(320, 470, 100, 28);
		
		this.mConvertBtn.addActionListener( e -> onConvert() );
				
		this.mWorkingDirTF.getDocument().addDocumentListener( new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				onWorkingDirChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				onWorkingDirChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{				
				onWorkingDirChanged();
			}
			
		});
		
		this.mWorkingDirBtn.addActionListener( e -> onBrowseClicked());
		
		this.mWorkingDirTF.setText(CSoccerBoss.SETUP_DIR);
	}
	
	private void onSelectAll()
	{
		initList(true);
	}
	
	private void onClearAll()
	{
		initList(false);
	}
	
	private void onConvert()
	{
		if (JOptionPane.showConfirmDialog(this, 
								 					 "Convert Setup Files, continue (Y/N) ?", 
								 					 "Convert Setup Files", JOptionPane.YES_NO_OPTION, 
								 					 JOptionPane.QUESTION_MESSAGE,
									             CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0006-48.png")) == JOptionPane.YES_OPTION)
		{
			for(int i = 0; i < this.mModel.getSize(); i++)
			{
				CFileRowItem ri = this.mModel.getElementAt(i);
				
				if (ri.isSelected())
				{
					CTextFile tf = new CTextFile(this.mWorkingDirTF.getText(), ri.getFileName());
					
					try
					{
						tf.read();
						
						tf.performOp(CTextFile.OP_SORT | CTextFile.OP_CAPITALIZE | CTextFile.OP_REMOVE_DUPLICATES | CTextFile.OP_REMOVE_BLANK_LINES);
						
						tf.save();
					} 
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(this, e.getMessage(), "Convert Setup Files", JOptionPane.ERROR_MESSAGE);
						
						e.printStackTrace();
					}
				}
			}
			
			JOptionPane.showMessageDialog(this, "Converting Files Complete.", "Convert Setup Files", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void initList(boolean bSelected)
	{
		for(int i = 0; i < this.mModel.getSize(); i++)
		{
			CFileRowItem ri = this.mModel.getElementAt(i);
			
			ri.setSelected(bSelected);
		}		
		
		this.mList.invalidate();
		
		this.mList.repaint();
	}
	
	private void onBrowseClicked()
	{
		CJFileChooser fc = new CJFileChooser(CSoccerBoss.SETUP_DIR);
		
		fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
						
		if (fc.showDialog(this, "Select Folder") == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			this.mWorkingDirTF.setText( f.getParent() );
		}
	}
	
	private void onWorkingDirChanged()
	{
		this.mModel.removeAllElements();
		
		boolean bEnable = false;
		
		if (CFile.exists(this.mWorkingDirTF.getText()))
		{
			CArrayList<String> fileList = CFile.getListOfFilePaths(this.mWorkingDirTF.getText(), true);
			
			Collections.sort(fileList);
			
			if (fileList != null)
			{
				bEnable = true;
				
				for(String fName : fileList)
				{
					this.mModel.addElement( new CFileRowItem(fName) );
				}
			}
		}
		
		this.mSelectAllBtn.setEnabled(bEnable);
		
		this.mClearAllBtn.setEnabled(bEnable);
		
		this.mConvertBtn.setEnabled(bEnable);
	}
}

@SuppressWarnings("serial")
class CFileRowItem extends CCheckBoxListItem
{
	private String mFilename=null;
	
	public CFileRowItem(String fName) 
   {
   	super(fName);
   	
   	this.mFilename = fName;
   }
	
	public final String getFileName()
	{
		return this.mFilename;
	}
	
	@Override
	public String toString()
	{
		return this.mFilename;
	}
}

