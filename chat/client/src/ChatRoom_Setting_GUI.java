
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

public class ChatRoom_Setting_GUI extends JDialog {

	private JLabel lblLogo; // 로고
	private JLabel lblRoomName; // 방이름
	private JLabel lblRoomPeople; // 방인원

	private JTextField tfRoomName; // 방이름
	private JComboBox<Integer> cbRoomMaxPeople; // 방인원

	private JButton btnUpdate; // 수정 버튼
	private JButton btnCancel; // 취소 버튼
	private String title;
	private ObjectOutputStream oos;
	private ChatRoom_GUI crg;

	public ChatRoom_Setting_GUI(ChatRoom_GUI crg, Socket sock, ObjectOutputStream oos, String title) {
		super(crg, true);
		this.title = title;
		this.oos = oos;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		lblLogo = new JLabel(new ImageIcon("sub_logo.png"));
		lblRoomName = new JLabel("방이름:");
		lblRoomPeople = new JLabel("참여인원:");
		lblRoomPeople.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		tfRoomName = new JTextField(10);
		tfRoomName.setDocument(new JTextFieldLimit(10));

		cbRoomMaxPeople = new JComboBox<Integer>();
		for (int i = 1; i <= 10; i++) {
			cbRoomMaxPeople.addItem(i);
		}
		cbRoomMaxPeople.setPreferredSize(new Dimension(115, 24));
		btnUpdate = new JButton("Update");
		btnUpdate.setUI(new Design_ButtonUI());
		btnUpdate.setPreferredSize(new Dimension(76, 28));
		btnUpdate.setBackground(new Color(0x66CC66));
		btnCancel = new JButton("Cancel");
		btnCancel.setUI(new Design_ButtonUI());
		btnCancel.setPreferredSize(new Dimension(76, 28));
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
		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnCancel);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	// 버튼 리스너
	private void addListeners() {
		ActionListener alistener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == btnCancel) {
					dispose();
				} else if (obj == btnUpdate) {

					String changeTitle = tfRoomName.getText();
					int maxPeople = (int) cbRoomMaxPeople.getSelectedItem();

					changeTitle = changeTitle.replaceAll(" ", "");

					if (tfRoomName.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "공백란을 채워주세요");
					} else if (changeTitle.trim().length() > 10) {
						JOptionPane.showMessageDialog(ChatRoom_Setting_GUI.this, "방 이름은 10자 이하로 입력해주세요.");
					} else {
						SendData sd = new SendData(ClientProtocol.CHATROOM_SETTING, title, changeTitle, maxPeople);
						try {
							oos.writeObject(sd);
							oos.flush();
							oos.reset();
							dispose();
						} catch (IOException e2) {
							// e2.printStackTrace();
						}
					}
				}
			}
		};

		btnCancel.addActionListener(alistener);
		btnUpdate.addActionListener(alistener);

		// 인원수 리스너
		ItemListener ilistener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Object obj = cbRoomMaxPeople.getSelectedItem();

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
