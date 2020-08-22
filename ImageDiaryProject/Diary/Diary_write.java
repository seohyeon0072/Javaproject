 
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

	private JMenu mMenu; // �޴��� �̸�
	private JMenuBar mBar1; // ���ʸ޴���

	private JMenuItem mMain; // �������� �̵�
	private JMenuItem mExit; // ���� ����
	private JMenuItem mImageOpen; // ���� ����

	private JLabel lblSave; // ���� �Ʒ� �����ư
	private JLabel lblExit; // ���� �Ʒ� �������ư

	private JLabel lblyear; // ����
	private JTextField tfye; // ����_�Է�
	private JLabel lblmonth; // ��
	private JTextField tfmo; // ��_�Է�
	private JLabel lblday; // ��
	private JTextField tfda; // ��_�Է�

	private JLabel lblti; // ����
	private JTextField tfti; // ����_�Է�

	private JTextField tfhs; // �ؽ��±�_�Է�
	private int hsCount;

	private JLabel lblimg; // �߾� �̹��� ����
	private JTextArea tadown; // �ϱ�_�Է�
	private int taCount;

	private int SaveCount;
	private String date; //
	private CalendarStart owner;
	private File imagesrc; // �̹��� ���
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
		lblSave.setToolTipText("������ �����մϴ�.");

		ImageIcon iexit = new ImageIcon("Exit.png");
		Image img3 = iexit.getImage();
		Image iexit2 = img3.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
		lblExit = new JLabel(new ImageIcon(iexit2));
		lblExit.setToolTipText("�ϱ� ���⸦ �����մϴ�");
		mImageOpen = new JMenuItem("�̹��� ����");
		mMain = new JMenuItem("�������� �̵�");
		
		mImageOpen.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_I,
						Event.CTRL_MASK
						));
		mMain.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_O,
						Event.CTRL_MASK
						));
		
		mExit = new JMenuItem("������");
		mExit.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_F4,
						Event.ALT_MASK
						));
		

		System.out.println("Diary_write ��¥ :" + date);
		y = date.substring(0, 4);
		m = date.substring(5, 7);
		d = date.substring(8, 10);
		tfye = new JTextField(date.substring(0, 4));
		lblyear = new JLabel("��", JLabel.CENTER);
		tfmo = new JTextField(date.substring(5, 7));
		lblmonth = new JLabel("��", JLabel.CENTER);
		tfda = new JTextField(date.substring(8, 10));
		lblday = new JLabel("��", JLabel.CENTER);
		lblti = new JLabel("����: ");
		tfti = new JTextField(10);

		tfhs = new JTextField("#�ؽ��±� #���� #�̷��� #������.", 20);

		ImageIcon icon = new ImageIcon("1.png");
		lblimg = new JLabel("�̹����� �߰��Ϸ��� ����Ŭ���ϼ��� (Ctrl+i)"); // �̹���
		 
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

		// ����� ���� �Է�â�� ��� �г�
		JPanel pnlNorth2 = new JPanel();
		pnlNorth2.add(lblti);
		pnlNorth2.add(tfti);

		pnlNorth2.setBackground(new Color(220, 220, 220));

		JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
		pnlNorth.add(pnlNorth1);
		pnlNorth.add(pnlNorth2);

		// �̹��� �г�
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(lblimg);
		pnlCenter.setBorder(new TitledBorder("Image"));
		pnlCenter.setBackground(Color.WHITE);
		// �ϱ��� �г�
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
		// �̹������Ͽ���
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
				JOptionPane.showMessageDialog(null, "�̹��� Ȯ���ڰ� �ƴմϴ� ! ! ");
			}

		}else {
			
		}
		
	}

	public int getLastDate() {

	 int lastDate = 0;
	 	Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt( tfye.getText() ),Integer.parseInt( tfmo.getText() )-1, 1);

		 
		lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // �״��Ǹ�������
		return lastDate;
	}
	private void addListener() {
		// �̹������� ����Ŭ������ �� â ����
		MouseListener mlistener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Object obj = e.getSource();
				JLabel lblimg = (JLabel) obj;
				if (e.getClickCount() == 2) {
					 jpgcheck();
				}
			}
		};
		// �̹���Label ����Ŭ����
		lblimg.addMouseListener(mlistener);

		// ���� JLabel Ŭ����
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
					String str = tfye.getText(); // �⵵
					String str1 = tfmo.getText(); // ��
					String str2 = tfda.getText();// ��
					String str3 = tfhs.getText();

					// ���� �ϱ� ���� ����ó��@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					 
					// textfield �� ���ڰ� �� ���
					if (!(str.matches("[0-9]+")) || !(str1.matches("[0-9]+")) || !(str2.matches("[0-9]+"))) {
						JOptionPane.showMessageDialog(null, "���ڸ� �Է��ϼ���");
						tfye.setText(y);
						tfmo.setText(m);
						tfda.setText(d);
					}else if(!((Integer.valueOf(str)>=1920) && (Integer.valueOf(str)<=2070)) ||
							!((Integer.valueOf(str1)>=1) && (Integer.valueOf(str1)<=12))||
							!((Integer.valueOf(str2)>=1) && (Integer.valueOf(str2)<= getLastDate() ))
							) 
					{
						JOptionPane.showMessageDialog(null, "��¥ ������ ������ϴ�");
						tfye.setText(y);
						tfmo.setText(m);
						tfda.setText(d);
					}
					
					else if (!(str3.contains("#"))) { // # �� ���� ���
						JOptionPane.showMessageDialog(null, "�±׸� �Է��ϼ��� ex) #�ؽ��±�");
					} else {
						if (tfye.getText().equals("") || tfmo.getText().equals("") || tfda.getText().equals("")
								|| tfti.getText().equals("") || tadown.getText().equals("")
								|| tfhs.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "�Է����� ���� ������ �����մϴ�.", "Ȯ�ο��",
									JOptionPane.WARNING_MESSAGE);
						} else {
							// ����
						 
							if (imagesrc != null) {
								
								new FileImage(date, imagesrc);
							}

							// �ùٸ��� �ϱ����� �Է��ߴٸ� ���� ����
							Information info = new Information(
									tfye.getText() + "." + tfmo.getText() + "." + tfda.getText(), tfti.getText(),
									tadown.getText(), tfhs.getText());

							calendar.addInfo(info); // calendar�� ���� �ֱ�
							//calendar.save();
						}
					}

				} catch (Exception e) {

				}

			}

		});
		// ������ JLabel Ŭ����
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
		// ���� â Ŭ����
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
		// �ؽ��±� â Ŭ����
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

		// �޴��� ActionListener
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				// �̹��� Open
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
