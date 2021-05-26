package com.maximuscooke.app.soccerboss.editor.db;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.maximuscooke.app.soccerboss.CClub;
import com.maximuscooke.app.soccerboss.CCountry;
import com.maximuscooke.app.soccerboss.CCup;
import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CDbMsg;
import com.maximuscooke.app.soccerboss.CLeague;
import com.maximuscooke.app.soccerboss.CRegion;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.IMutableIconTreeNode;
import com.maximuscooke.lib.common.INotifyListener;
import com.maximuscooke.lib.common.INotifyMessage;
import com.maximuscooke.lib.common.gui.CDefaultMutableTreeNode;
import com.maximuscooke.lib.common.gui.CJFileChooser;
import com.maximuscooke.lib.common.gui.CJFrameEx;
import com.maximuscooke.lib.common.gui.CJInfoBar;
import com.maximuscooke.lib.common.gui.CJInfoButton;
import com.maximuscooke.lib.common.gui.CJMenu;
import com.maximuscooke.lib.common.gui.CJMenuBar;
import com.maximuscooke.lib.common.gui.CJPanel;
import com.maximuscooke.lib.common.gui.CJScrollPane;
import com.maximuscooke.lib.common.gui.CJSplitPane;
import com.maximuscooke.lib.common.gui.CJStatusBar;
import com.maximuscooke.lib.common.gui.CJTabbedPaneEx;
import com.maximuscooke.lib.common.gui.CJTreeEx;

@SuppressWarnings("serial")
public class CEditor extends CJFrameEx implements INotifyListener
{
	public static final byte SUPPRESS_EXPLORER_EVENTS_BIT = 0;

	public static final int BUILD_DB_MASK = (1<<0); 
	public static final int REFRESH_DB_MASK = (1<<1);
	
	public static final int BUILD_MASK = BUILD_DB_MASK;
	public static final int REFRESH_MASK = REFRESH_DB_MASK;
	
	private static CEditor mInstance;
	
	private static final int mWidth = 1200;
	private static final int mHeight = 960;
	
	private CJMenuBar mMenuBar = null;
	
	private CJMenu mFileMenu = new CJMenu("File");
	private CJMenu mNewMenu = new CJMenu("New");
	private CJMenu mDbMenu = new CJMenu("Database");
	private CJMenu mOpenFileMI = new CJMenu("Open File...");
	
	private JMenuItem mOpenMI = new JMenuItem("Open...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "ni0048-16.png"));
	private JMenuItem mSaveMI = new JMenuItem("Save", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0056-16.png"));
	private JMenuItem mCloseMI = new JMenuItem("Close");
	private JMenuItem mSaveAsMI = new JMenuItem("Save As...");
	private JMenuItem mExportMI = new JMenuItem("Export...");
	private JMenuItem mImportMI = new JMenuItem("Import...");
	private JMenuItem mDeleteMI = new JMenuItem("Delete...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0048-16.png"));
	private JMenuItem mQuitMI = new JMenuItem("Quit");
	
	private JMenuItem mNewDbMI = new JMenuItem("Database...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0006-16.png"));
	private JMenuItem mNewRegionMI = new JMenuItem("Region...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0064-16.png"));
	private JMenuItem mNewCountryMI = new JMenuItem("Country...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0045-16.png"));
	private JMenuItem mNewClubMI = new JMenuItem("Club...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0165-16.png"));
	private JMenuItem mNewClubFileMI = new JMenuItem("Club File...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0021-16.png"));
	private JMenuItem mOpenClubFileMI = new JMenuItem("Club File...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0021-16.png"));
	private JMenuItem mOpenPersonnelFileMI = new JMenuItem("Personnel File...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "ac0051-16.png"));
	private JMenuItem mNewPersonnelFileMI = new JMenuItem("Personnel File...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "ac0051-16.png"));
	private JMenuItem mSettingsMI = new JMenuItem("Settings...", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0060-16.png"));
	
	private CTabPgDb mDbTabPg = new CTabPgDb();
	private CTabPgRegion mRegionTabPg = new CTabPgRegion();
	private CTabPgCountry mCountryTabPg = new CTabPgCountry();
	private CTabPgClub mClubTabPg = new CTabPgClub();
	private CTabPgLeague mLeagueTabPg = new CTabPgLeague();
	private CTabPgCup mCupTabPg = new CTabPgCup();
	
	private CDatabase mDB=null;

	private CJStatusBar mStatusBar = new CJStatusBar(100, 14);
	private CJInfoBar mInfoBar = new CJInfoBar(80, 80);
	
	private CDbExplorer mExplorer = new CDbExplorer( mDB );
	private CJTabbedPaneEx mTabPane = new CJTabbedPaneEx();
	
	private CJInfoButton mNewDbBtn = null;
	private CJInfoButton mOpenBtn = null;
	private CJInfoButton mSaveBtn = null;
	private CJInfoButton mDeleteBtn = null;
	private CJInfoButton mBuildBtn = null;
	private CJInfoButton mRefreshBtn = null;
	
	private String mDbDir = null;
	private String mDbFileName = null;
	
	private int mOptions=0;
	
	public CEditor()
	{
		super("Soccer Boss Database Editor", mWidth, mHeight, null, JFrame.EXIT_ON_CLOSE);

		CEditor.mInstance = this;
		
		this.setupFrame();

		this.setupMenus();

		this.setVisible(true);
		
		this.mExplorer.setCellRenderer(new CExplorerRenderer());
	}

	public static void main(String[] args)
	{		
		javax.swing.SwingUtilities.invokeLater( () -> new CEditor() );
	}
	
	private void setupFrame()
	{
		CJPanel panel = new CJPanel( new BorderLayout() );
		
		this.mTabPane.addPage( this.mDbTabPg );
		
		this.mTabPane.addPage( this.mRegionTabPg );
		
		this.mTabPane.addPage( this.mCountryTabPg );
		
		this.mTabPane.addPage( this.mClubTabPg );
		
		this.mTabPane.addPage( this.mLeagueTabPg );
		
		this.mTabPane.addPage( this.mCupTabPg );
		
		CJSplitPane splitPane = new CJSplitPane(CJSplitPane.HORIZONTAL_SPLIT);
		
		splitPane.setSplitPercentage(0.2);
		
		splitPane.setLeftComponent( new CJScrollPane(this.mExplorer) );
		
		splitPane.setRightComponent( this.mTabPane );
		
		panel.add(this.mInfoBar, BorderLayout.NORTH);
		
		panel.add(splitPane, BorderLayout.CENTER);
		
		panel.add(this.mStatusBar, BorderLayout.SOUTH);
		
		this.mStatusBar.setStatusBarColor(Color.ORANGE);

		this.add( panel, BorderLayout.CENTER);
		
		this.mExplorer.addTreeSelectionListener( e -> onExplorerSelectionChanged(e) );
		
		setupToolBar();
	}
	
	private void onExplorerSelectionChanged(TreeSelectionEvent e)
	{
		if (!this.isExplorerEventsEnabled())
		{
			CDefaultMutableTreeNode node = (CDefaultMutableTreeNode)this.mExplorer.getLastSelectedPathComponent();
			
			CDatabase db = null;
			CRegion rg = null;
			CCountry country = null;
			CClub club=null;
//			CCompetition competition=null;
			CLeague league=null;
			CCup cup=null;
			
			if (node != null)
			{
				if(node.getUserObject() instanceof CDatabase)
				{
					db = (CDatabase)node.getUserObject();
				}
				else if (node.getType() == CDatabase.REGION_TYPE)
				{
					rg = (CRegion)node.getUserObject();								
				}
				else if (node.getType() == CDatabase.COUNTRY_TYPE)
				{
					country = (CCountry)node.getUserObject();	
				}
				else if (node.getType() == CDatabase.CLUB_TYPE)
				{
					club = (CClub)node.getUserObject();
				}
//				else if (node.getType() == CDatabase.COMPETITION_TYPE)
//				{
//					competition = (CCompetition)node.getUserObject();
//				}
				else if (node.getType() == CDatabase.LEAGUE_TYPE)
				{
					league = (CLeague)node.getUserObject();
				}
				else if (node.getType() == CDatabase.CUP_TYPE)
				{
					cup = (CCup)node.getUserObject();
				}

			}
						
			this.mDbTabPg.initTabPage(this.mDB, db, 0);
			
			this.mRegionTabPg.initTabPage(this.mDB, rg, 0);
			
			this.mCountryTabPg.initTabPage(this.mDB, country, 0);
			
			this.mClubTabPg.initTabPage(this.mDB, club, 0);
			
			this.mLeagueTabPg.initTabPage(this.mDB, league, 0);
			
			this.mCupTabPg.initTabPage(this.mDB, cup, 0);
		}
	}
	
	public final void updateExplorer(int options, boolean supressEvents)
	{
		this.setIsExplorerEventsEnabled(supressEvents);
		
		this.mExplorer.reload(options);
		
		this.setIsExplorerEventsEnabled(!supressEvents);
		
		this.getDB().setModified(true);
	}
	
	private void setupToolBar()
	{
		this.mNewDbBtn = this.mInfoBar.addButton("New DB", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0006-48.png"));
		
		this.mNewDbBtn.addActionListener( e -> onNewDb());

		this.mOpenBtn = this.mInfoBar.addButton("Open", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "ni0048-48.png"));
		
		this.mOpenBtn.addActionListener( e -> onOpen() );

		this.mSaveBtn = this.mInfoBar.addButton("Save", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0056-48.png"));
		
		this.mSaveBtn.addActionListener( e -> onSave() );
		
		this.mDeleteBtn = this.mInfoBar.addButton("Delete", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0048-48.png"));
		
		this.mDeleteBtn.addActionListener( e -> onDelete() );
		
		this.mBuildBtn = this.mInfoBar.addButton("Build", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0012-48.png"));
		
		this.mBuildBtn.addActionListener( e -> onBuild() );
		
		this.mRefreshBtn = this.mInfoBar.addButton("Refresh", CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "ni0105-48.png"));
		
		this.mRefreshBtn.addActionListener( e -> onRefresh() );		
	}

	private void setupMenus()
	{
		CApplication.setAppleUseScreenMenuBar(true);
		
		this.mMenuBar = new CJMenuBar();
		
		this.setupFileMenu();
		
		this.setupDbMenu();
		
		this.setJMenuBar(this.mMenuBar);
		
		this.onContentChanged(-1);
	}
	
	private void setupDbMenu()
	{		
		this.mMenuBar.add(this.mDbMenu);
		
	}
	
	private void setupFileMenu()
	{
		this.mMenuBar.add(this.mFileMenu);
		
		this.mFileMenu.addMenuListener( new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e)
			{
				enableFileMenu();
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
		
		this.mFileMenu.add(this.mNewMenu);
				
		this.mNewMenu.add(this.mNewDbMI);

		this.mNewMenu.addSeparator();
		
		this.mNewMenu.add(this.mNewRegionMI);
		
		this.mNewMenu.add(this.mNewCountryMI);
		
		this.mNewMenu.add(this.mNewClubMI);
		
		this.mNewMenu.addSeparator();
				
		this.mNewMenu.add(this.mNewClubFileMI);
		
		this.mNewClubFileMI.addActionListener( e -> onNewClubFile() );
		
		this.mNewMenu.add(this.mNewPersonnelFileMI);
		
		this.mNewPersonnelFileMI.addActionListener( e -> onNewPersonnelFile());
		
		this.mNewRegionMI.addActionListener( e -> onNewRegion() );
		
		this.mNewCountryMI.addActionListener( e -> onNewCountry() );
		
		this.mNewDbMI.setMnemonic(KeyEvent.VK_D);
		
		this.mNewDbMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
		
		this.mNewDbMI.addActionListener( e -> onNewDb() );
						
		this.mFileMenu.add(this.mOpenMI);
		
		this.mFileMenu.add(this.mOpenFileMI);
		
		this.mOpenFileMI.add(this.mOpenClubFileMI);
		
		this.mOpenClubFileMI.addActionListener( e -> onOpenClubFile() );
		
		this.mOpenPersonnelFileMI.addActionListener( e -> onOpenPersonnelFile() );

		this.mOpenFileMI.add(this.mOpenPersonnelFileMI);
		
		this.mOpenMI.addActionListener( e -> onOpen() );
		
		this.mFileMenu.addSeparator();
		
		this.mOpenMI.setMnemonic(KeyEvent.VK_Q);

		this.mOpenMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));
		
		this.mQuitMI.addActionListener( e -> onOpen() );
		
		this.mFileMenu.add(this.mCloseMI);
		
		this.mCloseMI.addActionListener( e -> onClose() );
		
		this.mCloseMI.setMnemonic(KeyEvent.VK_C);

		this.mCloseMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		
		this.mFileMenu.addSeparator();
		
		this.mFileMenu.add(this.mSaveMI);
		
		this.mSaveMI.addActionListener( e -> onSave() );
		
		this.mFileMenu.add(this.mSaveAsMI);
		
		this.mSaveAsMI.addActionListener( e -> onSaveAs() );
				
		this.mFileMenu.addSeparator();
		
		this.mFileMenu.add(this.mExportMI);
		
		this.mExportMI.addActionListener( e -> onExport());
		
		this.mFileMenu.add(this.mImportMI);
		
		this.mImportMI.addActionListener( e -> onImport() );
		
		this.mFileMenu.add(this.mSettingsMI);
		
		this.mSettingsMI.addActionListener( e -> onSettings());
		
		this.mFileMenu.addSeparator();
		
		this.mFileMenu.add(this.mDeleteMI);
		
		this.mDeleteMI.addActionListener( e -> onDelete() );
		
		this.mFileMenu.addSeparator();
		
		this.mFileMenu.add(this.mQuitMI);
		
		this.mQuitMI.setMnemonic(KeyEvent.VK_Q);

		this.mQuitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));
		
		this.mQuitMI.addActionListener( e -> onQuit() );
	}
	
	private void init(CDatabase db)
	{
		this.mDB = db;
		
		this.mDB.register(this);
								
		this.onContentChanged(-1);
		
		this.onRefresh();
	}
	
	private void onRefresh()
	{
		for(int i = 0; i < this.mTabPane.getPageCount(); i++)
		{
			CTabPgGameObject tabpg = (CTabPgGameObject)this.mTabPane.getPageAt(i);
			
			tabpg.onRefresh();
		}
	}
	
	private void onBuild()
	{
		if (this.mDB != null && 
			 this.mDB.isModified() &&
				 JOptionPane.showConfirmDialog(this, 
							 							 "Build Database, continue (Y/N) ?", 
							 							 "Build Database", JOptionPane.YES_NO_OPTION, 
							 							 JOptionPane.QUESTION_MESSAGE,
								                   CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0006-48.png")) == JOptionPane.YES_OPTION)
				{
					this.mDB.build(-1);
				}		
	}
	
	private void onAdd()
	{
		CTabPgGameObject pg = (CTabPgGameObject)this.mTabPane.getActivePage();
		
		pg.onAdd();
	}
	
	private void onRemove()
	{
		CTabPgGameObject pg = (CTabPgGameObject)this.mTabPane.getActivePage();
		
		pg.onRemove();
	}

	private void onOpenClubFile()
	{		
//		CJFileChooser fc = new CJFileChooser(CSoccerBoss.CLUB_DIR);
//				
//		fc.addChoosableFileFilter(new FileNameExtensionFilter("Club file", CSoccerBoss.CLUB_EXT));
//		
//		fc.setMultiSelectionEnabled(false);
//		
//		fc.setFileSelectionMode(CJFileChooser.FILES_ONLY);
//		
//		fc.setAcceptAllFileFilterUsed(true);
//				
//		if (fc.showDialog(this, "Open Club File") == CJFileChooser.APPROVE_OPTION)
//		{
//			File f = fc.getSelectedFile();
//			
//			CNewClubFileDlg dlg = new CNewClubFileDlg(f.getParent(), f.getName());
//			
//			dlg.setVisible(true);
//		}

	}
	
	private void onOpenPersonnelFile()
	{
//		CJFileChooser fc = new CJFileChooser(CSoccerBoss.PERSONNEL_DIR);
//		
//		fc.addChoosableFileFilter(new FileNameExtensionFilter("Personnel File", CSoccerBoss.PERSONNEL_EXT));
//		
//		fc.setMultiSelectionEnabled(false);
//		
//		fc.setFileSelectionMode(CJFileChooser.FILES_ONLY);
//		
//		fc.setAcceptAllFileFilterUsed(true);
//				
//		if (fc.showDialog(this, "Open Personnel File") == CJFileChooser.APPROVE_OPTION)
//		{
//			File f = fc.getSelectedFile();
//			
//			CNewPersonnelFileDlg dlg = new CNewPersonnelFileDlg(f.getParent(), f.getName());
//			
//			dlg.setVisible(true);
//		}
	}
		
	private void onNewClubFile()
	{
		CNewClubFileDlg dlg = new CNewClubFileDlg();
		
		dlg.setVisible(true);
	}
	
	private void onNewPersonnelFile()
	{
		CNewPersonnelFileDlg dlg = new CNewPersonnelFileDlg();
		
		dlg.setVisible(true);
	}
	
	private void onSettings()
	{
		CSettingsDlg dlg = new CSettingsDlg(CDatabase.getConfigFile(), this.mDB);
		
		dlg.setVisible(true);
	}
	
	private void onImport()
	{		
		CJFileChooser fc = new CJFileChooser(CSoccerBoss.EXPORT_DIR);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Database Export file", CSoccerBoss.DB_EXPORT);
		
		fc.addChoosableFileFilter(filter);
		
		fc.setAcceptAllFileFilterUsed(false);
				
		if (fc.showDialog(this, "Import") == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			try
			{
				this.mDB.importDb(f.getParent(), f.getName());
				
				JOptionPane.showMessageDialog(this, "Database Imported Successfully", "Importing Database", JOptionPane.INFORMATION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR+ "wi0115-48.png"));
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Import database", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void onExport()
	{		
		CJFileChooser fc = new CJFileChooser(CSoccerBoss.EXPORT_DIR);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Database Export file", CSoccerBoss.DB_EXPORT);
		
		fc.addChoosableFileFilter(filter);
		
		fc.setAcceptAllFileFilterUsed(false);
								
		if (fc.showDialog(this, "Export") == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			try
			{
				this.mDB.exportDb(f.getParent(), f.getName()+ "." + CSoccerBoss.DB_EXPORT);
				
				JOptionPane.showMessageDialog(this, "Database Exported Successfully", "Exporting Database", JOptionPane.INFORMATION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0115-48.png"));
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Export database", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
		
	private void onNewDb()
	{
		this.onClose();
		
		CNewDbDlg dlg = new CNewDbDlg(this.mDB);

		dlg.setVisible(true);

		this.mDbDir = null;
		
		this.mDbFileName = null;
				
		this.onContentChanged(-1);
	}
	
	private void onNewRegion()
	{
	}
	
	private void onNewCountry()
	{
	}
	
	private void onDelete()
	{
		CJFileChooser fc = createFileChooser("Delete Database");
						
		fc.setAcceptAllFileFilterUsed(false);
		
		fc.setApproveButtonText("Delete");
		
		if (fc.showOpenDialog(this) == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			try
			{
//				CScheduleEditor.delete(f.getParent(), f.getName());
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "Error deleting database.", "Deleting database", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void onOpen()
	{
		CJFileChooser fc = createFileChooser("Open Database");

		if (fc.showOpenDialog(this) == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			this.mDbDir = f.getParent();
			
			this.mDbFileName = f.getName();

			try
			{				
				this.setDB( CDatabase.openDB(this.mDbDir, this.mDbFileName) );
			}
			
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Opening database", JOptionPane.ERROR_MESSAGE);
			}			
		}
	}
	
	private CJFileChooser createFileChooser(String title)
	{
		CJFileChooser fc = new CJFileChooser(CSoccerBoss.DB_DIR);
		
		fc.setDialogTitle(title);
		
		fc.setMultiSelectionEnabled(false);
		
		fc.setFileSelectionMode(CJFileChooser.FILES_ONLY);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Database file", CSoccerBoss.DB_EXT);
		
		fc.addChoosableFileFilter(filter);
		
		fc.setAcceptAllFileFilterUsed(false);

		return fc;
	}
	
	private void onClose()
	{
		this.isDbModified();
		
		this.mDbDir = null;
		
		this.mDbFileName = null;
		
		if (this.mDB != null)
		{
			this.mDB.unregister(this);			
		}
	}
	
	private void onSave()
	{
		try
		{
			CDatabase.saveDB(this.mDbDir, this.mDbFileName, this.getDB());
			
			this.onContentChanged(-1);
			
			JOptionPane.showMessageDialog(this, "Database Saved Successfully", "Saving Database", JOptionPane.INFORMATION_MESSAGE, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0115-48.png"));
		}
		
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, "Error saving schedule.", "Save schedule", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void onSaveAs()
	{	
		CJFileChooser fc = createFileChooser("Save Database");
		
		if (fc.showSaveDialog(this) == CJFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			
			this.mDbDir = f.getParent();
			
			this.mDbFileName = String.format("%s.%s", f.getName(), CSoccerBoss.DB_EXT);
			
			this.onSave();
		}
	}
	
	private void saveDB()
	{
		if (this.mDbDir != null && this.mDbFileName != null)
		{
			this.onSave();
		}
		else
		{
			this.onSaveAs();
		}
	}
	
	private void isDbModified()
	{
		if (this.mDB != null && 
			 this.mDB.isModified() &&
			 JOptionPane.showConfirmDialog(this, 
						 							 "Database modified, save changes (Y/N) ?", 
						 							 "Database modified", JOptionPane.YES_NO_OPTION, 
						 							 JOptionPane.QUESTION_MESSAGE,
							                   CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0047-48.png")) == JOptionPane.YES_OPTION)
			{
				saveDB();
			}		
	}
	
	private void onQuit()
	{		
		isDbModified();
		
		System.exit(0);	
	}
		
	public final void onContentChanged(int options)
	{
		enableFileMenu();
				
		this.mExplorer.refresh(this.mDB, options);
				
//		if ((options & CEditor) != 0)
		{
//			this.mDbTabPage.refresh(this.mDB, 0);
			
//			this.mRegionTabPage.refresh(this.mDB, 0);
			
//			this.mCountryTabPage.refresh(this.mDB, 0);
			
//			this.mExplorer.reload();
		}
	}
	
	private void enableFileMenu()
	{
		boolean bDbNull = (this.mDB == null) ? false : true;
		
		boolean bSave = (bDbNull && this.mDbDir != null && this.mDbFileName != null) ? true : false;
		
		this.mSaveMI.setEnabled( bSave );
		
		this.mSaveBtn.setEnabled( bSave );
		
		this.mSaveAsMI.setEnabled( bDbNull );
		
		this.mCloseMI.setEnabled( bDbNull );
		
		this.mNewRegionMI.setEnabled(bDbNull);
		
		this.mNewCountryMI.setEnabled(bDbNull);
		
		this.mNewClubMI.setEnabled(bDbNull);
		
		this.mBuildBtn.setEnabled(bDbNull);
		
		this.mRefreshBtn.setEnabled(bDbNull);
	}

	@Override
	public void notifyListeners(Object sender, int code, INotifyMessage msg)
	{
	//	this.onContentChanged(-1);
		
		CDbMsg dbmsg = (CDbMsg)msg;
		
		int options = CJTreeEx.OPT_RELOAD | CJTreeEx.OPT_SORT | CJTreeEx.OPT_SAVE_EXPANDED_STATE | CJTreeEx.OPT_SAVE_SELECTED_NODES;
		
		if (code == CDatabase.ACTION_ADD)
		{
			if (dbmsg.getType() == CDatabase.REGION_TYPE)
			{
				this.mExplorer.addRegion( dbmsg.getRegion(), options);				
			}
			else if (dbmsg.getType() == CDatabase.COUNTRY_TYPE)
			{
				this.mExplorer.addCountry(dbmsg.getCountry(), options);
			}
		}
		else if (code == CDatabase.ACTION_DELETE)
		{
			if (dbmsg.getType() == CDatabase.COUNTRY_TYPE)
			{
				this.mExplorer.deleteCountry(dbmsg.getCountry(), options);
			}
			else if (dbmsg.getType() == CDatabase.REGION_TYPE)
			{
				this.mExplorer.deleteRegion(dbmsg.getRegion(), options);
			}
		}
		else if (code == CDatabase.ACTION_UPDATE)
		{
//			if (dbmsg.getType() == CDatabase.COUNTRY_TYPE)
//			{
//			}
//			else if (dbmsg.getType() == CDatabase.REGION_TYPE)
//			{
//			}
			
			this.mExplorer.reload(options);
		}
		else if (code == CDatabase.ACTION_START_IMPORT_DB)
		{
			this.mExplorer.removeAllTreeNodes();
			
//			this.mExplorer.build();
		}
		
//		System.out.println(msg);
	}
		
	@Override
	public void windowClosing(WindowEvent e)
	{
		this.onQuit();
	}

	public static CEditor getInstance()
	{
		return mInstance;
	}
	
	public static CDbExplorer getExplorer()
	{
		return getInstance().mExplorer;
	}

	public final CDatabase getDB()
	{
		return mDB;
	}

	public final void setDB(CDatabase db)
	{
		init(db);
	}
	
	public final boolean isExplorerEventsEnabled()
	{
		return CApplication.isBitSet(CEditor.SUPPRESS_EXPLORER_EVENTS_BIT, this.mOptions);		
	}
	
	public final void setIsExplorerEventsEnabled(boolean bEnable)
	{
		this.mOptions = bEnable ? CApplication.setBit(CEditor.SUPPRESS_EXPLORER_EVENTS_BIT, this.mOptions) : CApplication.clearBit(CEditor.SUPPRESS_EXPLORER_EVENTS_BIT, this.mOptions);
	}
}

@SuppressWarnings("serial")
class CExplorerRenderer extends DefaultTreeCellRenderer
{
	CExplorerRenderer()
	{
		this.setTextSelectionColor(Color.ORANGE);

		this.setBackgroundSelectionColor(Color.BLACK);

		this.setBackgroundNonSelectionColor(Color.WHITE);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		Object o = ((DefaultMutableTreeNode) value).getUserObject();

		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

		if (o instanceof IMutableIconTreeNode)
		{
			IMutableIconTreeNode tn = (IMutableIconTreeNode) o;
			
			label.setIcon(tn.getDefaultIcon());
		}

		return label;
	}

}

