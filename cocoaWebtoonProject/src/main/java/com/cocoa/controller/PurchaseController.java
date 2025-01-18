package com.cocoa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cocoa.domain.EpisodeDTO;
import com.cocoa.domain.PurchaseDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.domain.WebToonDTO;
import com.cocoa.service.EpisodeService;
import com.cocoa.service.PurchaseService;
import com.cocoa.service.ToonUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
@RequestMapping("/*")
public class PurchaseController {

	private final PurchaseService purchaseService;
	private final EpisodeService episodeService;
	private final ToonUserService toonUserService;
	
	@GetMapping("/purchase")
	public String purchase(Integer toonId, Integer epId, HttpServletRequest request, RedirectAttributes rttr, Model model) {

		HttpSession session = request.getSession();
		ToonUserDTO ToonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");

		if (ToonUserDTO == null) {
			rttr.addAttribute("origin", "purchase");
			rttr.addAttribute("toonId", toonId);
			return "redirect:/login"; // 로그인 페이지 URL
		} else {
			// 전달받은 epId로 에피소드 객체 세션에 저장
			session.setAttribute("EpisodeDTO", episodeService.getEpisode(epId));
			return "/purchase";
		}
	}		

	@PostMapping(value = "/purchase")
	public String purchaseaction(HttpServletRequest request, RedirectAttributes rttr) throws Exception {

		log.info("purchase post 들어옴");
		HttpSession session = request.getSession();
		ToonUserDTO toonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");
		EpisodeDTO episodeDTO = (EpisodeDTO) session.getAttribute("EpisodeDTO");
		WebToonDTO webtoonDTO = (WebToonDTO) session.getAttribute("WebToonDTO");

		PurchaseDTO purchaseDTO = new PurchaseDTO();
		purchaseDTO.setUserId(toonUserDTO.getUserId());
		purchaseDTO.setEpId(episodeDTO.getEpId());

		try {
			int result = purchaseService.purchase(purchaseDTO, episodeDTO.getPrice());
			log.info("구매 결과 :" + result);
			session.removeAttribute("EpisodeDTO");
			ToonUserDTO lastestUserInfo = toonUserService.login(toonUserDTO);
			// 유저 정보 업데이트
			session.setAttribute("ToonUserDTO", lastestUserInfo);
			
			rttr.addAttribute("toonId",webtoonDTO.getToonId());
			return "redirect:/toondetail";

		} catch (Exception ex) {

			throw ex; // 예외를 던져서 CommonExceptionAdvice 클래스에서 처리하도록 함

		}

	}
	
	
	@GetMapping("/mystorage")
	public void mystorage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		ToonUserDTO toonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");
		
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
