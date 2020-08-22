 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CalendarStart extends JFrame {
	private JPanel toppnl;
	private JButton prebtn;
	private JButton nextbtn;

	private JLabel yearlbl;
	private JLabel monthlbl;
	private JLabel daylbl;

	private JPanel centerpnl;
	private JPanel titlepnl;

	private String titleStr[] = { "일", "월", "화", "수", "목", "금", "토" };
	private JPanel datepnl = new JPanel(new GridLayout(0, 7));

	private Calendar now;
	private int year, month, date;

	private JButton[] daybtn = null;

	private JComboBox<Integer> yearCombo;
	private JComboBox<Integer> monthCombo;

	private JButton btnread;
	private JButton btnwrite;
	private JButton btnsearch;

	private String selectDay; // 클릭한 날짜
	private Vector<Information> infovector;
	private boolean isUpdated;

	public CalendarStart() {
		loadData();
		init();
		setDisplay();
		addListener();
		showFrame();
		 
	}

	public int addInfo(Information info) {
		int state = 1;

		for (int i = 0; i < infovector.size(); i++) {
			if (infovector.get(i).getDate().equals(info.getDate())) {
				state = 0;
				int r = JOptionPane.showConfirmDialog(null, "덮어쓰시겠습니까?", "really?", JOptionPane.YES_NO_OPTION);

				if (r == JOptionPane.YES_OPTION) {
					infovector.add(info);
					infovector.remove(i);
					save();
					state = 0;
					return 0;
				}

			}

		}
		if (state == 1) {
			infovector.add(info);
			save();
		}
		return 1;
	}

	// 데이터 삭제
	public void dataremove(Information info) {

		for (int i = 0; i < infovector.size(); i++) {
			if (infovector.get(i).getDate().equals(info.getDate())) {
				int r = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "really?", JOptionPane.YES_NO_OPTION);

				if (r == JOptionPane.YES_OPTION) {
					infovector.remove(i);
					save();

					// 그림 파일 존재하는지 확인 존재하면 삭제
					File dir = new File("image");
					if (dir.exists()) {
						File[] fList = dir.listFiles();
						for (File file : fList) {
							if (file.getName().contains(info.getDate())) {
								file.delete();
							}
						}
					}

					dispose();
					new CalendarStart();
					return;
				}
			}
		}

	}

	// 파일 불러오기
	private void loadData() {
		FileInputStream fis = null;
		DataInputStream dis = null;
		File file = new File("myDiary.dat");
		try {
			fis = new FileInputStream(file);
			dis = new DataInputStream(fis);
			infovector = new Vector<Information>();

			while (dis.available() > 0) {
				String day = dis.readUTF();
				String title = dis.readUTF();
				String body = dis.readUTF();
				String hash = dis.readUTF();
				infovector.add(new Information(day, title, body, hash));
			}
		} catch (FileNotFoundException e) {
			infovector = new Vector<Information>();

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "데이터 파일 로딩 실패");
		} finally {
			try {
				dis.close();
				fis.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	protected void save() {

		FileOutputStream fos = null;
		DataOutputStream dos = null;
		int result = JOptionPane.YES_OPTION;
		File f = new File("myDiary.dat");

		try {
			fos = new FileOutputStream(f);
			dos = new DataOutputStream(fos);
			int count = 0;
			for (Information info : infovector) {
				dos.writeUTF(String.valueOf(info.getDate()));
				dos.writeUTF(String.valueOf(info.getTitle()));
				dos.writeUTF(String.valueOf(info.getBody()));
				dos.writeUTF(String.valueOf(info.getHash()));
			}
			JOptionPane.showMessageDialog(null, "Update 되었습니다 ..!");

			dos.flush();
		} catch (IOException e) {
			result = JOptionPane.showConfirmDialog(this, "error occurred(during save user list). do you want to exit?" ,
					"question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		} finally {
			try {
				dos.close();
				fos.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	private void init() {
		prebtn = new JButton("◀");
		nextbtn = new JButton("▶");

		yearlbl = new JLabel("년");
		monthlbl = new JLabel("월");

		yearCombo = new JComboBox<Integer>();
		monthCombo = new JComboBox<Integer>();

		now = Calendar.getInstance();
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH) + 1;
		date = now.get(Calendar.DATE);

		for (int i = year - 100; i <= year + 50; i++) {

			yearCombo.addItem(i);

		}
		yearCombo.setSelectedItem(year);// 현재년도선택

		// 월시작
		for (int i = 1; i <= 12; i++) {
			monthCombo.addItem(i);
		}
		monthCombo.setSelectedItem(month);// 현재월선택

		// 날짜출력
		dayPrint(year, month);

		// 버튼

		btnread = new JButton("읽기");
		btnread.setBackground(new Color(224, 224, 224));
		btnwrite = new JButton("쓰기");
		btnwrite.setBackground(new Color(224, 224, 224));
		btnsearch = new JButton("검색");
		btnsearch.setBackground(new Color(224, 224, 224));

	}

	private void setDisplay() {
		// 패널
		JPanel centerpnl = new JPanel(new BorderLayout());
		JPanel titlepnl = new JPanel(new GridLayout(1, 7));
		JPanel datePane = new JPanel(new GridLayout(0, 7));

		toppnl = new JPanel();

		toppnl.add(prebtn);

		toppnl.add(yearCombo);
		toppnl.add(yearlbl);

		toppnl.add(monthCombo);
		toppnl.add(monthlbl);

		toppnl.add(nextbtn);

		add(toppnl, BorderLayout.NORTH);

		toppnl.setBackground(Color.GRAY);

		titlepnl.setBackground(new Color(255, 182, 193));

		// 토,일 색
		for (int i = 0; i < titleStr.length; i++) {
			daylbl = new JLabel(titleStr[i], JLabel.CENTER);
			if (i == 0) {
				daylbl.setForeground(Color.red);
			} else if (i == 6) {
				daylbl.setForeground(Color.blue);
			}
			titlepnl.add(daylbl);
		}

		centerpnl.add(titlepnl, BorderLayout.NORTH);
		centerpnl.add(datepnl, BorderLayout.CENTER);

		// 버튼
		JPanel Southpnl = new JPanel();

		Southpnl.add(btnread);
		Southpnl.add(btnwrite);
		Southpnl.add(btnsearch);

		add(toppnl, BorderLayout.NORTH);
		add(centerpnl, BorderLayout.CENTER);
		add(Southpnl, BorderLayout.SOUTH);
	}

	private void addListener() {

		ActionListener listener = new ActionListener() {
			// 날짜 콤보박스 ㅣlistener
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object obj = ae.getSource();
				if (obj instanceof JButton) { // 형변환 검사해주는거
					JButton eventBtn = (JButton) obj;

					int yy = (Integer) yearCombo.getSelectedItem(); // 선택된 값을
																	// 가져오는거
					int mm = (Integer) monthCombo.getSelectedItem();
					if (eventBtn.equals(prebtn)) {// 전의 달
						if (mm == 1) {
							yy--;
							mm = 12;
						} else {
							mm--;
						}
					} else if (eventBtn.equals(nextbtn)) { // 다음달
						if (mm == 12) {
							yy++;
							mm = 1;
						} else {
							mm++;

						}

					}
					yearCombo.setSelectedItem(yy); // 선택한 값을 배치한다.
					monthCombo.setSelectedItem(mm);
				} else if (obj instanceof JComboBox) {
					createDayStart();

				} // 날짜 콤보박스 데이터처리 끝

			}

		};

		prebtn.addActionListener(listener);
		nextbtn.addActionListener(listener);
		yearCombo.addActionListener(listener);
		monthCombo.addActionListener(listener);

		// 버튼 선택한게 없을때 오늘 일기 읽기
		// //////////////////////////////////////////////////
		btnread.addActionListener((e) -> {

			// 메소드 빼기

			if (selectDay == null) {
				int state = 0;
				for (int i = 0; i < infovector.size(); i++) {
					if (infovector.get(i).getDate().equals(getToday())) {
						state = 1;
						dispose();
						new Diary_read(this, infovector.get(i));

						return;
					}
				}

				if (state == 0) {
					int r = JOptionPane.showConfirmDialog(null, "일기가 없습니다. 일기 쓸까?", "really?",
							JOptionPane.YES_NO_OPTION);
					if (r == JOptionPane.YES_OPTION) {
						dispose();
						new Diary_write(this, getToday());
					}
				}

			} else { // select
				int state = 0;
				for (int i = 0; i < infovector.size(); i++) {
					if (infovector.get(i).getDate().equals(selectDay)) {
						state = 1;

						dispose();
						new Diary_read(this, infovector.get(i));
						return;
					}
				}

				if (state == 0) {
					int r = JOptionPane.showConfirmDialog(null, "일기가 없습니다. 일기 쓸까?", "really?",
							JOptionPane.YES_NO_OPTION);
					if (r == JOptionPane.YES_OPTION) {
						new Diary_write(this, selectDay);
					}
				}
			}

		});

		// 버튼 선택한게 없을때 오늘 일기 쓰기
		btnwrite.addActionListener((e) -> {
			if (selectDay == null) {
				new Diary_write(this, getToday());
				dispose();
			} else {
				new Diary_write(this, selectDay);
				dispose();
			}
		});
		// 버튼 검색
		btnsearch.addActionListener((e1) -> {

			String result = JOptionPane.showInputDialog(CalendarStart.this, "해시태그를 검색하세요",
					JOptionPane.QUESTION_MESSAGE);

			if (!(result == null)) {
				String[] arr;
				Vector<Information> search = new Vector<Information>(); // 해시코드 일치했을때 info 저장
				String a = "";
				for (int i = 0; i < infovector.size(); i++) {
					String hash = infovector.get(i).getHash();
					arr = hash.split("#");
					 
					for (int j = 0; j < arr.length; j++) {
						 
						if (arr[j].contains(result)) {

							a = "a";
						}
					}
					if (a.equals("a")) {
						search.add(infovector.get(i));
					}

				}

				if (search.size() > 0) {
					dispose();
					new Diary_search(search);

				} else {
					JOptionPane.showMessageDialog(null, "검색 결과가 없습니다ㅠㅠ", "눈물", JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(CalendarStart.this, "exit?", "question",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
					// 사용자가 "예"를 누를떄
				} else {
					new CalendarStart();
				}
			}
		});

	}

	public void getSelectDay(JButton e1) {
		selectDay = String.valueOf(yearCombo.getSelectedItem()) + ".";
		if ((Integer) monthCombo.getSelectedItem() < 10) {
			selectDay += String.valueOf("0" + monthCombo.getSelectedItem()) + ".";
		} else {
			selectDay += String.valueOf(monthCombo.getSelectedItem()) + ".";
		}
		if (Integer.parseInt(e1.getText()) < 10) {
			selectDay += "0" + e1.getText();

		} else {
			selectDay += e1.getText();
		}
	}

	// 오늘날짜 2020.06.08
	public String getToday() {
		String today;
		now = Calendar.getInstance();
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH) + 1;
		date = now.get(Calendar.DATE);

		today = String.valueOf(year) + ".";
		if (month < 10) {
			today += "0" + String.valueOf(month) + ".";
		} else {
			today += String.valueOf(month) + ".";
		}

		if (date < 10) {
			today += "0" + String.valueOf(date);
		} else {
			today += String.valueOf(date);
		}

		return today;

	}

	public void dayPrint(int y, int m) {
		Calendar cal = Calendar.getInstance();
		cal.set(y, m - 1, 1);

		int week = cal.get(Calendar.DAY_OF_WEEK); // 해당 날짜의 요일값 일요일1 토요일7
		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 그달의마지막날
		for (int i = 1; i < week; i++) { // 날짜 출력전까지 초기화
			datepnl.add(new JLabel(""));
		}

		daybtn = new JButton[lastDate]; // 배열은 꼭 길이를 지정해주어야한다.

		for (int i = 1; i <= lastDate; i++) {
			daybtn[i - 1] = new JButton(String.valueOf(i)); // 버튼에 날짜 셋팅
			daybtn[i - 1].setBackground(Color.WHITE);
			daybtn[i - 1].setOpaque(true);
			cal.set(y, m - 1, i);
			int outWeek = cal.get(Calendar.DAY_OF_WEEK);
			if (outWeek == 1) { // 일욜
				daybtn[i - 1].setForeground(Color.red);
			} else if (outWeek == 7) { // 토욜
				daybtn[i - 1].setForeground(Color.BLUE);
			}
			datepnl.add(daybtn[i - 1]);

		}
		diarybtnChange(y, m);
		// 오늘날짜
		if (y == now.get(Calendar.YEAR)) {
			if ((m - 1) == now.get(Calendar.MONTH)) {
				daybtn[date - 1].setBackground(new Color(229, 204, 255)); // 보라색
			}
		}
		todaybackground();
	}

//일기 쓴거 날짜바뀜 
	private void diarybtnChange(int y, int m) {
		String[] spliteword = null;
		for (int i = 0; i < infovector.size(); i++) {
			String date = infovector.get(i).getDate();
			spliteword = date.split("\\.");
			int year = Integer.parseInt(spliteword[0]);
			int month = Integer.parseInt(spliteword[1]);
			int day = Integer.parseInt(spliteword[2]);
			if (year == y) {
				if (month == m) {
					daybtn[day - 1].setBackground(Color.PINK);
				}
			}
		}

	}

	// 오늘날짜 버튼 색상 표시
	private void todaybackground() {

		MouseListener mouselistener = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();
				JButton e1 = (JButton) obj;

				// 내가 선택한 버튼
				getSelectDay(e1);
				// 더블클릭
				if (e.getClickCount() > 1) {

					// 메소드 빼기
					int state = 0;
					for (int i = 0; i < infovector.size(); i++) {
						if (infovector.get(i).getDate().equals(selectDay)) {
							state = 1;
							dispose();
							new Diary_read(CalendarStart.this, infovector.get(i));
							return;
						}

					}

					if (state == 0) {

						int r = JOptionPane.showConfirmDialog(null, "일기가 없습니다. 일기 쓸까?", "really?",
								JOptionPane.YES_NO_OPTION);

						if (r == JOptionPane.YES_OPTION) {
							dispose();
							new Diary_write(CalendarStart.this, selectDay);
						}
					}

					///
				} // 더블클릭 끝

			}

		};

		for (int i = 0; i < daybtn.length; i++) {
			daybtn[i].addMouseListener(mouselistener);
			daybtn[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

	}

	// 달력 갱신
	private void createDayStart() {
		datepnl.setVisible(false);
		datepnl.removeAll();
		dayPrint((Integer) yearCombo.getSelectedItem(), (Integer) monthCombo.getSelectedItem());

		// 캘린더 set
		datepnl.setVisible(true);

	}

	private void showFrame() {
		setTitle("MyDiary");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new CalendarStart();
	}
}
