package com.cocoa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cocoa.domain.ToonUserDTO;
import com.cocoa.service.ToonUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@Log4j
public class ToonUserController {

	private final ToonUserService toonUserService;

	@GetMapping("/login")
	public String loginPage(@RequestParam(name = "toonId", required = false) Integer toonId,
			@RequestParam(name = "origin", required = false) String origin, 
			@RequestParam(name = "redirect", required = false) String redirectURL,
			Model model, HttpServletRequest request) {
		log.info("로그인 페이지 요청"+ origin);
		
		HttpSession session = request.getSession();
		ToonUserDTO ToonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");
		//로그인 안했을 때
		if (ToonUserDTO == null) {	
			//로그인 안하고 유료 회차 클릭 시
			model.addAttribute("origin", origin);
			model.addAttribute("toonId", toonId);
			model.addAttribute("redirect", redirectURL);
			return "login";
		} else {
			// 이미 로그인 되있을 시
			return "redirect:/layout";
		}
	}

	@PostMapping("/login")
	public String login(@RequestParam(name = "toonId", required = false) Integer toonId,
			@RequestParam(name = "origin", required = false) String origin,
			@RequestParam(name = "redirect", required = false) String redirectURL,
			RedirectAttributes rttr, ToonUserDTO user,
			HttpServletRequest request) {
		log.info("로그인 요청" + user);
		HttpSession session = request.getSession();
		// 회원 모든 정보가 담겨있는 객체를 세션에 저장
		ToonUserDTO loginUser = toonUserService.login(user);
		if (loginUser == null) {
			log.info("로그인 실패");
			rttr.addFlashAttribute("loginResult", "0");
			rttr.addAttribute("origin",origin);
			rttr.addAttribute("toonId",toonId);
			rttr.addAttribute("redirect",redirectURL);
			return "redirect:/login";
		} else {
			log.info("로그인 성공");
			session.setAttribute("ToonUserDTO", loginUser);
			session.setMaxInactiveInterval(60 * 60);
			//구매페이지에서 로그인 할 떼
			if(origin.equals("purchase")) {
				rttr.addAttribute("toonId",toonId);
				return "redirect:/toondetail";
			//댓글작성에서 로그인 할 때
			} else if(origin.equals("comment")) {
				return "redirect:" + redirectURL;
			}
			return "redirect:/layout";
		}
	}

	@PostMapping("/signup")
	public String singup(ToonUserDTO user, RedirectAttributes rttr) {
		log.info("회원가입 요청 정보 : " + user);
		int signupResult = toonUserService.signUp(user);
		// 1 또는 0
		log.info("회원가입 결과 :" + signupResult);
		rttr.addFlashAttribute("signupResult", signupResult);
		return "redirect:/login";
	}

	@GetMapping("/myinfo")
	public String myinfo(HttpServletRequest request, Model model, RedirectAttributes rttr) {
		log.info("myinfo 페이지 요청");
		HttpSession session = request.getSession();
		ToonUserDTO ToonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");

		if (ToonUserDTO != null) {
			return "myinfo";
		} else {
			// loginUser가 null인 경우 로그인 페이지로 리다이렉트 또는 다른 처리를 수행
			rttr.addFlashAttribute("origin", "myinfo");
			return "redirect:/login"; // 로그인 페이지 URL
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		log.info("logout 요청");
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/layout";
	}

	@GetMapping("/remove")
	public String remove(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ToonUserDTO toonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");
		log.info("회원 탈퇴 유저 : " + toonUserDTO);
		toonUserService.removeUser(toonUserDTO.getUserId());
		session.invalidate();
		log.info("회원탈퇴 완료");
		return "redirect:/login";
	}

	@GetMapping("/cocoahistory")
	public String cocoahistory(RedirectAttributes rttr, HttpServletRequest request, Model model) {
		log.info("cocoahistory 페이지 요청");
		HttpSession session = request.getSession();
		ToonUserDTO toonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");

		// loginUser가 null이 아닌 경우에만 cocoahistory.jsp를 반환
		if (toonUserDTO != null) {
			// 코코아 내역 가져와서 jsp 에 전달하는 작업
			model.addAttribute("list", toonUserService.findCphistory(toonUserDTO.getUserId()));
			return "cocoahistory";
		} else {
			// loginUser가 null인 경우 로그인 페이지로 리다이렉트 또는 다른 처리를 수행
			return "redirect:/login"; // 로그인 페이지 URL

		}
	}
	

	// 아이디 중복 체크 로직
	@GetMapping(value = "/iddupcheck", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String iddupcheck(@RequestParam("userId") String userId) {
		log.info("아이디 중복체크" + userId);
		if (toonUserService.idDupCheck(userId)) {
			return "0";
		} else {
			return "1";
		}
	}
}
