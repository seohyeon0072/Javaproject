package Lotto1;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageLabel extends JLabel{
	private int order;
	public ImageLabel (ImageIcon icon, int order) {
		super(icon, CENTER);
		this.order = order;
	}
	public int getOrder(){
		return order;
	}
}
