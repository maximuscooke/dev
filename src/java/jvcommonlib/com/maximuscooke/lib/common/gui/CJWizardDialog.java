package com.maximuscooke.lib.common.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CHashMap;

@SuppressWarnings("serial")
public class CJWizardDialog extends CJDialog
{
	public static final int WIZARD_NEXT_BTN = 10;
	public static final int WIZARD_PREVIOUS_BTN = 20;
	public static final int WIZARD_FINISH_BTN = 30;

	private CArrayList<CJWizardPage> mWizardPages = new CArrayList<CJWizardPage>();

	private static int DEFAULT_WIDTH = 600;
	private static int DEFAULT_HEIGHT = 400;

	private CJButton mPrevBtn = null;
	private CJButton mNextBtn = null;
	private CJButton mFinishBtn = null;

	private CJLabel mImageLbl = null;
	private CJPanel mNorthPanel = null;
	private CJPanel mSouthPanel = null;

	private CJLabel mTitleLbl = null;
	private CJLabel mDescLbl = null;

	private CJPanel mPagePanel = null;

	private int mActivePageIndex = 0;

	private static Font mDescFont = new Font("Serif", Font.PLAIN, 13);
	private static Font mTitleTextFont = new Font("Serif", Font.BOLD, 15);

	private CHashMap<String, Object> mCollection = new CHashMap<String, Object>();

	public boolean mShowStageIndicator = true;
	public boolean mCloseOnFinish = true;

	public CJWizardDialog(Frame owner, String title)
	{
		this(owner, title, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public CJWizardDialog(Frame owner, String title, ImageIcon image)
	{
		this(owner, title, DEFAULT_WIDTH, DEFAULT_HEIGHT, image);
	}

	public CJWizardDialog(Frame owner, String title, int width, int height)
	{
		this(owner, title, width, height, null);
	}

	public CJWizardDialog(Frame owner, String title, int width, int height, ImageIcon image)
	{
		this(owner, title, width, height, image, true, true);
	}

	public CJWizardDialog(Frame owner, String title, int width, int height, ImageIcon image, boolean modal, boolean bCloseOnFinish)
	{
		super(owner, title, modal);

		mCloseOnFinish = bCloseOnFinish;

		this.setLayout(new BorderLayout());

		this.setSize(width, height);

		this.setLocationRelativeTo(owner);

		this.add((mNorthPanel = this.setupNorthPanel(width, height, image)), BorderLayout.NORTH);

		this.add((mSouthPanel = this.setupSouthPanel(width, height)), BorderLayout.SOUTH);

		this.mPagePanel = new CJPanel();

		this.mPagePanel.setLayout(new BorderLayout());

		this.mPagePanel.setBackground(Color.RED);

		this.add(mPagePanel, BorderLayout.CENTER);

		this.setResizable(false);

		this.setBackground(Color.WHITE);
	}

	private CJPanel setupNorthPanel(int width, int height, ImageIcon image)
	{
		CJPanel p = new CJPanel();

		int topEdge = 4;
		
		int iconSize = 64;

		p.setLayout(null);

		p.setPreferredSize(width, 64);

		this.mTitleLbl = new CJLabel("Title");

		this.mTitleLbl.setFont(CJWizardDialog.mTitleTextFont);

		this.mTitleLbl.setBounds(8, topEdge, width - (iconSize + 8), 20);

		p.add(this.mTitleLbl);

		this.mDescLbl = new CJLabel("Description");

		this.mDescLbl.setFont(CJWizardDialog.mDescFont);

		this.mDescLbl.setBounds(20, 24, width - (iconSize + 16), 20);

		p.add(mDescLbl);

		if (image != null)
		{
			this.mImageLbl = new CJLabel(image);

			this.mImageLbl.setBounds(width - 64, topEdge, iconSize, iconSize);

			p.add(mImageLbl);
		}

		return p;
	}

	private CJPanel setupSouthPanel(int width, int height)
	{
		int btnWidth = 100;
		int btnHeight = 28;
		int top = 4;

		CJPanel p = new CJPanel();

		p.setLayout(null);

		p.setPreferredSize(width, 40);

		this.mPrevBtn = new CJButton("Previous");

		this.mPrevBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				onPrevious();
			}
		});

		this.mPrevBtn.setEnabled(false);

		this.mPrevBtn.setBounds(4, top, btnWidth, btnHeight);

		p.add(this.mPrevBtn);

		this.mNextBtn = new CJButton("Next");

		this.mNextBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				onNext();
			}
		});

		this.mNextBtn.setEnabled(false);

		int nxtBtnXPos = (width - btnWidth - 4);

		this.mNextBtn.setBounds(nxtBtnXPos, top, btnWidth, btnHeight);

		p.add(this.mNextBtn);

		this.mFinishBtn = new CJButton("Finish");

		this.mFinishBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				onFinish();
			}
		});

		this.mFinishBtn.setEnabled(false);

		this.mFinishBtn.setVisible(false);

		this.mFinishBtn.setBounds(nxtBtnXPos, top, btnWidth, btnHeight);

		p.add(this.mFinishBtn);

		return p;
	}

	private CJWizardPage setPageIndex(int index)
	{
		if (index <= 0)
		{
			this.mActivePageIndex = 0;
		} 
		else if (index >= this.mWizardPages.size())
		{
			this.mActivePageIndex = this.mWizardPages.size() - 1;
		} 
		else
		{
			this.mActivePageIndex = index;
		}

		return this.mWizardPages.get(mActivePageIndex);
	}

	private String doStageString()
	{
		CJWizardPage pg = this.mWizardPages.get(this.mActivePageIndex);

		StringBuilder sb = new StringBuilder();

		sb.append(pg.getTitle());

		sb.append(" (Stage ");

		sb.append(this.mActivePageIndex + 1);

		sb.append(" of ");

		sb.append(this.mWizardPages.size());

		sb.append(")");

		return sb.toString();
	}

	private boolean isFirstPage(CJWizardPage pg)
	{
		return (this.mWizardPages.get(0) == pg);
	}

	private boolean isLastPage(CJWizardPage pg)
	{
		return (this.mWizardPages.get((this.mWizardPages.size() - 1)) == pg);
	}

	private void doActivatePage(CJWizardPage pg)
	{
		this.mPagePanel.setVisible(false);

		this.mPagePanel.removeAll();

		this.mPagePanel.add(pg, BorderLayout.CENTER);

		this.mPagePanel.setVisible(true);

		this.mPagePanel.revalidate();

		this.mPagePanel.repaint();
	}

	private void setActivePage(CJWizardPage pg)
	{
		this.doActivatePage(pg);

		this.mTitleLbl.setText((getShowStageIndicator() == true) ? doStageString() : pg.getTitle());

		this.mDescLbl.setText(pg.getDescription());

		doButtons(pg);

		if (getShowStageIndicator())
		{
			doStageString();
		}
	}

	private void doButtons(CJWizardPage pg)
	{
		boolean isLastPage = this.isLastPage(pg);

		boolean isFirstPage = this.isFirstPage(pg);

		this.mPrevBtn.setEnabled(!isFirstPage);

		this.mNextBtn.setVisible(!isLastPage);

		this.mNextBtn.setEnabled((isLastPage == true) ? false : pg.doButtonValidation(this, CJWizardDialog.WIZARD_NEXT_BTN));

		this.mFinishBtn.setVisible(isLastPage);

		this.mFinishBtn.setEnabled((isLastPage == false) ? false : pg.doButtonValidation(this, CJWizardDialog.WIZARD_FINISH_BTN));
	}

	private void onNext()
	{
		CJWizardPage activePage = this.mWizardPages.get(this.mActivePageIndex);

		activePage.onNextOrFinish(this, mCollection);

		this.setActivePage(this.setPageIndex(this.mActivePageIndex + 1));
	}

	private void onPrevious()
	{
		CJWizardPage activePage = this.mWizardPages.get(this.mActivePageIndex);

		activePage.onPrevious(this, mCollection);

		this.setActivePage(this.setPageIndex(this.mActivePageIndex - 1));
	}

	private void onFinish()
	{
		CJWizardPage activePage = this.mWizardPages.get(this.mActivePageIndex);

		this.setActivePage(activePage);

		activePage.onNextOrFinish(this, mCollection);

		if (this.mCloseOnFinish)
		{
			this.dispose();
		}
	}

	public void validateWizardPage(CJWizardPage pg)
	{
		this.doButtons(pg);
	}

	public void addWizardPage(CJWizardPage pg)
	{
		CJWizardPage[] pages = { pg };

		this.addWizardPages(pages);
	}

	public void addWizardPages(CJWizardPage[] pages)
	{
		if (pages != null)
		{
			for (CJWizardPage pg : pages)
			{
				pg.setWizardParent(this);

				this.mWizardPages.add(pg);

				pg.setVisible(true);
			}

			if (this.mWizardPages.size() > 0)
			{
				this.setActivePage(this.setPageIndex(0));
			}
		}
	}

	@Override
	public void setBackground(Color bgColor)
	{
		super.setBackground(bgColor);

		if (this.mNorthPanel != null)
		{
			this.mNorthPanel.setBackground(bgColor);
		}

		if (this.mSouthPanel != null)
		{
			this.mSouthPanel.setBackground(bgColor);
		}
	}

	public final int getWizardPageCount()
	{
		return this.mWizardPages.size();
	}

	public final void setShowStageIndicator(boolean bState)
	{
		this.mShowStageIndicator = bState;
	}

	public final boolean getShowStageIndicator()
	{
		return this.mShowStageIndicator;
	}

	/**
	 * Returns a CJWizardPage object from zero based index
	 * 
	 * @param index position of object
	 * @return CJWizardPage object
	 */
	public CJWizardPage getWizardPageAt(int index)
	{
		return this.mWizardPages.get(index);
	}

}
