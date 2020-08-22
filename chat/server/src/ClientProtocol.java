
public class ClientProtocol {
   // Ŭ���̾�Ʈ
   public static final int INVITE = 77;
   public static final int ACCESS = 100; // ���� ID�Է�
   public static final int CREATE_CHATROOM = 110;// ä�ù� ����
   public static final int ENTER_CHATROOM = 120; // ä�ù� ����
   public static final int CHATROOM_SETTING = 130; //ä�ù� ���� 
   public static final int GRANT_CHATROOM_HOST = 140; // ���� ���� : ����
   public static final int CHATROOM_HOST_OUT = 141;
   public static final int CHATROOM_HOST_EXIT = 150; // ������ ������ ���� ����
   public static final int KICKOUT_USER = 160; // ������� : ����
   public static final int INVITE_USER = 170; // ���� �ʴ��ϱ�
   public static final int REPLY_INVITE_USER = 171; // �ʴ�(����/����)�� ���� ���
   public static final int INVITE_REJECT_CLIENT = 172;
   public static final int EXIT_CHATROOM = 180; // ä�ù� ������
   public static final int SEND_MESSAGE_CHATROOM = 200; // ��ȭ�� �޽��� ������
   public static final int SEND_MESSAGE_WAITINGROOM = 201; // ���� �޽��� ������
   public static final int SEND_WISPER_MESSAGE_CHATROOM = 202; // ��ȭ�� �ӼӸ� ������
   public static final int SEND_WISPER_MESSAGE_WAITINGROOM = 204; // ���� �ӼӸ� ������
   public static final int SEARCH_TITLE = 210; // �˻�
   public static final int CHATROOM_RETURN_ORIGINAL = 212;
   public static final int EXIT_WAITINGROOM = 220; // ���� ������

}