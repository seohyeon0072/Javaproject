
public class ClientProtocol {
   // 클라이언트
   public static final int INVITE = 77;
   public static final int ACCESS = 100; // 접속 ID입력
   public static final int CREATE_CHATROOM = 110;// 채팅방 생성
   public static final int ENTER_CHATROOM = 120; // 채팅방 입장
   public static final int CHATROOM_SETTING = 130; //채팅방 수정 
   public static final int GRANT_CHATROOM_HOST = 140; // 방장 권한 : 위임
   public static final int CHATROOM_HOST_OUT = 141;
   public static final int CHATROOM_HOST_EXIT = 150; // 방장이 나갈때 방장 위임
   public static final int KICKOUT_USER = 160; // 방장권한 : 강퇴
   public static final int INVITE_USER = 170; // 유저 초대하기
   public static final int REPLY_INVITE_USER = 171; // 초대(수락/거절)에 대한 결과
   public static final int INVITE_REJECT_CLIENT = 172;
   public static final int EXIT_CHATROOM = 180; // 채팅방 나가기
   public static final int SEND_MESSAGE_CHATROOM = 200; // 대화방 메시지 보내기
   public static final int SEND_MESSAGE_WAITINGROOM = 201; // 대기실 메시지 보내기
   public static final int SEND_WISPER_MESSAGE_CHATROOM = 202; // 대화방 귓속말 보내기
   public static final int SEND_WISPER_MESSAGE_WAITINGROOM = 204; // 대기실 귓속말 보내기
   public static final int SEARCH_TITLE = 210; // 검색
   public static final int CHATROOM_RETURN_ORIGINAL = 212;
   public static final int EXIT_WAITINGROOM = 220; // 대기실 나가기

}