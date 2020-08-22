 
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

	private JMenuBar mBar1; // ���ʸ޴���

	private JMenu mMenu; // �޴��� �̸�

	private JMenuItem mMain; // �������� �̵�
	private JMenuItem mExit; // ���� ����

	private JLabel lblHome; // ���� �Ʒ� Ȩ��ư
	private JLabel lblEdit; // ����
	private JLabel lblDelete; // ����
	private JLabel lblSave; // ���� �Ʒ� ����� �������ư

	private JLabel lblDay;

	private JLabel lblti; // ����
	private JTextField lblti_text;

	private JLabel lblimg; // �߾� �̹��� ����
	private JTextArea tadown; // �ϱ�_�Է�

	private String today;
	private String day;
	private JTextField tfhs; // �ؽ��±�_�Է�
	private Information Information;

	public Diary_read(CalendarStart owner, Information Information) {
		super(owner, "�б�", true);
		this.owner = owner;
		this.Information = Information;
		init();
		setDisplay();
		setting();
		addListener();
		showFrame();

	}

	// ����Ŭ�������� �� ����
	private void setting() {
		FileImage img = new FileImage(Information.getDate());
		// FileImage �ҷ�����
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
		lblHome.setToolTipText("Ȩ���� �̵��մϴ�.");

		ImageIcon isave = new ImageIcon("save.png");
		Image img4 = isave.getImage();
		Image isave2 = img4.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		lblSave = new JLabel(new ImageIcon(isave2));
		lblSave.setToolTipText("������ �����մϴ�.");

		ImageIcon iedit = new ImageIcon("Modify.png");
		Image img2 = iedit.getImage();
		Image iedit2 = img2.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		lblEdit = new JLabel(new ImageIcon(iedit2));
		lblEdit.setToolTipText("������ �����մϴ�.");

		ImageIcon idelete = new ImageIcon("delete.png");
		Image img3 = idelete.getImage();
		Image idelete2 = img3.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		lblDelete = new JLabel(new ImageIcon(idelete2));
		lblDelete.setToolTipText("�ϱ⸦ �����մϴ�.");

		mMain = new JMenuItem("�������� �̵�");

		mMain.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));

		mExit = new JMenuItem("������");
		mExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.ALT_MASK));

		lblDay = new JLabel(day);
		lblDay.setFont(new Font("���� ���", Font.BOLD, 15));
		lblti = new JLabel("����: ");
		lblti.setFont(new Font("���� ���", Font.BOLD, 15));
		lblti_text = new JTextField(20);
		lblti_text.setEditable(false);

		ImageIcon icon = new ImageIcon("");
		lblimg = new JLabel(icon);

		tadown = new JTextArea(12, 40);
		tadown.setTabSize(4);
		tadown.setLineWrap(true);
		tadown.setWrapStyleWord(false);
		tadown.setEditable(false);
		tadown.setFont(new Font("���� ���", Font.PLAIN, 12));
		tfhs = new JTextField("#�ؽ��±� #���� #�̷��� #������.");
		tfhs.setFont(new Font("���� ���", Font.PLAIN, 13));
		tfhs.setEditable(false);

		lblHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void setDisplay() {
		// �⵵�� �� �׸��� ���� ��� �г�
		JPanel pnlNorth1 = new JPanel();
		pnlNorth1.add(lblDay);
		// pnlNorth1.setBackground(Color.GRAY);
		pnlNorth1.setBackground(new Color(255, 182, 193));

		// ����� ���� �Է�â�� ��� �г�
		JPanel pnlNorth2 = new JPanel();
		pnlNorth2.add(lblti);
		pnlNorth2.add(lblti_text);
		pnlNorth2.setBackground(new Color(220, 220, 220));

		JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
		pnlNorth.add(pnlNorth1);
		pnlNorth.add(pnlNorth2);

		// �̹��� �г�
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(lblimg);
		// pnlCenter.setBorder(new TitledBorder("Image"));
		pnlCenter.setBackground(Color.WHITE);
		// �ϱ��� �г�
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

		// Ȩ��ư
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

					if (!(tfhs.getText().contains("#"))) { // # �� ���� ���
						JOptionPane.showMessageDialog(null, "�±׸� �Է��ϼ��� ex) #�ؽ��±�");
					} else if (lblti_text.getText().equals("") || tadown.getText().equals("")
								|| tfhs.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "�Է����� ���� ������ �����մϴ�.", "Ȯ�ο��",
									JOptionPane.WARNING_MESSAGE);
						} else {

							Information info = new Information(lblDay.getText(), lblti_text.getText(), tadown.getText(),
									tfhs.getText());

							int result = owner.addInfo(info); // calendar�� ���� �ֱ�
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

		// ����
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

					owner.dataremove(info); // calendar�� ���� �ֱ�

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
 		setTitle(" �� " + Information.getDate() + " �ϱ� ��" );
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
