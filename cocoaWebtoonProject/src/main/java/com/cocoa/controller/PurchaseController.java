package com.cocoa.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cocoa.domain.EpisodeDTO;
import com.cocoa.domain.PurchaseDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.domain.WebToonDTO;
import com.cocoa.service.EpisodeService;
import com.cocoa.service.PurchaseService;
import com.cocoa.service.SessionService;
import com.cocoa.service.ToonUserService;
import com.cocoa.service.WebToonService;
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
	private final WebToonService webtoonservice;
	private final SessionService sessionservice;

	/*
     * 유료 에피소드 구매 페이지 요청
     * GET /purchase?toonId={toonId}&epId={epId}
     * return "purchase"
     * */
	@GetMapping("/purchase")
	public String purchase(@RequestParam Integer toonId, @RequestParam Integer epId, HttpServletRequest request, RedirectAttributes rttr, Model model) {

		ToonUserDTO loggedInUser  = sessionservice.getLoggedInUser(request);

		//구매하려는 toonId, epId 세션에 저장(충전하고 세션데이터 읽어서/purchase?toonId={toonId}&epId={epId}로 들어오려고)
		request.getSession().setAttribute("toonId", toonId);
		request.getSession().setAttribute("epId", epId);

		if (loggedInUser  == null) {
			rttr.addAttribute("origin", "purchase"); //origin 어느 페이지에서 접속요청 했는지 확인하는 flag
			return "redirect:/login"; // 로그인 페이지 URL
		} 


		EpisodeDTO episode = episodeService.getEpisode(epId);
		model.addAttribute("EpisodeDTO", episode);

		WebToonDTO webtoon = webtoonservice.getWebToon(toonId);
		model.addAttribute("ToonName", webtoon.getToonName()); //웹툰 Name 출력위해 model에 담기

		model.addAttribute("UserCocoa", toonUserService.login(loggedInUser).getCocoa()); //변동된 유저 Cocoa잔액 출력위해 model에 담기
		return "/purchase";


	}		

	/*
     * 유료 에피소드 구매 추가
     * POST /purchase
     * return "redirect:/toondetail"
     * */
	@PostMapping(value = "/purchase")
	public String purchaseaction(HttpServletRequest request, RedirectAttributes rttr) throws Exception {

		log.info("purchase post 들어옴");
		
		ToonUserDTO loggedInUser  = sessionservice.getLoggedInUser(request);		
		if (loggedInUser == null) {
			return "redirect:/login";
		}

		Integer epId = (Integer) request.getSession().getAttribute("epId"); 
		log.info("epId = " + epId);
	    if (epId == null) {	    	
	        rttr.addFlashAttribute("errorMessage", "에피소드 정보가 없습니다. 다시 시도해 주세요.");
	        return "redirect:/errorPage";  // errorPage.jsp로 리다이렉트
	    }


	    // 구매 로직
	    EpisodeDTO episodeDTO = episodeService.getEpisode(epId);	    
	    PurchaseDTO purchaseDTO = new PurchaseDTO();
	    purchaseDTO.setUserId(loggedInUser.getUserId());
	    purchaseDTO.setEpId(episodeDTO.getEpId());

	    int purchaseResult = purchaseService.insertPurchase(purchaseDTO, episodeDTO.getPrice());
	    if(purchaseResult == 0) {
	    	rttr.addFlashAttribute("errorMessage", "구매 작업이 실패했습니다. 다시 시도해 주세요.");
	    	return "redirect:/errorPage";
	    }
	    
	    //중앙처리로 toonId, epId 세션 삭제 (혹시나 남아 있을 세션 메모리 누수 방지를 위해)
	    sessionservice.clearPurchaseSessionData(request);		    
	    rttr.addAttribute("toonId", episodeDTO.getToonId());
	    return "redirect:/toondetail";

	}


}
