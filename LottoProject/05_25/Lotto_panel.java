 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Lotto_panel extends JPanel {
	/*
	 * JPanel pnl; JPanel pnl1; JButton btn; JButton autobtn; JCheckBox[] chBox;
	 * ArrayList<Integer> list; Lotto_main main;
	 */
	public static final Dimension LBLNUM_SIZE = new Dimension(20, 20);
	public static final Dimension PNLROUND_SIZE = new Dimension(200, 400);

	JPanel pnlMiddle; // 체크박스 담는 패널
	JPanel pnlSouth; // 자동+수동+저장버튼 담는 패널

	JButton btnSave; // 저장 버튼
	JButton btnResult; // 결과 버튼
	JRadioButton rbtnAuto; // 자동버튼
	JRadioButton rbtnManu; // 수동버튼
	JRadioButton rbtnReset; // 리셋버튼

	JCheckBox[] chBox;
	ArrayList<Integer> list;
	Lotto_main main;
	private int num; //번호
	//

	public Lotto_panel() {
		
		chBox = new JCheckBox[45];

		// 체크박스
		JPanel pnlchk = new JPanel(new GridLayout(9, 5));
		
		
		for (int j = 0; j < 45; j++) {
			chBox[j] = new JCheckBox(String.valueOf(j + 1));
			pnlchk.add(chBox[j]);
		}
		pnlchk.setBorder(new TitledBorder(new LineBorder(Color.MAGENTA, 2), "Game "));
		
		JPanel pnl = new JPanel(new BorderLayout());

		pnlMiddle = new JPanel();

		pnlMiddle.add(pnlchk);
 
		pnlSouth = new JPanel();

		btnSave = new JButton("저장");
		rbtnAuto = new JRadioButton("자동");
		rbtnManu = new JRadioButton("수동", true);
		rbtnReset = new JRadioButton("리셋");

		ButtonGroup group = new ButtonGroup();
		group.add(rbtnAuto);
		group.add(rbtnManu);
		group.add(rbtnReset);

		pnlSouth.add(rbtnAuto);
		pnlSouth.add(rbtnManu);
		pnlSouth.add(rbtnReset);
		pnlSouth.add(btnSave);

		pnl.add(pnlMiddle, BorderLayout.CENTER);
		pnl.add(pnlSouth, BorderLayout.SOUTH);

		add(pnl, BorderLayout.CENTER); // 체크박스

		addListener();
 
	}

	private void addListener() {
		main = new Lotto_main();
		Set<Integer> temp = new HashSet<Integer>();
		 
		btnSave.addActionListener(new ActionListener() {
			int count = 0;

			@Override
			public void actionPerformed(ActionEvent e) {

				Object cmd = e.getSource();

				if (btnSave == cmd) {
					for (int i = 0; i < 45; i++) {
						if (chBox[i].isSelected()) {
							temp.add(i + 1);
							count++;
						}
					}
				}
				

				if (count < 6) {
					JOptionPane.showMessageDialog(null, "체크한 개수가 6개 미만입니다 체크해 주세요");
					count = 0;
					for (int i = 0; i < 45; i++) {
						chBox[i].setSelected(false);
						temp.clear();
					}

				} else if (count > 6) {
					JOptionPane.showMessageDialog(null, "체크한 개수가 6개 이상입니다");
					count = 0;
					for (int i = 0; i < 45; i++) {
						chBox[i].setSelected(false);
						temp.clear();
					}

				}else{
					list = new ArrayList<Integer>(temp);
					Collections.sort(list);
				}

			}

		});
		//수동
		rbtnManu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < 45; i++) {
					chBox[i].setEnabled(true);
				}
				 
				MyLottoNums mynums = new MyLottoNums();
				Integer[] number = mynums.getNums();
				ArrayList<Integer> list = new ArrayList<>(Arrays.asList(number));
				for (int i = 0; i < list.size(); i++) {
					int num = list.get(i);
					System.out.println(num);
					chBox[num-1].setSelected(true);
				}
				Collections.sort(list);
			}
		});
		
		/// 자동 눌렀을 때
		rbtnAuto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < 45; i++) {
					chBox[i].setSelected(false);
					chBox[i].setEnabled(false);
				}
				HashSet<Integer> set = new HashSet<Integer>();

				while (set.size() != 6) {
					set.add((int) (Math.random() * 45) + 1);
				}
				list = new ArrayList<Integer>(set);

				for (int i = 0; i < list.size(); i++) {
					int num = list.get(i);
					chBox[num-1].setSelected(true);
				}
				Collections.sort(list);

			}
		});

		// 리셋 했을때 arraylist 배열 지워주어야함.
		rbtnReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object cmd = e.getSource();
				for (int i = 0; i < 45; i++) {
					chBox[i].setEnabled(true);
				}
				if (rbtnReset == cmd) {
					for (int i = 0; i < 45; i++) {
						chBox[i].setSelected(false);
					}

				}

			}
		});

	}
	private int num(){
		return num;
	}
	public ArrayList<Integer> getList() {
		ArrayList<Integer> lists= new ArrayList<Integer>();
		lists.addAll(list);
		return lists;
	}

}
