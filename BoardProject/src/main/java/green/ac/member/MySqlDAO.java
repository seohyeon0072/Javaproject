package green.ac.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.springframework.stereotype.Repository;

@Repository
public class MySqlDAO implements IDAO{
	public MySqlDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch (ClassNotFoundException e) {
		}
	}

	@Override
	public Connection connect() {
		Connection con = null;
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","1234");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	@Override
	public void disconnect(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
		
	}

	@Override
	public int insertMember(Connection con, Member member) {
		int result = 0 ; 
		String sql = "insert into member(member_id, member_pw, member_name, member_phone, member_email)"
				+ "values (?,?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,member.getMember_id());
			pstmt.setString(2,member.getMember_pw());
			pstmt.setString(3,member.getMember_name());
			pstmt.setString(4,member.getMember_phone());
			pstmt.setString(5,member.getMember_email());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	private void close(Statement pstmt) {
		try {
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void close(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

 
	@Override
	public Member searchMember(Connection con, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int idcheck(Connection con, String id) {
		int result = 0 ; 
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM member where member_id = ?" ;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result =rs.getInt(1);
			}else {
				result = 0 ; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		System.out.println("idcheck"+ result);
		return result;
		
	}

	@Override
	public boolean loginMember(Connection con , Member member) {
		ResultSet rs = null;
		String sql = "SELECT * FROM member where member_id = ? and member_pw =?" ;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,member.getMember_id());
			pstmt.setString(2,member.getMember_pw());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return false;
	}

	@Override
	public Member memberInfo(Connection con, String member_id) {
		ResultSet rs = null;
		String sql = "SELECT * FROM member where member_id = ?" ;
		PreparedStatement pstmt = null;
		Member member =null; 
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,member_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new Member(
						rs.getString("member_id"),
						rs.getString("member_pw"),
						rs.getString("member_name"),
						rs.getString("member_phone"),
						rs.getString("member_email")
						);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return member;
	}

	@Override
	public int memberUpdate(Connection con, Member member) {
		int result = 0;
		String sql = "update member set member_pw=?,member_name=?,member_phone=?,member_email=? where member_id=?";
		PreparedStatement pStmt = null;

		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setString(1, member.getMember_pw());
			pStmt.setString(2, member.getMember_name());
			pStmt.setString(3,member.getMember_phone());
			pStmt.setString(4, member.getMember_email());
			pStmt.setString(5, member.getMember_id());

			result = pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		return result;
	}

	@Override
	public int memberDelete(Connection con, String member_id) {
		int result = 0; 
		System.out.println("dao"+ member_id);
		String sql = "delete from member where member_id=?";
		PreparedStatement pStmt = null;
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setString(1, member_id);
			System.out.println("");
			result = pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		return result;
	}

	 
	
}
