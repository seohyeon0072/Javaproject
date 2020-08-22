package green.ac.member;

 

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	@Autowired
	MemberService service;
	
	@RequestMapping("/goLogin")
	public String login() {
		return "LoginForm";
	}
	@RequestMapping("/goJoin")
	public String join() {
		return "JoinForm";
	}
	@RequestMapping(value="/doJoin",method=RequestMethod.POST)
	public String dojoin(@ModelAttribute Member member,Model model) {
		int r = service.insertMember(member);
		if(r==0) {
			model.addAttribute("msg", "이미 존재하는 아이디입니다");
			return "JoinForm";
		}else {
			model.addAttribute("msg", "가입이 완료되었습니다.");
			return "LoginForm";
		}
		
	}
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String doLogin(@ModelAttribute Member member,HttpSession session) {
		boolean result = service.loginMember(member,session);
		String page = null;
		if(result==true) {
			page= "redirect:/";
		}else {
			 page="LoginForm";
		}
		return page;
	}
	@RequestMapping("/doLogout")
	public String doLogout(HttpSession session) {
		service.logoutMember(session) ;
		return "LoginForm";
	} 
	/*회원정보 수정 */
	@RequestMapping(value="/doUpdate",method=RequestMethod.POST)
	public String doUpdate(@ModelAttribute Member member,HttpSession session) {
		service.updateMember(member,session);
		return "menu";
	}
	/*회원정보페이지 이동 */
	@RequestMapping("/goUpdate")
	public String goUpdate(HttpSession session,Model model) {
		
		model.addAttribute("info", service.memberInfo(session));
		return "memberUpdate";
	}
	/* 탈퇴 */
	@RequestMapping(value="/dodelete",method=RequestMethod.GET)
	public String doDelete(Member member,HttpSession session) {
		String id = member.getMember_id();
		System.out.println("delete"+ id);
		service.deleteMember(id, session);
		return "redirect:goLogin";
	}
}
