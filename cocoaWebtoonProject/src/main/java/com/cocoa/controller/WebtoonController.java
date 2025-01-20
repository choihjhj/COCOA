package com.cocoa.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cocoa.domain.EpisodeDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.domain.WebToonDTO;
import com.cocoa.service.EpisodeService;
import com.cocoa.service.PurchaseService;
import com.cocoa.service.SessionService;
import com.cocoa.service.WebToonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/*")
@RequiredArgsConstructor
public class WebtoonController {

	private final WebToonService webtoonservice;
	private final EpisodeService episodeservice;
	private final PurchaseService purchaseservice;
	private final SessionService sessionservice;

	@GetMapping(value ="/toondetail")
	public String toondetail(@RequestParam("toonId") int toonId, HttpServletRequest request, Model model) {
		log.info("toondetail 요청 toonId 값 : "+toonId);
		
		
		// 웹툰 정보 가져오기
		WebToonDTO webtoon = webtoonservice.getWebToon(toonId);
		model.addAttribute("WebToonDTO", webtoon);
		
		// 웹툰id에 해당하는 에피소드 정보 가져오기
		List<EpisodeDTO> episodes = episodeservice.findBytoonId(toonId);
		model.addAttribute("episodes", episodes);
		
		// 로그인 상태 확인 후, 구매한 에피소드 정보 전달
		ToonUserDTO loggedInUser = sessionservice.getLoggedInUser(request);
				
		// 로그인 한 상태일 때 구매한 웹툰Id를 jsp로 전달
		if (loggedInUser != null) {
	        List<Integer> purchasedEpIds = purchaseservice.getPurchasedEpId(loggedInUser.getUserId());
	        model.addAttribute("purchasedEpIds", purchasedEpIds);
	    }
		
		//중앙처리로 toonId, epId 세션 삭제 (혹시나 남아 있을 세션 메모리 누수 방지를 위해)
	    sessionservice.clearPurchaseSessionData(request);
	    
		return "toondetail"; // 웹툰 상세 페이지로 리턴
	}

}
