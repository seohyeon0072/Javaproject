public class ServerProtocol {
   public static final int LIST_RECEIVE = 777;
   // ����
   // ����
   public static final int ENTRANCE_SUCCESS = 1000; // �г��� �ߺ�üũ true
   public static final int ENTRANCE_FAIL = 1001; // �г��� �ߺ�üũ false
   // ����
   public static final int CREATE_CHATROOM_SUCCESS = 1010; // ä�ù� �̸� �ߺ�üũ true
   public static final int CREATE_CHATROOM_FAIL= 1011; // ä�ù� �̸� �ߺ�üũ false
//   public static final int UPDATE_WAITING_INFO= 2000; // �����-���� ���� ���� ������Ʈ
   // ä�ù� ����
//   public static final int ENTER_CHATROOM_SUCCESS = 2200; // ������ ����
//   public static final int UPDATE_WAITING_INFO = 2000; // ���� ���� ���� ������Ʈ
//   public static final int UPDATE_CHAT_INFO = 2500; // ä�ù� �������� ���� ������Ʈ
   public static final int ENTER_CHATROOM_MAX_USER_FAIL = 1021; // �ο� �ʰ� ������ ����
   public static final int ENTER_CHATROOM_KICKOUT_USER_FAIL = 1022; // �̹� ����� ���� ������ false
   // ä�ù� ����
   public static final int CHATROOM_SETTING_SUCCESS = 1030; // ä�ù� �����Ϸ�
//   public static final int UPDATE_WAITING_ROOMINFO = 2100; // ���� - ä�ù� ����Ʈ ������Ʈ
   public static final int ROOM_SETTING_FAIL_INWON = 1031; // ä�ù� ���� ����1 - �ο�üũ
   public static final int ROOM_SETTING_FALSE_TITLE = 1032; // ä�ù� ���� ����2 - ���̸� �ߺ�
   public static final int ROOM_SETTING_FALSE_TITLE_REPEAT = 1033;   //ä�ù� ���� ���� 3
   // ���� ���� : �Ӹ�
//   public static final int UPDATE_CHATROOM_HOST = 2300; // ���� �������� ���� ������Ʈ
//   public static final int UPDATE_WAITING_ROOMINFO = 2100; // ���� - ä�ù� ����Ʈ ������Ʈ
//   public static final int APPOINTED_HOST = 1300; // ���� ���� ���忡�� �˸�
   // ������ ���� ��
//   public static final int UPDATE_CHATROOM_HOST = 2300; // ���� �������� ���� ������Ʈ
   public static final int APPOINTED_HOST = 1300; // ���� ���� ���忡�� �˸�
//   public static final int EXIT_WAITING_INFO = 2400; // ���� �������� ���� ������Ʈ
   public static final int UNEXPECTED_HOST = 1051; // ������ ��������- �ڵ����� �����Ӹ�
   public static final int UPDATE_CHATROOM_HOST = 2300; // ���� �������� ���� ������Ʈ
   public static final int UPDATE_CHATROOM_HOST_APPOINT = 2301;
   
//   public static final int EXIT_WAITING_INFO = 2400; // ���� �������� ���� ������Ʈ
   // ���� ���� : ����
//   public static final int EXIT_WAITING_INFO = 2400; // ���� �������� ���� ������Ʈ
   public static final int KICKOUT_WAITING_INFO = 1060; // ������ �˸� + ���� ���� ������Ʈ
   public static final int NOTICE_KICKOUT_MESSAGE = 1061; // ������ �˸� + ä�ù� ���� ������Ʈ
   // ���� �ʴ��ϱ�
   public static final int INVITE_TO_ROOM = 1070; // �ʴ���� �������� �ʴ�޽��� ����
//   public static final int UPDATE_CHAT_INFO = 2500; // �ʴ� ����
   public static final int ENTER_CHATROOM_SUCCESS = 2200; // ������ ����
   public static final int UPDATE_WAITING_INFO = 2000; // �ʴ� ���� + ���� ������Ʈ
   public static final int INVITE_REJECT = 1071; // �ʴ� ����
   public static final int INVITE_FAIL_MAX_USER = 1072; // �ʴ� ���� - �ο��ʰ�
   public static final int INVITE_FAIL_EXIST_ROOM = 1073; // �ʴ� ���� - �̹� �ٸ��� ����
   // ä�ù� ������
   public static final int EXIT_WAITING_INFO = 2400; // ���� �������� - ���� ������Ʈ
   public static final int EXINT_WAITING_INFO2 = 2401;
   public static final int UPDATE_CHAT_INFO = 2500; // ä�ù� �������� - �������� ������Ʈ
   public static final int UPDATE_WAITING_ROOMINFO = 2600; // ��������X -> ����� + ���� ������Ʈ
   // �޽��� ������
   public static final int RECEIVE_MESSAGE_CHATROOM = 2000; // ä�ù濡�� �޽��� ����
   public static final int RECEIVE_MESSAGE_WAITINGROOM = 2001; // ���ǿ��� �޽��� ����
   public static final int RECEIVE_WISPER_MESSAGE_CHATROOM_SUCCSS = 2002; // ä�ù� �Ӹ� ����
   public static final int RECEIVE_WISPER_MESSAGE_CHATROOM_FAIL_MYSELF = 2003; // ���� ��� ���̵𿡰� ���¶� �Ӹ� ����
   public static final int RECEIVE_WISPER_MESSAGE_WAITINGROOM_SUCCESS = 2004; // ���� �Ӹ� ����
   public static final int RECEIVE_WISPER_MESSAGE_WAITINGROOM_FAIL = 2005; // ���� �Ӹ� ����
   public static final int RECEIVE_WISPER_MESSAGE_FAIL_NOT_FOUND = 2006; //���� ������ ������ �Ӹ� ����
   // �˻�
   public static final int RESULT_CHATROOM_LIST = 2010; // �˻����
   public static final int NOT_FOUNT_CHATROOM = 2011;
   public static final int CHATROOMLIST_ORIGINAL = 2012;
   public static final int CHATTINGROOM_NOT_FOUND_USER = 2032;   //ä�ù濡 ������ ã�� �� �����ϴ�
   // ���� ������
   public static final int UDATE_WAITING_INFORMATION = 2700; // ���� ���� ������Ʈ
   public static final int EXIT_MESSAGE_USERLIST_UPDATE = 3000;
   public static final int WAITINGROOM_USERLIST_AND_ROOMLIST = 3001;
   public static final int WAITINGROOM_UPDATING = 3002;


}