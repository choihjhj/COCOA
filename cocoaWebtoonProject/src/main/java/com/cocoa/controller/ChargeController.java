package com.cocoa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cocoa.domain.ChargeDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.service.ChargeService;
import com.cocoa.service.ToonUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
@RequestMapping("/*")
public class ChargeController {

	private final ChargeService chargeservice;
	private final ToonUserService toonUserService;

	@GetMapping("/charge")
	public String charge(@RequestParam(required = false, value = "epId") Integer epId, HttpServletRequest request, RedirectAttributes rttr, Model model) {
		log.info("/charge get 요청 ");
		HttpSession session = request.getSession();
		ToonUserDTO ToonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");
		// loginUser가 null이 아닌 경우에만 charge.jsp를 반환
		if (ToonUserDTO != null) {

		    if (epId != null) {
		        // epId가 전달된 경우
		        model.addAttribute("epId", epId);
		    } 
		    return "charge";
		// login 하지 않은 경우
		} else {
			return "redirect:/login"; // 로그인 페이지 URL
		}
	}

	@PostMapping(value = "/charge")
	public String charge(Integer epId, HttpServletRequest request, ChargeDTO ChargeDTO, Model model, RedirectAttributes rttr) throws Exception {
		log.info("charge post 요청 : " + ChargeDTO);
		
		HttpSession session = request.getSession();
		ToonUserDTO ToonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");
		ChargeDTO.setUserId(ToonUserDTO.getUserId());
		
		try {
			int result = chargeservice.charge(ChargeDTO);
			log.info("충전결과 :" + result);
			// 세션의 아이디를 가지고 최신 유저정보 가져오기
			ToonUserDTO lastestUserInfo = toonUserService.login(ToonUserDTO);
			// 최신 유저정보로 유저 객체 업데이트
			session.setAttribute("ToonUserDTO", lastestUserInfo);
			if (epId != null) {
				rttr.addAttribute("epId", epId);
				return "redirect:/purchase";
			}
			
			return "redirect:/myinfo";
		} catch (Exception ex) {
			throw ex;
		}

	}

}