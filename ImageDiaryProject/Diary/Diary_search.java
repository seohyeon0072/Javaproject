 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Diary_search extends JFrame {
	private JLabel lbl;
	private JRadioButton[] rb;
	Vector<Information> searchvector;
	JButton btn;
	private CalendarStart calendar;

	public Diary_search(Vector<Information> searchvector) {
		this.searchvector = searchvector; // �˻����
		init();
		addListener();
		showFrame();
	}

	private void init() {
		JPanel rbpnl = new JPanel(new GridLayout(0, 1));
		int k = searchvector.size();
		lbl = new JLabel(" ���� ��  <<" + k + "��>> �� �˻������ ã�ҽ��ϴ�.", JLabel.CENTER);

		rb = new JRadioButton[searchvector.size()]; // ������ư �迭
		ButtonGroup gb = new ButtonGroup();
		for (int i = 0; i < searchvector.size(); i++) {
			rb[i] = new JRadioButton(" ��¥ : " + searchvector.get(i).getDate()+" \n "
					+" ���� : " + searchvector.get(i).getTitle());
			rbpnl.add(rb[i]);
			gb.add(rb[i]);
		}
		
		btn = new JButton("����");
		
		rbpnl.setBorder(new LineBorder(Color.BLACK));
		
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(lbl);
		pnlNorth.setBackground(Color.WHITE);
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btn);
		pnlSouth.setBackground(Color.WHITE);
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(Color.WHITE);
		pnlCenter.add(pnlNorth, BorderLayout.NORTH);
		pnlCenter.add(rbpnl, BorderLayout.CENTER);
	
		pnlCenter.add(pnlSouth, BorderLayout.SOUTH);
		pnlCenter.setBorder(new EmptyBorder(10,10,10,10));
		add(pnlCenter);
	}

	private void setDisplay() {

	}

	private void addListener() {

		btn.addActionListener((e) -> {

			Object obj = e.getSource();
			if (btn == obj) {
				for (int i = 0; i < rb.length; i++) {
					if (rb[i].isSelected()) {
						dispose();
						new Diary_read(calendar, searchvector.get(i));
					}

				}
			}

		});
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
				new CalendarStart();
				
			}
		});
	}

	private void showFrame() {
		setTitle("�˻�");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
