import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;

public class ChatThread extends Thread {
	private Socket sock;
	private Vector<User> allUserList;
	private Vector<User> waitUserList;
	private Vector<ChatRoom> allChatRoomList;
	private User user;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Hashtable<User, ObjectOutputStream> ht;
	private SendData sd;
	private String id;
	private ChatRoom chroom;
	private String title;
	private Vector<ChatRoomInfo> allChatRoomInfo;
	private Vector<ChatRoomInfo> vec;
	private int count;
	private boolean stopFlag;

	public ChatThread(Socket sock, Vector<User> allUserList, Vector<User> waitUserList,
			Vector<ChatRoom> allChatRoomList, Hashtable<User, ObjectOutputStream> ht) {
		this.sock = sock;
		this.allUserList = allUserList;
		this.waitUserList = waitUserList;
		this.allChatRoomList = allChatRoomList;
		this.ht = ht;

		try {
			oos = new ObjectOutputStream(sock.getOutputStream()); //��ü����ȭ�� ����ϰ� ���ִ� ��ü
			ois = new ObjectInputStream(sock.getInputStream());
		} catch (IOException e) {
//			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		try {

			Object obj = null;
			while ((obj = ois.readObject()) != null) {
				sd = (SendData) obj;
				int code = sd.getCode();

				switch (code) {

				// ����
				case ClientProtocol.ACCESS:
					// ���� �ߺ� Ȯ��
					int state = ServerProtocol.ENTRANCE_SUCCESS;
					id = String.valueOf(sd.getObj()[0]); // ������ ����
					for (int i = 0; i < allUserList.size(); i++) {
						if (id.equals(allUserList.get(i).getId())) {
							state = ServerProtocol.ENTRANCE_FAIL;
							sd = new SendData(code);
							oos.writeObject(sd);
							oos.flush();
							oos.reset();
							return;
						}
					}
					// ���� ����
					if (state == ServerProtocol.ENTRANCE_SUCCESS) {
						user = new User(id, "����");

						allUserList.add(user);
						waitUserList.add(user);

						allChatRoomInfo = new Vector<ChatRoomInfo>();
						if (allChatRoomList.size() > 0) {
							for (int i = 0; i < allChatRoomList.size(); i++) {
								allChatRoomInfo.add(allChatRoomList.get(i).getRoomInfo());
							}
						}

						WaitingRoom wr = new WaitingRoom(allUserList, waitUserList, allChatRoomInfo);

						ht.put(user, oos); //hashtable �ȿ�  user,oos �־���
					 
						sd = new SendData(ServerProtocol.ENTRANCE_SUCCESS, wr); //sendData�� Ŭ������ �����ؼ� ����
						oos.writeObject(sd); 
						oos.flush(); //�ݵ�� ��������
						oos.reset();

						if (waitUserList.size() > 1) {
							updateWaitList(waitUserList, allChatRoomList, oos); //
							broadcast(ServerProtocol.ENTRANCE_SUCCESS);
						} else if (waitUserList.size() == 1) {
							broadcast(ServerProtocol.ENTRANCE_SUCCESS); //���� �ȿ� �ִ� ������� ���� �����ߴٰ� �˸���
						}
					}
					break;
				// �� �����
				case ClientProtocol.CREATE_CHATROOM:
					obj = ht.get(user);
					ObjectOutputStream oos1 = (ObjectOutputStream) obj;
					ChatRoomInfo ri = (ChatRoomInfo) sd.getObj()[0];
					int flag = 0;

					for (int i = 0; i < allChatRoomList.size(); i++) {
						if (ri.getTitle().equals(allChatRoomList.get(i).getRoomInfo().getTitle())) {
							sd = new SendData(ServerProtocol.CREATE_CHATROOM_FAIL);
							oos1.writeObject(sd);
							oos1.flush();
							oos1.reset();
							flag = 1;
							break;
						}
					}

					// ä�ù� ����
					if (flag == 0) {
						chroom = new ChatRoom(ri);
						title = ri.getTitle();
						waitUserList.remove(user);
						user.setChatLocation(title.trim());
						// chatRoomInfo�� �� ���̵� �־���
						for (int i = 0; i < allUserList.size(); i++) {
							if (allUserList.get(i).equals(user)) {
								allUserList.get(i).setChatLocation(title);
							}
						}
						chroom.getRoomInfo().getChatRoomUserList().add(id.trim());

						allChatRoomList.add(chroom);

						sd = new SendData(ServerProtocol.CREATE_CHATROOM_SUCCESS, ri);
						try {
							oos1.writeObject(sd);
							oos1.flush();
							oos1.reset();
						} catch (Exception e) {
//							e.printStackTrace();
						}

						if (waitUserList.size() >= 1) {
							updateWaitList(waitUserList, allChatRoomList, oos1);
						}
					}

					break;

				// ä�ù� �����ϱ�: 
				case ClientProtocol.ENTER_CHATROOM:
					title = String.valueOf(sd.getObj()[0]);
					ObjectOutputStream oos2 = (ObjectOutputStream) ht.get(user);
					int size = 0;
					Vector<String> chatuserlist = null;
					for (int i = 0; i < allChatRoomList.size(); i++) {

						size = allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().size();

						if (allChatRoomList.get(i).getRoomInfo().getTitle().equals(title)) {
							// �ִ��ο�
							if (allChatRoomList.get(i).getRoomInfo().getMaxPeople() == size) {
								sd = new SendData(1021, allChatRoomList.get(i).getRoomInfo());
								try {
									oos2.writeObject(sd);
									oos2.flush();
									oos2.reset();
									break;
								} catch (Exception e) {
								}

							} else {
								int state1 = 0;

								for (String u : allChatRoomList.get(i).getKickout_User()) {
									if (u.equals(user.getId())) {
										state1 = 1;
										sd = new SendData(ServerProtocol.ENTER_CHATROOM_KICKOUT_USER_FAIL);
										try {
											oos2.writeObject(sd);
											oos2.flush();
											oos2.reset();
										} catch (Exception e) {
//											e.printStackTrace();
//											System.out.println("MultiChatThread  -sendmsg-");
										}
										break;
									}
								}

								if (state1 == 0) {
									waitUserList.remove(user);
									user.setChatLocation(title.trim());
									// chatRoomInfo�� �� ���̵� �־���
									for (int j = 0; j < allUserList.size(); j++) {
										if (allUserList.get(j).equals(user)) {
											allUserList.get(j).setChatLocation(title.trim());
										}
									}
									allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().add(id.trim());
									chatuserlist = allChatRoomList.get(i).getRoomInfo().getChatRoomUserList();
									// �� ���� ok
									sd = new SendData(ServerProtocol.ENTER_CHATROOM_SUCCESS, allChatRoomList.get(i).getRoomInfo());
									try {
										oos2.writeObject(sd);
										oos2.flush();
										oos2.reset();
									} catch (Exception e) {
									}
									if (waitUserList.size() >= 1) {
										updateWaitList(waitUserList, allChatRoomList, oos2);
									}
									if (size >= 1) {
										updateChatList(code, size, chatuserlist, oos2, title);
									}
								}

							}
						}

					}

					break;

				// �ʴ�â ���� ��������Ʈ ȣ��
				case ClientProtocol.INVITE:
					ObjectOutputStream oos7 = (ObjectOutputStream) ht.get(user);

					String title2 = (String) sd.getObj()[0];
					sd = new SendData(ServerProtocol.LIST_RECEIVE, waitUserList, title2);
					try {
						oos7.writeObject(sd);
						oos7.flush();
						oos7.reset();
					} catch (Exception e) {
 
					}

					break;
				// ���� ����
				case ClientProtocol.CHATROOM_SETTING:
					ObjectOutputStream oosChange = (ObjectOutputStream) ht.get(user);
					String titlee = (String) sd.getObj()[0];
					String changeTitle = (String) sd.getObj()[1];
					int changemaxPeople = (int) sd.getObj()[2];
					int num = 0;
					boolean st = true;
					for (int i = 0; i < allChatRoomList.size(); i++) {
						// ä�ù� �ߺ�
						if (allChatRoomList.get(i).getRoomInfo().getTitle().equals(changeTitle)) {
							sd = new SendData(ServerProtocol.ROOM_SETTING_FALSE_TITLE_REPEAT);

							try {
								oosChange.writeObject(sd);
								oosChange.flush();
								oosChange.reset();
							} catch (IOException e) {
//								e.printStackTrace();
							}
							st = false;
							break;
						} else if (allChatRoomList.get(i).getRoomInfo().getTitle().equals(titlee)) {
							num = i;
							if (allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().size() > changemaxPeople) {
								sd = new SendData(ServerProtocol.ROOM_SETTING_FAIL_INWON);
								try {
									oosChange.writeObject(sd);
									oosChange.flush();
									oosChange.reset();
									st = false;
									break;
								} catch (IOException e) {
//									e.printStackTrace();
								}
								st = false;
								break;
							}
						}
					}
				 
					if (st == true) {
						for (int i = 0; i < allUserList.size(); i++) {
							if (allUserList.get(i).getChatLocation().equals(titlee)) {
								allUserList.get(i).setChatLocation(changeTitle);
							}
						}
						allChatRoomList.get(num).getRoomInfo().setTitle(changeTitle);
						allChatRoomList.get(num).getRoomInfo().setMaxPeople(changemaxPeople);

						roomupdate(130, changeTitle, changemaxPeople);
						if (waitUserList.size() >= 1) {
							updateWaitList(allChatRoomList);
						}
					}

					break;
				// ���� ����
				case ClientProtocol.GRANT_CHATROOM_HOST:
					ObjectOutputStream bjoos = (ObjectOutputStream) ht.get(user);

					String host = (String) sd.getObj()[0];
					String title = (String) sd.getObj()[1];
					for (int i = 0; i < allChatRoomList.size(); i++) {

						if (allChatRoomList.get(i).getRoomInfo().getTitle().equals(title)) {
							allChatRoomList.get(i).getRoomInfo().setChatRoom_host(host);
							allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().remove(host);
							allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().add(0, host);
							roomKingupdate(code, host, allChatRoomList.get(i).getRoomInfo().getChatRoomUserList(),
									bjoos, title);
							if (waitUserList.size() >= 1) {
								updateWaitList(waitUserList, allChatRoomList, bjoos);
							}
						}
					}

					break;
				// ���� ������
				case ClientProtocol.CHATROOM_HOST_OUT:
					ObjectOutputStream exitbjoos = (ObjectOutputStream) ht.get(user);

					String host1 = (String) sd.getObj()[0];
					String title1 = (String) sd.getObj()[1];
					
					Vector<String> cuserlist = null;
					boolean find = false;

					for (int i = 0; i < allChatRoomList.size(); i++) {

						if (allChatRoomList.get(i).getRoomInfo().getTitle().equals(title1)) {
							
							// ��������Ʈ�ȿ� �ٲܻ���̸���������
							for (String u : allChatRoomList.get(i).getRoomInfo().getChatRoomUserList()) {
								if (u.equals(host1)) {
									allChatRoomList.get(i).getRoomInfo().setChatRoom_host(host1);// ȣ��Ʈ�ٲ��ְ�
									allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().remove(host1); // ȣ��Ʈ�� �ε���0��°�� �Ű���
									allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().add(0, host1);
									waitUserList.add(user); // ��ġ ����
									user.setChatLocation("����");
									for (int j = 0; j < allUserList.size(); j++) {
										if (allUserList.get(j).equals(user)) {
											allUserList.get(j).setChatLocation("����");

										}
									}
									allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().remove(id);
									cuserlist = allChatRoomList.get(i).getRoomInfo().getChatRoomUserList();

									Vector<ChatRoomInfo> roominfolist = new Vector<ChatRoomInfo>();

									for (int k = 0; k < allChatRoomList.size(); k++) {
										roominfolist.add(allChatRoomList.get(k).getRoomInfo());
									}
									sd = new SendData(ServerProtocol.EXIT_WAITING_INFO, waitUserList, roominfolist);
									exitbjoos.writeObject(sd);
									exitbjoos.flush();
									exitbjoos.reset();

									// ������Ʈ
									roomKingupdate(141, host1, cuserlist, exitbjoos, title1);
									if (waitUserList.size() >= 1) {
										updateWaitList(waitUserList, allChatRoomList, exitbjoos);
									}
									find = true;
									break;
								} else {

								}
							}

						} // if �� ������
					}
					// ä�ù�ȿ� ������ ã�� �� ������
					if (find == false) {
						sd = new SendData(ServerProtocol.CHATTINGROOM_NOT_FOUND_USER);
						try {
							exitbjoos.writeObject(sd);
							exitbjoos.flush();
							exitbjoos.reset();
						} catch (Exception e) {
//							e.printStackTrace();
						}
					}

					break;

				// ��ȭ�� ������
				case ClientProtocol.EXIT_CHATROOM:
					title = String.valueOf(sd.getObj()[0]);
					ObjectOutputStream oos = (ObjectOutputStream) ht.get(user);
					int size1 = 0;
					Vector<String> chatuserlist1 = null;

					for (int i = 0; i < allChatRoomList.size(); i++) {

						if (allChatRoomList.get(i).getRoomInfo().getTitle().equals(title)) {
							waitUserList.add(user); // ��ġ ����
							user.setChatLocation("����");
							// chatRoomInfo�� �� ���̵� �־���
							for (int j = 0; j < allUserList.size(); j++) {
								if (allUserList.get(j).equals(user)) {
									allUserList.get(j).setChatLocation("����");

								}
							}
							allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().remove(id);
							chatuserlist1 = allChatRoomList.get(i).getRoomInfo().getChatRoomUserList();

							size1 = allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().size();
							if (size1 == 0) {
								allChatRoomList.remove(allChatRoomList.get(i));
							}

						}

					}
					Vector<ChatRoomInfo> roominfolist = new Vector<ChatRoomInfo>();
					for (int i = 0; i < allChatRoomList.size(); i++) {
						roominfolist.add(allChatRoomList.get(i).getRoomInfo());
					}
					sd = new SendData(ServerProtocol.EXIT_WAITING_INFO, waitUserList, roominfolist);
					oos.writeObject(sd);
					oos.flush();
					oos.reset();

					if (size1 >= 1) {
						updateChatList(code, size1, chatuserlist1, oos, title);
					}

					if (waitUserList.size() >= 1) {
						updateWaitList(waitUserList, allChatRoomList, oos);
					}

					break;
				// ���� 
				case ClientProtocol.KICKOUT_USER:
					String kickoutUser = (String) sd.getObj()[0];
					String roomti = (String) sd.getObj()[1];
					Vector<String> userlist = null;
					ObjectOutputStream koos = null;
					Set<User> userKey = ht.keySet();

					for (ChatRoom cr : allChatRoomList) {
						if (cr.getRoomInfo().getTitle().equals(roomti)) {
							cr.getKickout_User().add(kickoutUser);
							cr.getRoomInfo().getChatRoomUserList().remove(kickoutUser);
							userlist = cr.getRoomInfo().getChatRoomUserList();
							waitUserList.add(new User(kickoutUser, "����")); // ��ġ
																			// ����
							for (User user : allUserList) {
								if (user.getId().equals(kickoutUser)) {
									user.setChatLocation("����");
								}
							} // ��ü�������� ���Ƿιٲ���
						}
					}
					Vector<ChatRoomInfo> roomin = new Vector<ChatRoomInfo>();
					for (int k = 0; k < allChatRoomList.size(); k++) {
						roomin.add(allChatRoomList.get(k).getRoomInfo());
					}
					String km = "ä�ù濡�� ���� ���ϼ̽��ϴ�";
					sd = new SendData(ServerProtocol.EXINT_WAITING_INFO2, km, waitUserList, roomin, kickoutUser);
					for (User t : userKey) {
						if (t.getId().equals(kickoutUser)) {
							koos = ht.get(t);
							try {
								koos.writeObject(sd);
								koos.flush();
								koos.reset();
							} catch (IOException e) {
//								e.printStackTrace();
							}
						}
					}
					// update
					roomKingupdate(160, kickoutUser, userlist, koos, roomti);
					if (waitUserList.size() >= 1) {
						updateWaitList(waitUserList, allChatRoomList, koos);
					}

					break;

				// �����ʴ� 
				case ClientProtocol.INVITE_USER:
					// Ŭ���̾�Ʈ���� ���� ���� ���� ����Ʈ ��û
					// updateWaitUserList(waitUserList);
					String userName = (String) sd.getObj()[0];
					String RoomTitle = (String) sd.getObj()[1];
					// String SendUser = (String) sd.getObj()[2];

					for (User user : waitUserList) {
						if (user.getId().equals(userName)) {
							InviteSendData(new SendData(1070, id, RoomTitle), user);
						}
					}
					 
					break;

				// �ʴ� ����
				case ClientProtocol.REPLY_INVITE_USER:
					// ChatRoomInfo , chatRoomUserList:Vector<User>
					String title5 = String.valueOf(sd.getObj()[0]); // ������
					String name = String.valueOf(sd.getObj()[1]); // �޴� ���
					String SendUser = String.valueOf(sd.getObj()[2]); // �����»��
					for (int k = 0; k < waitUserList.size(); k++) {

						if (waitUserList.get(k).getId().equals(name)) {  
							//�޴� ����� ���ǿ� ������?? 
							
							ObjectOutputStream oos5 = (ObjectOutputStream) ht.get(user);
							// boolean flag1 = false;
							int size5 = 0;
							Vector<String> chatuserlist5 = null;
							for (int i = 0; i < allChatRoomList.size(); i++) {

								size = allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().size();

								if (allChatRoomList.get(i).getRoomInfo().getTitle().equals(title5)) {
									// �ִ��ο�
									if (allChatRoomList.get(i).getRoomInfo().getMaxPeople() == size) {
										sd = new SendData(ServerProtocol.ENTER_CHATROOM_MAX_USER_FAIL, allChatRoomList.get(i).getRoomInfo());
										try {
											oos5.writeObject(sd);
											oos5.flush();
											oos5.reset();
											break;
										} catch (Exception e) {
//											e.printStackTrace();
										}

									} else {
										waitUserList.remove(user);
										user.setChatLocation(title5.trim());
										// chatRoomInfo�� �� ���̵� �־���
										for (int j = 0; j < allUserList.size(); j++) {
											if (allUserList.get(j).equals(user)) {
												allUserList.get(j).setChatLocation(title5);
											}
										}
										
										allChatRoomList.get(i).getRoomInfo().getChatRoomUserList().add(id);

										chatuserlist5 = allChatRoomList.get(i).getRoomInfo().getChatRoomUserList();
										// �� ���� ok
										sd = new SendData(ServerProtocol.ENTER_CHATROOM_SUCCESS, allChatRoomList.get(i).getRoomInfo());
										try {
											oos5.writeObject(sd);
											oos5.flush();
											oos5.reset();
										} catch (Exception e) {
//											e.printStackTrace();
//											System.out.println("MultiChatThread  -sendmsg-");
										}
										// flag1 = true;
										if (waitUserList.size() >= 1) {
											updateWaitList(waitUserList, allChatRoomList, oos5);
										}
										if (size >= 1) {
											updateChatList(code, size5, chatuserlist5, oos5, title5);
										}
									}
								}

							}
						}

					} // for�� ��

					break; 
				// �ʴ� �ź� 
				case ClientProtocol.INVITE_REJECT_CLIENT:
					String SendUser2 = (String) sd.getObj()[0];
					ObjectOutputStream oos10 = null;
					for (User user : allUserList) {
						if (user.getId().equals(SendUser2)) {
							oos10 = ht.get(user);
						}
					}
					try {
						sd = new SendData(ServerProtocol.INVITE_REJECT);
						oos10.writeObject(sd);
						oos10.flush();
						oos10.reset();
					} catch (IOException e) {
//						e.printStackTrace();
					}

					break; 
				// ��ȭ�� �޼��� ������
				case ClientProtocol.SEND_MESSAGE_CHATROOM:
					String ms = id + ": " + (String) sd.getObj()[0];
					chatbroadcast(ServerProtocol.RECEIVE_MESSAGE_CHATROOM, ms, user);
					break;
				// ���� �޽��� ������
				case ClientProtocol.SEND_MESSAGE_WAITINGROOM:
					id = user.getId();
					String msg = id + ": " + (String) sd.getObj()[0];
					broadcast(ServerProtocol.RECEIVE_MESSAGE_WAITINGROOM, msg);
					break;

				// �ӼӸ� ������
				case ClientProtocol.SEND_WISPER_MESSAGE_CHATROOM:
					String wisperMsg = (String) sd.getObj()[0];
					if (wisperMsg.indexOf("/to ") == 0) {
						sendMsg(wisperMsg);
					}
					break;

				// btnSearch ��ư ������ ��
				case ClientProtocol.SEARCH_TITLE:
					boolean flag2 = false;
					count = 0;
					ObjectOutputStream oos3 = (ObjectOutputStream) ht.get(user);
					String searchTitle = (String) sd.getObj()[0]; // �˻� Ű����
					vec = new Vector<ChatRoomInfo>();
					for (int i = 0; i < allChatRoomList.size(); i++) {
						if (allChatRoomList.get(i).getRoomInfo().getTitle().contains(searchTitle.trim())) {
							// ���� �˻��� ������ �ִ����� �˻��� �ִٸ� ������ �κ���
							flag2 = true;
							vec.add(allChatRoomList.get(i).getRoomInfo());
						}
					}
					// �˻��� ����� ������
					if (flag2 == true) {
						sd = new SendData(ServerProtocol.RESULT_CHATROOM_LIST, vec, count);// 0
						try {
							oos3.writeObject(sd);
							oos3.flush();
							oos3.reset();
						} catch (Exception e) {
//							e.printStackTrace();
						}

					} else { // �˻� ����� ������
						sd = new SendData(ServerProtocol.NOT_FOUNT_CHATROOM);
						try {
							oos3.writeObject(sd);
							oos3.flush();
							oos3.reset();
						} catch (Exception e) {
//							e.printStackTrace();
						}

					}

					break;

				// �� �ʱ�ȭ
				case ClientProtocol.CHATROOM_RETURN_ORIGINAL:

					ObjectOutputStream oos12 = (ObjectOutputStream) ht.get(user);
					Vector<ChatRoomInfo> roominfo = new Vector<ChatRoomInfo>();
					for (int i = 0; i < allChatRoomList.size(); i++) {
						roominfo.add(allChatRoomList.get(i).getRoomInfo());
					}

					sd = new SendData(ServerProtocol.CHATROOMLIST_ORIGINAL, roominfo);
					try {
						oos12.writeObject(sd);
						oos12.flush();
						oos12.reset();
					} catch (Exception e) {
//						e.printStackTrace();
					}

					break;

				// ���� ������
				case ClientProtocol.EXIT_WAITINGROOM:
					stopFlag = true;
					allUserList.remove(user);
					waitUserList.remove(user);
					ht.remove(user);

					if (waitUserList.size() >= 1) {
						updateWaitUserList(waitUserList);
						broadcast(ServerProtocol.UDATE_WAITING_INFORMATION);
					}
					break;
				}
				if (code == ClientProtocol.EXIT_WAITINGROOM) {
					break;
				}
			}
		} catch (SocketException e) {
//			System.out.println(e);
			if (stopFlag == false) {
				ht.remove(user);
				if (user.getChatLocation().equals("����")) {
					allUserList.remove(user);
					waitUserList.remove(user);
					if (waitUserList.size() >= 1) {
						updateWaitUserList(waitUserList);
						broadcast(2700);
					}

				} else {
					// ä�ù� ���� ��������
					Vector<String> chatuserlist = null;
					if(allChatRoomList!=null){
						for(int i=0 ; i<allChatRoomList.size();i++) {
							String userId = allChatRoomList.get(i).searchUser(user.getId());
							if(userId != null) {
								// ���� �Ϸ�
								allUserList.remove(user);
								 allChatRoomList.get(i).removeUser(userId);
								
								chatuserlist =  allChatRoomList.get(i).getRoomInfo().getChatRoomUserList();
								int size1 = chatuserlist.size();
								
								if (size1 == 0) {
									allChatRoomList.remove( allChatRoomList.get(i) );
								}
								
								if (size1 >= 1) {
									if( allChatRoomList.get(i).getRoomInfo().getChatRoom_host().equals(userId)){
										 allChatRoomList.get(i).getRoomInfo().setChatRoom_host(chatuserlist.get(0));
										roomKingupdate(ServerProtocol.UPDATE_CHATROOM_HOST,chatuserlist.get(0),chatuserlist,oos,
												 allChatRoomList.get(i).getRoomInfo().getTitle());
									}else{
										updateChatList(180, size1,chatuserlist, oos , title);
									}
									
								}
								
								
								if (waitUserList.size() >= 1) {
									updateWaitList(waitUserList, allChatRoomList, oos);
								}
								
								
							}
							
						}
					}
				}
			}

		} catch (Exception e) {
//			System.out.println(e);

		} finally {
//			System.out.println("��������");
			try {
				sock.close();
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	// �ʴ� �޽��� 
	public void InviteSendData(SendData sd, User user) {
		ObjectOutputStream oos = null;
		for (User users : ht.keySet()) {
			if (users.getId().equals(user.getId())) {
				oos = ht.get(users);
			}
		}
		try {
			oos.writeObject(sd);
			oos.flush();
			oos.reset();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

	// �ӼӸ�
	public void sendMsg(String msg) {
		int code = ServerProtocol.RECEIVE_WISPER_MESSAGE_CHATROOM_SUCCSS; // 2002
		int start = msg.indexOf(" ") + 1; // 4
		int end = msg.indexOf(" ", start); // 7

		if (end != -1) {
			String to = msg.substring(start, end); // String to = "���μ�"
			String msg2 = msg.substring(end + 1); // String msg2 = "�ȳ�~"
			Object obj = null;
			Set<User> userKey = ht.keySet(); // keySet()�� Ű���� ����
			Vector<User> key = new Vector<User>(userKey);
			for (User u : key) {
				if (to.equals(user.getId())) {
					obj = ht.get(user);
					ObjectOutputStream oos = (ObjectOutputStream) obj;
					sd = new SendData(ServerProtocol.RECEIVE_WISPER_MESSAGE_CHATROOM_FAIL_MYSELF); // ����
																									// ����
					// ���� ������ �������� ����
					try {
						oos.writeObject(sd);
						oos.flush();
						oos.reset();
						return;
					} catch (Exception e) {
//						e.printStackTrace();
					}

				} else if (u.getId().equals(to)) {
					obj = ht.get(u); // obj�� ���μ� Hashtable�� �����ͼ� �ִ´�.
					ObjectOutputStream oos = (ObjectOutputStream) obj; // ���μ�
																		// ObjectOutputStream��
																		// ������

					String str = "[" + user.getId() + "]���� �ӼӸ� <<<< " + msg2;

					SendData sendMsg = new SendData(ServerProtocol.RECEIVE_WISPER_MESSAGE_CHATROOM_SUCCSS, str);
					try {
						oos.writeObject(sendMsg);
						oos.flush();
						oos.reset();
					} catch (Exception e) {
//						e.printStackTrace();
					}
					try {
						msg2 = "[" + to + "]�Կ��� �ӼӸ� >>>> " + msg2;
						ht.get(user)
								.writeObject(new SendData(ServerProtocol.RECEIVE_WISPER_MESSAGE_CHATROOM_SUCCSS, msg2));
						ht.get(user).flush();
						ht.get(user).reset();
						return;
					} catch (IOException e) {
//						e.printStackTrace();
					}
				}
			}

			obj = ht.get(user);
			ObjectOutputStream oos = (ObjectOutputStream) obj;
			SendData sd = new SendData(ServerProtocol.RECEIVE_WISPER_MESSAGE_FAIL_NOT_FOUND); // ����
																								// ����
			try {
				oos.writeObject(sd);
				oos.flush();
				oos.reset();
				return;
			} catch (Exception e) {
//				e.printStackTrace();
			}

		}

	}

	// �Ӹ� ��
	// �ڵ� + �ܼ� �˸� �޽���
	public void broadcast(int code) {
		Collection<ObjectOutputStream> collection = ht.values();
		Iterator<ObjectOutputStream> iter = collection.iterator();
		while (iter.hasNext()) {
			oos = iter.next();
			try {
				sd = new SendData(code, id);
				oos.writeObject(sd);
				oos.flush();
				oos.reset();
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
	}

	// �ڵ� + ä�ó���
	public void broadcast(int code, String msg) {
		Collection<ObjectOutputStream> collection = ht.values();
		Iterator<ObjectOutputStream> iter = collection.iterator();
		while (iter.hasNext()) {
			oos = iter.next();
			try {
				sd = new SendData(code, msg);
				oos.writeObject(sd);
				oos.flush();
				oos.reset();
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
	}

	// ä�ù� �޼��� ������
	public void chatbroadcast(int code, String msg, User user) {
		Set<User> userKey = ht.keySet(); // keySet()�� Ű���� ����
		sd = new SendData(2000, msg);
		for (User temp : userKey) {
			if (temp.getChatLocation().equals(user.getChatLocation())) {
				ObjectOutputStream oos = ht.get(temp);

				try {
					oos.writeObject(sd);
					oos.flush();
					oos.reset();
				} catch (IOException e) {
//					e.printStackTrace();
				}
			}
		}

	}

	// ���� ��������Ʈ ����
	public void updateWaitUserList(Vector<User> waitUserList) {
		Collection<ObjectOutputStream> collection = ht.values();
		Iterator<ObjectOutputStream> iter = collection.iterator();

		while (iter.hasNext()) {
			oos = iter.next();
			try {
				sd = new SendData(ServerProtocol.EXIT_MESSAGE_USERLIST_UPDATE, waitUserList);
				oos.writeObject(sd);
				oos.flush();
				oos.reset();
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
	}

	// ���� ��������Ʈ ���� , ����� ���� , ���� �޼���
	public void updateWaitingList(Vector<User> waitUserList, Vector<ChatRoom> allchatRoomList) {

		Set<User> userKey = ht.keySet(); // keySet()�� Ű���� ����
		Vector<User> key = new Vector<User>(userKey);

		Vector<ChatRoomInfo> romminfo = new Vector<ChatRoomInfo>();
		for (int i = 0; i < allchatRoomList.size(); i++) {
			romminfo.add(allchatRoomList.get(i).getRoomInfo());
		}

		String msg = id + "���� �����ϼ̽��ϴ� ";
		Object obj = null;
		for (User u : key) {
			if (u.getChatLocation().equals("����")) {
				obj = ht.get(u);
				ObjectOutputStream oos = (ObjectOutputStream) obj;
				try {
			 
					sd = new SendData(ServerProtocol.EXIT_WAITING_INFO, msg, waitUserList, romminfo);
					oos.writeObject(sd);
					oos.flush();
					oos.reset();
				} catch (IOException e) {
//					e.printStackTrace();

				}
			}
		}
	}

	private void updateChatList(int code, int size, Vector<String> chatuserlist, ObjectOutputStream oos2,
			String title) {
		Set<User> userKey = ht.keySet(); // keySet()�� Ű���� ����
		String msg = null;

		if (code == ClientProtocol.EXIT_CHATROOM) {
			msg = user.getId() + "���� ���� �ϼ̽��ϴ� ! ";
		} else {
			msg = user.getId() + "���� ���� �ϼ̽��ϴ� ! ";
		}
		sd = new SendData(ServerProtocol.UPDATE_CHAT_INFO, msg, chatuserlist);
		for (User temp : userKey) {
			if (temp.getChatLocation().equals(title)) {
				ObjectOutputStream oos = ht.get(temp);
				if (oos != oos2) {
					try {
						oos.writeObject(sd);
						oos.flush();
						oos.reset();
					} catch (IOException e) {
//						e.printStackTrace();
					}
				}
				/*
				 * try { oos.writeObject(sd); oos.flush(); oos.reset(); } catch
				 * (IOException e) { e.printStackTrace(); }
				 */

			}
		}
	}

	// ���� �Ӹ� , ����
	private void roomKingupdate(int code, String user, Vector<String> chatuserlist, ObjectOutputStream oos2,
			String title) {
		Set<User> userKey = ht.keySet(); // keySet()�� Ű���� ����
		String msg = null;
		if (code == ClientProtocol.KICKOUT_USER) {
			msg = user + "���� ������ϼ̽��ϴ� ";

		} else {
			msg = "������ " + chatuserlist.get(0) + "������ ���� �Ǿ����ϴ�  ";

		}
	 
		sd = new SendData(ServerProtocol.UPDATE_CHATROOM_HOST, msg, chatuserlist);
		for (User temp : userKey) {
			if (temp.getChatLocation().equals(title)) {

				ObjectOutputStream oos = ht.get(temp);

				try {

					oos.writeObject(sd);
					oos.flush();
					oos.reset();

				} catch (IOException e) {
//					e.printStackTrace();
				}
 

			}
		}
	}

	// ä�ù� ���� update
	// ���� �Ӹ�
	private void roomupdate(int code, String title, int max) {

		Set<User> userKey = ht.keySet(); // keySet()�� Ű���� ����
		sd = new SendData(ServerProtocol.CHATROOM_SETTING_SUCCESS, title, max);
		for (User temp : userKey) {
			if (temp.getChatLocation().equals(title)) {

				ObjectOutputStream oos = ht.get(temp);

				try {
					oos.writeObject(sd);
					oos.flush();
					oos.reset();
				} catch (IOException e) {
//					e.printStackTrace();
				}

 
			}
		}
	}

	// ���� ��������Ʈ ���� , ����� ����
	public void updateWaitList(Vector<User> waitUserList, Vector<ChatRoom> allchatRoomList, ObjectOutputStream myoos) {

		Set<User> userKey = ht.keySet(); // keySet()�� Ű���� ����
		Vector<User> key = new Vector<User>(userKey);

		Vector<ChatRoomInfo> romminfo = new Vector<ChatRoomInfo>();
		for (int i = 0; i < allchatRoomList.size(); i++) {
			romminfo.add(allchatRoomList.get(i).getRoomInfo());
		}
		Object obj = null;
		for (User u : key) {
			if (u.getChatLocation().equals("����")) {
				obj = ht.get(u);
				ObjectOutputStream oos = (ObjectOutputStream) obj;
				try {

					sd = new SendData(ServerProtocol.WAITINGROOM_USERLIST_AND_ROOMLIST, waitUserList, romminfo);
					oos.writeObject(sd);
					oos.flush();
					oos.reset();
				} catch (IOException e) {
//					e.printStackTrace();

				}
			}
		}
	}

	// ä�ù� ������ ����
	public void updateWaitList(Vector<ChatRoom> allchatRoomList) {

		Set<User> userKey = ht.keySet(); // keySet()�� Ű���� ����
		Vector<User> key = new Vector<User>(userKey);

		Vector<ChatRoomInfo> romminfo = new Vector<ChatRoomInfo>();
		for (int i = 0; i < allchatRoomList.size(); i++) {
			romminfo.add(allchatRoomList.get(i).getRoomInfo());
		}
		Object obj = null;
		for (User u : key) {
			if (u.getChatLocation().equals("����")) {
				obj = ht.get(u);
				ObjectOutputStream oos = (ObjectOutputStream) obj;
				try {

					sd = new SendData(ServerProtocol.WAITINGROOM_UPDATING, romminfo);
					oos.writeObject(sd);
					oos.flush();
					oos.reset();
				} catch (IOException e) {
//					e.printStackTrace();

				}
			}
		}
	}
}
