package com.maximuscooke.lib.common.gui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CGridList;

public class CDefaultTableModel extends DefaultTableModel
{
	private static final long serialVersionUID = -115547205462549585L;

	public CDefaultTableModel(int rowCount, int columnCount)
	{
		super(rowCount, columnCount);
	}

	public CDefaultTableModel(Vector<?> columnNames, int rowCount)
	{
		super(columnNames, rowCount);
	}

	public CDefaultTableModel(Object[] columnNames, int rowCount)
	{
		super(columnNames, rowCount);
	}

	public CDefaultTableModel(Vector<?> data, Vector<?> columnNames)
	{
		super(data, columnNames);
	}

	public CDefaultTableModel(Object[][] data, Object[] columnNames)
	{
		super(data, columnNames);
	}

	@Override
	public Class<?> getColumnClass(int col)
	{
		if (col >= 0 && col < this.getColumnCount())
		{
			Object obj = this.getValueAt(0, col);

			if (obj != null)
			{
				return obj.getClass();
			}
		}

		return Object.class;
	}

	public void addRow(CGridList<?> g)
	{
		for (int i = 0; i < g.getRowCount(); i++)
		{
			this.addRow(g.getRowAt(i).toArray());
		}
	}

	public void addRow(CArrayList<?> list)
	{
		this.addRowArray(list.toArray());
	}

	public void addRow(Object[]... args)
	{
		Object[] array = new Object[args.length];

		for (int i = 0; i < args.length; i++)
		{
			array[i] = args[i];
		}

		this.addRowArray(array);
	}

	private void addRowArray(Object[] objs)
	{
		this.addRow(objs);
	}

	public void clearAllRows()
	{
		this.setRowCount(0);
	}
}
