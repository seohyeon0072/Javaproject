

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Design_ScrollBarUI extends BasicScrollBarUI {
	private static final int thumbWidth = 7;
	private static final float opaque = 0.8f;
	private static final Color color = new Color(0xa0a0a0);
	
	@Override
	protected void configureScrollBarColors() {
		setThumbBounds(0, 0, 3, 10);
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		c.setPreferredSize(new Dimension(thumbWidth, thumbWidth));
		return super.getPreferredSize(c);
	}

	public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		Graphics2D g2 = (Graphics2D) g;
		GradientPaint gp = null;
		if (this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {
			gp = new GradientPaint(0, 0, color, 0, trackBounds.height, color);
		}
		if (this.scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
			gp = new GradientPaint(0, 0, color, trackBounds.width, 0, color);
		}
		g2.setPaint(gp);
		g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
		if (trackHighlight == BasicScrollBarUI.DECREASE_HIGHLIGHT)
			this.paintDecreaseHighlight(g);
		if (trackHighlight == BasicScrollBarUI.INCREASE_HIGHLIGHT)
			this.paintIncreaseHighlight(g);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		g.translate(thumbBounds.x, thumbBounds.y);
		g.setColor(color);
		g.drawRoundRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 1, 5, 5);
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
		g2.setPaint(
				new GradientPaint(c.getWidth() / 2, 1, color, c.getWidth() / 2, c.getHeight(), color));
		g2.fillRoundRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 1, 5, 5);
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton();
		button.setBorderPainted(true);
		button.setContentAreaFilled(true);
		button.setBorder(null);
		return button;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton();
		button.setBorderPainted(true);
		button.setContentAreaFilled(true);
		button.setFocusable(false);
		button.setBorder(null);
		return button;
	}
}