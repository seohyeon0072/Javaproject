package green.ac.board;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 

@Service
public class boardService {
	@Autowired
	private IBoardDAO dao;
	
	public Board[] getList(int pageNum, int docsPerPage) {
		Connection con = dao.connect();
		Board[] list = dao.getList(con, pageNum, docsPerPage);
		dao.disconnect(con);
		return list;
	}
	public int getTotalCount() {
		Connection con = dao.connect();
		int result = dao.getTotalCount(con);
		dao.disconnect(con);
		return result;
	}
	public Board[] getAll() {
		Connection con = dao.connect();
		Board[] list = dao.getAll(con);
		dao.disconnect(con);
		return list;
	}
	public int insertBoard(Board board) {
		Connection con = dao.connect();
		int result = dao.insertBoard(con, board);
		dao.disconnect(con);
		return result;
	}
	public Board getOne(int b_num) {
		Connection con = dao.connect();
		Board list = dao.getOne(con, b_num);
		dao.disconnect(con);
		return list;
	}
	public int plusHit(int b_num) {
		Connection con = dao.connect();
		int result = dao.plusHit(con, b_num);
		dao.disconnect(con);
		return result;
	}
	public int deleteBoard(int b_num) {
		Connection con = dao.connect();
		int result = dao.deleteBoard(con, b_num);
		dao.disconnect(con);
		return result;
	} 
	public int updateBoard(Board board) {
		Connection con = dao.connect();
		int result = dao.updateBoard(con, board);
		dao.disconnect(con);
		return result;
	}  
	public Board[] searchAll(String option, String search_desc) {
		Connection con = dao.connect();
		Board[] list = dao.searchAll(con, option, search_desc);
		dao.disconnect(con);
		return list;
	}
}
