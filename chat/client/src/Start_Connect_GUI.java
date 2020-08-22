import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/* ù ȭ�� */
public class Start_Connect_GUI extends JFrame {

	private JLabel lblLogo; // ���ηΰ�

	private JLabel lblIp; // ������
	private JLabel lblId; // ���̵�

	private JTextField tfIp; // ������
	private JTextField tfId; // ���̵�

	private JButton btnConnect; // ����
	private SendData s;

	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private WaitingRoom watingRoom;
	private Vector<User> waitUserList;
	
	public Start_Connect_GUI() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
  
	private void init() {
		// ��ư
		UIManager.put("ButtonUI", Design_ButtonUI.class.getName()); 
		
		// JOptionPane 
		UIManager.put("Panel.background", new Color(0xf5f5f5));
		UIManager.put("OptionPane.background", new Color(0xf5f5f5));
		
		// ��ũ�ѹ�
		UIManager.put("ScrollBarUI", Design_ScrollBarUI.class.getName());
		UIManager.put("ScrollBar.thumb", new Color(0xf5f5f5));
		UIManager.put("ScrollBar.thumbShadow", new Color(0xa0a0a0));
		UIManager.put("ScrollBar.thumbHighlight", new Color(0xa0a0a0));
		UIManager.put("ScrollBar.thumbDarkShadow", new Color(0xf5f5f5));
		
		// �ؽ�Ʈ����� ��Ʈ����
		UIManager.put("TextArea.font", new Font("���� ���", Font.BOLD, 12));
		 
		// �ؽ�Ʈ�ʵ� �׵θ� ����
		UIManager.put("FormattedTextField.border", new LineBorder(Color.red));
		 
		lblLogo = new JLabel(new ImageIcon("main_logo.png"));
		lblIp = new JLabel("IP");
		lblId = new JLabel("ID");
		tfIp = new JTextField(12);
		tfIp.setText("localhost");
		tfId = new JTextField(12); 
		btnConnect = new JButton("Connect");
		btnConnect.setUI(new Design_ButtonUI());
		btnConnect.setPreferredSize(new Dimension(90,28));
		btnConnect.setBackground(new Color(0x66CC66));
		
	}

	private void setDisplay() {
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(lblLogo);

		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));

		JPanel pnlCenterTop = new JPanel();
		pnlCenterTop.add(lblIp);
		pnlCenterTop.add(tfIp);

		JPanel pnlCenterBottom = new JPanel();
		pnlCenterBottom.add(lblId);
		pnlCenterBottom.add(tfId);

		pnlCenter.add(pnlCenterTop);
		pnlCenter.add(pnlCenterBottom);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnConnect);
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void addListeners() {
		btnConnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginInfo();
			}
		});
		
		
		tfId.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					loginInfo();
				}
			}
		});
		
	}
	 
	private void loginInfo(){
		String ip = tfIp.getText();
		String name = tfId.getText();
		name = name.replaceAll(" ", "");

		if (ip.equals("")) {
			JOptionPane.showMessageDialog(Start_Connect_GUI.this, "ip�� �Է����ּ���.");
		} else if (name.equals("")) {
			JOptionPane.showMessageDialog(Start_Connect_GUI.this, "id�� �Է����ּ���.");
		} else if (name.trim().length() > 6) {
			JOptionPane.showMessageDialog(Start_Connect_GUI.this, "id�� 6�����Ϸ� �Է����ּ���.");
		} else {

			try {
				sock = new Socket(ip, 10005);
				oos = new ObjectOutputStream(sock.getOutputStream());
				ois = new ObjectInputStream(sock.getInputStream());
				// �޴°�
				Object obj = null;

				s = new SendData(ClientProtocol.ACCESS, name);
				oos.writeObject(s);
				oos.flush();
				oos.reset();

				// �������� ���� �޾ƿ�
				if ((obj = ois.readObject()) != null) {
					s = (SendData) obj;
					int code = s.getCode();
					if (code == ServerProtocol.ENTRANCE_SUCCESS) {
						watingRoom = (WaitingRoom) s.getObj()[0];
						// String name = (String) s.getObj()[0];
//						System.out.println("�� ���̵�"+name ); 
				 		new WaitingRoom_GUI(sock, oos, ois, watingRoom, name);
						dispose();
					} else {
						JOptionPane.showMessageDialog(Start_Connect_GUI.this, "�ߺ��� ���̵� �ֽ��ϴ�");
					}
				}
			} catch (Exception e1) {
//				e1.printStackTrace();
			}
		}
	}

	private void showFrame() {
		setIconImage(new ImageIcon("home.png").getImage());
		setTitle("Connect");
//		setSize(250, 180);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Start_Connect_GUI();
	}

}
