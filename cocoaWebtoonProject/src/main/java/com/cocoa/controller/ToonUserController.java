package com.cocoa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.service.PurchaseService;
import com.cocoa.service.SessionService;
import com.cocoa.service.ToonUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@Log4j
public class ToonUserController {

	private final ToonUserService toonUserService;
	private final PurchaseService purchaseService;
	private final SessionService sessionservice;

	@GetMapping("/login")
	public String loginPage(@RequestParam(name = "origin", required = false) String origin, 
			@RequestParam(name = "redirect", required = false) String redirectURL,
			Model model, HttpServletRequest request) {
		log.info("로그인 페이지 요청 origin: " + origin);
		

		ToonUserDTO ToonUserDTO = sessionservice.getLoggedInUser(request);

		//로그인 안했을 때
		if (ToonUserDTO == null) {	
			//로그인 안하고 유료 회차 클릭 시
			model.addAttribute("origin", origin);
			model.addAttribute("redirect", redirectURL);
			return "login";
		} else {
			// 이미 로그인 되있을 시
			return "redirect:/layout";
		}
	}

	@PostMapping("/login")
	public String login(
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
			rttr.addAttribute("redirect",redirectURL);
			return "redirect:/login";
		} else {
			log.info("로그인 성공");
			session.setAttribute("ToonUserDTO", loginUser);
			session.setMaxInactiveInterval(60 * 60);
			//구매페이지에서 로그인 할 때
			if(origin.equals("purchase")) {				
				Integer toonId = (Integer)session.getAttribute("toonId");
				log.info("toonId : "+toonId);
				rttr.addAttribute("toonId",toonId);
				return "redirect:/toondetail";
			//댓글작성에서 로그인 할 때
			} else if(origin.equals("episode")) {
				return "redirect:" + redirectURL; //'http://localhost:8081/episode?toonId=11&epId=61'
			}
			return "redirect:/layout";
		}
	}

	@PostMapping("/signup")
	@ResponseBody
	public Map<String, Object> singup(ToonUserDTO user) {
		log.info("회원가입 요청 정보 : " + user);
		Map<String, Object> response = new HashMap<>();
		
		int signupResult = toonUserService.signUp(user);
		log.info("회원가입 결과 :" + signupResult);
		
		if (signupResult == 1) {
			response.put("message", "회원가입 성공");
        } else if (signupResult == 0) {
        	response.put("message", "회원가입 실패: 중복된 아이디");
        } else {
        	 response.put("message", "회원가입 실패: 알 수 없는 오류");
        }
		return response;

	}

	@GetMapping("/myinfo")
	public String myinfo(HttpServletRequest request, Model model) {
		log.info("myinfo 페이지 요청");
		
		ToonUserDTO loggedInUser  = sessionservice.getLoggedInUser(request);
		if(loggedInUser == null)
			return "redirect:/login";
		
		model.addAttribute("ToonUserDTO",toonUserService.login(loggedInUser));
		return "myinfo";

	}

	
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		log.info("logout 요청");
		HttpSession session = request.getSession();
		session.invalidate(); // 세션 무효화 (로그아웃)
		return "redirect:/layout";
	}
	

	@DeleteMapping("/remove")
	@ResponseBody
	public ResponseEntity<String> remove(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ToonUserDTO toonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");

		if (toonUserDTO == null) {
			log.warn("회원 탈퇴 요청 중 로그인되지 않은 사용자.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}


		log.info("회원 탈퇴 유저 : " + toonUserDTO);
		toonUserService.removeUser(toonUserDTO.getUserId());
		session.invalidate(); // 세션 무효화
		log.info("회원탈퇴 완료");
		return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");

	}

	@GetMapping("/cocoahistory")
	public String cocoahistory(HttpServletRequest request, Model model) {
		log.info("cocoahistory 페이지 요청");
		ToonUserDTO toonUserDTO = sessionservice.getLoggedInUser(request);

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
	
	@GetMapping("/mystorage")
	public void mystorage(HttpServletRequest request, Model model) {
		
		ToonUserDTO toonUserDTO = sessionservice.getLoggedInUser(request);
		
		// 로그인 했으면 구매한 에피소드 목록을 전송해야 함
		if(toonUserDTO != null) {
			log.info(purchaseService.getPurchasedEpToonId(toonUserDTO.getUserId()));
			model.addAttribute("loginresult", 1);
			model.addAttribute("PurchaseVO",purchaseService.getPurchasedEpToonId(toonUserDTO.getUserId()));	
		} else {
			model.addAttribute("loginresult", 0);
		}
		
	}
	
	
}
