 
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;


import java.awt.Color;

public class Lotto_start extends JFrame {
	private JButton btn;
	private Integer[] sheet = { 1, 2, 3, 4, 5 }; // 종이 몇장
	private JComboBox sheetBox; // 장수

	private JLabel img;
	private Lotto_main select;

	public Lotto_start() {
		init();
		setDisplay();
		addListener();
		showFrame();
		 
	}

	private void init() {
		ImageIcon image = new ImageIcon("image/logo.png");
		img = new JLabel(image); // 로고

		sheetBox = new JComboBox(sheet);
		/*
		 * sheetBox.addItem(1); sheetBox.addItem(2);
		 */
		btn = new JButton("구매");

	}

	private void setDisplay() {
		JPanel p1 = new JPanel();
		p1.add(img);
		JPanel p = new JPanel();
		p.add(sheetBox);
		p.add(btn);
		
		add(p1, BorderLayout.NORTH);
		add(p, BorderLayout.CENTER);
	}

	private void addListener() {

		btn.addActionListener((e) -> {

			new Lotto_main(Lotto_start.this, (Integer) sheetBox.getSelectedItem());
		});

	}

	private void showFrame() {
		 
		setTitle("Main");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Lotto_start();
	}
}
