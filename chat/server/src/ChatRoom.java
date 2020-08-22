import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class ChatRoom implements Serializable {
	private ChatRoomInfo roomInfo;
	private Vector<String> kickout_User;
	private Vector<String> chatRoomUserList;
	private Hashtable<User, ObjectOutputStream> ht;

	public ChatRoom(ChatRoomInfo roomInfo) {
		setRoomInfo(roomInfo);
		kickout_User = new Vector<String>();
		chatRoomUserList = new Vector<String>();
		ht = new Hashtable<User, ObjectOutputStream>();
	}

	public ChatRoom() {
	}

	public ChatRoomInfo getRoomInfo() {
		return roomInfo;
	}

	public void setRoomInfo(ChatRoomInfo roomInfo) {
		this.roomInfo = roomInfo;
	}

	public Vector<String> getKickout_User() {
		return kickout_User;
	}

	public void setKickout_User(Vector<String> kickout_User) {
		this.kickout_User = kickout_User;
	}

	public Vector<String> getChatRoomUserList() {
		return chatRoomUserList;
	}

	public void setChatRoomUserList(Vector<String> chatRoomUserList) {
		this.chatRoomUserList = chatRoomUserList;
	}

	public Hashtable<User, ObjectOutputStream> getHt() {
		return ht;
	}

	public String searchUser(String nickName) {
		Vector<String> userlist = roomInfo.getChatRoomUserList();
		System.out.println(userlist);
		
		for (String userNickName : userlist) {
			if (userNickName.equals(nickName)) {
				return userNickName;
			}
		}
		return null;
	}
	
	public void removeUser(String nickName) {
		String user = searchUser(nickName);
		if(user!= null) {
			 roomInfo.getChatRoomUserList().remove(user);
		}
	}

	@Override
	public boolean equals(Object src) {
		if (src == null || !(src instanceof ChatRoom)) {
			return false;
		}
		ChatRoom cr = (ChatRoom) src;
		return roomInfo.getTitle().equals(cr.getRoomInfo().getTitle());
	}

}
