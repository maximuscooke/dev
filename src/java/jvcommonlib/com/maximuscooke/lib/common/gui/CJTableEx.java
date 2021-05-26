package com.maximuscooke.lib.common.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CGridList;

@SuppressWarnings("serial")
public class CJTableEx extends CJTable
{	
	private static final byte ENABLE_ALTERNATE_ROW_COLOR_BIT = 0;
	private static final byte ENABLE_FULL_ROW_SELECT_BIT = 1;

	private CDefaultTableModel mDefaultTableModel = null;

	private Color mAlternateRowColor = new Color( 228, 228, 228);
	private Color mRowColor = Color.WHITE;
	private Color mTextColor = Color.BLACK;
	private Color mSelectedRowColor = Color.BLACK;
	private Color mSelectedTextColor = Color.ORANGE;
	
	private short mOptions =0;
	
	public CJTableEx()
	{
		this(20, false);
	}

	public CJTableEx(int rowHeight, boolean bEnableCellSelection)
	{
		this.setAutoResizeMode(CJTable.AUTO_RESIZE_OFF);

		this.setRowHeight(rowHeight);

		this.setCellSelectionEnabled(bEnableCellSelection);
		
	}

	public CDefaultTableModel init(CGridList<?> grid)
	{
		Object[] columns = null;

		if (grid.hasHeaders())
		{
			columns = grid.getColumnHeaders().toArray();
			
		} 
		else
		{
			int maxCols = grid.getColumnCount();

			columns = new Object[maxCols];

			for (int col = 0; col < maxCols; col++)
			{
				columns[col] = "Column " + (col + 1);
			}
		}

		CDefaultTableModel dm = this.init(columns);

		dm.addRow(grid);

		return dm;
	}

	public CDefaultTableModel init(Object[] columns)
	{
		return init(columns, (TableCellRenderer) null);
	}

	private CDefaultTableModel init(Object[] columns, TableCellRenderer renderer)
	{
		this.removeAllColumns();
		
		CDefaultTableModel dm = new CDefaultTableModel(columns, 0);

		this.setModel(dm);

		if (renderer != null)
		{
			Enumeration<TableColumn> cols = this.getColumnModel().getColumns();

			while (cols.hasMoreElements())
			{
				TableColumn tc = cols.nextElement();

				tc.setCellRenderer(renderer);
			}
		}

		this.mDefaultTableModel = dm;

		return dm;
	}

	public void clear()
	{
		if (this.mDefaultTableModel != null)
		{
			this.mDefaultTableModel.clearAllRows();
		}

		this.removeAllColumns();

		this.refresh();
	}

	public void setColumnFont(Font f)
	{
		this.getTableHeader().setFont(f);
	}
	
	public final TableColumn getColumn(int index)
	{
		return this.getColumnModel().getColumn(index);
	}
	
	public void setColumnHorizontalAlignment(int alignment)
	{
		((DefaultTableCellRenderer)this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(alignment);
	}

	public void setColumnSize(int width)
	{
		Enumeration<TableColumn> columns = this.getColumnModel().getColumns();

		while (columns.hasMoreElements())
		{
			TableColumn tc = columns.nextElement();

			tc.setPreferredWidth(width);
		}
	}

	public void removeAllColumns()
	{
		Enumeration<TableColumn> eColumns = this.getColumnModel().getColumns();

		if (eColumns != null && eColumns.hasMoreElements())
		{
			CArrayList<TableColumn> list = new CArrayList<TableColumn>();

			while (eColumns.hasMoreElements())
			{
				list.add(eColumns.nextElement());
			}

			for (TableColumn tc : list)
			{
				this.removeColumn(tc);
			}
		}
	}

	public void clearColumns()
	{
		this.removeAllColumns();
	}

	public void insertColumn(int index, TableColumn tc)
	{
		int maxCol = this.getColumnCount();

		this.addColumn(tc);

		this.moveColumn(maxCol, index);
	}

	public void addColumns(List<String> names, int width)
	{
		for (String nm : names)
		{
			addColumn(nm, width);
		}
	}

	public TableColumn addColumn(String name, int width)
	{
		TableColumn tc = new TableColumn(0, width);
		
		tc.setHeaderValue(name);

		this.addColumn(tc);

		return tc;
	}

	public void refresh()
	{
		this.invalidate();

		this.repaint();
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		Component c = super.prepareRenderer(renderer, row, column);

		Color fc = this.mTextColor;
		
		Color bc = (!this.isAlternateRowColorOption()) ? this.mRowColor : ((row % 2 == 0) ? this.mRowColor : mAlternateRowColor);

		if (this.isFullRowSelectOption() && this.getSelectedRow() == row)
		{
			bc = this.mSelectedRowColor;

			fc = this.mSelectedTextColor;
		}
		else if (this.isCellSelected(row, column))
		{
			bc = this.mSelectedRowColor;

			fc = this.mSelectedTextColor;
		}

		c.setForeground(fc);

		c.setBackground(bc);

		return c;
	}

	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
	
	public final boolean isAlternateRowColorOption()
	{
		return CApplication.isBitSet(CJTableEx.ENABLE_ALTERNATE_ROW_COLOR_BIT, this.mOptions);
	}

	public final void setAlternateRowColorOption(boolean enableAltColor)
	{
		this.mOptions = (enableAltColor ? CApplication.setBit(CJTableEx.ENABLE_ALTERNATE_ROW_COLOR_BIT, this.mOptions) : CApplication.clearBit(CJTableEx.ENABLE_ALTERNATE_ROW_COLOR_BIT, this.mOptions));
	}
	
	public final boolean isFullRowSelectOption()
	{
		return CApplication.isBitSet(CJTableEx.ENABLE_FULL_ROW_SELECT_BIT, this.mOptions);
	}

	public final void setFullRowSelectOption(boolean enableFullRowSelect)
	{
		this.mOptions = (enableFullRowSelect ? CApplication.setBit(CJTableEx.ENABLE_FULL_ROW_SELECT_BIT, this.mOptions) : CApplication.clearBit(CJTableEx.ENABLE_FULL_ROW_SELECT_BIT, this.mOptions));
	}

	public final Color getAlternateRowColor()
	{
		return this.mAlternateRowColor;
	}

	public final void setAlternateRowColor(Color c)
	{
		this.mAlternateRowColor = c;
	}

	public final Color getRowColor()
	{
		return this.mRowColor;
	}

	public final void setRowColor(Color c)
	{
		this.mRowColor = c;
	}

	public final void setTextColor(Color c)
	{
		this.mTextColor = c;
	}

	public final Color getTextColor()
	{
		return this.mTextColor;
	}

	public final Color getSelectedRowColor()
	{
		return this.mSelectedRowColor;
	}

	public final void setSelectedRowColor(Color c)
	{
		this.mSelectedRowColor = c;
	}

	public final Color getSelectedTextColor()
	{
		return this.mSelectedTextColor;
	}

	public final void setSelectedTextColor(Color c)
	{
		this.mSelectedTextColor = c;
	}

	public final CDefaultTableModel getDefaultTableModel()
	{
		return this.mDefaultTableModel;
	}
}

class CTableCellRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 614373698047489664L;

	public CTableCellRenderer()
	{
		this.setHorizontalAlignment(CJLabel.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		return c;
	}
}
