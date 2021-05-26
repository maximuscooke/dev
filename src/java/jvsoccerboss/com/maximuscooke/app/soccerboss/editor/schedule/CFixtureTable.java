package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.maximuscooke.app.soccerboss.CFixture;
import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CGridList;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJTableEx;

@SuppressWarnings("serial")
public class CFixtureTable extends CJTableEx
{
	private CGridList<CFixtureCell> mGrid = new CGridList<CFixtureCell>();
	
	public CFixtureTable()
	{
		this.setAlternateRowColorOption(true);
				
		this.setSelectedRowColor(CScheduleEditor.SELECTED_ROW_COLOR);

		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.setCellSelectionEnabled(true);

		this.setFont(new Font("helvetica", Font.BOLD, 12));

		this.setColumnFont(new Font("helvetica", Font.BOLD, 18));

		this.setDefaultRenderer(CFixtureCell.class, new CFixtureRenderer());
	}

	public CFixtureTable(int rowHeight, boolean bEnableCellSelection)
	{
		super(rowHeight, bEnableCellSelection);
	}
	
	public void setup(CSchedule sch)
	{
		this.clear();
		
		this.mGrid.clear();
		
		this.setupColumnHeadings(sch);
		
		this.setupFixtureCells(sch);
				
		this.init(this.mGrid);
		
		this.setColumnHorizontalAlignment(CJLabel.CENTER);

		this.setColumnSize(CScheduleEditor.COL_WIDTH);
	}
	
	private void setupFixtureCells(CSchedule sch)
	{		
		int maxClubs = sch.getMaxClubs();

		for(int i = 1; i <= maxClubs; i++)
		{
			CArrayList<CFixtureCell> newRow = new CArrayList<CFixtureCell>();
			
			for(int j = 1; j <= maxClubs; j++)
			{
				CFixture f = sch.findFixture(j, i);
				
				newRow.add( new CFixtureCell( f ) );
			}
			
			this.mGrid.addRow(newRow);
		}
	}
	
	private void setupColumnHeadings(CSchedule sch)
	{
		this.mGrid.clearHeader();
		
		CArrayList<String> headings = new CArrayList<String>(sch.getMaxClubs());
		
		for(int i = 0; i < sch.getMaxClubs(); i++)
		{
			headings.add( String.format("Club %02d", i+1) );
		}
		
		this.mGrid.addColumnHeader(headings);
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		Component c = super.prepareRenderer(renderer, row, column);

		CFixtureCell fxt = (CFixtureCell) this.getModel().getValueAt(row, column);

		if (fxt != null)
		{
			if (fxt.isDummy())
			{
				c.setForeground(Color.GRAY);
			}
			else if (fxt.isScheduled())
			{
				if (fxt.getFixture().getStatus() == CFixture.FXT_STATE_VALID)
				{
					c.setForeground(CScheduleEditor.SCHEDULED_COLOR);
				} 
				else
				{
					c.setForeground(CScheduleEditor.ERROR_COLOR);
				}
			}
			else
			{
				c.setForeground(CScheduleEditor.NOT_SCHEDULED_COLOR);
			}

		}
		
		return c;
	}

	public final CFixtureCell getCellAt(int row, int col)
	{
		if (row >= 0 && col >= 0)
		{
			return (CFixtureCell) this.getValueAt(row, col);
		}

		return null;
	}

	public final CFixtureCell getSelectedCell()
	{
		return getCellAt(this.getSelectedRow(), this.getSelectedColumn());
	}

}

class CFixtureRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 614373698047489664L;

	public CFixtureRenderer()
	{
//		this.setHorizontalAlignment(CJLabel.CENTER);
	}
}
