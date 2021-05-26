package com.maximuscooke.app.soccerboss.editor.db;

import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CResource;

@SuppressWarnings("serial")
public class CNewClubDlg extends CDlgBase
{
	public CNewClubDlg(CDatabase db)
	{
		super("New Club", "Create club", "Create a new club", 400, 300, CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + "wi0064-48.png"), true);
	}

	@Override
	protected void onOkClicked()
	{
	}

}
