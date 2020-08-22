 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Lotto_result extends JDialog {

	private Lotto_main owner;
	private JButton btntrace;
	private JButton btnclose;
	private ArrayList<Integer> mynums;// lottos[idx].getList();
	private ArrayList<ArrayList<Integer>> lottoNumberLists; // mynums �� ���� �迭
	private Integer[] winArrayNums;
	private ArrayList<Integer> winArrayListNums;
	private ArrayList<Integer> exebonuslist; // ���ʽ� �� ��÷ ����Ʈ
	private Lotto_panel[] lottos;
	private int number;
	private ImageLabel[] lblImgs;
	private JPanel[] pnlValue1;
	private JPanel[] pnlValue2;
	private JPanel[] pnlreal;
	public static final String PATH = "jpg/";
	public static final String[] IMAGENUM = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
			"33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "plus" };

	public Lotto_result(Lotto_main owner) {
		super(owner, "��� â", true);
		this.owner = owner;
		number = owner.getNumber();
		
		//��÷��ȣ 
		WinNums winnumbers = new WinNums();
		winArrayNums = winnumbers.getNums();
		
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {

		lblImgs = new ImageLabel[IMAGENUM.length];
		for (int idx = 0; idx < lblImgs.length; idx++) {
			lblImgs[idx] = new ImageLabel(getImageIcon(getsrcPath(idx)), idx);
		}
		pnlreal = new JPanel[number];
		 
		pnlValue1 = new JPanel[number];
		pnlValue2 = new JPanel[number];

		// �г��ʱ�ȭ
		for (int i = 0; i < number; i++) {
			pnlreal[i] = new JPanel(new GridLayout(0, 1));
			pnlValue1[i] = new JPanel(new GridLayout());
			pnlValue2[i] = new JPanel();
		}
		btntrace = new JButton("Trace");
		btnclose = new JButton("Close");

		lottoNumberLists = new ArrayList<>();
		winArrayListNums = new ArrayList<>(Arrays.asList(winArrayNums));
		//���ʽ�
		Integer[] exeBonus = Arrays.copyOfRange(winArrayNums, 0, 6);
		exebonuslist = new ArrayList<>(Arrays.asList(exeBonus));

	}

	private void setDisplay() {

		JPanel pnlNum = new JPanel();
		JLabel lblNum = new JLabel();
		int number = owner.getNumber();

		pnlNum.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
		pnlNum.setBackground(Color.WHITE);

		for (int i = 0; i < exebonuslist.size(); i++) {
			pnlNum.add(lblImgs[exebonuslist.get(i) - 1]);
		}
		pnlNum.add(lblImgs[45]);
		pnlNum.add(lblImgs[winArrayListNums.get(6) - 1]);

		TitledBorder tb = new TitledBorder(new LineBorder(Color.GRAY, 1), "�� �� ÷ �� ȣ ��");
		tb.setTitleJustification(TitledBorder.CENTER);
		tb.setTitleColor(Color.BLACK);
		tb.setTitleFont(new Font("����", Font.BOLD, 16));
		pnlNum.setBorder(tb);


		 lottos = new Lotto_panel[number];
		lottos = owner.getPnl();

		// ���� ������ ��ȣ
		for (int idx = 0; idx < lottos.length; idx++) {
			mynums = lottos[idx].getList();
			lottoNumberLists.add(mynums);
			Iterator<Integer> it = mynums.iterator();
			pnlValue1[idx].add(new JLabel("���ù�ȣ : ", JLabel.LEFT));
			while (it.hasNext()) {
				int value = (int) it.next();
				pnlValue1[idx].add(getnumberpnl(value));
				 
			}
		}

		goalResult();// ��÷��ȣ ��÷�ϰ� ���� �ű�� �޼ҵ�

		
		for (int j = 0; j < pnlValue1.length; j++) {
			pnlreal[j].add(pnlValue1[j]);
			pnlreal[j].add(pnlValue2[j]);
			pnlreal[j].setBorder(new TitledBorder(j + 1 + "��° ����"));
			pnlreal[j].setOpaque(true);
		 
		}
		
		JPanel pnlreal1 = new JPanel(new GridLayout(0, 1));
		for (int k = 0; k < number; k++) {
			pnlreal1.add(pnlreal[k]);
			 
		}
	
		JPanel pnlbtn = new JPanel();
		pnlbtn.add(btnclose);
		pnlbtn.add(btntrace);
	 
		pnlreal1.setOpaque(true);
		pnlreal1.setBackground(Color.WHITE);
		setBackground(Color.white);
		add(pnlNum, BorderLayout.NORTH);
		add(pnlreal1, BorderLayout.CENTER);
		add(pnlbtn, BorderLayout.SOUTH);
	}

	private void goalResult() {
		ArrayList<Integer> lottoarray = new  ArrayList<Integer>();
		lottoarray.addAll(mynums);
		// ��ġ�ϴ� ��ȣ ã�� ��ġ�����ʴ� ��ȣ ���� ��� �����
		for (int i = 0; i < lottoNumberLists.size(); i++) {
			lottoNumberLists.get(i).retainAll(exebonuslist);
		}
		int no = 0;
		// ��÷ ���
		System.out.println("size"+lottoNumberLists.size());
		for (int idx = 0; idx < lottoNumberLists.size(); idx++) {
			int count = lottoNumberLists.get(idx).size();
			int rank = 0;
			switch (count) {
			case 6:
				rank = 1;
				break;
			case 5:
				boolean ans = lottoarray.contains(winArrayListNums.get(6));
					if (ans) {
						rank = 2;
					} else {
						rank = 3;
					}
				break;
			case 4:
				rank = 4;
				break;
			case 3:
				rank = 5;
				break;
			}
			String result;
			if (rank == 0) {
				// ��ġ�ϴ°� 0�̸� ��
				result = "��";
			} else {
				// 1���� ������ switch���� ���� rank����
				result = rank + "��";

			}
			no++;
			System.out.print(no + "ȸ ���� ��� : " + result);
			System.out.println("\t��ġ��ȣ ->" + lottoNumberLists.get(idx));

			pnlValue2[idx].add(new JLabel("��÷��� : ", JLabel.LEFT ));
			for (int i = 0; i < lottoNumberLists.get(idx).size(); i++) {
				pnlValue2[idx].add(getnumberpnl(lottoNumberLists.get(idx).get(i)));
				 
			}
			JLabel lblresult = new JLabel("����: " + String.valueOf(result), JLabel.LEFT);
			lblresult.setFont(new Font("����",Font.BOLD,18));
			lblresult.setForeground(Color.red);
			pnlreal[idx].add(lblresult);
			 
		}

		System.out.println(lottoNumberLists);

	}
 
	private JPanel getnumberpnl(int value) {

		JPanel pnl = new JPanel();
		value = value - 1;
		ImageIcon icon = new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(getsrcPath(value)).getScaledInstance(40, 40, Image.SCALE_SMOOTH));

		lblImgs[value] = new ImageLabel(icon, value);
		pnl.add(lblImgs[value]);

		 
		return pnl;
	}

	// �̹��� ������ ũ������
	public static ImageIcon getImageIcon(String srcPath) {
		return new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(srcPath).getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	}

	// �̹��� ���
	public String getsrcPath(int idx) {
		return "image/" + IMAGENUM[idx] + ".png";
	}

	private void addListeners() {
		btntrace.addActionListener((e) -> {
			int count = 0;
			int rank= 0;
			do{ 
				rank = goalcount();
				System.out.println(rank);
				count++;
			}while(rank!=2);
			JOptionPane.showMessageDialog(null, count);
			
		});

 
		btnclose.addActionListener((e) -> {

			int result = JOptionPane.showConfirmDialog(Lotto_result.this, "���� �Ͻðڽ��ϱ�?", "����", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		});

	}

	private int goalcount() {
	 	MyLottoNums lottonums = new MyLottoNums();

		ArrayList<Integer> myNumslist = new ArrayList<>(Arrays.asList(lottonums.getNums()));
		ArrayList<Integer>  myNumslist2 = new ArrayList<>();
		myNumslist2.addAll(myNumslist);
		
		Integer[] exeBonus = Arrays.copyOfRange(winArrayNums, 0, 6);
		 
		myNumslist.retainAll(exebonuslist);
		 
		System.out.println("myNumslists"+myNumslist2);
		System.out.println(myNumslist);
		
		int count = myNumslist.size();
		System.out.println(count);
		
		int rank = 0;
	 
		
		switch (count) {
		case 6:
			rank = 1;
			break;
		case 5:
			boolean ans = myNumslist2.contains(winArrayListNums.get(6));
				if (ans) {
					rank = 2;
				} else {
					rank = 3;
				}
			break;
		case 4:
			rank = 4;
			break;
		case 3:
			rank = 5;
			break;
		case 2:
			rank = 6;
		}
		System.out.println("rank"+rank+"��");
		return rank; 
	 
	}
		
		
	private void showFrame() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}

	// �ζǴ�÷��ȣ
	
}


