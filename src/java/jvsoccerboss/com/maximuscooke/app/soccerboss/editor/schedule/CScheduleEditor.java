package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.maximuscooke.app.soccerboss.CFixture;
import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.app.soccerboss.CScheduleValidationException;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CFile;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.IExportFile;
import com.maximuscooke.lib.common.INotifyListener;
import com.maximuscooke.lib.common.INotifyMessage;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CJFileChooser;
import com.maximuscooke.lib.common.gui.CJFrameEx;
import com.maximuscooke.lib.common.gui.CJInfoBar;
import com.maximuscooke.lib.common.gui.CJInfoButton;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJMenu;
import com.maximuscooke.lib.common.gui.CJMenuBar;
import com.maximuscooke.lib.common.gui.CJPanel;
import com.maximuscooke.lib.common.gui.CJScrollPane;
import com.maximuscooke.lib.common.gui.CJSplitPane;
import com.maximuscooke.lib.common.gui.CJTabbedPaneEx;

@SuppressWarnings("serial")
public class CScheduleEditor extends CJFrameEx implements INotifyListener
{
	private static CScheduleEditor mInstance;
	
	private static final int mWidth = 1024;
	private static final int mHeight = 768;
	
	public static final int COL_WIDTH = 300;
	
	public static final String RES_PATH = "/com/maximuscooke/app/soccerboss/editor/res/";
	
	public static final int MENU_MASK = (1<<0);
	public static final int INFO_BAR_MASK = (1<<1);
	public static final int FIXTURE_TABLE_MASK = (1<<2);
	public static final int SCHEDULE_TABLE_MASK = (1<<3);
	public static final int SETUP_SCHEDULE_TABLE_MASK = (1<<4);
	public static final int SETUP_FIXTURE_TABLE_MASK = (1<<5);
	public static final int STATUS_BAR_MASK = (1<<6);
	public static final int FIXTURE_LIST_MASK = (1<<7);
	public static final int PROPERTY_MASK = (1<<8);
	
	public static final int ALL_MASK = -1;
	public static final int REFRESH_MASK = (MENU_MASK | INFO_BAR_MASK | FIXTURE_TABLE_MASK | SCHEDULE_TABLE_MASK | STATUS_BAR_MASK | FIXTURE_LIST_MASK | PROPERTY_MASK);
	
	public static Color ERROR_COLOR = Color.RED;
	public static Color SCHEDULED_COLOR = Color.BLACK;
	public static Color NOT_SCHEDULED_COLOR = Color.LIGHT_GRAY;
	public static Color SELECTED_ROW_COLOR = Color.GREEN;
	
	private CJMenuBar mMenuBar = null;
	private CJMenu mFileMenu = new CJMenu("File");
	private CJMenu mFixtureMenu = new CJMenu("Fixture");
	private CJMenu mHelpMenu = new CJMenu("Help");
	private CJMenu mNewMenu = new CJMenu("New");
	private CJMenu mClearMenu = new CJMenu("Clear");
	private CJMenu mAutoMenu = new CJMenu("Auto");

	private JMenuItem mFileSchMI = new JMenuItem("Schedule...", CResource.getResourceAsImageIcon(CScheduleEditor.RES_PATH + "wi0122-16.png"));
	private JMenuItem mOpenMI = new JMenuItem("Open...", CResource.getResourceAsImageIcon(CScheduleEditor.RES_PATH + "ni0048-16.png"));
	private JMenuItem mSaveMI = new JMenuItem("Save", CResource.getResourceAsImageIcon(CScheduleEditor.RES_PATH + "wi0056-16.png"));
	private JMenuItem mSaveAsMI = new JMenuItem("Save As...");
	private JMenuItem mDeleteMI = new JMenuItem("Delete...", CResource.getResourceAsImageIcon(CScheduleEditor.RES_PATH + "nd0048-16.png"));
	private JMenuItem mCloseMI = new JMenuItem("Close");
	private JMenuItem mExportMI = new JMenuItem("Export...");
	private JMenuItem mImportMI = new JMenuItem("Import...");
	private JMenuItem mPropertiesMI = new JMenuItem("Properties...");
	private JMenuItem mQuitMI = new JMenuItem("Quit");
	private JMenuItem mAboutMI = null;

	private JMenuItem mClearRowsMI = new JMenuItem("Row...");
	private JMenuItem mClearScheduleMI = new JMenuItem("Schedule");
	private JMenuItem mClearClubsMI = new JMenuItem("Clubs...");

	private JMenuItem mTrimMI = new JMenuItem("Trim");
	private JMenuItem mExtendMI = new JMenuItem("Extend...");
	private JMenuItem mScheduleMI = new JMenuItem("Schedule");
	private JMenuItem mUnScheduleMI = new JMenuItem("UnSchedule");
	private JMenuItem mUnScheduleErrorsMI = new JMenuItem("UnSchedule Invalid Fixtures");
	private JMenuItem mScheduleRevRowMI = new JMenuItem("Schedule Reverse Row...");
	private JMenuItem mMoveRowMI = new JMenuItem("Move Row...");
	private JMenuItem mAutoNameMI = new JMenuItem("Name...");
	
	private CJLabel mStatusBar = new CJLabel("Ready.");
	private CJInfoBar mInfoBar = new CJInfoBar(80, 80);
	
	private CJFixturePanel mFixturePanel  = new CJFixturePanel();
	private CJPropertyPanel mPropertyPanel = new CJPropertyPanel();
	
	private CJTabbedPaneEx mTabPage = new CJTabbedPaneEx();
	
	private CJInfoButton mNewSchBtn = null;
	private CJInfoButton mOpenBtn = null;
	private CJInfoButton mSaveBtn = null;
	private CJInfoButton mScheduleBtn=null;
	private CJInfoButton mUnScheduleBtn=null;
	
	private CFixtureTable mFixtureTable = new CFixtureTable();
	private CScheduleTable mScheduleTable = new CScheduleTable();
	
	private String mDir=null;
	private String mFilename=null;
	
	private CSchedule mSchedule=null;
		
	public CScheduleEditor()
	{
		super("Fixture Planner Editor", mWidth, mHeight, null, JFrame.EXIT_ON_CLOSE);
		
		CScheduleEditor.mInstance = this;

		this.setupMenus();
		
		this.setupForm();
		
		this.onContentChanged(CScheduleEditor.ALL_MASK);
		
		this.mFixtureTable.getSelectionModel().addListSelectionListener(e -> onFixtureTableEvent(e));
		
		this.mFixtureTable.getColumnModel().addColumnModelListener( new TableColumnModelListener() {

			@Override
			public void columnAdded(TableColumnModelEvent e)
			{
			}

			@Override
			public void columnRemoved(TableColumnModelEvent e)
			{
			}

			@Override
			public void columnMoved(TableColumnModelEvent e)
			{
			}

			@Override
			public void columnMarginChanged(ChangeEvent e)
			{
			}

			@Override
			public void columnSelectionChanged(ListSelectionEvent e)
			{
				onFixtureTableEvent(e);
			}
			
		});

		this.mScheduleTable.getSelectionModel().addListSelectionListener(e -> onScheduleTableEvent(e));
		
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(() -> {
			final CScheduleEditor editor = new CScheduleEditor();
		});
	}
	
	private void onFixtureTableEvent(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting())
		{
			CFixtureCell cell = this.mFixtureTable.getSelectedCell();
			
			if (cell != null && cell.getFixture() != null && cell.getFixture().getStatus() != CFixture.FXT_STATE_VALID)
			{
				this.mStatusBar.setText(cell.getFixture().getDescription());
			}
			else
			{
				this.onContentChanged(CScheduleEditor.STATUS_BAR_MASK | CScheduleEditor.INFO_BAR_MASK );
			}
		}
	}
	
	private void onScheduleTableEvent(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting())
		{
			this.refreshPropertyPanel();
		}
	}
	
	private void setupForm()
	{
		CJPanel centrePanel = new CJPanel();
		
		centrePanel.setLayout(new BorderLayout() );
		
		this.add(this.mInfoBar, BorderLayout.NORTH);
		
		this.mStatusBar.setPreferredSize(100, 24);
		
		this.add(this.mStatusBar, BorderLayout.SOUTH);
		
		CJSplitPane hSplit = new CJSplitPane();
						
		this.mTabPage.addPage(this.mFixturePanel);
		
		this.mTabPage.addPage( this.mPropertyPanel );
		
		hSplit.setSplitPercentage(0.20);
		
		hSplit.setLeftComponent( this.mTabPage );
		
		CJSplitPane vSplit = new CJSplitPane(CJSplitPane.VERTICAL_SPLIT);
		
		vSplit.setSplitPercentage(0.50);
		
		vSplit.setTopComponent( new CJScrollPane(this.mFixtureTable) );
		
		vSplit.setBottomComponent( new CJScrollPane(this.mScheduleTable) );
		
		hSplit.setRightComponent( vSplit );
		
		centrePanel.add( hSplit, BorderLayout.CENTER );
		
		this.add(centrePanel, BorderLayout.CENTER);
		
		this.setupToolBar();
	}
		
	private void setupToolBar()
	{
		this.mNewSchBtn = this.mInfoBar.addButton("New", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png"));

		this.mNewSchBtn.addActionListener( e -> onNew());

		this.mOpenBtn = this.mInfoBar.addButton("Open", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "ni0048-48.png"));
		
		this.mOpenBtn.addActionListener( e -> onOpen() );

		this.mSaveBtn = this.mInfoBar.addButton("Save", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0056-48.png"));
		
		this.mSaveBtn.addActionListener( e -> onSave() );
				
		this.mScheduleBtn = this.mInfoBar.addButton("Schedule", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0115-48.png"));
		
		this.mScheduleBtn.addActionListener( e -> onSchedule() );

		this.mUnScheduleBtn = this.mInfoBar.addButton("UnSchedule", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0108-48.png"));
		
		this.mUnScheduleBtn.addActionListener( e -> onUnSchedule() );
	}

	
	private void setupMenus()
	{
		CApplication.setAppleUseScreenMenuBar(true);

		final CScheduleEditor editor = CScheduleEditor.this;

		this.mMenuBar = new CJMenuBar();
		
		this.mFileMenu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e)
			{
			}

			@Override
			public void menuDeselected(MenuEvent e)
			{
			}

			@Override
			public void menuCanceled(MenuEvent e)
			{
			}
		});
		
		this.mFileMenu.setMnemonic(KeyEvent.VK_F);

		// New MenuItems		
		this.mFileSchMI.setMnemonic(KeyEvent.VK_N);
		
		this.mFileSchMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));

		this.mFileMenu.add(this.mNewMenu);

		this.mFileMenu.add(this.mOpenMI);
		
		this.mFileMenu.add(this.mCloseMI);
		
		this.mCloseMI.setMnemonic(KeyEvent.VK_C);
		
		this.mCloseMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		
		this.mOpenMI.setMnemonic(KeyEvent.VK_O);
		
		this.mOpenMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));

		this.mFileMenu.addSeparator();

		this.mFileMenu.add(this.mSaveMI);
		
		this.mSaveMI.setMnemonic(KeyEvent.VK_S);
		
		this.mSaveMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));

		this.mFileMenu.add(this.mSaveAsMI);
				
		this.mFileMenu.addSeparator();
		
		this.mFileMenu.add(this.mExportMI);
		
		this.mExportMI.addActionListener( e -> onExport() );
		
		this.mFileMenu.add(this.mImportMI);
		
		this.mImportMI.addActionListener( e -> onImport() );
		
		this.mFileMenu.addSeparator();
		
		this.mFileMenu.add( this.mPropertiesMI );
		
		this.mPropertiesMI.addActionListener( e -> onProperties() );

		this.mFileMenu.addSeparator();
		
		this.mFileMenu.add(this.mDeleteMI);
		
		this.mDeleteMI.addActionListener( e -> onDelete() );
		
		this.mFileMenu.addSeparator();

		this.mOpenMI.addActionListener(e -> onOpen());

		this.mSaveMI.addActionListener(e -> onSave());

		this.mSaveAsMI.addActionListener(e -> onSaveAs());
				
		this.mCloseMI.addActionListener( e -> onClose() );

		this.mQuitMI.addActionListener(e -> onQuit() );

		this.mQuitMI.setMnemonic(KeyEvent.VK_Q);

		this.mQuitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));

		this.mFileMenu.add(this.mQuitMI);

		// Schedule MenuItem
		this.mNewMenu.add(this.mFileSchMI);
		
		this.mFileSchMI.addActionListener(e -> onNew());

		this.mFixtureMenu.addSeparator();

		this.mFixtureMenu.add(this.mClearMenu);
			
		this.mClearMenu.add(this.mClearRowsMI);
	
		this.mClearMenu.add(this.mClearClubsMI);
		
		this.mClearMenu.addSeparator();

		this.mClearMenu.add(this.mClearScheduleMI);
		
		this.mFixtureMenu.add(this.mAutoMenu);
				
		this.mAutoMenu.addSeparator();

		this.mAutoMenu.add(this.mAutoNameMI);
				
		this.mFixtureMenu.addSeparator();

		this.mFixtureMenu.add(this.mExtendMI);
		
		this.mFixtureMenu.add(this.mScheduleMI);

		this.mFixtureMenu.add(this.mUnScheduleMI);
		
		this.mFixtureMenu.add(this.mTrimMI);
		
		this.mTrimMI.addActionListener( e -> onTrim());
		
		this.mFixtureMenu.add(this.mUnScheduleErrorsMI);
		
		this.mFixtureMenu.add(this.mScheduleRevRowMI);
		
		this.mFixtureMenu.addSeparator();
				
		this.mFixtureMenu.add(this.mMoveRowMI);
						
		this.mClearRowsMI.addActionListener( e -> onClearRows() );

		this.mClearScheduleMI.addActionListener( e -> onClearSchedule() );
		
		this.mClearClubsMI.addActionListener( e -> onClearClubs() );

		this.mExtendMI.addActionListener( e -> onExtend());
		
		this.mScheduleMI.addActionListener( e -> onSchedule());
				
		this.mMoveRowMI.addActionListener( e -> onMoveRow());
						
		this.mScheduleMI.setMnemonic(KeyEvent.VK_S);

		this.mScheduleMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		
		this.mUnScheduleMI.addActionListener( e -> onUnSchedule());
		
		this.mUnScheduleErrorsMI.addActionListener( e -> onUnscheduleErrors());
		
		this.mUnScheduleMI.setMnemonic(KeyEvent.VK_U);

		this.mUnScheduleMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
		
		this.mScheduleRevRowMI.addActionListener( e -> onScheduleReverseRow() );
								
		this.mAutoNameMI.addActionListener( e -> onAutoName() );
		
		// Help Menu
		this.mAboutMI = new JMenuItem("About...");

		this.mAboutMI.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
			}
		});

		this.mHelpMenu.add(this.mAboutMI);

		this.mMenuBar.add(this.mFileMenu);

		this.mMenuBar.add(this.mFixtureMenu);

		this.mMenuBar.add(this.mHelpMenu);

		this.setJMenuBar(this.mMenuBar);
	}
	
	public void onContentChanged()
	{
		onContentChanged(CScheduleEditor.ALL_MASK);
	}
	
	public void onContentChanged(int options)
	{
		boolean bSchedule = (this.mSchedule != null);

		this.mNewSchBtn.setEnabled(true);
		
		this.mOpenBtn.setEnabled(true);
		
		boolean bSave =  (bSchedule && this.mDir != null && this.mFilename != null) ? true: false;
				
		this.mSaveBtn.setEnabled( bSave );
		
		this.mSaveMI.setEnabled( bSave);
		
		this.mSaveAsMI.setEnabled( bSchedule );
		
		this.mCloseMI.setEnabled( bSchedule );
		
		this.mExportMI.setEnabled( bSchedule);
		
		this.mImportMI.setEnabled( bSchedule );
		
		this.mClearRowsMI.setEnabled(bSchedule);
		
		this.mClearClubsMI.setEnabled(bSchedule);
		
		this.mClearScheduleMI.setEnabled(bSchedule);
		
		this.mAutoNameMI.setEnabled(bSchedule);
		
		this.mTrimMI.setEnabled(bSchedule);
				
		this.mScheduleMI.setEnabled(bSchedule);
				
		this.mUnScheduleMI.setEnabled(bSchedule);
		
		this.mUnScheduleErrorsMI.setEnabled(bSchedule);
		
		this.mScheduleRevRowMI.setEnabled(bSchedule);
		
		this.mExtendMI.setEnabled(bSchedule);
				
		this.mMoveRowMI.setEnabled(bSchedule);
		
		this.mPropertiesMI.setEnabled(bSchedule);
		
		if ((options & CScheduleEditor.FIXTURE_LIST_MASK) != 0)
		{
			this.mFixturePanel.refresh(this.mSchedule);
		}
		
		if ((options & CScheduleEditor.PROPERTY_MASK) != 0)
		{
			this.refreshPropertyPanel();
		}

		if ((options & CScheduleEditor.INFO_BAR_MASK) != 0)
		{
			updateInfoBar();
		}
		
		if ((options & CScheduleEditor.MENU_MASK) != 0)
		{
			updateMenu();
		}
		
		if ((options & CScheduleEditor.SETUP_FIXTURE_TABLE_MASK) != 0)
		{
			if (this.mSchedule != null)
			{
				this.mFixtureTable.setup(this.mSchedule);			
			}
		}
		
		if ((options & CScheduleEditor.SETUP_SCHEDULE_TABLE_MASK) != 0)
		{
			if (this.mSchedule != null)
			{
				this.mScheduleTable.setup(this.mSchedule);
			}
		}
		
		if ((options & CScheduleEditor.FIXTURE_TABLE_MASK) != 0)
		{
			this.mFixtureTable.refresh();
		}

		if ((options & CScheduleEditor.SCHEDULE_TABLE_MASK) != 0)
		{
			this.mScheduleTable.refresh(this.mSchedule);
		}
		
		if ((options & CScheduleEditor.STATUS_BAR_MASK) != 0)
		{
			if (this.mSchedule != null)
			{
				this.mStatusBar.setText(this.mSchedule.toString());				
			}
		}
	}
	
	private void onProperties()
	{
		CPropertiesDlg dlg = new CPropertiesDlg();
		
		dlg.setVisible(true);
	}
	
	private void onTrim()
	{
		if (this.mSchedule != null && JOptionPane.showConfirmDialog(this, "Trim schedule, continue (Y/N) ?", "Trim Schedule", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)
		{
			this.mSchedule.trim();
		}
	}
	
	private void refreshPropertyPanel()
	{
		CScheduleCell cell = this.mScheduleTable.getSelectedCell();
		
		this.mPropertyPanel.refresh(this.mSchedule, cell != null ? cell.getDateTime() : null);
	}
	
	private void updateInfoBar()
	{		
		CFixtureCell cell = this.mFixtureTable.getSelectedCell();
				
		boolean bEnable = (this.mSchedule != null && cell != null && cell.getFixture() != null && !cell.isDummy());
		
		this.mScheduleBtn.setEnabled(bEnable && !cell.getFixture().isScheduled() );
		
		this.mUnScheduleBtn.setEnabled(bEnable && cell.getFixture().isScheduled());
	}
	
	private void updateMenu()
	{
		
	}
	
	private void onQuit()
	{
		if (this.mSchedule != null && this.mSchedule.isModified() && JOptionPane.showConfirmDialog(this, "Schedule modified, save changes (Y/N) ?", "Schedule modified", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)
		{
			onSave();
		}

		System.exit(0);
	}
		
	private void onUnscheduleErrors()
	{
		if (JOptionPane.showConfirmDialog(this, "Confirm unschedule all fixtures in error, continue (Y/N) ?", "Unschedule Errors", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)
		{
			this.getSchedule().unScheduleErrors();

			this.onContentChanged(CScheduleEditor.REFRESH_MASK);
		}
	}
		
	private void onAutoName()
	{
		CAutoNameDlg dlg = new CAutoNameDlg();
		
		dlg.setVisible(true);
	}
	
	private void onExport()
	{
		CJFileChooser fc = new CJFileChooser(CSoccerBoss.EXPORT_DIR);
				
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Schedule Export file", CSoccerBoss.SCH_TMPL_EXPORT));
		
		fc.setAcceptAllFileFilterUsed(false);
								
		if (fc.showDialog(this, "Export") == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			try
			{
				String fName = CFile.removeExtension(f.getName());
								
				CApplication.doExport(f.getParent(), fName + "." + CSoccerBoss.SCH_TMPL_EXPORT, this.mSchedule, IExportFile.EXPORT_OPT_CREATE);
				
				JOptionPane.showMessageDialog(this, "Schedule Exported Successfully", "Exporting Schedule", JOptionPane.INFORMATION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0115-48.png"));
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Export Schedule", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void onImport()
	{
		CJFileChooser fc = new CJFileChooser(CSoccerBoss.EXPORT_DIR);
		
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Schedule Export file", CSoccerBoss.SCH_TMPL_EXPORT));
		
		fc.setAcceptAllFileFilterUsed(false);
								
		if (fc.showDialog(this, "Import") == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			try
			{
				CSchedule newSch = new CSchedule();
				
				CApplication.doImport(f.getParent(), f.getName(), newSch);
								
				this.setSchedule(newSch);
																
				JOptionPane.showMessageDialog(this, "Schedule Imported Successfully", "Importing Schedule", JOptionPane.INFORMATION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0115-48.png"));
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error Importing Schedule", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
		
	private void onScheduleReverseRow()
	{
		CSchReverseRowDlg dlg = new CSchReverseRowDlg();
		
		dlg.setVisible(true);
	}
	
	private void onMoveRow()
	{
		CMoveRowDlg dlg = new CMoveRowDlg();
		
		dlg.setVisible(true);
	}
		
	private void onSchedule()
	{
		CFixtureCell fxtcell = this.mFixtureTable.getSelectedCell();
		
		CArrayList<CScheduleCell> cellRow = this.mScheduleTable.getSelectedCellRow();

		if (fxtcell != null && !fxtcell.isDummy() && cellRow != null)
		{
			CFixture fxt =  fxtcell.getFixture();
			
			if (fxt.isScheduled())
			{
				if (JOptionPane.showConfirmDialog(this, 
						"Fixture <" + fxt.getName() + "> is already scheduled, do you wish to move this fixture, continue (Y/N) ?", 
						"Move Fixture", JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE,
						CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)
					{
						this.getSchedule().unSchedule(fxt);
					}
				else
				{
					return;
				}
			}
	
			CGregorianCalendar schdate = cellRow.getAt(0).getDateTime();
						
			for(CScheduleCell cell : cellRow)
			{
				if (!cell.isDateOnly() && !cell.isScheduled())
				{					
					try
					{
						this.getSchedule().schedule(fxt, schdate);
						
						cell.setFixture( fxt );
					} 
					catch (CScheduleValidationException e)
					{
						JOptionPane.showMessageDialog(this, e.getMessage(), "Error Scheduling Fixture", JOptionPane.ERROR_MESSAGE);
					}
					break;
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "No date specified, please select a row in the schedule date grid", "No Date Selected", JOptionPane.WARNING_MESSAGE);
		}
	}
		
	private void onUnSchedule()
	{
		CFixtureCell fxtcell = this.mFixtureTable.getSelectedCell();
		
		if (fxtcell != null && !fxtcell.isDummy() && fxtcell.isScheduled())
		{
			unSchedule(fxtcell.getFixture());
		}
	}
	
	private void unSchedule(CFixture fxt)
	{
		if (fxt != null && fxt.isScheduled())
		{
			if (JOptionPane.showConfirmDialog(this, "Confirm unschedule fixture <" + fxt.getName() + ">, continue (Y/N) ?", "Unschedule Fixture", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)
			{
				this.getSchedule().unSchedule(fxt);
			}
		}
	}
	
	private void onClearRows()
	{
		CClearRowDlg dlg = new CClearRowDlg();
		
		dlg.setVisible(true);
	}
		
	private void onClearSchedule()
	{
		if (JOptionPane.showConfirmDialog(this, "Confirm clear schedule, continue (Y/N) ?", "Unschedule Fixture", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0122-48.png")) == JOptionPane.YES_OPTION)
		{
			this.getSchedule().unScheduleAll();
		}
	}
	
	private void onClearClubs()
	{
		CClearClubDlg dlg = new CClearClubDlg();
		
		dlg.setVisible(true);
	}
	
	private void onExtend()
	{
		CExtendDlg dlg = new CExtendDlg();
		
		dlg.setVisible(true);
	}
	
	private void onSave()
	{
		if (this.mDir != null && this.mFilename != null)
		{
			doSave();
		}
		else
		{
			onSaveAs();
		}
	}
	
	private void onSaveAs()
	{
		CJFileChooser fc = new CJFileChooser(CSoccerBoss.SCH_TMPL_DIR);
		
		fc.setDialogTitle("Save Schedule");
		
		fc.setMultiSelectionEnabled(false);
		
		fc.setFileSelectionMode(CJFileChooser.FILES_ONLY);
				
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Schedule file", CSoccerBoss.SCH_TMPL_EXT));
		
		fc.setAcceptAllFileFilterUsed(false);
		
		if (fc.showSaveDialog(this) == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			this.mDir = f.getParent();
			
			this.mFilename = String.format("%s.%s", f.getName(), CSoccerBoss.SCH_TMPL_EXT);
			
			doSave();
		}
	}
	
	private void doSave()
	{
		try
		{
			this.mSchedule.save(this.mDir, this.mFilename);
			
			this.setTitle(this.mFilename);
			
			JOptionPane.showMessageDialog(this, "Schedule Saved Successfully", "Saving Schedule", JOptionPane.INFORMATION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0115-48.png"));
		} 
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "Save schedule", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void onDelete()
	{
		CJFileChooser.runFileChooser(fc -> delete(fc), "Delete Schedule", CJFileChooser.FILES_ONLY, CSoccerBoss.SCH_TMPL_DIR);
	}

	private int delete(CJFileChooser fc)
	{
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Schedule file", CSoccerBoss.SCH_TMPL_EXT));
		
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Schedule Export file", CSoccerBoss.SCH_TMPL_EXPORT));
		
		fc.setAcceptAllFileFilterUsed(false);
		
		fc.setApproveButtonText("Delete");
		
		if (fc.showOpenDialog(this) == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			try
			{
				CFile.delete(f.getParent(), f.getName());
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "Error opening fixture schedule.", "Open schedule", JOptionPane.ERROR_MESSAGE);
			}
			
			return 0;
		}

		return -1;
	}
	
	public final void extendSchedule(CGregorianCalendar dt)
	{
		this.mSchedule.setEndDate(dt);
				
		this.onContentChanged(CScheduleEditor.SETUP_SCHEDULE_TABLE_MASK | CScheduleEditor.SCHEDULE_TABLE_MASK | CScheduleEditor.INFO_BAR_MASK | CScheduleEditor.STATUS_BAR_MASK);
	}
	
	private void onNew()
	{
		CNewDlg dlg = new CNewDlg();
		
		dlg.setVisible(true);
	}
	
	private void onClose()
	{
//		this.mTabPane.removePage();
		
//		refresh(CScheduleEditor.REFRESH_ALL);
	}

	private void onOpen()
	{
		CJFileChooser fc = new CJFileChooser(CSoccerBoss.SCH_TMPL_DIR);
		
		fc.setDialogTitle("Open Schedule");
		
		fc.setMultiSelectionEnabled(false);
		
		fc.setFileSelectionMode(CJFileChooser.FILES_ONLY);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Schedule file", CSoccerBoss.SCH_TMPL_EXT);
		
		fc.addChoosableFileFilter(filter);
		
		fc.setAcceptAllFileFilterUsed(false);

		if (fc.showOpenDialog(this) == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			try
			{				
				this.setTitle(this.mFilename);

				this.init( CSchedule.load(f.getParent(), f.getName()), f.getParent(), f.getName(), 0 );
			}
			
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "Error opening schedule.", "Open schedule", JOptionPane.ERROR_MESSAGE);
			}			
		}
	}
		
	private void init(CSchedule sch, String dir, String fName, int options)
	{
		this.mDir = dir;
		
		this.mFilename = fName;
		
		this.mSchedule = sch;
		
		if (this.mSchedule != null)
		{
			this.mSchedule.register(this);			
		}
								
		this.onContentChanged(CScheduleEditor.ALL_MASK);		
	}
	
	@Override
	public void notifyListeners(Object sender, int action, INotifyMessage msg)
	{
		if (action == CSchedule.ACTION_EXTEND_SCHEDULE || action == CSchedule.ACTION_TRIM_SCHEDULE)
		{
			this.onContentChanged(CScheduleEditor.ALL_MASK);
		}
		
		this.onContentChanged(CScheduleEditor.REFRESH_MASK);
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		this.onQuit();
	}

	private void init(CSchedule sch, int options)
	{
		this.init(sch, null, null, options);
	}
	
	public final CSchedule getSchedule()
	{
		return this.mSchedule;
	}
	
	public final void setSchedule(CSchedule s)
	{
		this.init(s, 0);;
	}
	
	public static CScheduleEditor getInstance()
	{
		return mInstance;
	}
}
