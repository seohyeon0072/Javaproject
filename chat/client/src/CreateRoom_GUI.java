
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CreateRoom_GUI extends JDialog {

	private JLabel lblLogo; // �ΰ�
	private JLabel lblRoomName; // ���̸�
	private JLabel lblRoomPeople; // ���ο�

	private JTextField tfRoomName; // ���̸�
	private JComboBox<Integer> cbRoomMaxPeople; // ���ο�

	private JButton btnCreateRoom; // �����
	private JButton btnCancel; // ������

	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String name;
	private String roomName;
	private int maxPeople = 2;
	private SendData sd;
	private ChatRoomInfo roomInfo;
	
	private WaitingRoom_GUI wrg;

	public CreateRoom_GUI(WaitingRoom_GUI wrg, Socket sock, ObjectOutputStream oos, ObjectInputStream ois, String name) {
		super(wrg,true);
		this.sock = sock;
		this.oos = oos;
		this.ois = ois;
		this.name = name;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		lblLogo = new JLabel(new ImageIcon("sub_logo.png"));
		lblRoomName = new JLabel("���̸�:");
		lblRoomPeople = new JLabel("�����ο�:");
		lblRoomPeople.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		tfRoomName = new JTextField(10);
		tfRoomName.setDocument(new JTextFieldLimit(15));

		cbRoomMaxPeople = new JComboBox<Integer>();
		for (int i = 2; i <= 10; i++) {
			cbRoomMaxPeople.addItem(i);
		}
		cbRoomMaxPeople.setPreferredSize(new Dimension(115, 24));
		btnCreateRoom = new JButton("Create");
		btnCreateRoom.setUI(new Design_ButtonUI());
		btnCreateRoom.setPreferredSize(new Dimension(76,28));
		btnCreateRoom.setBackground(new Color(0x66CC66));
		btnCancel = new JButton("Cancel");
		btnCancel.setUI(new Design_ButtonUI());
		btnCancel.setPreferredSize(new Dimension(76,28));
		btnCancel.setBackground(new Color(0xFF6600));
		
	}

	private void setDisplay() {

		JPanel pnlNorth = new JPanel();
		pnlNorth.add(lblLogo);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());

		JPanel pnlCenterW = new JPanel(new GridLayout(2, 1));
		JPanel pnlRoomName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlRoomName.add(lblRoomName);
		JPanel pnlRoomPeople = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlRoomPeople.add(lblRoomPeople);
		pnlCenterW.add(pnlRoomName);
		pnlCenterW.add(pnlRoomPeople);

		JPanel pnlCenterC = new JPanel(new GridLayout(2, 1));
		JPanel pnltfRoomName = new JPanel();
		pnltfRoomName.add(tfRoomName);
		JPanel pnlcbRoomPeople = new JPanel();
		pnlcbRoomPeople.add(cbRoomMaxPeople);

		pnlCenterC.add(pnltfRoomName);
		pnlCenterC.add(pnlcbRoomPeople);

		pnlCenter.add(pnlCenterW, BorderLayout.WEST);
		pnlCenter.add(pnlCenterC, BorderLayout.CENTER);

		JPanel pnlSouth = new JPanel();
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		pnlSouth.add(btnCreateRoom);
		pnlSouth.add(btnCancel);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	// ��ư ������
	private void addListeners() {
		ActionListener alistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == btnCancel) {
					// ��� ��ư
					// new WaitingRoom_GUI(waitingroom, sock, oos, ois);
					dispose();
				} else if (obj == btnCreateRoom) {
					// �� ���� ��ư
					roomName = tfRoomName.getText();
					maxPeople = (int) cbRoomMaxPeople.getSelectedItem();
					
					roomName = roomName.replaceAll(" ", "");

					if (roomName.equals("")) {
						JOptionPane.showMessageDialog(CreateRoom_GUI.this, "�� �̸��� �Է����ּ���.");
					} else if (roomName.trim().length() > 10) {
						JOptionPane.showMessageDialog(CreateRoom_GUI.this, "�� �̸��� 10�� ���Ϸ� �Է����ּ���.");
					} else {

						roomInfo = new ChatRoomInfo(roomName, maxPeople, name);
						sd = new SendData(ClientProtocol.CREATE_CHATROOM, roomInfo);
						Object object = null;
						try {

							oos.writeObject(sd);
							oos.flush();
							oos.reset();

						} catch (Exception e1) {
//							e1.printStackTrace();
						}
						setVisible(false);
					}
				}

			}
		};

		btnCancel.addActionListener(alistener);
		btnCreateRoom.addActionListener(alistener);

		// �޺��ڽ� ������
		ItemListener ilistener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					maxPeople = (int) cbRoomMaxPeople.getSelectedItem();
				}
			}
		};
		cbRoomMaxPeople.addItemListener(ilistener);

	}

	private void showFrame() {
		setIconImage(new ImageIcon("home.png").getImage());
		setTitle("Create Room");
		// setSize(350,180);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
