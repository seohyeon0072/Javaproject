package green.ac.board;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import green.ac.member.Member;

@Repository
public class bMySqlDAO implements IBoardDAO {

	public bMySqlDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
		}
	}

	@Override
	public Connection connect() {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");

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
	public Board[] getAll(Connection con) {
		Board[] list = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			pStmt = con.prepareStatement("SELECT * FROM board ORDER BY b_num DESC");
			rs = pStmt.executeQuery();

			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();

			list = new Board[count];

			int idx = 0;
			while (rs.next()) {
				int b_num = rs.getInt("b_num");
				String b_category = toKor(rs.getString("b_category"));
				String b_title = toKor(rs.getString("b_title"));
				String b_writer = toKor(rs.getString("b_writer"));
				String b_contents = toKor(rs.getString("b_contents"));
				String b_reg = toKor(rs.getString("b_reg"));
				int b_hit = rs.getInt("b_hit");

				list[idx++] = new Board(b_num, b_category, b_title, b_writer, b_contents, b_reg, b_hit);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pStmt);
		}

		return list;
	}

	@Override
	public Board getOne(Connection con, int number) {
		Board list = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try {
			pStmt = con.prepareStatement("SELECT * FROM board WHERE b_num = ?");
			pStmt.setInt(1, number);
			rs = pStmt.executeQuery();

			list = new Board();

			if (rs.next()) {
				int b_num = rs.getInt("b_num");
				String b_category = toKor(rs.getString("b_category"));
				String b_title = toKor(rs.getString("b_title"));
				String b_writer = toKor(rs.getString("b_writer"));
				String b_contents = toKor(rs.getString("b_contents"));
				String b_reg = toKor(rs.getString("b_reg"));
				int b_hit = rs.getInt("b_hit");
				list = new Board(b_num, b_category, b_title, b_writer, b_contents, b_reg, b_hit);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pStmt);
		}

		return list;
	}

	@Override
	public int insertBoard(Connection con, Board board) {
		// TODO Auto-generated method stub
		int result = 0;
		PreparedStatement pStmt = null;
		try {
			pStmt = con.prepareStatement(
					"INSERT INTO board (b_title, b_category, b_writer, b_contents, b_reg, b_id) VALUES (?,?,?,?,?,?)");

			pStmt.setString(1, toEn(board.getB_title()));
			pStmt.setString(2, toEn(board.getB_category()));
			pStmt.setString(3, toEn(board.getB_writer()));
			pStmt.setString(4, toEn(board.getB_contents()));
			pStmt.setString(5, toEn(board.getB_reg()));
			pStmt.setString(6, toEn(board.getB_id()));
			result = pStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}

		return result;
	}

	@Override
	public int updateBoard(Connection con, Board board) {
		int result = 0;
		String sql = "update board set b_category= ?, b_title= ?, b_writer= ?, b_contents= ? where b_num= ?";
		PreparedStatement pStmt = null;

		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setString(1, board.getB_category());
			pStmt.setString(2, board.getB_title());
			pStmt.setString(3, board.getB_writer());
			pStmt.setString(4, board.getB_contents());
			System.out.println(board.getB_num());
			pStmt.setInt(5, board.getB_num());

			result = pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		return result;
	}

	@Override
	public int deleteBoard(Connection con, int b_num) {
		int result = 0;
		PreparedStatement pStmt = null;
		try {
			pStmt = con.prepareStatement("DELETE FROM board WHERE b_num = ?");
			pStmt.setInt(1, b_num);
			result = pStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		return result;
	}

	@Override
	public int plusHit(Connection con, int num) {
		int result = 0;
		PreparedStatement pStmt = null;
		try {
			System.out.println("hit");
			pStmt = con.prepareStatement("UPDATE board SET b_hit = IFNULL(b_hit, 0)+1 WHERE b_num =?");
			pStmt.setInt(1, num);
			result = pStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}

		return result;
	}

	// °Ë»ö
	public Board[] searchAll(Connection con, String option, String search_desc) {
		Board[] list = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			if (option.equals("b_num")) {
				pStmt = con.prepareStatement("SELECT * FROM board WHERE b_num LIKE ? ORDER BY b_num DESC");
				pStmt.setString(1, toEn("%" + search_desc + "%"));
			} else if (option.equals("b_title")) {
				pStmt = con.prepareStatement("SELECT * FROM board WHERE b_title LIKE ? ORDER BY b_num DESC");
				pStmt.setString(1, toEn("%" + search_desc + "%"));
			} else if (option.equals("b_writer")) {
				pStmt = con.prepareStatement("SELECT * FROM board WHERE b_writer LIKE ? ORDER BY b_num DESC");
				pStmt.setString(1, toEn("%" + search_desc + "%"));
			} else if (option.equals("all")) {
				pStmt = con.prepareStatement("SELECT * FROM board WHERE b_num LIKE ? OR b_title LIKE ? OR b_writer LIKE ? ORDER BY b_num DESC");
				pStmt.setString(1, toEn("%" + search_desc + "%"));
				pStmt.setString(2, toEn("%" + search_desc + "%"));
				pStmt.setString(3, toEn("%" + search_desc + "%"));
			}

			rs = pStmt.executeQuery();

			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();

			list = new Board[count];

			int idx = 0;
			while (rs.next()) {
				int b_num = rs.getInt("b_num");
				String b_category = toKor(rs.getString("b_category"));
				String b_title = toKor(rs.getString("b_title"));
				String b_writer = toKor(rs.getString("b_writer"));
				String b_contents = toKor(rs.getString("b_contents"));
				String b_reg = toKor(rs.getString("b_reg"));
				int b_hit = rs.getInt("b_hit");

				list[idx] = new Board(b_num,b_category, b_title, b_writer, b_contents, b_reg, b_hit);
				idx++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pStmt);
		}

		return list;
	}

	@Override
	public int getTotalCount(Connection con) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Board[] getList(Connection con, int pageNum, int docsPerPage) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toKor(String en) {
		String kor = null;

		try {
			kor = new String(en.getBytes("8859_1"), "euc_kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return kor;
	}

	public String toEn(String kor) {
		String en = null;

		try {
			en = new String(kor.getBytes("euc_kr"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return en;
	}

	public void close(PreparedStatement pStmt) {
		try {
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
