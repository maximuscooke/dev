package com.maximuscooke.lib.common.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class CJCheckBoxList<T extends CCheckBoxListItem> extends CJList<T>
{
	public CJCheckBoxList()
	{
		init();
	}

	public CJCheckBoxList(ListModel<T> dataModel)
	{
		super(dataModel);

		init();
	}

	public CJCheckBoxList(T[] listData)
	{
		super(listData);

		init();
	}

	public CJCheckBoxList(Vector<T> listData)
	{
		super(listData);

		init();
	}

	private final void init()
	{
		this.setCellRenderer(new CheckboxListRenderer());

		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		addMouseListener();
	}
	
	private void addMouseListener()
	{
		this.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mousePressed(MouseEvent event)
			{
				CJCheckBoxList<CCheckBoxListItem> list = (CJCheckBoxList<CCheckBoxListItem>) event.getSource();
				
				int index = list.locationToIndex(event.getPoint());
				
				CCheckBoxListItem item = (CCheckBoxListItem) list.getModel().getElementAt(index);

				item.setSelected(!item.isSelected());

				list.repaint(list.getCellBounds(index, index));
			}
		});
	}
}

class CheckboxListRenderer extends CJCheckBox implements ListCellRenderer<CCheckBoxListItem>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6626033994864248658L;

	@Override
	public Component getListCellRendererComponent(JList<? extends CCheckBoxListItem> list, CCheckBoxListItem value, int index, boolean isSelected, boolean cellHasFocus)
	{
		setEnabled(list.isEnabled());

		setSelected(value.isSelected());

		setFont(list.getFont());

		setBackground(list.getBackground());

		setForeground(list.getForeground());

		setText(value.toString());

		return this;
	}
}