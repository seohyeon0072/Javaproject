
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class WaitingRoom_GUI extends JFrame {

	private JLabel lblRoomList; // ���� �̸�
	private JLabel lblSearchRoom; // �� �˻� �̸�
	private JLabel lblWaitingPeople; // ����ο� �̸�

	private JTextField tfSearchRoom; // �� �˻� �Է�â
	private JTextField tfWaitingRoomChat; // ���� ä�� �Է�â

	private JTextArea taWaitingRoomChat; // ���� ä�� ȭ��

	private JComboBox<String> cbInputOption; // ä�� �Է� �ɼ�

	private JButton btnCreateRoom; // �� ���� ��ư
	private JButton btnExit; // ������ ��ư
	private JButton btnSearch; // �˻���ư
	private JButton btnSearchAll; // �˻� ���ΰ�ħ

	private DefaultTableModel model;
	private JTable table; // ����

	private JPopupMenu waitingUserListPopupMenu; // ��������Ʈ �˾��޴�
	private JMenuItem w_whisper_Menu; // ��������Ʈ �˾��޴� ������

	private JList<String> waitingRoomUserList; // ��������Ʈ
	private DefaultListModel<String> waitingRoomUserModel; // ��������Ʈ ��

	private JScrollPane scrollpane; // ���Ͽ� ��ũ�ѹ�
	private JScrollPane scrollpane2; // ��������Ʈ�� ��ũ�ѹ�
	private JScrollPane scrollpane3; // ä��â ��ũ�ѹ�

	private String[] title = { "����", "����", "�ο���" };

	private Vector<String> colName;
	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private SendData send;
	private int code;
	private WaitingRoom waitingroom;
	private Vector<User> waitUserList;
	private Vector<ChatRoom> croomlist;
	private Vector<ChatRoomInfo> allChatRoomList;
	private ChatRoomInfo roomInfo;
	private String name;
	private Vector<ChatRoomInfo> vec;
	private int count = 1;
	private Vector<String> info;

	private boolean Exitflag;

	//���� GUI
	public WaitingRoom_GUI(Socket sock, ObjectOutputStream oos, ObjectInputStream ois, WaitingRoom waitingroom,
			String name) {

		this.waitingroom = waitingroom;
		waitUserList = waitingroom.getWatingRoomUserList();
		allChatRoomList = waitingroom.getAllChatRoomList();

		croomlist = new Vector<ChatRoom>();

		this.sock = sock;
		this.oos = oos;
		this.ois = ois;
		this.name = name;

		init();
		connect();
		setDisplay();
		addListeners();
		showFrame();

	}

	public WaitingRoom_GUI(Socket sock, ObjectOutputStream oos, ObjectInputStream ois, Vector<User> waitUserList,
			Vector<ChatRoomInfo> allChatRoomList, String name) {

		this.waitUserList = waitUserList;
		this.allChatRoomList = allChatRoomList;

		this.sock = sock;
		this.oos = oos;
		this.ois = ois;
		this.name = name;

		init();
		connect();
		setDisplay();
		addListeners();
		showFrame();

	}

	private void init() {

		lblRoomList = new JLabel("");
		lblSearchRoom = new JLabel("��˻�: ");
		lblSearchRoom.setFont(new Font("���� ���", Font.BOLD, 15));
		lblSearchRoom.setBorder(null);
		// lblSearchRoom.setPreferredSize(new Dimension(32, 32));
		lblWaitingPeople = new JLabel("����" + "(" + waitUserList.size() + ")");
		lblWaitingPeople.setFont(new Font("���� ���", Font.BOLD, 13));
		taWaitingRoomChat = new JTextArea(10, 49);
		taWaitingRoomChat.setEditable(false);
		taWaitingRoomChat.setLineWrap(true);
		cbInputOption = new JComboBox<String>();
		cbInputOption.setPreferredSize(new Dimension(70, 24));
		cbInputOption.addItem("����");
		cbInputOption.addItem("�ӼӸ�");
		tfSearchRoom = new JTextField(12);
		tfSearchRoom.setPreferredSize(new Dimension(0, 24));
		tfSearchRoom.setDocument(new JTextFieldLimit(12));

		tfWaitingRoomChat = new JTextField(44);
		tfWaitingRoomChat.setDocument(new JTextFieldLimit(100));

		btnCreateRoom = new JButton("Create");
		btnCreateRoom.setUI(new Design_ButtonUI());
		btnCreateRoom.setPreferredSize(new Dimension(95, 26));
		btnCreateRoom.setBackground(new Color(0x3399FF));

		btnSearch = new JButton(new ImageIcon("search.png"));
		btnSearch.setBorderPainted(false); // ��ư �ܰ��� ����
		btnSearch.setContentAreaFilled(false); // ���� ���� ä��� ����
		btnSearch.setFocusPainted(false); // ���ý� �׵θ� ����
		btnSearch.setPreferredSize(new Dimension(28, 24));
		btnSearch.setToolTipText("��˻�");

		btnExit = new JButton("Exit");
		btnExit.setUI(new Design_ButtonUI());
		btnExit.setPreferredSize(new Dimension(53, 26));
		btnExit.setBackground(new Color(0xFFCC00));

		btnSearchAll = new JButton(new ImageIcon("refresh.png"));
		btnSearchAll.setBorderPainted(false); // ��ư �ܰ��� ����
		btnSearchAll.setContentAreaFilled(false); // ���� ���� ä��� ����
		btnSearchAll.setFocusPainted(false); // ���ý� �׵θ� ����
		btnSearchAll.setPreferredSize(new Dimension(28, 24));
		btnSearchAll.setToolTipText("���ΰ�ħ");

		waitingUserListPopupMenu = new JPopupMenu();
		w_whisper_Menu = new JMenuItem("�ӼӸ�");
		waitingUserListPopupMenu.add(w_whisper_Menu);

		waitingRoomUserList = new JList(new DefaultListModel<>());

		class MyCellRender extends DefaultListCellRenderer {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				setIcon(new ImageIcon("Basic.png"));

				if (value.equals(name)) {
					setText(name + " < �� >");
				}

				return this;
			}

			// ����
			public int getHorizontalAlignment() {
				return LEFT;
			}
		}

		waitingRoomUserList.setCellRenderer(new MyCellRender());

		waitingRoomUserModel = (DefaultListModel<String>) waitingRoomUserList.getModel();
		waitingRoomUserModel.removeAllElements();
		for (int i = 0; i < waitUserList.size(); i++) {
			waitingRoomUserModel.addElement(waitUserList.get(i).getId());
		}

		scrollpane2 = new JScrollPane(waitingRoomUserList);
		scrollpane2.setPreferredSize(new Dimension(130, 200));

		model = new DefaultTableModel(title, 0) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		for (int i = 0; i < allChatRoomList.size(); i++) {
			colName = new Vector<String>();
			colName.add(allChatRoomList.get(i).getTitle());
			colName.add(allChatRoomList.get(i).getChatRoom_host());
			colName.add(
					allChatRoomList.get(i).getChatRoomUserList().size() + "/" + allChatRoomList.get(i).getMaxPeople());
			model.addRow(colName);
		}

		table = new JTable(model);
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(400, 200));

		// ���̺� ��ũ�� ��å
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		table.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		table.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�

		// �÷� ����
		table.getColumn("����").setPreferredWidth(170);
		table.getColumn("�ο���").setPreferredWidth(40);
		table.setRowHeight(22);

		scrollpane3 = new JScrollPane(taWaitingRoomChat);
		scrollpane3.setPreferredSize(new Dimension(565, 200));

		// ���̺�� ����
		class MyRenderer extends DefaultTableCellRenderer {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (column == 0) {
					setIcon(new ImageIcon("home.png"));
				} else if (column == 1) {
					setIcon(new ImageIcon("Crown.png"));
				} else if (column == 2) {
					setIcon(new ImageIcon("team.png"));
				}

				// ������ ��
				if (isSelected) {
					setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
				} else {
					setBackground(Color.WHITE);
				}

				// �����, ������
				if (((row % 2) == 0)) {
					setBackground(new Color(0xffffff));
				} else {
					setBackground(new Color(0xf5f5f5));
				}

				// �÷� �ؽ�Ʈ ����
				if (column == 0) {
					this.setHorizontalAlignment(this.LEFT);
				} else {
					this.setHorizontalAlignment(this.CENTER);
				}

				return this;

			}
		}

		table.setDefaultRenderer(Object.class, new MyRenderer());

	}

	private void setDisplay() {

		JPanel pnlNorth = new JPanel(new BorderLayout());

		JPanel pnlNorthWest = new JPanel(new BorderLayout());

		JPanel pnlNorthWestTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlNorthWestTop.setBorder(BorderFactory.createEmptyBorder(0, 0, -5, 0));
		JPanel pnlbtnCreateRoom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlbtnCreateRoom.setBorder(BorderFactory.createEmptyBorder(0, 26, 0, 0));
		pnlbtnCreateRoom.add(btnCreateRoom);
		pnlNorthWestTop.add(lblRoomList);
		pnlNorthWestTop.add(lblSearchRoom);
		pnlNorthWestTop.add(tfSearchRoom);
		pnlNorthWestTop.add(btnSearch);
		pnlNorthWestTop.add(btnSearchAll);
		pnlNorthWestTop.add(pnlbtnCreateRoom);

		JPanel pnlNorthWestCenter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlNorthWestCenter.add(scrollpane);

		pnlNorthWest.add(pnlNorthWestTop, BorderLayout.NORTH);
		pnlNorthWest.add(pnlNorthWestCenter, BorderLayout.CENTER);

		JPanel pnlNorthEast = new JPanel(new BorderLayout());
		pnlNorthEast.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 9));

		JPanel pnlNorthEastTop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlNorthEastTop.setBorder(BorderFactory.createEmptyBorder(0, 0, -9, 0));
		JPanel pnllblWaitingPeople = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblWaitingPeople.setBorder(BorderFactory.createEmptyBorder(11, 0, 0, 0));
		pnllblWaitingPeople.add(lblWaitingPeople);
		pnlNorthEastTop.add(pnllblWaitingPeople);
		pnlNorthEastTop.add(btnExit);

		JPanel pnlNorthEastCenter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlNorthEastCenter.add(scrollpane2);

		pnlNorthEast.add(pnlNorthEastTop, BorderLayout.NORTH);
		pnlNorthEast.add(pnlNorthEastCenter, BorderLayout.CENTER);

		pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
		pnlNorth.add(pnlNorthEast, BorderLayout.EAST);

		JPanel pnlSouth = new JPanel(new BorderLayout());

		JPanel pnlSouthTop = new JPanel(new FlowLayout(FlowLayout.LEFT));

		pnlSouthTop.add(scrollpane3);

		JPanel pnlSouthCenter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSouthCenter.add(cbInputOption);
		pnlSouthCenter.add(tfWaitingRoomChat);

		pnlSouth.add(pnlSouthTop, BorderLayout.NORTH);
		pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);

		JPanel pnlAll = new JPanel(new BorderLayout());
		pnlAll.add(pnlNorth, BorderLayout.NORTH);
		pnlAll.add(pnlSouth, BorderLayout.SOUTH);

		add(pnlAll, BorderLayout.CENTER);
	}

	// ��ư ������
	private void addListeners() {

		ActionListener alistener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();

				if (obj == btnCreateRoom) {
					// �� ����
					new CreateRoom_GUI(WaitingRoom_GUI.this, sock, oos, ois, name);

					// setVisible(false);
				} else if (obj == btnExit) {
					int result = JOptionPane.showConfirmDialog(WaitingRoom_GUI.this, "�����Ͻðڽ��ϱ� ?", "����",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (result == 0) { // JOptionPane���� ���Ḧ ������ ����
						try {
							send = new SendData(ClientProtocol.EXIT_WAITINGROOM);
							oos.writeObject(send);
							oos.flush();
							oos.reset();
							// throw new Exception();
						} catch (Exception ex) {
//							ex.printStackTrace();
						}
						try {
							if (oos != null) {
								oos.close();
							}
						} catch (Exception e1) {
						}
						try {
							if (ois != null)
								ois.close();
						} catch (Exception e2) {
						}
						try {
							if (sock != null)
								sock.close();
						} catch (SocketException sock) {

						} catch (Exception e3) {
						}
						Exitflag = false;
//						System.out.println("Ŭ���̾�Ʈ�� ������ �����մϴ�");
						return;
					} else {

					}
				} else if (obj == btnSearch) {
					String chatTitle = tfSearchRoom.getText();
					send = new SendData(ClientProtocol.SEARCH_TITLE, chatTitle);
					try {
						oos.writeObject(send);
						oos.flush();
						oos.reset();
					} catch (Exception ae) {
//						ae.printStackTrace();
					}

				} else if (obj == btnSearchAll) {
					send = new SendData(ClientProtocol.CHATROOM_RETURN_ORIGINAL);
					try {
						oos.writeObject(send);
						oos.flush();
						oos.reset();
					} catch (Exception ae) {
//						ae.printStackTrace();
					}
					tfSearchRoom.setText("");
				}
			}
		};
		btnSearch.addActionListener(alistener);
		btnSearchAll.addActionListener(alistener);
		btnCreateRoom.addActionListener(alistener);
		btnExit.addActionListener(alistener);

		// �޺��ڽ� ������
		ItemListener ilistener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Object obj = cbInputOption.getSelectedItem();
					if (obj.equals("����")) {
						tfWaitingRoomChat.setText("");

					} else if (obj.equals("�ӼӸ�")) {

						tfWaitingRoomChat.setText("/to �ӼӸ� ����) /to ������̵� �����޼���");
						tfWaitingRoomChat.setSelectionStart(4);
						tfWaitingRoomChat.requestFocus();
					}
				}
			}
		};
		cbInputOption.addItemListener(ilistener);

		// X��ư ������
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(WaitingRoom_GUI.this, "�����Ͻðڽ��ϱ� ?", "����",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result == 0) { // JOptionPane���� ���Ḧ ������ ����
					try {
						send = new SendData(ClientProtocol.EXIT_WAITINGROOM);
						oos.writeObject(send);
						oos.flush();
						oos.reset();
					} catch (Exception ex) {
//						ex.printStackTrace();
					}
					try {
						if (oos != null) {
							oos.close();
						}
					} catch (Exception e1) {
					}
					try {
						if (ois != null)
							ois.close();
					} catch (Exception e2) {
					}
					try {
						if (sock != null)
							sock.close();
					} catch (SocketException sock) {

					} catch (Exception e3) {
					}
					Exitflag = false;
//					System.out.println("Ŭ���̾�Ʈ�� ������ �����մϴ�");
					return;
				} else {

				}
			}
		});

		// ���� ���콺 ������
		MouseListener mlistener = new MouseAdapter() {
			// ���콺�� ������
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			// ���콺�� ��������
			@Override
			public void mousePressed(MouseEvent e) {

			}

			// ���콺�� ��������
			@Override
			public void mouseExited(MouseEvent e) {

			}

			// ���콺�� �÷�����
			@Override
			public void mouseEntered(MouseEvent e) {

			}

			// ��ȭ�� ���� ����Ŭ�� ���� �� client->server 120
			@Override
			public void mouseClicked(MouseEvent e) {
				Object obj = e.getSource();
				if (e.getClickCount() == 2 && obj == table) {
					// new ChatRoom_GUI();
					String title = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
					try {
						send = new SendData(ClientProtocol.ENTER_CHATROOM, title);
						oos.writeObject(send);
						oos.flush();
						oos.reset();
					} catch (Exception e1) {
//						e1.printStackTrace();
					}
					// ���� , oos, ois
				}
			}
		};
		table.addMouseListener(mlistener);

		// ��������Ʈ ���콺 �̺�Ʈ
		waitingRoomUserList.addMouseListener(new MouseAdapter() {
			// ���콺�� ������
			@Override
			public void mouseReleased(MouseEvent e) {
				check(e);
			}

			// ���콺�� ��������
			@Override
			public void mousePressed(MouseEvent e) {
				check(e);
			}
		});

		// ��������Ʈ �˾��޴� �̺�Ʈ
		ActionListener menuListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent pe) {
				Object obj = pe.getSource();
				if (obj == w_whisper_Menu) {

					cbInputOption.setSelectedItem("�ӼӸ�");
					tfWaitingRoomChat.setText("/to " + waitingRoomUserList.getSelectedValue() + " ");
					int start = tfWaitingRoomChat.getText().indexOf(" ") + 1;
					int end = tfWaitingRoomChat.getText().indexOf(" ", start);

					tfWaitingRoomChat.setSelectionStart(end + 1);
					tfWaitingRoomChat.requestFocus();
				}
			}
		};
		w_whisper_Menu.addActionListener(menuListener);
		// ä���Է�â ����Ű�� �Է�
		tfWaitingRoomChat.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					if (cbInputOption.getSelectedItem().equals("�ӼӸ�")) {
						// if(tfWaitingRoomChat.getText().equals("/to ")) {
						String msg = tfWaitingRoomChat.getText();

						if (msg.trim().length() > 100) {
							JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "100���̻� �Է��� �� �����ϴ�.");
							tfWaitingRoomChat.setText("");
						} else {
								
								SendData send = new SendData(ClientProtocol.SEND_WISPER_MESSAGE_CHATROOM, msg);
								try {
									oos.writeObject(send);
									oos.flush();
									oos.reset();
								} catch (Exception me) {
//									me.printStackTrace();
								}
								int start = tfWaitingRoomChat.getText().indexOf(" ") + 1;
								int end = tfWaitingRoomChat.getText().indexOf(" ", start);

								tfWaitingRoomChat.setSelectionStart(end + 1);
								tfWaitingRoomChat.requestFocus();
							}
						
					} else if (cbInputOption.getSelectedItem().equals("����")) {
						String msg = tfWaitingRoomChat.getText();

						if (msg.trim().length() > 100) {
							JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "100���̻� �Է��� �� �����ϴ�.");
							tfWaitingRoomChat.setText("");
						} else {

							if (msg.trim().length() > 0) {
								SendData send = new SendData(ClientProtocol.SEND_MESSAGE_WAITINGROOM, msg);
								try {
									oos.writeObject(send);
									oos.flush();
									oos.reset();
								} catch (Exception me) {
//									me.printStackTrace();
								}
								tfWaitingRoomChat.selectAll();
								tfWaitingRoomChat.requestFocus();
								tfWaitingRoomChat.setText("");
							}
						}

						// taWaitingRoomChat.append(msg+"\n");
					}
				}
			}
		});

		// �˻�â ����Ű
		tfSearchRoom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					String chatTitle = tfSearchRoom.getText();
					send = new SendData(ClientProtocol.SEARCH_TITLE, chatTitle);
					try {
						oos.writeObject(send);
						oos.flush();
						oos.reset();
					} catch (Exception ae) {
//						ae.printStackTrace();
					}
					tfSearchRoom.selectAll();
					tfSearchRoom.requestFocus();
					tfSearchRoom.setText("");
				}
			}
		});
	}

	//������
	ExecutorService executorService = Executors.newFixedThreadPool(20);

	// class WinInputThread extends Thread {
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

			int poolSize = threadPoolExecutor.getPoolSize();// ������ Ǯ ������ ���
			String threadName = Thread.currentThread().getName();// ������ Ǯ�� �ִ� �ش�
																	// ������ �̸� ���

			// System.out.println("[�� ������ ����:" + poolSize + "] �۾� ������ �̸�:
			// "+threadName);

			try {
				Object obj = null;
				while ((obj = ois.readObject()) != null) {
					// System.out.println("���� Ŭ���̾�Ʈ code " + code);
					send = (SendData) obj;
					code = send.getCode();

					switch (code) {

					// ���� ���� �޽��� �ޱ�
					case ServerProtocol.ENTRANCE_SUCCESS:
						String id = (String) send.getObj()[0];

						taWaitingRoomChat.append(id + "���� ������ �ϼ̽��ϴ�." + "\n");
						break;

					// �� ���� -> ����
					case ServerProtocol.CREATE_CHATROOM_SUCCESS:
						ChatRoomInfo ri = (ChatRoomInfo) send.getObj()[0];
						dispose();
						new ChatRoom_GUI(ri, sock, oos, ois, name);

						break;
					// ä�ù� �̸� �ߺ� �϶�
					case ServerProtocol.CREATE_CHATROOM_FAIL:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "ä�ù� �̸� �ߺ��Դϴ� !!:) ");
						break;

					// ������� �ο��ʰ�
					case ServerProtocol.ENTER_CHATROOM_MAX_USER_FAIL:

						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "�ο��ʰ��� ���� �Ͻ� �� �����ϴ�!!:) ");
						break;

					// �������� ���� ����Ʈ ����// ++++++++++++++++++++
					// �̹� ����� ������
					case ServerProtocol.ENTER_CHATROOM_KICKOUT_USER_FAIL:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "�ش�濡�� ����� ������ ���� �Ͻ� �� �����ϴ�!!:) ");
						break;

					case ServerProtocol.INVITE_TO_ROOM:
						String SendUser = (String) send.getObj()[0];
						String RoomTitle = (String) send.getObj()[1];
						// String SendUser = (String)send.getObj()[2];
						// System.out.println("Ŭ�󿡼� 1070 �ʴ�޽��� ����");

						int result = JOptionPane.showConfirmDialog(WaitingRoom_GUI.this,
								RoomTitle + "����" + ", " + SendUser + "���� �ʴ븦 �����Ͻðڽ��ϱ�?", "�ʴ�",
								JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

						if (result == 0) {
							// ����
							SendData send = new SendData(ClientProtocol.REPLY_INVITE_USER, RoomTitle, name, SendUser);
							try {
								oos.writeObject(send);
								oos.flush();
								oos.reset();
							} catch (Exception me) {
//								me.printStackTrace();
							}
						} else {
							// ����
							SendData send = new SendData(ClientProtocol.INVITE_REJECT_CLIENT, SendUser);
							try {
								oos.writeObject(send);
								oos.flush();
								oos.reset();
							} catch (Exception me) {
							}
						}

						break;
					// ���� �޽��� �ޱ�
					case ServerProtocol.RECEIVE_MESSAGE_WAITINGROOM:
						String message = (String) send.getObj()[0];
						taWaitingRoomChat.append(message + "\n");
						taWaitingRoomChat.setCaretPosition(taWaitingRoomChat.getDocument().getLength());
						break;

					// �ӼӸ� ����
					case ServerProtocol.RECEIVE_WISPER_MESSAGE_CHATROOM_SUCCSS:
						String wmessage = (String) send.getObj()[0];
						taWaitingRoomChat.append(wmessage + "\n");
						taWaitingRoomChat.setCaretPosition(taWaitingRoomChat.getDocument().getLength());
						break;

					// ������ �ӼӸ� ������ ����
					case ServerProtocol.RECEIVE_WISPER_MESSAGE_CHATROOM_FAIL_MYSELF:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "�����Դ� �ӼӸ��� �Ͻ� �� �����ϴ�!!");
						break;

					// �ӼӸ� �ߴµ� ���̵� �������� ���� ��
					case ServerProtocol.RECEIVE_WISPER_MESSAGE_FAIL_NOT_FOUND:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "�������� �ʴ� ���̵��Դϴ�.");
						break;

					// ���� �Է��� �������� �� ����Ʈ�� ���� ��
					case ServerProtocol.RESULT_CHATROOM_LIST:

						vec = (Vector<ChatRoomInfo>) send.getObj()[0]; //
						count = (int) send.getObj()[1];
						// System.out.println("2010" + vec.get(0).getTitle());

						model.setNumRows(0);

						for (int i = 0; i < vec.size(); i++) {
							Vector<String> info = new Vector<String>();
							info.add(vec.get(i).getTitle());
							info.add(vec.get(i).getChatRoom_host());
							info.add(vec.get(i).getChatRoomUserList().size() + "/" + vec.get(i).getMaxPeople());
							model.addRow(info);
						}

						break;

					// ���� �Է��� ���� ���� ��
					case ServerProtocol.NOT_FOUNT_CHATROOM:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "�˻��Ͻ� ä�ù��� ã�� �� �����ϴ�");
						break;

					// ���� �ʱ�ȭ
					case ServerProtocol.CHATROOMLIST_ORIGINAL:
						count = 1;
						allChatRoomList = (Vector<ChatRoomInfo>) send.getObj()[0]; //

						model.setNumRows(0);

						for (int i = 0; i < allChatRoomList.size(); i++) {
							colName = new Vector<String>();
							colName.add(allChatRoomList.get(i).getTitle());
							colName.add(allChatRoomList.get(i).getChatRoom_host());
							colName.add(allChatRoomList.get(i).getChatRoomUserList().size() + "/"
									+ allChatRoomList.get(i).getMaxPeople());
							model.addRow(colName);
						}
						break;

					// ��ȭ�� ����
					case ServerProtocol.ENTER_CHATROOM_SUCCESS:
						roomInfo = (ChatRoomInfo) send.getObj()[0];

						// setVisible(false);
						dispose();
						new ChatRoom_GUI(roomInfo, sock, oos, ois, name);

						break;
					// ���� ���� �޽��� �ޱ�
					case ServerProtocol.UDATE_WAITING_INFORMATION:
						String id1 = (String) send.getObj()[0];

						taWaitingRoomChat.append(id1 + "���� ������ �����ϼ̽��ϴ�." + "\n");

						break;

					// ���� ������ �޽��������� + ���� ��������Ʈ ������Ʈ
					case ServerProtocol.EXIT_MESSAGE_USERLIST_UPDATE:

						waitingRoomUserModel.removeAllElements();
						waitUserList = (Vector<User>) send.getObj()[0];

						for (int i = 0; i < waitUserList.size(); i++) {
							waitingRoomUserModel.addElement(waitUserList.get(i).getId());
						}
						lblWaitingPeople.setText("����" + "(" + waitUserList.size() + ")");
						break;

					// ���ǿ��� userlist + ����
					case ServerProtocol.WAITINGROOM_USERLIST_AND_ROOMLIST:
						// System.out.println("���� ������Ʈ ~~~");
						waitUserList = (Vector<User>) send.getObj()[0];
						lblWaitingPeople.setText("����" + "(" + waitUserList.size() + ")");
						waitingRoomUserModel.removeAllElements();
						for (int i = 0; i < waitUserList.size(); i++) {
							waitingRoomUserModel.addElement(waitUserList.get(i).getId());
						}
						model.setNumRows(0);
						allChatRoomList = (Vector<ChatRoomInfo>) send.getObj()[1];
						// �˻� ���� ������ ��ü update x
						if (count == 0) {

							for (int j = 0; j < vec.size(); j++) {
								for (int i = 0; i < allChatRoomList.size(); i++) {
									if (vec.get(j).getTitle().equals(allChatRoomList.get(i).getTitle())) {
										Vector<String> info = new Vector<String>();
										info.add(allChatRoomList.get(i).getTitle());
										info.add(allChatRoomList.get(i).getChatRoom_host());
										info.add(allChatRoomList.get(i).getChatRoomUserList().size() + "/"
												+ allChatRoomList.get(i).getMaxPeople());

										model.addRow(info);
									}
								}
							}

							break;
						} else {
							for (int i = 0; i < allChatRoomList.size(); i++) {
								colName = new Vector<String>();
								colName.add(allChatRoomList.get(i).getTitle());
								colName.add(allChatRoomList.get(i).getChatRoom_host());
								colName.add(allChatRoomList.get(i).getChatRoomUserList().size() + "/"
										+ allChatRoomList.get(i).getMaxPeople());
								model.addRow(colName);
							}
						}
						break;

					case ServerProtocol.WAITINGROOM_UPDATING:
						// System.out.println("���� ������Ʈ ~~~");

						model.setNumRows(0);

						allChatRoomList = (Vector<ChatRoomInfo>) send.getObj()[0];

						if (count == 0) {

							for (int j = 0; j < vec.size(); j++) {
								for (int i = 0; i < allChatRoomList.size(); i++) {
									if (vec.get(j).getTitle().equals(allChatRoomList.get(i).getTitle())) {
										Vector<String> info = new Vector<String>();
										info.add(allChatRoomList.get(i).getTitle());
										info.add(allChatRoomList.get(i).getChatRoom_host());
										info.add(allChatRoomList.get(i).getChatRoomUserList().size() + "/"
												+ allChatRoomList.get(i).getMaxPeople());

										model.addRow(info);
									}
								}
							}
						} else {
							for (int i = 0; i < allChatRoomList.size(); i++) {
								colName = new Vector<String>();
								colName.add(allChatRoomList.get(i).getTitle());
								colName.add(allChatRoomList.get(i).getChatRoom_host());
								colName.add(allChatRoomList.get(i).getChatRoomUserList().size() + "/"
										+ allChatRoomList.get(i).getMaxPeople());
								model.addRow(colName);
							}
							break;
						}

					}
					if (code == ServerProtocol.CREATE_CHATROOM_SUCCESS
							|| code == ServerProtocol.ENTER_CHATROOM_SUCCESS) {
						break;
					}
				}

			} catch (SocketException e) {

				dispose();
				new Start_Connect_GUI();

			} catch (Exception e1) {
//				e1.printStackTrace();
				new Start_Connect_GUI();
			} finally {

			}
		}
	};

	// }

	// ��������Ʈ���� �˾��޴��� ���õ� ���� �� ��������
	public void check(MouseEvent pe) {
		if (pe.isPopupTrigger()) { // if the event shows the menu
			waitingRoomUserList.setSelectedIndex(waitingRoomUserList.locationToIndex(pe.getPoint()));
			waitingUserListPopupMenu.show(waitingRoomUserList, pe.getX(), pe.getY());
		}
	}

	private void showFrame() {
		setIconImage(new ImageIcon("home.png").getImage());
		setTitle("Waiting Room");
		pack();
		// setSize(600,450);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private void connect() {
		// �Է� �Ϸ��
		try {
			executorService.submit(runnable);
			// Thread wit = new Thread(runnable);
			// WinInputThread wit = new WinInputThread();
			// wit.start();
		} catch (Exception e) {
			// System.out.println("������ ���ӽ� ������ �߻��Ͽ����ϴ�.");
//			System.out.println(e);
		} finally {
			executorService.shutdown();
		}

	}

	private void exit() {
		try {
			if (ois != null) {
				ois.close();
			}
		} catch (Exception e) {
		}
		try {
			if (oos != null) {
				oos.close();
			}
		} catch (Exception e) {
		}
		try {
			if (sock != null)
				sock.close();
		} catch (Exception e) {
		}

//		System.out.println("Ŭ���̾�Ʈ�� ������ �����մϴ�.");
		System.exit(0);
	}
}
