package com.maximuscooke.lib.common.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.function.Predicate;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.maximuscooke.lib.common.IMutableIconTreeNode;
import com.maximuscooke.lib.common.IWalk;
import com.maximuscooke.lib.common.collections.CArrayList;

@SuppressWarnings("serial")
public class CJTreeEx extends CJTree
{
	public static final int OPT_RELOAD = (1 << 0);
	public static final int OPT_SAVE_EXPANDED_STATE = (1 << 1);
	public static final int OPT_SORT = (1 << 2);
	public static final int OPT_REMOVE_ALL = (1 << 3);
	public static final int OPT_SAVE_SELECTED_NODES = (1<<4);

	private static final Comparator<CDefaultMutableTreeNode> mDefaultComparer;

	private static CDefaultMutableTreeNode mNode = null;

	private CDefaultMutableTreeNode mRootNode = null;
	private DefaultTreeModel mDefaultTreeModel = null;

	static
	{
		mDefaultComparer = new Comparator<CDefaultMutableTreeNode>() {
			@Override
			public int compare(CDefaultMutableTreeNode o1, CDefaultMutableTreeNode o2)
			{
				return o1.getUserObject().toString().compareTo(o2.getUserObject().toString());
			}
		};
	}

	{
		this.setCellRenderer(new CTreeCellRenderer());
	}
	
	public CJTreeEx(Object root)
	{
		this.init(root);
	}
	
	public void init(Object root)
	{
		this.mRootNode = new CDefaultMutableTreeNode(root);

		this.mDefaultTreeModel = new DefaultTreeModel(mRootNode);

		this.setModel(mDefaultTreeModel);		
	}

	public CDefaultMutableTreeNode addTreeNode(Object o)
	{
		return this.addTreeNode(o, null, 0);
	}
	
	public CDefaultMutableTreeNode addTreeNode(Object o, int options)
	{
		return this.addTreeNode(o, (((options & CJTreeEx.OPT_SORT) != 0) ? mDefaultComparer : null), options);
	}

	public CDefaultMutableTreeNode addTreeNode(Object o, Comparator<CDefaultMutableTreeNode> comparer, int options)
	{
		return addTreeNode(this.mRootNode, o, comparer, options);
	}

	public CDefaultMutableTreeNode addTreeNode(CDefaultMutableTreeNode parent, Object o)
	{
		return this.addTreeNode(parent, o, 0);
	}

	public CDefaultMutableTreeNode addTreeNode(CDefaultMutableTreeNode parent, Object o, int options)
	{
		return this.addTreeNode(parent, o, null, options);
	}

	public CDefaultMutableTreeNode addTreeNode(CDefaultMutableTreeNode parent, Object o, Comparator<CDefaultMutableTreeNode> comparer, int options)
	{
		CDefaultMutableTreeNode node = new CDefaultMutableTreeNode(o);

		parent.add(node);

		resync(options, comparer);
		
		return node;
	}
	
	public CDefaultMutableTreeNode removeTreeNode(Predicate<CDefaultMutableTreeNode> func, int options)
	{
		return this.removeTreeNode(func, this.mRootNode, null, options);
	}
	
	public CDefaultMutableTreeNode removeTreeNode(Predicate<CDefaultMutableTreeNode> func, CDefaultMutableTreeNode root, Comparator<CDefaultMutableTreeNode> comparer, int options)
	{
		return removeTreeNode(findTreeNode(func, root), comparer, options);
	}
	
	public CDefaultMutableTreeNode removeTreeNode(CDefaultMutableTreeNode tn, int options)
	{
		return this.removeTreeNode(tn, null, options);
	}
	
	public CDefaultMutableTreeNode removeTreeNode(CDefaultMutableTreeNode tn, Comparator<CDefaultMutableTreeNode> comparer, int options)
	{
		if (tn != null)
		{
			this.mDefaultTreeModel.removeNodeFromParent(tn);
			
			resync(options, comparer);
		}
		
		return tn;
	}
	
	public CDefaultMutableTreeNode findTreeNode(Predicate<CDefaultMutableTreeNode> func)
	{
		return this.findTreeNode(func, this.mRootNode);
	}
	
	public CDefaultMutableTreeNode findTreeNode(Predicate<CDefaultMutableTreeNode> func, CDefaultMutableTreeNode root)
	{
		CJTreeEx.mNode = null;
		
		this.walk( tn -> { if ( func.test(tn) ) { CJTreeEx.mNode = tn; return false; } return true; }, this.mDefaultTreeModel, root );
		
		return CJTreeEx.mNode;
	}
	 	
	private void walk(IWalk<CDefaultMutableTreeNode> func)
	{
		this.walk(func, this.mDefaultTreeModel);
	}
	
	private void walk(IWalk<CDefaultMutableTreeNode> func, DefaultTreeModel model)
	{
		this.walk(func, model, this.mRootNode);
	}
	
	private void walk(IWalk<CDefaultMutableTreeNode> func, DefaultTreeModel model, CDefaultMutableTreeNode root)
	{
		if (func.walk(root))
		{
			this.walkTree(func, model, root);			
		}
	}

	private void walkTree(IWalk<CDefaultMutableTreeNode> fnc, DefaultTreeModel model, Object o)
	{
		int cc = model.getChildCount(o);

		for (int i = 0; i < cc; i++)
		{
			CDefaultMutableTreeNode child = (CDefaultMutableTreeNode) model.getChild(o, i);
			
			if (fnc.walk(child))
			{
				walkTree(fnc, model, child);				
			}
			else
			{
				break;
			}
		}
	}

	public void reload()
	{
		reload(0);
	}

	public void reload(int options)
	{
		this.reload(options, null);
	}

	public void reload(int options, Comparator<CDefaultMutableTreeNode> comparer)
	{
		this.resync((options | CJTreeEx.OPT_RELOAD), comparer);
	}

	public void sort()
	{
		sort(0);
	}

	public void sort(int options)
	{
		this.sort(options, null);
	}

	public void sort(int options, Comparator<CDefaultMutableTreeNode> comparer)
	{
		resync((options | CJTreeEx.OPT_SORT), comparer);
	}

	private void resync(int options, Comparator<CDefaultMutableTreeNode> comparer)
	{
		ArrayList<TreePath> paths = null;
		
		TreePath[] selpaths = null;

		if ((options & CJTreeEx.OPT_SAVE_EXPANDED_STATE) != 0)
		{
			paths = this.saveExpandedState();
		}
		
		if ((options & CJTreeEx.OPT_SAVE_SELECTED_NODES) != 0)
		{
			selpaths = this.getSelectionPaths();
		}

		if ((options & CJTreeEx.OPT_SORT) != 0)
		{
			sort((comparer == null) ? CJTreeEx.mDefaultComparer : comparer);
		}

		if ((options & CJTreeEx.OPT_RELOAD) != 0)
		{
			mDefaultTreeModel.reload();
		}
		
		if (paths != null)
		{
			this.restoreExpandedState(paths);
		}
		
		if(selpaths != null && selpaths.length > 0)
		{
			this.setSelectionPaths(selpaths);
		}
	}

	private void sort(Comparator<CDefaultMutableTreeNode> comparer)
	{		
		this.walk( tn -> { if( !tn.isLeaf() ) { tn.sort(comparer); } ; return true; } ); 
	}

	public void removeAllTreeNodes()
	{
		this.removeAllTreeNodes(CJTreeEx.OPT_RELOAD);
	}

	public void removeAllTreeNodes(int options)
	{
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();

		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

		root.removeAllChildren();

		resync(options, null);
	}

	public CArrayList<CDefaultMutableTreeNode> toList(CDefaultMutableTreeNode parentNode)
	{
		CArrayList<CDefaultMutableTreeNode> list = new CArrayList<CDefaultMutableTreeNode>();
		
		this.walk( tn -> { list.add(tn); return true; } ); 

		return list;
	}

	public CArrayList<CDefaultMutableTreeNode> toList()
	{
		return toList(this.mRootNode);
	}

	public final CDefaultMutableTreeNode getRootNode()
	{
		return this.mRootNode;
	}

	public final boolean rootHasChildren()
	{
		return !this.mRootNode.isLeaf();
	}

	public void restoreExpandedState(ArrayList<TreePath> expPaths)
	{
		if (expPaths != null)
		{
			for (TreePath tp : expPaths)
			{
				this.expandPath(tp);
			}
		}
	}

	public ArrayList<TreePath> saveExpandedState()
	{
		Enumeration<TreePath> expandEnum = this.getExpandedDescendants(new TreePath(this.mRootNode.getPath()));

		ArrayList<TreePath> paths = null;

		if (expandEnum != null)
		{
			paths = new ArrayList<>();

			while (expandEnum.hasMoreElements())
			{
				paths.add(expandEnum.nextElement());
			}
		}

		return paths;
	}
}

@SuppressWarnings("serial")
class CTreeCellRenderer extends DefaultTreeCellRenderer
{
	CTreeCellRenderer()
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

			if (selected)
			{
				label.setIcon(tn.getSelectedIcon());
			} 
			else if (expanded)
			{
				label.setIcon(tn.getExpandedIcon());
			} 
			else if (leaf)
			{
				label.setIcon(tn.getLeafIcon());
			} 
			else
			{
				label.setIcon(tn.getDefaultIcon());
			}
		}

		return label;
	}

}
