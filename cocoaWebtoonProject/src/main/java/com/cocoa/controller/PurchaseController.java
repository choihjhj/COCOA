package com.cocoa.controller;

import javax.servlet.http.HttpServletRequest;
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
	
	@GetMapping("/purchase")
	public String purchase(Integer toonId, Integer epId, HttpServletRequest request, RedirectAttributes rttr, Model model) {

		ToonUserDTO loggedInUser  = getLoggedInUser(request);

		if (loggedInUser  == null) {
			rttr.addAttribute("origin", "purchase"); //origin 어느 페이지에서 접속요청 했는지 확인하는 flag
			rttr.addAttribute("toonId", toonId);
			return "redirect:/login"; // 로그인 페이지 URL
		} 
		
		
		EpisodeDTO episode = episodeService.getEpisode(epId);
		model.addAttribute("EpisodeDTO", episode);
		
		WebToonDTO webtoon = webtoonservice.getWebToon(toonId);
		model.addAttribute("ToonName", webtoon.getToonName()); //웹툰 Name 출력위해 model에 담기
		
		model.addAttribute("UserCocoa", toonUserService.login(loggedInUser).getCocoa()); //변동된 유저 Cocoa잔액 출력위해 model에 담기
		return "/purchase";
		
		
	}		

	@PostMapping(value = "/purchase")
	public String purchaseaction(HttpServletRequest request, RedirectAttributes rttr) throws Exception {

		log.info("purchase post 들어옴");
		ToonUserDTO loggedInUser  = getLoggedInUser(request);		
		if (loggedInUser == null) {
	        return "redirect:/login";
	    }
		
		Integer epId = Integer.parseInt(request.getParameter("epId"));

	    // 구매 로직
	    EpisodeDTO episodeDTO = episodeService.getEpisode(epId);
	    
	    PurchaseDTO purchaseDTO = new PurchaseDTO();
	    purchaseDTO.setUserId(loggedInUser.getUserId());
	    purchaseDTO.setEpId(episodeDTO.getEpId());
	    	    
	    purchaseService.purchase(purchaseDTO, episodeDTO.getPrice());

	    
	    rttr.addAttribute("toonId", episodeDTO.getToonId());
	    return "redirect:/toondetail";
		

	}
	
	 private ToonUserDTO getLoggedInUser(HttpServletRequest request) {
		 return (ToonUserDTO) request.getSession().getAttribute("ToonUserDTO");
	    }
	

}
