package com.maximuscooke.app.soccerboss.editor.db;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CFile;
import com.maximuscooke.lib.common.CRange;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.CTextFile;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CHashMap;
import com.maximuscooke.lib.common.gui.CJButton;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJList;
import com.maximuscooke.lib.common.gui.CJScrollPane;
import com.maximuscooke.lib.common.gui.CJTextField;

@SuppressWarnings("serial")
public class CNewClubFileDlg extends CDlgBase
{
	private CHashMap<String, String> mNameMap = new CHashMap<String, String>();
	private CHashMap<String, String> mExtMap = new CHashMap<String, String>();
	
	private CJLabel mNameLbl = new CJLabel("Club Name :");
	private CJLabel mExtLbl = new CJLabel("Extension :");
	
	private DefaultListModel<String> mNameMdl = new DefaultListModel<String>();
	private DefaultListModel<String> mExtMdl = new DefaultListModel<String>();
	
	private CJTextField mNameTF = new CJTextField();
	private CJTextField mExtTF = new CJTextField();
	
	private CJList<String> mNameList = new CJList<String>(this.mNameMdl);
	private CJList<String> mExtList = new CJList<String>(this.mExtMdl);
	
	private CJButton mAddBtn = new CJButton("Add");
	private CJButton mNameRemoveBtn = new CJButton("Remove");
	private CJButton mExtRemoveBtn = new CJButton("Remove");
	private CJButton mNameSortBtn = new CJButton("Sort");
	private CJButton mExtSortBtn = new CJButton("Sort");
	
	private String mDir = null;
	private String mFilename = null;
	
	private boolean mModified = false;

	
	public CNewClubFileDlg()
	{
		this(null, null);
	}
	
	public CNewClubFileDlg(String dir, String fName)
	{
		super("Club File", "Club File Details", "Add new club file name details", 900, 560, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0021-48.png"), true);
				
		this.init(dir, fName);
	}
	
	private void init(String dir, String fName)
	{
		this.mDir = dir;
		
		this.mFilename = fName;

		this.getCenterPanel().add(this.mNameLbl);
		
		this.getCenterPanel().add(this.mExtLbl);
		
		this.getCenterPanel().add(this.mNameTF);
		
		this.getCenterPanel().add(this.mExtTF);
		
		this.getCenterPanel().add(this.mAddBtn);
		
		this.getCenterPanel().add(this.mNameRemoveBtn);
		
		this.getCenterPanel().add(this.mExtRemoveBtn);
		
		this.getCenterPanel().add(this.mNameSortBtn);
		
		this.getCenterPanel().add(this.mExtSortBtn);
		
		this.mNameRemoveBtn.setBounds(140, 330, 80, 28);
		
		this.mNameSortBtn.setBounds(220, 330, 80, 28);
		
		this.mExtRemoveBtn.setBounds(510, 330, 80, 28);
		
		this.mExtSortBtn.setBounds(590, 330, 80, 28);
		
		CJScrollPane namescroll = new CJScrollPane(this.mNameList);
		
		this.getCenterPanel().add(namescroll );
		
		CJScrollPane extscroll = new CJScrollPane(this.mExtList);
		
		this.getCenterPanel().add(extscroll);
		
		this.mNameLbl.setBounds(20, 20, 120, 28);
		
		this.mNameLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mNameTF.setBounds(150, 20, 250, 28);
		
		this.mExtLbl.setBounds(380, 20, 120, 28);
		
		this.mExtLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mExtTF.setBounds(520, 20, 250, 28);
		
		this.mAddBtn.setBounds(780, 22, 90, 28);
		
		namescroll.setBounds(150, 60, 250, 260);
		
		extscroll.setBounds(520, 60, 250, 260);
		
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
		
		this.mExtTF.getDocument().addDocumentListener( new DocumentListener() {

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
		
		this.mNameList.addListSelectionListener( e -> onNameSelectionChanged(e) );
		
		this.mExtList.addListSelectionListener( e -> onExtSelectionChanged(e) );
		
		this.mAddBtn.addActionListener( e -> onAdd() );
		
		this.mNameRemoveBtn.addActionListener( e -> onNameRemove() );
		
		this.mNameSortBtn.addActionListener( e -> onNameSort() );
		
		this.mExtRemoveBtn.addActionListener( e -> onExtRemove() );
		
		this.mExtSortBtn.addActionListener( e -> onExtSort() );
		
		this.mAddBtn.setEnabled(false);
		
		this.mNameRemoveBtn.setEnabled(false);
		
		this.mExtRemoveBtn.setEnabled(false);
		
		this.setOkButtonText("Save");
		
		this.load(dir, fName);

		this.initButtons();
	}
	
	private void initButtons()
	{		
		this.mNameRemoveBtn.setEnabled( (this.mNameMdl.getSize() > 0 && this.mNameList.getSelectedIndex() >= 0) );
		
		this.mNameSortBtn.setEnabled( this.mNameMdl.getSize() > 1 );
		
		this.mExtRemoveBtn.setEnabled( (this.mExtMdl.getSize() > 0 && this.mExtList.getSelectedIndex() >= 0) );
		
		this.mExtSortBtn.setEnabled( this.mExtMdl.getSize() > 1 );
		
		this.getOkBtn().setEnabled(this.mModified);
	}
	
	private void load(String dir, String fName)
	{
		if (dir != null && fName != null)
		{			
			this.mNameMap.clear();
			
			this.mExtMap.clear();
			
			CTextFile tf = new CTextFile(dir, fName);
			
			try
			{
				tf.read();
				
				CRange<Long> range = tf.findMarkerRange(CSoccerBoss.CLUB_MARKER);
				
				for(long i = range.getMin()+1; i < range.getMax(); i++)
				{
					String ln = tf.getLineAt((int)i);
					
					this.mNameMap.add(ln, ln);
				}
				
				range = tf.findMarkerRange(CSoccerBoss.EXT_MARKER);
				
				for(long i = range.getMin()+1; i < range.getMax(); i++)
				{
					String ln = tf.getLineAt((int)i);
					
					this.mExtMap.add(ln, ln);
				}
				
				this.onExtSort();
				
				this.onNameSort();
			} 
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error Opening Club File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void onNameSelectionChanged(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting())
		{
			this.initButtons();
		}
	}
	
	private void onExtSelectionChanged(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting())
		{
			this.initButtons();
		}
	}
	
	private void onExtSort()
	{
		doSort(this.mExtList, this.mExtMdl, this.mExtMap);
	}
	
	private void onExtRemove()
	{
		this.doRemove(this.mExtList, this.mExtMdl, this.mExtMap);
	}
	
	private void onNameSort()
	{
		doSort(this.mNameList, this.mNameMdl, this.mNameMap);
	}
	
	private void onNameRemove()
	{
		this.doRemove(this.mNameList, this.mNameMdl, this.mNameMap);
	}
	
	private void doRemove(CJList<String> listbox, DefaultListModel<String> model, CHashMap<String, String> map)
	{
		int index = listbox.getSelectedIndex();

		if (index >= 0)
		{
			String key = model.get(index);

			if (JOptionPane.showConfirmDialog(this, String.format("Remove Element %s (Y/N) ?", key), "Confirm Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			{
				map.remove(key);

				model.removeElementAt(index);

				this.mModified = true;
				
				this.initButtons();

			}
		}
	}
	
	private void doSort(CJList<String> listbox, DefaultListModel<String> model, CHashMap<String, String> map)
	{
		int index = listbox.getSelectedIndex();
		
		model.removeAllElements();
		
		CArrayList<String> list = new CArrayList<String>(map.values());
		
		list.sort( (s1, s2) -> s1.compareTo(s2) );
		
		for(String ele : list)
		{
			model.addElement(ele);
		}
		
		listbox.setSelectedIndex(index);
	}
	
	private void onAdd()
	{
		if (this.mNameTF.getText().length() > 0 && !this.mNameMap.containsKey(this.mNameTF.getText()))
		{
			this.mNameMap.add(this.mNameTF.getText(), this.mNameTF.getText());
			
			this.mNameMdl.addElement( this.mNameTF.getText() );
			
			this.mNameTF.setText(CApplication.EMPTY_STRING);
			
			this.mModified = true;
		}
		
		if (this.mExtTF.getText().length() > 0 && !this.mExtMap.containsKey(this.mExtTF.getText()))
		{
			this.mExtMap.add(this.mExtTF.getText(), this.mExtTF.getText());
			
			this.mExtMdl.addElement( this.mExtTF.getText() );
			
			this.mExtTF.setText(CApplication.EMPTY_STRING);
			
			this.mModified = true;
		}
		
		this.initButtons();
	}
	
	private void onContentChanged()
	{
		this.mAddBtn.setEnabled( (this.mNameTF.getText().length() > 0 || this.mExtTF.getText().length() > 0) );
	}
	
	private boolean save(String dir, String fName)
	{
		if (dir != null && fName != null)
		{
			if (CFile.exists(dir, fName) && JOptionPane.showConfirmDialog(this, String.format("File <%s> already exists, overwrite (Y/N)", fName), "Save Club File", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
			{				
				return false;
			}
			
			CFile.deleteIfExists(dir, fName);					
			
			CTextFile tf = new CTextFile(dir, fName);
					
			tf.addLine(CSoccerBoss.CLUB_MARKER);

			this.mNameMap.values().forEach( ln -> tf.addLine(ln) );
			
			tf.addLine(CSoccerBoss.CLUB_MARKER);
			
			tf.addLine(CSoccerBoss.EXT_MARKER);

			this.mExtMap.values().forEach( ln -> tf.addLine(ln) );
			
			tf.addLine(CSoccerBoss.EXT_MARKER);

			try
			{
				tf.save(dir, fName);
				
				this.mModified = false;
				
				this.initButtons();				
			} 
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error Saving File", JOptionPane.ERROR_MESSAGE);
			}
			
			return true;
		}
		return false;
	}
	
	protected void onCancelClicked()
	{
		if (!this.mModified || JOptionPane.showConfirmDialog(this, "Content has been modified, discard changes (Y/N) ?", "Confirm Discard Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
		{
			this.dispose();			
		}
	}

	@Override
	protected void onOkClicked()
	{
//		if (this.mDir == null || this.mFilename == null)
//		{
//			CJFileChooser fc = new CJFileChooser(CSoccerBoss.CLUB_DIR);
//			
//			fc.setDialogTitle("Save Club File");
//			
//			fc.setMultiSelectionEnabled(false);
//			
//			fc.setFileSelectionMode(CJFileChooser.FILES_ONLY);
//			
//			FileNameExtensionFilter filter = new FileNameExtensionFilter("Club file", CSoccerBoss.CLUB_EXT);
//			
//			fc.addChoosableFileFilter(filter);
//			
//			fc.setAcceptAllFileFilterUsed(false);
//			
//			if (fc.showSaveDialog(this) == CJFileChooser.APPROVE_OPTION)
//			{
//				File f = fc.getSelectedFile();
//				
//				this.mDir = f.getParent();
//				
//				this.mFilename = f.getName() + "." + CSoccerBoss.CLUB_EXT;
//			}	
//		}
//		
//		if (save(this.mDir, this.mFilename))
//		{				
//			dispose();
//		}
//		else
//		{
//			this.mDir = null;
//			
//			this.mFilename = null;
//		}			
	}
}
