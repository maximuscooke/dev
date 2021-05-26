package com.maximuscooke.lib.common.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CConfigFile;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CGridList;
import com.maximuscooke.lib.common.collections.CHashMap;

@SuppressWarnings("serial")
public class CJConfigDialog extends CJOptionDialog
{
	private CConfigFile mConfigFile = null;
	
	private CJTableEx mTable = new CJTableEx(20, true);
	
	private CJButton mRemoveBtn = new CJButton("Remove");
	private CJButton mAddBtn = new CJButton("Add");
	private CJButton mUpdateBtn = new CJButton("Update");
	private CJButton mClearBtn = new CJButton("Clear");
	private CJButton mBrowseBtn = new CJButton("Browse...");
	
	private CJLabel mKeyLbl = new CJLabel("Key : ");
	private CJLabel mValueLbl = new CJLabel("Value : ");
	
	private CJTextField mKeyTF = new CJTextField();
	private CJTextField mValueTF = new CJTextField();
	
	private CHashMap<String, String> mMap = new CHashMap<String, String>();
	
	private CGridList<String> mGrid = new CGridList<String>();
	
	private boolean mModified=false;

	
	public CJConfigDialog(CConfigFile configFile, Frame owner, String title, String titleText, String description, int width, int height, ImageIcon image)
	{
		super(owner, title, titleText, description, width, height, image, true);
		
		int btnwidth = 120;

		this.mConfigFile = configFile;
				
		this.mTable.setAlternateRowColorOption(true);
		
		this.mTable.setAlternateRowColor(new Color( 228, 248, 248));

		this.mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.mTable.setFont(new Font("helvetica", Font.PLAIN, 12));

		this.mTable.setColumnFont(new Font("helvetica", Font.PLAIN, 16));

		this.mTable.setDefaultRenderer(String.class, new CJConfigDialogRenderer());
		
		this.mTable.setFullRowSelectOption(true);
					
		this.mGrid.addColumnHeader("KEY", "VALUE");
			
		for(String key : configFile.getMap().keySet())
		{			
			this.mMap.add(key, configFile.getMap().get(key));
		}
							
		this.mTable.init( this.mGrid );
		
		this.mTable.setAutoResizeMode(CJTable.AUTO_RESIZE_ALL_COLUMNS);
		
		this.mTable.setColumnHorizontalAlignment(CJLabel.CENTER);
				
		CJScrollPane scrollpane = new CJScrollPane(this.mTable);
		
		int tableHeight = height - 400;
				
		scrollpane.setBounds(20, 20, width - 40, tableHeight);
				
		tableHeight += 28;
		
		this.mKeyLbl.setBounds(20, tableHeight, 100, 24);
		
		this.mValueLbl.setBounds(width / 2, tableHeight, 100, 24);
		
		this.mKeyTF.setBounds(60, tableHeight, 320, 24);
		
		this.mValueTF.setBounds(width / 2 + 50, tableHeight, 320, 24);
		
		tableHeight += 50;

		this.mAddBtn.setBounds(20, tableHeight, btnwidth, 24);
		
		this.mRemoveBtn.setBounds(20 + btnwidth, tableHeight, btnwidth, 24);
		
		this.mUpdateBtn.setBounds(20 + btnwidth * 2, tableHeight, btnwidth, 24);
		
		this.mClearBtn.setBounds(20 + btnwidth * 3, tableHeight, btnwidth, 24);
		
		this.mBrowseBtn.setBounds(20 + btnwidth * 4, tableHeight, btnwidth, 24);
		
		this.getCenterPanel().add( scrollpane );
		
		this.getCenterPanel().add(this.mAddBtn);
		
		this.getCenterPanel().add(this.mUpdateBtn);
		
		this.getCenterPanel().add(this.mRemoveBtn);
		
		this.getCenterPanel().add(this.mClearBtn);
		
		this.getCenterPanel().add(this.mBrowseBtn);
		
		this.getCenterPanel().add(this.mKeyLbl);
		
		this.getCenterPanel().add(this.mValueLbl);
		
		this.getCenterPanel().add(this.mKeyTF);
		
		this.getCenterPanel().add(this.mValueTF);
				
		this.mTable.getSelectionModel().addListSelectionListener( e -> onSelectionChanged(e) );
		
		this.mAddBtn.setEnabled(false);
		
		this.mRemoveBtn.setEnabled(false);
		
		this.mUpdateBtn.setEnabled(false);
		
		this.mClearBtn.addActionListener( e -> onClear() );
		
		this.mAddBtn.addActionListener( e -> onAdd() );
		
		this.mRemoveBtn.addActionListener( e -> onRemove() );
		
		this.mUpdateBtn.addActionListener( e -> onUpdate() );
		
		this.mBrowseBtn.addActionListener( e -> onBrowse() );
		
		this.mKeyTF.getDocument().addDocumentListener( new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				onKeyChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				onKeyChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				onKeyChanged();
			}
			
		});
		
		this.mValueTF.getDocument().addDocumentListener( new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				onKeyChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				onKeyChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				onKeyChanged();
			}
			
		});
				
		this.setOkButtonText("Save");
		
		this.getOkBtn().setEnabled(false);
		
		this.refreshGrid();
	}
	
	private void refreshGrid()
	{
		this.mGrid.clear();
		
		for(String key : this.mMap.keySet())
		{
			CArrayList<String> row = new CArrayList<String>(2);
				
			row.add(key);
			
			row.add(this.mMap.get(key));
								
			this.mGrid.add(row);
		}

		this.mTable.clear();
		
		this.mTable.init(this.mGrid);
		
		this.mTable.refresh();
		
		this.getOkBtn().setEnabled(this.mModified);
	}
	
	private void onKeyChanged()
	{
		boolean bAdd = false;
		boolean bRemove = false;
		boolean bUpdate = false;
		
		if (this.mKeyTF.getText().length() > 0)
		{			
			int selRow = this.mTable.getSelectedRow();

			String key = this.mKeyTF.getText();
			
			if ( selRow >= 0 && this.mMap.containsKey(key))
			{
				bRemove = true;
				
				if (!this.mMap.get(key).equals(this.mValueTF.getText()))
				{
					bUpdate = true;
				}
			}
			else
			{
				if (this.mValueTF.getText().length() > 0)
				{
					bAdd = true;					
				}
			}
		}
		
		this.mAddBtn.setEnabled(bAdd);
		
		this.mRemoveBtn.setEnabled(bRemove);
		
		this.mUpdateBtn.setEnabled(bUpdate);
		
	}
	
	private void onBrowse()
	{
		CJFileChooser fc = new CJFileChooser();
		
		fc.setDialogTitle("Select file or directory");
		
		fc.setMultiSelectionEnabled(false);
		
		fc.setFileSelectionMode(CJFileChooser.FILES_AND_DIRECTORIES);
										
		fc.setApproveButtonText("Select");
		
		if (fc.showOpenDialog(this) == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			this.mValueTF.setText(f.getAbsolutePath());
		}
	}
	
	private void onAdd()
	{		
		this.mMap.add(this.mKeyTF.getText(), this.mValueTF.getText());
		
		this.mKeyTF.setText(CApplication.EMPTY_STRING);
		
		this.mValueTF.setText(CApplication.EMPTY_STRING);

		this.mModified = true;
		
		this.onKeyChanged();
		
		this.refreshGrid();
	}
	
	private void onRemove()
	{		
		int selRow = this.mTable.getSelectedRow();
		
		if ( JOptionPane.showConfirmDialog(this, 
							 						  String.format("Remove Row %d (Y/N) ?", selRow+1), 
							 							"Remove Row", JOptionPane.YES_NO_OPTION, 
							 							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					
					String key = (String)this.mTable.getValueAt(selRow, 0);
					
					this.mMap.remove(key);
					
					this.mModified = true;

					this.mKeyTF.setText(CApplication.EMPTY_STRING);
					
					this.mValueTF.setText(CApplication.EMPTY_STRING);
					

					this.onKeyChanged();
					
					this.refreshGrid();
					
				}		

	}
	
	private void onUpdate()
	{
		int selRow = this.mTable.getSelectedRow();
		
		if ( JOptionPane.showConfirmDialog(this, 
							 						  String.format("Update Row %d (Y/N) ?", selRow+1), 
							 							"Update Row", JOptionPane.YES_NO_OPTION, 
							 							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					
					
					this.mMap.add(this.mKeyTF.getText(), this.mValueTF.getText());
					
					this.mModified = true;

					this.mKeyTF.setText(CApplication.EMPTY_STRING);
					
					this.mValueTF.setText(CApplication.EMPTY_STRING);
					
					this.onKeyChanged();
					
					this.refreshGrid();
					
				}		

	}
	
	private void onClear()
	{
		this.mKeyTF.setText(CApplication.EMPTY_STRING);
		
		this.mValueTF.setText(CApplication.EMPTY_STRING);
		
		this.onKeyChanged();
	}
	
	private void onSelectionChanged(ListSelectionEvent e)
	{		
		if(!e.getValueIsAdjusting())
		{			
			int selRow = this.mTable.getSelectedRow();
			
			if (selRow >= 0)
			{			
				this.mKeyTF.setText(getValueAt(selRow, 0));
				
				this.mValueTF.setText(getValueAt(selRow, 1));
			}
			
			onKeyChanged();
		}
	}
	
	public String getValueAt(int row, int col)
	{
		return (String)this.mTable.getValueAt(row, col);
	}
	
	@Override
	protected void onCancelClicked()
	{
		if ( !this.mModified)
		{
			super.dispose();
		}
		else
		{
			if ( this.mModified && 
					JOptionPane.showConfirmDialog(this, 
							"Discard Changes (Y/N) ?", 
							"Update Row", JOptionPane.YES_NO_OPTION, 
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			{			
				super.dispose();
			}
		}
	}

	@Override
	protected void onOkClicked()
	{
		this.mConfigFile.clear();
		
		for(String key : this.mMap.keySet())
		{
			this.mConfigFile.addParam(key, this.mMap.get(key));
		}
		
		try
		{
			
			this.mConfigFile.save();
		} 
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error Saving Configuration File", JOptionPane.ERROR_MESSAGE);
		}
		
		dispose();
	}

	public final CConfigFile getConfigFile()
	{
		return mConfigFile;
	}
}

class CJConfigDialogRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 614373698047489664L;

	public CJConfigDialogRenderer()
	{
		this.setHorizontalAlignment(CJLabel.CENTER);
	}
}

