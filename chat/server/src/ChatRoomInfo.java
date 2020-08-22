import java.io.Serializable;
import java.util.Vector;

public class ChatRoomInfo implements Serializable {
	private String title;
	private int maxPeople;
	private String chatRoom_host;
	private Vector<String> chatRoomUserList;
	public ChatRoomInfo(String title, int maxPeople, String chatRoom_host) {
		chatRoomUserList =new Vector<String>();
		setTitle(title);
		setMaxPeople(maxPeople);
		setChatRoom_host(chatRoom_host);
		
	}

	public ChatRoomInfo(){}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}

	public String getChatRoom_host() {
		return chatRoom_host;
	}

	public void setChatRoom_host(String chatRoom_host) {
		this.chatRoom_host = chatRoom_host;
	}
	public Vector<String> getChatRoomUserList() {
		return chatRoomUserList;
	}

	public void setChatRoomUserList(Vector<String> chatRoomUserList) {
		this.chatRoomUserList = chatRoomUserList;
	}

}
