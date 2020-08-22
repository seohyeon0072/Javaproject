public class ServerProtocol {
   public static final int LIST_RECEIVE = 777;
   // 서버
   // 접속
   public static final int ENTRANCE_SUCCESS = 1000; // 닉네임 중복체크 true
   public static final int ENTRANCE_FAIL = 1001; // 닉네임 중복체크 false
   // 대기방
   public static final int CREATE_CHATROOM_SUCCESS = 1010; // 채팅방 이름 중복체크 true
   public static final int CREATE_CHATROOM_FAIL= 1011; // 채팅방 이름 중복체크 false
//   public static final int UPDATE_WAITING_INFO= 2000; // 방생성-대기실 유저 정보 업데이트
   // 채팅방 입장
//   public static final int ENTER_CHATROOM_SUCCESS = 2200; // 방입장 성공
//   public static final int UPDATE_WAITING_INFO = 2000; // 대기실 유저 정보 업데이트
//   public static final int UPDATE_CHAT_INFO = 2500; // 채팅방 유저에게 정보 업데이트
   public static final int ENTER_CHATROOM_MAX_USER_FAIL = 1021; // 인원 초과 방입장 실패
   public static final int ENTER_CHATROOM_KICKOUT_USER_FAIL = 1022; // 이미 강퇴된 유저 방입장 false
   // 채팅방 수정
   public static final int CHATROOM_SETTING_SUCCESS = 1030; // 채팅방 수정완료
//   public static final int UPDATE_WAITING_ROOMINFO = 2100; // 대기실 - 채팅방 리스트 업데이트
   public static final int ROOM_SETTING_FAIL_INWON = 1031; // 채팅방 수정 실패1 - 인원체크
   public static final int ROOM_SETTING_FALSE_TITLE = 1032; // 채팅방 수정 실패2 - 방이름 중복
   public static final int ROOM_SETTING_FALSE_TITLE_REPEAT = 1033;   //채팅방 수정 실패 3
   // 방장 권한 : 임명
//   public static final int UPDATE_CHATROOM_HOST = 2300; // 방장 변경으로 인한 업데이트
//   public static final int UPDATE_WAITING_ROOMINFO = 2100; // 대기실 - 채팅방 리스트 업데이트
//   public static final int APPOINTED_HOST = 1300; // 위임 받은 방장에게 알림
   // 방장이 나갈 때
//   public static final int UPDATE_CHATROOM_HOST = 2300; // 방장 변경으로 인한 업데이트
   public static final int APPOINTED_HOST = 1300; // 위임 받은 방장에게 알림
//   public static final int EXIT_WAITING_INFO = 2400; // 대기실 유저에게 정보 업데이트
   public static final int UNEXPECTED_HOST = 1051; // 방장이 강제종료- 자동으로 방장임명
   public static final int UPDATE_CHATROOM_HOST = 2300; // 방장 변경으로 인한 업데이트
   public static final int UPDATE_CHATROOM_HOST_APPOINT = 2301;
   
//   public static final int EXIT_WAITING_INFO = 2400; // 대기실 유저에게 정보 업데이트
   // 방장 권한 : 강퇴
//   public static final int EXIT_WAITING_INFO = 2400; // 대기실 유저에게 정보 업데이트
   public static final int KICKOUT_WAITING_INFO = 1060; // 강퇴사실 알림 + 대기실 정보 업데이트
   public static final int NOTICE_KICKOUT_MESSAGE = 1061; // 강퇴사실 알림 + 채팅방 정보 업데이트
   // 유저 초대하기
   public static final int INVITE_TO_ROOM = 1070; // 초대받은 유저에게 초대메시지 보냄
//   public static final int UPDATE_CHAT_INFO = 2500; // 초대 성공
   public static final int ENTER_CHATROOM_SUCCESS = 2200; // 방입장 성공
   public static final int UPDATE_WAITING_INFO = 2000; // 초대 성공 + 대기실 업데이트
   public static final int INVITE_REJECT = 1071; // 초대 거절
   public static final int INVITE_FAIL_MAX_USER = 1072; // 초대 실패 - 인원초과
   public static final int INVITE_FAIL_EXIST_ROOM = 1073; // 초대 실패 - 이미 다른방 입장
   // 채팅방 나가기
   public static final int EXIT_WAITING_INFO = 2400; // 대기실 유저에게 - 정보 업데이트
   public static final int EXINT_WAITING_INFO2 = 2401;
   public static final int UPDATE_CHAT_INFO = 2500; // 채팅방 유저에게 - 유저정보 업데이트
   public static final int UPDATE_WAITING_ROOMINFO = 2600; // 남은유저X -> 방삭제 + 대기실 업데이트
   // 메시지 보내기
   public static final int RECEIVE_MESSAGE_CHATROOM = 2000; // 채팅방에서 메시지 받음
   public static final int RECEIVE_MESSAGE_WAITINGROOM = 2001; // 대기실에서 메시지 받음
   public static final int RECEIVE_WISPER_MESSAGE_CHATROOM_SUCCSS = 2002; // 채팅방 귓말 성공
   public static final int RECEIVE_WISPER_MESSAGE_CHATROOM_FAIL_MYSELF = 2003; // 없는 사람 아이디에게 보냈때 귓말 실패
   public static final int RECEIVE_WISPER_MESSAGE_WAITINGROOM_SUCCESS = 2004; // 대기실 귓말 성공
   public static final int RECEIVE_WISPER_MESSAGE_WAITINGROOM_FAIL = 2005; // 대기실 귓말 실패
   public static final int RECEIVE_WISPER_MESSAGE_FAIL_NOT_FOUND = 2006; //내가 나한테 보낼때 귓말 실패
   // 검색
   public static final int RESULT_CHATROOM_LIST = 2010; // 검색결과
   public static final int NOT_FOUNT_CHATROOM = 2011;
   public static final int CHATROOMLIST_ORIGINAL = 2012;
   public static final int CHATTINGROOM_NOT_FOUND_USER = 2032;   //채팅방에 유저를 찾을 수 없습니다
   // 대기실 나가기
   public static final int UDATE_WAITING_INFORMATION = 2700; // 대기실 정보 업데이트
   public static final int EXIT_MESSAGE_USERLIST_UPDATE = 3000;
   public static final int WAITINGROOM_USERLIST_AND_ROOMLIST = 3001;
   public static final int WAITINGROOM_UPDATING = 3002;


}