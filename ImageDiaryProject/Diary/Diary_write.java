 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Diary_write extends JFrame {

	private JMenu mMenu; // 메뉴바 이름
	private JMenuBar mBar1; // 왼쪽메뉴바

	private JMenuItem mMain; // 메인으로 이동
	private JMenuItem mExit; // 실행 종료
	private JMenuItem mImageOpen; // 실행 종료

	private JLabel lblSave; // 제일 아래 저장버튼
	private JLabel lblExit; // 제일 아래 나가기버튼

	private JLabel lblyear; // 연도
	private JTextField tfye; // 연도_입력
	private JLabel lblmonth; // 월
	private JTextField tfmo; // 월_입력
	private JLabel lblday; // 일
	private JTextField tfda; // 일_입력

	private JLabel lblti; // 제목
	private JTextField tfti; // 제목_입력

	private JTextField tfhs; // 해쉬태그_입력
	private int hsCount;

	private JLabel lblimg; // 중앙 이미지 사진
	private JTextArea tadown; // 일기_입력
	private int taCount;

	private int SaveCount;
	private String date; //
	private CalendarStart owner;
	private File imagesrc; // 이미지 경로
	private CalendarStart calendar;
	private String y, m, d;

	public Diary_write(CalendarStart calendar, String date) {
		this.date = date;
		this.calendar = calendar;
	
	
		init();
		setDisplay();
		addListener();
		showFrame();
	}

	private void init() {

		mBar1 = new JMenuBar();

		mMenu = new JMenu("Menu");

		ImageIcon isave = new ImageIcon("Save.png");
		Image img2 = isave.getImage();
		Image isave2 = img2.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
		lblSave = new JLabel(new ImageIcon(isave2));
		lblSave.setToolTipText("파일을 저장합니다.");

		ImageIcon iexit = new ImageIcon("Exit.png");
		Image img3 = iexit.getImage();
		Image iexit2 = img3.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
		lblExit = new JLabel(new ImageIcon(iexit2));
		lblExit.setToolTipText("일기 쓰기를 종료합니다");
		mImageOpen = new JMenuItem("이미지 삽입");
		mMain = new JMenuItem("메인으로 이동");
		
		mImageOpen.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_I,
						Event.CTRL_MASK
						));
		mMain.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_O,
						Event.CTRL_MASK
						));
		
		mExit = new JMenuItem("끝내기");
		mExit.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_F4,
						Event.ALT_MASK
						));
		

		System.out.println("Diary_write 날짜 :" + date);
		y = date.substring(0, 4);
		m = date.substring(5, 7);
		d = date.substring(8, 10);
		tfye = new JTextField(date.substring(0, 4));
		lblyear = new JLabel("년", JLabel.CENTER);
		tfmo = new JTextField(date.substring(5, 7));
		lblmonth = new JLabel("월", JLabel.CENTER);
		tfda = new JTextField(date.substring(8, 10));
		lblday = new JLabel("일", JLabel.CENTER);
		lblti = new JLabel("제목: ");
		tfti = new JTextField(10);

		tfhs = new JTextField("#해쉬태그 #사용법 #이렇게 #쓰세요.", 20);

		ImageIcon icon = new ImageIcon("1.png");
		lblimg = new JLabel("이미지를 추가하려면 더블클릭하세용 (Ctrl+i)"); // 이미지
		 
		tadown = new JTextArea("", 12,18);
		tadown.setTabSize(4);
		tadown.setLineWrap(true);
		tadown.setWrapStyleWord(false);
		lblSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void setDisplay() {

		JPanel pnlNorth1 = new JPanel();
		pnlNorth1.add(tfye);
		pnlNorth1.add(lblyear);
		pnlNorth1.add(tfmo);
		pnlNorth1.add(lblmonth);
		pnlNorth1.add(tfda);
		pnlNorth1.add(lblday);
		pnlNorth1.setBackground(Color.GRAY);

		// 제목과 제목 입력창을 담는 패널
		JPanel pnlNorth2 = new JPanel();
		pnlNorth2.add(lblti);
		pnlNorth2.add(tfti);

		pnlNorth2.setBackground(new Color(220, 220, 220));

		JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
		pnlNorth.add(pnlNorth1);
		pnlNorth.add(pnlNorth2);

		// 이미지 패널
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(lblimg);
		pnlCenter.setBorder(new TitledBorder("Image"));
		pnlCenter.setBackground(Color.WHITE);
		// 일기장 패널
		JPanel pnlSouth1 = new JPanel();
		pnlSouth1.add(new JScrollPane(tadown), BorderLayout.CENTER);
		pnlSouth1.setBorder(new TitledBorder("Write"));
		pnlSouth1.setBackground(Color.WHITE);

		JPanel pnlSouth3 = new JPanel();
		pnlSouth3.add(tfhs);
		pnlSouth3.setBorder(new TitledBorder("Hash Tags"));
		pnlSouth3.setBackground(Color.WHITE);

		JPanel pnlSouth2 = new JPanel();
		pnlSouth2.add(lblSave);
		pnlSouth2.add(lblExit);
		pnlSouth2.setBackground(new Color(255, 182, 193));

		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.add(pnlSouth1, BorderLayout.NORTH);
		pnlSouth.add(pnlSouth2, BorderLayout.CENTER);
		pnlSouth.add(pnlSouth3, BorderLayout.SOUTH);

		mMenu.add(mMain);
		mMenu.add(mImageOpen);
		mMenu.add(mExit);
		mBar1.add(mMenu);

		setJMenuBar(mBar1);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void imageOpen(File imagesrc) {
		// 이미지파일오픈
		String fileName = imagesrc.getAbsolutePath();
		lblimg.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(fileName).getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
		pack();
		lblimg.setText("");
		
	}
	
	private void jpgcheck() {
		int choice = -1 ;
		JFileChooser chooser = new JFileChooser();
		choice = chooser.showOpenDialog(null);
		if (choice == JFileChooser.APPROVE_OPTION) {
			imagesrc = chooser.getSelectedFile();
			if(imagesrc.getName().endsWith(".png")||imagesrc.getName().endsWith(".jpg")
					||imagesrc.getName().endsWith(".jpeg")
					||imagesrc.getName().endsWith(".bmp")
					||imagesrc.getName().endsWith(".PNG")
			   ) {
				 
				imageOpen(imagesrc);
			}else {
				JOptionPane.showMessageDialog(null, "이미지 확장자가 아닙니다 ! ! ");
			}

		}else {
			
		}
		
	}

	public int getLastDate() {

	 int lastDate = 0;
	 	Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt( tfye.getText() ),Integer.parseInt( tfmo.getText() )-1, 1);

		 
		lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 그달의마지막날
		return lastDate;
	}
	private void addListener() {
		// 이미지파일 더블클릭했을 때 창 열기
		MouseListener mlistener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Object obj = e.getSource();
				JLabel lblimg = (JLabel) obj;
				if (e.getClickCount() == 2) {
					 jpgcheck();
				}
			}
		};
		// 이미지Label 더블클릭시
		lblimg.addMouseListener(mlistener);

		// 저장 JLabel 클릭시
		lblSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSave.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblSave.setBorder(null);
			}
			@Override
			public void mousePressed(MouseEvent me) {

				try {
					String str = tfye.getText(); // 년도
					String str1 = tfmo.getText(); // 월
					String str2 = tfda.getText();// 일
					String str3 = tfhs.getText();

					// 저장 하기 전에 예외처리@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					 
					// textfield 에 문자가 들어갈 경우
					if (!(str.matches("[0-9]+")) || !(str1.matches("[0-9]+")) || !(str2.matches("[0-9]+"))) {
						JOptionPane.showMessageDialog(null, "숫자만 입력하세요");
						tfye.setText(y);
						tfmo.setText(m);
						tfda.setText(d);
					}else if(!((Integer.valueOf(str)>=1920) && (Integer.valueOf(str)<=2070)) ||
							!((Integer.valueOf(str1)>=1) && (Integer.valueOf(str1)<=12))||
							!((Integer.valueOf(str2)>=1) && (Integer.valueOf(str2)<= getLastDate() ))
							) 
					{
						JOptionPane.showMessageDialog(null, "날짜 범위를 벗어났습니다");
						tfye.setText(y);
						tfmo.setText(m);
						tfda.setText(d);
					}
					
					else if (!(str3.contains("#"))) { // # 이 없는 경우
						JOptionPane.showMessageDialog(null, "태그를 입력하세요 ex) #해시태그");
					} else {
						if (tfye.getText().equals("") || tfmo.getText().equals("") || tfda.getText().equals("")
								|| tfti.getText().equals("") || tadown.getText().equals("")
								|| tfhs.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "입력하지 않은 내용이 존재합니다.", "확인요망",
									JOptionPane.WARNING_MESSAGE);
						} else {
							// 사진
						 
							if (imagesrc != null) {
								
								new FileImage(date, imagesrc);
							}

							// 올바르게 일기장을 입력했다면 내용 저장
							Information info = new Information(
									tfye.getText() + "." + tfmo.getText() + "." + tfda.getText(), tfti.getText(),
									tadown.getText(), tfhs.getText());

							calendar.addInfo(info); // calendar에 정보 넣기
							//calendar.save();
						}
					}

				} catch (Exception e) {

				}

			}

		});
		// 나가기 JLabel 클릭시
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblExit.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblExit.setBorder(null);
			}
			@Override
			public void mousePressed(MouseEvent me) {

				if (me.getButton() == MouseEvent.BUTTON1) {
					dispose();
					new CalendarStart();
				}
			}
		});
		// 내용 창 클릭시
		tadown.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (taCount == 0) {
					if (me.getButton() == MouseEvent.BUTTON1) {
						tadown.setText("");
						taCount++;
					}
				}
			}
		});
		// 해쉬태그 창 클릭시
		tfhs.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (hsCount == 0) {
					if (me.getButton() == MouseEvent.BUTTON1) {
						tfhs.setText("");
						hsCount++;
					}
				}
			}
		});

		// 메뉴바 ActionListener
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				// 이미지 Open
				if (src == mImageOpen) {

					 jpgcheck();
					
				}

			}

		};
		mImageOpen.addActionListener(listener);

		mMain.addActionListener((e) -> {
			new CalendarStart();
			dispose();
		});

		mExit.addActionListener((e) -> {
			dispose();
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
				new CalendarStart();
			}
		});
	}

	private void showFrame() {
		setTitle("My Diary");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
