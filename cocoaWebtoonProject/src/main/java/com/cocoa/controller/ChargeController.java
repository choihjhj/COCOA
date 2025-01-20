package com.cocoa.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cocoa.domain.ChargeDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.service.ChargeService;
import com.cocoa.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
@RequestMapping("/*")
public class ChargeController {

	private final ChargeService chargeservice;
	private final SessionService sessionservice;

	@GetMapping("/charge")
	public String charge(HttpServletRequest request) {
		log.info("/charge get 요청 ");
		
		ToonUserDTO loggedInUser = sessionservice.getLoggedInUser(request);
		return (loggedInUser != null) ? "charge" :"redirect:/login";
		
	}

	@PostMapping(value = "/charge")
	public String charge(HttpServletRequest request, ChargeDTO ChargeDTO, RedirectAttributes rttr) throws Exception {
		log.info("charge post 요청 : " + ChargeDTO);
		
		ToonUserDTO loggedInUser = sessionservice.getLoggedInUser(request);
		ChargeDTO.setUserId(loggedInUser.getUserId());		
		
		chargeservice.charge(ChargeDTO);
		Integer epId = (Integer) request.getSession().getAttribute("epId");
		
		if (epId != null) { //구매하다가 충전하러 온거면
			rttr.addAttribute("toonId", request.getSession().getAttribute("toonId"));
			rttr.addAttribute("epId", epId);
			return "redirect:/purchase";
		}
			
		return "redirect:/myinfo"; //myinfo에서 충전하러 온거면
		

	}

}