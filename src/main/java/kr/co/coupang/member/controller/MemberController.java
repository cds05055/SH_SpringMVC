package kr.co.coupang.member.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.coupang.member.domain.Member;
import kr.co.coupang.member.service.MemberService;
import kr.co.coupang.member.service.impl.MemberServiceImpl;

@Controller
@SessionAttributes({"memberId", "memberName"})
public class MemberController {
	@Autowired
	private MemberService service;
	
	// doGet - 페이지 이동
	@RequestMapping(value="/member/register.do", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		
		return "member/register";
	}
	
	// doPost - 데이터 저장
	@RequestMapping(value="/member/register.do", method=RequestMethod.POST)
	public String registerMember(HttpServletRequest request, HttpServletResponse response
			, @RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, @RequestParam("memberName") String memberName
			, @RequestParam("memberAge") int memberAge
			, @RequestParam("memberGender") String memberGender
			, @RequestParam("memberEmail") String memberEmail
			, @RequestParam("memberPhone") String memberPhone
			, @RequestParam("memberAddress") String memberAddress
			, @RequestParam("memberHobby") String memberHobby
			, Model model) {
//		request.setAttribute("", "");
//		request.getRequestDispatcher("").forward(request, response);
//		response.sendRedirect("/index.jsp");
//		String memberId = request.getParameter("memberId");
//		request.setCharacterEncoding("UTF-8");
		
		Member member = new Member(memberId, memberPw, memberName, memberAge, memberGender, memberEmail, memberPhone, memberAddress, memberHobby);
		try {
			int result = service.registerMember(member);
			if(result > 0) {
				// 성공
//				response.sendRedirect("/index.jsp");
				return "redirect:/index.jsp";
			} else {
				// 실패
//				request.setAttribute("msg", "실패");
				model.addAttribute("msg", "회원가입 실패");
//				request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(request, response);
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace(); 	// 콘솔창에 빨간색으로 뜨게 하는 구문
			model.addAttribute("msg", e.getMessage());	// 콘솔창에 뜨는 메시지를 웹 페이지에서도 뜨게 함
			return "common/errorPage";	
		}
	}
	
	@RequestMapping(value="/member/mypage.do", method=RequestMethod.GET)
	public String showDetailMemeber(HttpServletRequest request
			, @RequestParam("memberId") String memberId
			, Model model) {
		
		try {
			Member member = service.selectOneById(memberId);
			if(member != null) {
				model.addAttribute("member", member);
				return "member/mypage";
			} else {
				model.addAttribute("msg", "정보 불러오기 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/modify.do", method=RequestMethod.GET)
	public String modifyViewMember(HttpServletRequest request
			, @RequestParam("memberId") String memberId
			, Model model) {
		
		try {
			Member member = service.selectOneById(memberId);
			if(member != null) {
				model.addAttribute("member", member);
				return "member/modify";
			} else {
				model.addAttribute("msg", "정보 불러오기 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/modify.do", method=RequestMethod.POST)
	public String modifyMember(
			HttpServletRequest request
			, @RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, @RequestParam("memberEmail") String memberEmail
			, @RequestParam("memberPhone") String memberPhone
			, @RequestParam("memberAddress") String memberAddress
			, @RequestParam("memberHobby") String memberHobby
			,Model model
			) {
		Member member = new Member(memberId, memberPw, memberEmail, memberPhone, memberAddress, memberHobby);
		
		try {
			int result = service.modifyMember(member);
			if(result > 0) {
				return "redirect:/member/mypage.do";
			} else {
				model.addAttribute("msg", "수정 실패");
				return "common/errorPage";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)
	public String removeMember(
			HttpServletRequest request
			, @RequestParam("memberId") String memberId
			, Model model) {
		try {
			int result = service.removeMember(memberId);
			if(result > 0) {
				return "redirect:/member/logout.do";
			} else {
				model.addAttribute("msg", "회원탈퇴 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/login.do", method=RequestMethod.POST)
	public String memberLogin(
			HttpServletRequest request
			, @RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, Model model) {
		// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?
		try {
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPw(memberPw);
			Member mOne = service.memberLoginCheck(member);
			if(mOne != null) {
				// 성공
//				model.addAttribute("member", mOne);
//				HttpSession session = request.getSession();
//				session.setAttribute("memberId", mOne.getMemberId());
//				session.setAttribute("memberName", mOne.getMemberName());
				model.addAttribute("memberId", mOne.getMemberId());
				model.addAttribute("memberName", mOne.getMemberName());
				return "redirect:/index.jsp";
			} else {
				// 실패
				model.addAttribute("msg", "로그인 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession session_prev
			// SessionStatus는 스프링의 어노테이션(@SessionAttributes)로 지원되는 세션을 만료시킴
			// 사용된 메소드는 setComplete();
			, SessionStatus session
			, Model model) {
		if(session != null) {
//			session.invalidate();
			session.setComplete();
			if(session.isComplete()) {
				// 세션 만료 유효성 체크
			}
			return "redirect:/index.jsp";
		} else {
			model.addAttribute("msg", "로그아웃 실패");
			return "common/errorPage";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
