import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;
//server ���� ȭ��
public class ChatServer {
	private Vector<User> allUserList;
	private Vector<User> waitUserList;
	private Vector<ChatRoom> allChatRoomList;
	public ChatServer() {
		allUserList = new Vector<User>();
		waitUserList = new Vector<User>();
		allChatRoomList = new Vector<ChatRoom>();
		try {
			ServerSocket server = new ServerSocket(10005); //���� ������ ��Ʈ��ȣ 10005�� �����ؼ� ��������

			System.out.println("Ŭ���̾�Ʈ ��ٸ������Դϴ�");
			Hashtable<User, ObjectOutputStream> ht = new Hashtable<User, ObjectOutputStream>();
			while (true) {
				Socket sock = server.accept(); //Ŭ���̾�Ʈ ��û�� �޾ƿ´�
				ChatThread thread = new ChatThread(sock, waitUserList, allUserList, allChatRoomList, ht);
				thread.start();
			} 
		} catch (Exception e) {
//			System.out.println("�������Ĥ�");
		}
	}

	public static void main(String[] args) {
		new ChatServer();
	}
}