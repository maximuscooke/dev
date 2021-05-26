package com.maximuscooke.lib.common;

public interface IExportFile
{
	public static final int EXPORT_OPT_CREATE = (1<<0);
	public static final int EXPORT_OPT_APPEND = (1<<1);

	void doExport(CTextFile expFile);
	void doImport(CTextFile expFile, long lineNum) throws Exception;
}
