import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;
//server 메인 화면
public class ChatServer {
	private Vector<User> allUserList;
	private Vector<User> waitUserList;
	private Vector<ChatRoom> allChatRoomList;
	public ChatServer() {
		allUserList = new Vector<User>();
		waitUserList = new Vector<User>();
		allChatRoomList = new Vector<ChatRoom>();
		try {
			ServerSocket server = new ServerSocket(10005); //서버 소켓을 포트번호 10005로 설정해서 서버생성

			System.out.println("클라이언트 기다리는중입니다");
			Hashtable<User, ObjectOutputStream> ht = new Hashtable<User, ObjectOutputStream>();
			while (true) {
				Socket sock = server.accept(); //클라이언트 요청을 받아온다
				ChatThread thread = new ChatThread(sock, waitUserList, allUserList, allChatRoomList, ht);
				thread.start();
			} 
		} catch (Exception e) {
//			System.out.println("서버아픔ㅠ");
		}
	}

	public static void main(String[] args) {
		new ChatServer();
	}
}