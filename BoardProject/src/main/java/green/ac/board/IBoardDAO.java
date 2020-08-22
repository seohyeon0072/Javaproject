package green.ac.board;

import java.sql.Connection;

public interface IBoardDAO {
	Connection connect();
	void disconnect(Connection con);
	
	Board[] getAll(Connection con);
	Board getOne(Connection con, int b_num);
	
	int insertBoard(Connection con, Board board);
	int updateBoard(Connection con, Board board);
	int deleteBoard(Connection con, int b_num);
	
	public Board[] searchAll(Connection con, String option, String search_desc);
	
	public int plusHit(Connection con, int b_num);
	
	public int getTotalCount(Connection con);
	
	public Board[] getList(Connection con, int pageNum, int docsPerPage);
}
