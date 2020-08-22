package green.ac.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

	@Autowired
	boardService service;
	
	//start 화면 
	@RequestMapping({ "/", "home" })
	public String home(Model model) {
		int totalBoardCount = service.getTotalCount();

		int perPage = 10;

		int pageCount = totalBoardCount / perPage;

		if (totalBoardCount % perPage > 0) {
			pageCount++;
		}
		// model.addAttribute("pageNum", pageNum);
		model.addAttribute("totalBoardCount", totalBoardCount);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("list", service.getAll());
		// model.addAttribute("list", service.getList(pageNum, perPage));

		return "home";
	}
	//boardInsert.jsp 이동
	@RequestMapping("/insertBoard")
	public String insertBoard() {

		return "boardInsert";
	}
	//게시글 input
	@RequestMapping(value = "/doInsert", method = RequestMethod.POST)
	public String doinsertBoard(@ModelAttribute Board board) {
		service.insertBoard(board);
		return "redirect:/home";
	}

	// 상세보기 화면
	@RequestMapping("/showDetail")
	public String getOne(Model model, @RequestParam("b_num") int b_num) {

		service.plusHit(b_num);

		model.addAttribute("list", service.getOne(b_num));

		return "showDetail";
	}
	//게시글 삭제
	@RequestMapping("/deleteBoard")
	public String deleteBoard(@RequestParam("b_num") int b_num) {

		service.deleteBoard(b_num);

		return "redirect:/home";
	}
	
	//게시글 수정
	@RequestMapping("/updateBoard")
	public String updateBoard(
			@RequestParam("b_num") int b_num,
			@RequestParam("b_category") String b_category,
			@RequestParam("b_title") String b_title,
			@RequestParam("b_writer") String b_writer,
			@RequestParam("b_contents") String b_contents
		) { 
		
		Board board = new Board();
		board.setB_num(b_num);
		board.setB_category(b_category);
		board.setB_title(b_title);
		board.setB_writer(b_writer);
		board.setB_contents(b_contents);
		 
		service.updateBoard(board);
		return "redirect:/home";
	} 
	
	// 게시글 검색
		@RequestMapping("searchAll")
		public String searchAll(Model model,
				@RequestParam("option") String option,
				@RequestParam("search_desc") String search_desc	) {
			
			model.addAttribute("list", service.searchAll(option, search_desc));
			
			return "home";
		}  
		
}
