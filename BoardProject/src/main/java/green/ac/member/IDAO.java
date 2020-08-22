package green.ac.member;

import java.sql.Connection;

import javax.servlet.http.HttpSession;

public interface IDAO {
	public Connection connect();
	public void disconnect(Connection con);
	public int insertMember(Connection con,Member member);
	public Member searchMember(Connection con,String id);
	public int idcheck(Connection con,String id);
	public boolean loginMember(Connection con, Member member);
	public Member memberInfo(Connection con, String member_id);
	public int memberUpdate(Connection con,Member member);
	public int memberDelete(Connection con,String member_id);

}
