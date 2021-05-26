package com.maximuscooke.lib.common.gui;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class CJTable extends JTable
{
	public CJTable()
	{
	}

	public CJTable(TableModel dm)
	{
		super(dm);
	}

	public CJTable(TableModel dm, TableColumnModel cm)
	{
		super(dm, cm);
	}

	public CJTable(int numRows, int numColumns)
	{
		super(numRows, numColumns);
	}

	public CJTable(Vector<?> rowData, Vector<?> columnNames)
	{
		super(rowData, columnNames);
	}

	public CJTable(Object[][] rowData, Object[] columnNames)
	{
		super(rowData, columnNames);
	}

	public CJTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm)
	{
		super(dm, cm, sm);
	}

}
