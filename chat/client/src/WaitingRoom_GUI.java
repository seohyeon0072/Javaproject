
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

	private JLabel lblRoomList; // 방목록 이름
	private JLabel lblSearchRoom; // 방 검색 이름
	private JLabel lblWaitingPeople; // 대기인원 이름

	private JTextField tfSearchRoom; // 방 검색 입력창
	private JTextField tfWaitingRoomChat; // 대기실 채팅 입력창

	private JTextArea taWaitingRoomChat; // 대기실 채팅 화면

	private JComboBox<String> cbInputOption; // 채팅 입력 옵션

	private JButton btnCreateRoom; // 방 개설 버튼
	private JButton btnExit; // 나가기 버튼
	private JButton btnSearch; // 검색버튼
	private JButton btnSearchAll; // 검색 새로고침

	private DefaultTableModel model;
	private JTable table; // 방목록

	private JPopupMenu waitingUserListPopupMenu; // 유저리스트 팝업메뉴
	private JMenuItem w_whisper_Menu; // 유저리스트 팝업메뉴 아이템

	private JList<String> waitingRoomUserList; // 유저리스트
	private DefaultListModel<String> waitingRoomUserModel; // 유저리스트 모델

	private JScrollPane scrollpane; // 방목록용 스크롤바
	private JScrollPane scrollpane2; // 유저리스트용 스크롤바
	private JScrollPane scrollpane3; // 채팅창 스크롤바

	private String[] title = { "제목", "방장", "인원수" };

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

	//대기실 GUI
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
		lblSearchRoom = new JLabel("방검색: ");
		lblSearchRoom.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblSearchRoom.setBorder(null);
		// lblSearchRoom.setPreferredSize(new Dimension(32, 32));
		lblWaitingPeople = new JLabel("대기실" + "(" + waitUserList.size() + ")");
		lblWaitingPeople.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		taWaitingRoomChat = new JTextArea(10, 49);
		taWaitingRoomChat.setEditable(false);
		taWaitingRoomChat.setLineWrap(true);
		cbInputOption = new JComboBox<String>();
		cbInputOption.setPreferredSize(new Dimension(70, 24));
		cbInputOption.addItem("대기실");
		cbInputOption.addItem("귓속말");
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
		btnSearch.setBorderPainted(false); // 버튼 외곽선 삭제
		btnSearch.setContentAreaFilled(false); // 내용 영역 채우기 안함
		btnSearch.setFocusPainted(false); // 선택시 테두리 안함
		btnSearch.setPreferredSize(new Dimension(28, 24));
		btnSearch.setToolTipText("방검색");

		btnExit = new JButton("Exit");
		btnExit.setUI(new Design_ButtonUI());
		btnExit.setPreferredSize(new Dimension(53, 26));
		btnExit.setBackground(new Color(0xFFCC00));

		btnSearchAll = new JButton(new ImageIcon("refresh.png"));
		btnSearchAll.setBorderPainted(false); // 버튼 외곽선 삭제
		btnSearchAll.setContentAreaFilled(false); // 내용 영역 채우기 안함
		btnSearchAll.setFocusPainted(false); // 선택시 테두리 안함
		btnSearchAll.setPreferredSize(new Dimension(28, 24));
		btnSearchAll.setToolTipText("새로고침");

		waitingUserListPopupMenu = new JPopupMenu();
		w_whisper_Menu = new JMenuItem("귓속말");
		waitingUserListPopupMenu.add(w_whisper_Menu);

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

		// 테이블 스크롤 정책
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		table.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가

		// 컬럼 길이
		table.getColumn("제목").setPreferredWidth(170);
		table.getColumn("인원수").setPreferredWidth(40);
		table.setRowHeight(22);

		scrollpane3 = new JScrollPane(taWaitingRoomChat);
		scrollpane3.setPreferredSize(new Dimension(565, 200));

		// 테이블용 셋팅
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

				// 선택한 행
				if (isSelected) {
					setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
				} else {
					setBackground(Color.WHITE);
				}

				// 행단위, 셀단위
				if (((row % 2) == 0)) {
					setBackground(new Color(0xffffff));
				} else {
					setBackground(new Color(0xf5f5f5));
				}

				// 컬럼 텍스트 정렬
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

	// 버튼 리스너
	private void addListeners() {

		ActionListener alistener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();

				if (obj == btnCreateRoom) {
					// 방 생성
					new CreateRoom_GUI(WaitingRoom_GUI.this, sock, oos, ois, name);

					// setVisible(false);
				} else if (obj == btnExit) {
					int result = JOptionPane.showConfirmDialog(WaitingRoom_GUI.this, "종료하시겠습니까 ?", "종료",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (result == 0) { // JOptionPane에서 종료를 누르면 할일
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
//						System.out.println("클라이언트의 접속을 종료합니다");
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

		// 콤보박스 리스너
		ItemListener ilistener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Object obj = cbInputOption.getSelectedItem();
					if (obj.equals("대기실")) {
						tfWaitingRoomChat.setText("");

					} else if (obj.equals("귓속말")) {

						tfWaitingRoomChat.setText("/to 귓속말 예시) /to 상대방아이디 보낼메세지");
						tfWaitingRoomChat.setSelectionStart(4);
						tfWaitingRoomChat.requestFocus();
					}
				}
			}
		};
		cbInputOption.addItemListener(ilistener);

		// X버튼 리스너
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(WaitingRoom_GUI.this, "종료하시겠습니까 ?", "종료",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result == 0) { // JOptionPane에서 종료를 누르면 할일
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
//					System.out.println("클라이언트의 접속을 종료합니다");
					return;
				} else {

				}
			}
		});

		// 방목록 마우스 리스너
		MouseListener mlistener = new MouseAdapter() {
			// 마우스를 땟을때
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			// 마우스를 눌렀을때
			@Override
			public void mousePressed(MouseEvent e) {

			}

			// 마우스를 내렸을때
			@Override
			public void mouseExited(MouseEvent e) {

			}

			// 마우스를 올렸을때
			@Override
			public void mouseEntered(MouseEvent e) {

			}

			// 대화방 입장 더블클릭 했을 때 client->server 120
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
					// 소켓 , oos, ois
				}
			}
		};
		table.addMouseListener(mlistener);

		// 유저리스트 마우스 이벤트
		waitingRoomUserList.addMouseListener(new MouseAdapter() {
			// 마우스를 땟을때
			@Override
			public void mouseReleased(MouseEvent e) {
				check(e);
			}

			// 마우스를 눌렀을때
			@Override
			public void mousePressed(MouseEvent e) {
				check(e);
			}
		});

		// 유저리스트 팝업메뉴 이벤트
		ActionListener menuListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent pe) {
				Object obj = pe.getSource();
				if (obj == w_whisper_Menu) {

					cbInputOption.setSelectedItem("귓속말");
					tfWaitingRoomChat.setText("/to " + waitingRoomUserList.getSelectedValue() + " ");
					int start = tfWaitingRoomChat.getText().indexOf(" ") + 1;
					int end = tfWaitingRoomChat.getText().indexOf(" ", start);

					tfWaitingRoomChat.setSelectionStart(end + 1);
					tfWaitingRoomChat.requestFocus();
				}
			}
		};
		w_whisper_Menu.addActionListener(menuListener);
		// 채팅입력창 엔터키로 입력
		tfWaitingRoomChat.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					if (cbInputOption.getSelectedItem().equals("귓속말")) {
						// if(tfWaitingRoomChat.getText().equals("/to ")) {
						String msg = tfWaitingRoomChat.getText();

						if (msg.trim().length() > 100) {
							JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "100자이상 입력할 수 없습니다.");
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
						
					} else if (cbInputOption.getSelectedItem().equals("대기실")) {
						String msg = tfWaitingRoomChat.getText();

						if (msg.trim().length() > 100) {
							JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "100자이상 입력할 수 없습니다.");
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

		// 검색창 엔터키
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

	//스레드
	ExecutorService executorService = Executors.newFixedThreadPool(20);

	// class WinInputThread extends Thread {
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

			int poolSize = threadPoolExecutor.getPoolSize();// 스레드 풀 사이즈 얻기
			String threadName = Thread.currentThread().getName();// 스레드 풀에 있는 해당
																	// 스레드 이름 얻기

			// System.out.println("[총 스레드 개수:" + poolSize + "] 작업 스레드 이름:
			// "+threadName);

			try {
				Object obj = null;
				while ((obj = ois.readObject()) != null) {
					// System.out.println("대기실 클라이언트 code " + code);
					send = (SendData) obj;
					code = send.getCode();

					switch (code) {

					// 접속 성공 메시지 받기
					case ServerProtocol.ENTRANCE_SUCCESS:
						String id = (String) send.getObj()[0];

						taWaitingRoomChat.append(id + "님이 접속을 하셨습니다." + "\n");
						break;

					// 방 생성 -> 입장
					case ServerProtocol.CREATE_CHATROOM_SUCCESS:
						ChatRoomInfo ri = (ChatRoomInfo) send.getObj()[0];
						dispose();
						new ChatRoom_GUI(ri, sock, oos, ois, name);

						break;
					// 채팅방 이름 중복 일때
					case ServerProtocol.CREATE_CHATROOM_FAIL:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "채팅방 이름 중복입니다 !!:) ");
						break;

					// 방입장시 인원초과
					case ServerProtocol.ENTER_CHATROOM_MAX_USER_FAIL:

						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "인원초과로 입장 하실 수 없습니다!!:) ");
						break;

					// 서버에서 유저 리스트 받음// ++++++++++++++++++++
					// 이미 강퇴된 유저임
					case ServerProtocol.ENTER_CHATROOM_KICKOUT_USER_FAIL:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "해당방에서 강퇴된 유저로 입장 하실 수 없습니다!!:) ");
						break;

					case ServerProtocol.INVITE_TO_ROOM:
						String SendUser = (String) send.getObj()[0];
						String RoomTitle = (String) send.getObj()[1];
						// String SendUser = (String)send.getObj()[2];
						// System.out.println("클라에서 1070 초대메시지 받음");

						int result = JOptionPane.showConfirmDialog(WaitingRoom_GUI.this,
								RoomTitle + "번방" + ", " + SendUser + "님의 초대를 수락하시겠습니까?", "초대",
								JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

						if (result == 0) {
							// 수락
							SendData send = new SendData(ClientProtocol.REPLY_INVITE_USER, RoomTitle, name, SendUser);
							try {
								oos.writeObject(send);
								oos.flush();
								oos.reset();
							} catch (Exception me) {
//								me.printStackTrace();
							}
						} else {
							// 거절
							SendData send = new SendData(ClientProtocol.INVITE_REJECT_CLIENT, SendUser);
							try {
								oos.writeObject(send);
								oos.flush();
								oos.reset();
							} catch (Exception me) {
							}
						}

						break;
					// 대기실 메시지 받기
					case ServerProtocol.RECEIVE_MESSAGE_WAITINGROOM:
						String message = (String) send.getObj()[0];
						taWaitingRoomChat.append(message + "\n");
						taWaitingRoomChat.setCaretPosition(taWaitingRoomChat.getDocument().getLength());
						break;

					// 귓속말 성공
					case ServerProtocol.RECEIVE_WISPER_MESSAGE_CHATROOM_SUCCSS:
						String wmessage = (String) send.getObj()[0];
						taWaitingRoomChat.append(wmessage + "\n");
						taWaitingRoomChat.setCaretPosition(taWaitingRoomChat.getDocument().getLength());
						break;

					// 나에게 귓속말 했을때 실패
					case ServerProtocol.RECEIVE_WISPER_MESSAGE_CHATROOM_FAIL_MYSELF:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "나에게는 귓속말을 하실 수 없습니다!!");
						break;

					// 귓속말 했는데 아이디가 존재하지 않을 때
					case ServerProtocol.RECEIVE_WISPER_MESSAGE_FAIL_NOT_FOUND:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "존재하지 않는 아이디입니다.");
						break;

					// 내가 입력한 방제목이 방 리스트에 있을 때
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

					// 내가 입력한 방이 없을 떄
					case ServerProtocol.NOT_FOUNT_CHATROOM:
						JOptionPane.showMessageDialog(WaitingRoom_GUI.this, "검색하신 채팅방을 찾을 수 없습니다");
						break;

					// 방목록 초기화
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

					// 대화방 입장
					case ServerProtocol.ENTER_CHATROOM_SUCCESS:
						roomInfo = (ChatRoomInfo) send.getObj()[0];

						// setVisible(false);
						dispose();
						new ChatRoom_GUI(roomInfo, sock, oos, ois, name);

						break;
					// 접속 종료 메시지 받기
					case ServerProtocol.UDATE_WAITING_INFORMATION:
						String id1 = (String) send.getObj()[0];

						taWaitingRoomChat.append(id1 + "님이 접속을 종료하셨습니다." + "\n");

						break;

					// 대기실 나가기 메시지보내기 + 대기실 유저리스트 업데이트
					case ServerProtocol.EXIT_MESSAGE_USERLIST_UPDATE:

						waitingRoomUserModel.removeAllElements();
						waitUserList = (Vector<User>) send.getObj()[0];

						for (int i = 0; i < waitUserList.size(); i++) {
							waitingRoomUserModel.addElement(waitUserList.get(i).getId());
						}
						lblWaitingPeople.setText("대기실" + "(" + waitUserList.size() + ")");
						break;

					// 대기실에서 userlist + 방목록
					case ServerProtocol.WAITINGROOM_USERLIST_AND_ROOMLIST:
						// System.out.println("대기실 업데이트 ~~~");
						waitUserList = (Vector<User>) send.getObj()[0];
						lblWaitingPeople.setText("대기실" + "(" + waitUserList.size() + ")");
						waitingRoomUserModel.removeAllElements();
						for (int i = 0; i < waitUserList.size(); i++) {
							waitingRoomUserModel.addElement(waitUserList.get(i).getId());
						}
						model.setNumRows(0);
						allChatRoomList = (Vector<ChatRoomInfo>) send.getObj()[1];
						// 검색 중인 유저는 전체 update x
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
						// System.out.println("대기실 업데이트 ~~~");

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

	// 유저리스트에서 팝업메뉴가 선택된 유저 값 가져오기
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
		// 입력 완료시
		try {
			executorService.submit(runnable);
			// Thread wit = new Thread(runnable);
			// WinInputThread wit = new WinInputThread();
			// wit.start();
		} catch (Exception e) {
			// System.out.println("서버와 접속시 오류가 발생하였습니다.");
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

//		System.out.println("클라이언트의 접속을 종료합니다.");
		System.exit(0);
	}
}
