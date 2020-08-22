 
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Lotto_main extends JFrame {
	private int number;
	private Lotto_panel[] pnl;
	private JButton jbtn;
	private ArrayList<ArrayList<Integer>> array;

	public Lotto_main(Lotto_start owner, int number) {
		this.number = number;
		pnl = new Lotto_panel[number];
		init();
		setDisplay();
		addListener();
		showFrame();
	}

	public Lotto_main() {
	}

	private void init() {
		JPanel pnlCenter = new JPanel(new GridLayout(1, 0));
		array = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < number; i++) {
			pnl[i] = new Lotto_panel();
			pnlCenter.add(pnl[i]);
		}

		jbtn = new JButton("결과");
		JPanel jpnl = new JPanel();
		add(pnlCenter, BorderLayout.NORTH);
		add(jpnl.add(jbtn), BorderLayout.SOUTH);
	}

	private void setDisplay() {
	}

	private void addListener() {
		// 결과버튼 눌렀을 때
		jbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					for (int i = 0; i < number; i++) {
						array.add(pnl[i].getList());
					}
					System.out.println(array);
					new Lotto_result(Lotto_main.this);
					
				} catch (NullPointerException e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null,"빈 값이 있습니다. 수동일 경우 저장을 눌러주세요");
					System.out.println("null 값 ");
					array.clear();
				}
 
			}
		});

	}

	 

	private void showFrame() {
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	public Lotto_panel[] getPnl() {
		return pnl;
	}

	public int getNumber() {
		return number;
	}

}