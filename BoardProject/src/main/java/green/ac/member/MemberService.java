package green.ac.member;

import java.sql.Connection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired
	private IDAO dao; 
	
	public boolean loginMember(Member member, HttpSession session) {
		Connection con = dao.connect();
		boolean result = dao.loginMember(con,member);
		if(result) {
			session.setAttribute("member_id", member.getMember_id());
			session.setAttribute("member_name", member.getMember_name());
		}
		dao.disconnect(con);
		 return result;
	}
	public void logoutMember(HttpSession session) {
		session.invalidate();
	}

	public int insertMember(Member member) {
		Connection con = dao.connect();
		int flag = 0 ;
		int result = 0;
		flag = idcheck(member.getMember_id());
		if(flag>0) {
			System.out.println("���̵� ������!!! ");
		}else {
			result = dao.insertMember(con, member);
		}
		dao.disconnect(con);
		
		//���̵� �����ϸ� result =0 , ���Լ����ϸ� 1 
		 return result;
	}
	public int idcheck(String userid) {
		Connection con = dao.connect();
		int result = dao.idcheck(con, userid);
		dao.disconnect(con);
		return result;
	}
	/*ȸ������ ��������*/
	public Member memberInfo(HttpSession session) {
		Connection con = dao.connect();
		Member member = null;
		String id = String.valueOf(session.getAttribute("member_id"));
		member = dao.memberInfo(con, id);
		dao.disconnect(con);
		session.setAttribute("member_id", id);
		return member;
	}
	/*ȸ������ ����*/
	public int updateMember(Member member, HttpSession session) {
		Connection con = dao.connect();
		int result = dao.memberUpdate(con, member);
		dao.disconnect(con);
		session.setAttribute("member_id", member.getMember_id());
		return result;
	}
	/*ȸ�� ����*/
	public int deleteMember(String id, HttpSession session) {
		Connection con = dao.connect();
		System.out.println("ddurleh dhktdma");
		int result = dao.memberDelete(con,id);
		dao.disconnect(con);
		logoutMember(session);
		return result;
	}
	
}
