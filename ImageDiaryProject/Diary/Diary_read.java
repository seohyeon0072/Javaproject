 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
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

public class Diary_read extends JDialog {
	private CalendarStart owner;

	private JMenuBar mBar1; // 왼쪽메뉴바

	private JMenu mMenu; // 메뉴바 이름

	private JMenuItem mMain; // 메인으로 이동
	private JMenuItem mExit; // 실행 종료

	private JLabel lblHome; // 제일 아래 홈버튼
	private JLabel lblEdit; // 수정
	private JLabel lblDelete; // 삭제
	private JLabel lblSave; // 제일 아래 저장과 나가기버튼

	private JLabel lblDay;

	private JLabel lblti; // 제목
	private JTextField lblti_text;

	private JLabel lblimg; // 중앙 이미지 사진
	private JTextArea tadown; // 일기_입력

	private String today;
	private String day;
	private JTextField tfhs; // 해쉬태그_입력
	private Information Information;

	public Diary_read(CalendarStart owner, Information Information) {
		super(owner, "읽기", true);
		this.owner = owner;
		this.Information = Information;
		init();
		setDisplay();
		setting();
		addListener();
		showFrame();

	}

	// 파일클래스에서 온 정보
	private void setting() {
		FileImage img = new FileImage(Information.getDate());
		// FileImage 불러오기
		File imgfile = new File("1.png");

		if (!(img.findImage() == null)) {
			imgfile = img.findImage();
			System.out.println(imgfile);
			ImageIcon imgIcon = new ImageIcon(String.valueOf(imgfile));
			// lblimg.setIcon(imgIcon);
			lblimg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.valueOf(imgIcon))
					.getScaledInstance(250, 250, Image.SCALE_SMOOTH)));

		} else {
			lblimg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.valueOf(imgfile))
					.getScaledInstance(250, 250, Image.SCALE_SMOOTH)));

		}
		lblDay.setText(Information.getDate());
		lblti_text.setText(Information.getTitle());
		tadown.setText(Information.getBody());
		tfhs.setText(Information.getHash());
	}

	private void init() {

		mBar1 = new JMenuBar();

		mMenu = new JMenu("Menu");

		ImageIcon ihome = new ImageIcon("home.png");
		Image img1 = ihome.getImage();
		Image ihome2 = img1.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		lblHome = new JLabel(new ImageIcon(ihome2));
		lblHome.setToolTipText("홈으로 이동합니다.");

		ImageIcon isave = new ImageIcon("save.png");
		Image img4 = isave.getImage();
		Image isave2 = img4.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		lblSave = new JLabel(new ImageIcon(isave2));
		lblSave.setToolTipText("파일을 저장합니다.");

		ImageIcon iedit = new ImageIcon("Modify.png");
		Image img2 = iedit.getImage();
		Image iedit2 = img2.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		lblEdit = new JLabel(new ImageIcon(iedit2));
		lblEdit.setToolTipText("내용을 수정합니다.");

		ImageIcon idelete = new ImageIcon("delete.png");
		Image img3 = idelete.getImage();
		Image idelete2 = img3.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		lblDelete = new JLabel(new ImageIcon(idelete2));
		lblDelete.setToolTipText("일기를 삭제합니다.");

		mMain = new JMenuItem("메인으로 이동");

		mMain.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));

		mExit = new JMenuItem("끝내기");
		mExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.ALT_MASK));

		lblDay = new JLabel(day);
		lblDay.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblti = new JLabel("제목: ");
		lblti.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblti_text = new JTextField(20);
		lblti_text.setEditable(false);

		ImageIcon icon = new ImageIcon("");
		lblimg = new JLabel(icon);

		tadown = new JTextArea(12, 40);
		tadown.setTabSize(4);
		tadown.setLineWrap(true);
		tadown.setWrapStyleWord(false);
		tadown.setEditable(false);
		tadown.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		tfhs = new JTextField("#해쉬태그 #사용법 #이렇게 #쓰세요.");
		tfhs.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		tfhs.setEditable(false);

		lblHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void setDisplay() {
		// 년도와 월 그리고 일을 담는 패널
		JPanel pnlNorth1 = new JPanel();
		pnlNorth1.add(lblDay);
		// pnlNorth1.setBackground(Color.GRAY);
		pnlNorth1.setBackground(new Color(255, 182, 193));

		// 제목과 제목 입력창을 담는 패널
		JPanel pnlNorth2 = new JPanel();
		pnlNorth2.add(lblti);
		pnlNorth2.add(lblti_text);
		pnlNorth2.setBackground(new Color(220, 220, 220));

		JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
		pnlNorth.add(pnlNorth1);
		pnlNorth.add(pnlNorth2);

		// 이미지 패널
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(lblimg);
		// pnlCenter.setBorder(new TitledBorder("Image"));
		pnlCenter.setBackground(Color.WHITE);
		// 일기장 패널
		JPanel pnlSouth1 = new JPanel();
		pnlSouth1.add(new JScrollPane(tadown), BorderLayout.CENTER);
		// pnlSouth1.setBorder(new TitledBorder("Write"));
		pnlSouth1.setBackground(Color.WHITE);

		JPanel pnlSouth2 = new JPanel();
		pnlSouth2.add(lblHome);
		pnlSouth2.add(lblEdit);
		pnlSouth2.add(lblSave);
		pnlSouth2.add(lblDelete);
		pnlSouth2.setBackground(new Color(255, 182, 193));

		JPanel pnlSouth3 = new JPanel();
		pnlSouth3.add(tfhs);
		pnlSouth3.setBorder(new TitledBorder("Hash Tags"));
		pnlSouth3.setBackground(Color.WHITE);

		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.add(pnlSouth1, BorderLayout.NORTH);
		pnlSouth.add(pnlSouth2, BorderLayout.CENTER);
		pnlSouth.add(pnlSouth3, BorderLayout.SOUTH);

		mMenu.add(mMain);
		mMenu.add(mExit);
		mBar1.add(mMenu);

		setJMenuBar(mBar1);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void addListener() {

		// 홈버튼
		lblHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblHome.setBorder(new LineBorder(Color.GRAY));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblHome.setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					dispose();
					new CalendarStart();
				}

			}
		});
		lblSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSave.setBorder(new LineBorder(Color.BLACK));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblSave.setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent me) {

				if (me.getButton() == MouseEvent.BUTTON1) {

					if (!(tfhs.getText().contains("#"))) { // # 이 없는 경우
						JOptionPane.showMessageDialog(null, "태그를 입력하세요 ex) #해시태그");
					} else if (lblti_text.getText().equals("") || tadown.getText().equals("")
								|| tfhs.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "입력하지 않은 내용이 존재합니다.", "확인요망",
									JOptionPane.WARNING_MESSAGE);
						} else {

							Information info = new Information(lblDay.getText(), lblti_text.getText(), tadown.getText(),
									tfhs.getText());

							int result = owner.addInfo(info); // calendar에 정보 넣기
							System.out.println(result);
							if (result == 0) {
								lblti_text.setEditable(false);
								tadown.setEditable(false);
								tfhs.setEditable(false);
							}

						}

					 
				}
			}
		});
		lblEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblEdit.setBorder(new LineBorder(Color.GRAY));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblEdit.setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					lblti_text.setEditable(true);
					tadown.setEditable(true);
					tfhs.setEditable(true);
				}
			}
		});

		// 삭제
		lblDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDelete.setBorder(new LineBorder(Color.GRAY));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblDelete.setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					Information info = new Information(lblDay.getText(), lblti_text.getText(), tadown.getText(),
							tfhs.getText()

					);

					owner.dataremove(info); // calendar에 정보 넣기

				}
			}
		});
		mMain.addActionListener((e) -> {
			dispose();
			new CalendarStart();
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
 		setTitle(" ♡ " + Information.getDate() + " 일기 ♡" );
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
