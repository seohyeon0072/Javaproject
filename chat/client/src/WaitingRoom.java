import java.io.Serializable;
import java.util.Vector;

public class WaitingRoom  implements Serializable {
	private Vector<User> allUserList; //모든 유저 리스트
	private Vector<User> watingRoomUserList;
	private Vector<ChatRoomInfo> allChatRoomList;
	
	public WaitingRoom(Vector<User> allUserList,Vector<User> watingRoomUserList, Vector<ChatRoomInfo> allChatRoomList) {
		allUserList = new Vector<User>(allUserList); 
		watingRoomUserList = new Vector<User>(watingRoomUserList);
		allChatRoomList = new Vector<ChatRoomInfo>(allChatRoomList);
		setAllUserList(allUserList);
		setWatingRoomUserList(watingRoomUserList);
		setAllChatRoomList(allChatRoomList);
	}
	public WaitingRoom() {
	}
	public Vector<User> getAllUserList() {
		return allUserList;
	}
	public void setAllUserList(Vector<User> allUserList) {
		this.allUserList = allUserList;
	}
	public Vector<User> getWatingRoomUserList() {
		return watingRoomUserList;
	}
	public void setWatingRoomUserList(Vector<User> watingRoomUserList) {
		this.watingRoomUserList = watingRoomUserList;
	}
	public Vector<ChatRoomInfo> getAllChatRoomList() {
		return allChatRoomList;
	}
	public void setAllChatRoomList(Vector<ChatRoomInfo> allChatRoomList) {
		this.allChatRoomList = allChatRoomList;
	}
	
}
