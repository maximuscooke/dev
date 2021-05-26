package com.maximuscooke.app.soccerboss.editor.db;

import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CDbConfigFile;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.gui.CJConfigDialog;

@SuppressWarnings("serial")
public class CSettingsDlg extends CJConfigDialog
{
	public CSettingsDlg(CDbConfigFile configFile, CDatabase db)
	{
		super(configFile, CEditor.getInstance(), "Settings", "Application Settings", "Modify application settings", 800, 600, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "nd0060-48.png"));
	}

}
