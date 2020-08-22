
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Invite_GUI extends JDialog {

	private JLabel lblInviteList; // 초대 창

	private JButton btnInvite; // 초대
	private JButton btnCancel; // 취소

	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private JList<String> waitingRoomUserList; // 대기실 유저 리스트
	private DefaultListModel<String> waitingRoomUserModel; // 대기실 유저 리스트 모델

	private JScrollPane scrollpane; // 대기실 유저목록 스크롤바

	private Vector<User> waitUserList; 

	private String userName; // 초대리스트에 선택된 이름
	private String name; // 내이름
	
	private String title2;
	
	private ChatRoom_GUI crg;
	
	public Invite_GUI(ChatRoom_GUI crg, Socket sock, ObjectOutputStream oos, ObjectInputStream ois, String name,
			Vector<User> waitUserList, String title2) {
		super(crg, true);
		this.sock = sock;
		this.oos = oos;
		this.ois = ois;
		this.name = name;
		this.waitUserList = waitUserList;
		this.title2 = title2;
		 	init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init() {
		lblInviteList = new JLabel(new ImageIcon("sub_logo.png"));

		btnInvite = new JButton("Invite");
		btnInvite.setUI(new Design_ButtonUI());
		btnInvite.setPreferredSize(new Dimension(80,28));
		btnInvite.setBackground(new Color(0x3399FF));
		btnCancel = new JButton("Cancel");
		btnCancel.setUI(new Design_ButtonUI());
		btnCancel.setPreferredSize(new Dimension(80,28));
		btnCancel.setBackground(new Color(0xFFCC00));

		waitingRoomUserList = new JList(new DefaultListModel<>());

		
		class MyCellRender extends DefaultListCellRenderer {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			
					setIcon(new ImageIcon("Basic.png"));
				
				if (value.equals(name)) {
					setText(name + " < 나 >");
				}

				return this;
			}

			// 정렬
			public int getHorizontalAlignment() {
				return LEFT;
			}
		}

		waitingRoomUserList.setCellRenderer(new MyCellRender());
		
		waitingRoomUserModel = (DefaultListModel<String>) waitingRoomUserList.getModel();

		
		waitingRoomUserModel.removeAllElements();
		// ++++++++++++++++++++
		for (int i = 0; i < waitUserList.size(); i++) {
			waitingRoomUserModel.addElement(waitUserList.get(i).getId());
		}
		// ++++++++++++++++++++
		scrollpane = new JScrollPane(waitingRoomUserList);
		scrollpane.setPreferredSize(new Dimension(120, 200));
		scrollpane.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY),"초대목록(대기실)"));

	}

	private void setDisplay() {
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(lblInviteList);

		JPanel pnlCenter = new JPanel();
		pnlCenter.add(scrollpane);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnInvite);
		pnlSouth.add(btnCancel);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void addListeners() {

		// 버튼 리스너
		ActionListener alistener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == btnInvite) {
					// ++++++++++++++++++++
				 	SendData send = new SendData(ClientProtocol.INVITE_USER,userName,title2,name);
					 	try {
							oos.writeObject(send);
							oos.flush();
							oos.reset();
							dispose();
						} catch (IOException e2) {
//							e2.printStackTrace();
						}
					}else if (obj == btnCancel) {
						dispose();
				} 
			}
		};
		btnInvite.addActionListener(alistener);
		btnCancel.addActionListener(alistener);

		// 유저리스트 마우스 이벤트
		waitingRoomUserList.addMouseListener(new MouseAdapter() {

			// 마우스를 눌렀을때
			@Override
			public void mousePressed(MouseEvent e) {
				//
				if(waitingRoomUserList.getSelectedValue() != null) {
					userName = waitingRoomUserList.getSelectedValue().toString();
				}
			}
		});

		// X창 종료 리스너
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(Invite_GUI.this, "나가시겠습니까 ?", "나가기",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result == 0) {
					dispose();
				} else {

				}
			}
		});
	}

	private void showFrame() {
		setIconImage(new ImageIcon("home.png").getImage());
		setTitle("Invite");
		pack();
		// setSize(250,180);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

}
