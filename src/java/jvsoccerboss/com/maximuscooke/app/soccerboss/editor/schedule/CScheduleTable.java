package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.maximuscooke.app.soccerboss.CFixture;
import com.maximuscooke.app.soccerboss.CSchedule;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CGridList;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJTableEx;

@SuppressWarnings("serial")
public class CScheduleTable extends CJTableEx
{
	private CGridList<CScheduleCell> mGrid = new CGridList<CScheduleCell>();
	
	public CScheduleTable()
	{
		this.setAlternateRowColorOption(true);

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		

		this.setCellSelectionEnabled(true);
		
		this.setFullRowSelectOption(true);

		this.setFont(new Font("helvetica", Font.BOLD, 12));

		this.setColumnFont(new Font("helvetica", Font.BOLD, 18));
		
		this.setDefaultRenderer(CScheduleCell.class, new CFixtureDateRenderer() );
	}

	public CScheduleTable(int rowHeight, boolean bEnableCellSelection)
	{
		super(rowHeight, bEnableCellSelection);
	}
	
	public void setup(CSchedule sch)
	{
		this.clear();
		
		this.mGrid.clear();
		
		this.setupColumnHeadings(sch);
		
		this.setupSchedules(sch);
		
		this.init(this.mGrid);
		
		this.setColumnHorizontalAlignment(CJLabel.CENTER);

		this.setColumnSize(CScheduleEditor.COL_WIDTH);
		
		this.rebuildScheduleTable(sch);
	}
	
	private void setupColumnHeadings(CSchedule sch)
	{
		this.mGrid.clearHeader();
				
		CArrayList<String> headings = new CArrayList<String>(sch.getMaxClubs());
		
		headings.add("Date");
		
		int maxFixture = sch.getMaxFixturesPerDay();
		
		for(int i = 0; i < maxFixture; i++)
		{
			headings.add( String.format("Fixture %02d", i+1) );
		}
		
		this.mGrid.addColumnHeader(headings);
	}
	
	private final void rebuildScheduleTable(CSchedule sch)
	{
		this.setSelectedRowColor(CScheduleEditor.SELECTED_ROW_COLOR);

		this.getGrid().forEachCell( cell -> cell.setFixture(null) );
		
		if (sch != null)
		{
			CArrayList<CFixture> fixtures = sch.getFixtures();
			
			if (fixtures != null)
			{				
				for(CFixture f : fixtures)
				{
					if (f.isScheduled())
					{
						CArrayList<CScheduleCell> row = this.getCellRowAt(f.getDateTime());
						
						if (row != null)
						{
							for(CScheduleCell cell : row)
							{
								if (!cell.isScheduled() && !cell.isDateOnly())
								{
									cell.setFixture(f);
									
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public final void refresh(CSchedule sch)
	{
		this.rebuildScheduleTable(sch);
		
		this.refresh();
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		Component c = super.prepareRenderer(renderer, row, column);

		CScheduleCell fxt = (CScheduleCell) this.getModel().getValueAt(row, column);

		if (fxt != null)
		{
			if(fxt.isDateOnly())
			{
				c.setForeground(Color.GRAY);
			}
			else if(fxt.isScheduled())
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
		}		
		
		return c;
	}
	
	private void setupSchedules(CSchedule sch)
	{		
		CGregorianCalendar end = new CGregorianCalendar(sch.getEndDate());

		end.add(CGregorianCalendar.DAY_OF_MONTH, 1);
		
		setupSchedules(sch, new CGregorianCalendar( sch.getStartDate() ), end);
		
	}
	
	private void setupSchedules(CSchedule sch, CGregorianCalendar start, CGregorianCalendar end )
	{
		int maxFixtures = sch.getMaxFixturesPerDay();
		
		while (start.compareToDateTime( end ) <= 0)
		{
			CArrayList<CScheduleCell> newRow = new CArrayList<CScheduleCell>();

			newRow.add(new CScheduleCell(start, true));

			for (int j = 0; j < maxFixtures; j++)
			{
				newRow.add(new CScheduleCell(start, false));
			}

			this.mGrid.addRow(newRow);
						
			start.add(CGregorianCalendar.DAY_OF_MONTH, 1);
		}
	}
	
	public final CGregorianCalendar getSelectedDate()
	{
		int selRow = this.getSelectedRow();
		
		if (selRow >= 0)
		{
			return this.mGrid.getObjectAt(selRow, 0).getDateTime();
		}
		
		return null;
	}
		
	public final CScheduleCell getCellAt(int row, int col)
	{
		if (row >= 0 && col >= 0)
		{
			return (CScheduleCell) this.getValueAt(row, col);
		}

		return null;
	}
	
	public final CScheduleCell getSelectedCell()
	{
		return getCellAt(this.getSelectedRow(), this.getSelectedColumn());
	}
	
	public final CArrayList<CScheduleCell> getSelectedCellRow()
	{
		int selRow = this.getSelectedRow();
		
		if (selRow >= 0)
		{
			return getCellRowAt(selRow);
		}
		
		return null;
	}
	
	public int getCellRowIndex(CGregorianCalendar date)
	{
		for(int rowIndex = 0; rowIndex < this.mGrid.getSize(); rowIndex++)
		{
			CArrayList<CScheduleCell> row = this.mGrid.getAt(rowIndex);
			
			CGregorianCalendar dt = row.getAt(0).getDateTime();
			
			if (dt.getYear() == date.getYear() && 
				 dt.getMonth() == date.getMonth() && 
				 dt.getDayOfMonth() == date.getDayOfMonth())
			{
				return rowIndex;
			}
		}
		return -1;
	}
	
	public final CArrayList<CScheduleCell> getCellRowAt(CGregorianCalendar date)
	{
		return getCellRowAt( getCellRowIndex(date) );
	}

	public final CArrayList<CScheduleCell> getCellRowAt(int row)
	{
		if (row >= 0)
		{
			return this.mGrid.getAt(row);
		}

		return null;
	}
		
	public final CGridList<CScheduleCell> getGrid()
	{
		return this.mGrid;
	}
}

class CFixtureDateRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 614373698047489664L;

	public CFixtureDateRenderer()
	{
		this.setHorizontalAlignment(CJLabel.CENTER);
	}
}
