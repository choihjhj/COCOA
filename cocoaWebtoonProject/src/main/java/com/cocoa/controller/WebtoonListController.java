package com.cocoa.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.cocoa.service.WebToonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/*")
@RequiredArgsConstructor
public class WebtoonListController {

	private final WebToonService webtoonservice;
	private final EpisodeService episodeservice;
	private final PurchaseService purchaseservice;

	@GetMapping(value ="/layout")
	public String layout(@RequestParam(required = false)String dayOfWeek, Model model) {
		if(dayOfWeek == null) {
	        // 현재 날짜 얻기
	        LocalDate currentDate = LocalDate.now();
	        // 현재 날짜를 기반으로 요일 계산
	        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
	        // 요일을 숫자로 변환
	        int numericDayOfWeek = currentDayOfWeek.getValue();
	        // 요일에 맞는 웹툰 객체 찾아서 전달
			model.addAttribute("webtoons",webtoonservice.findAll(numericDayOfWeek)); 
			model.addAttribute("dayOfWeek",numericDayOfWeek);
			return "layout";
		} else {
			model.addAttribute("webtoons",webtoonservice.findAll(Integer.parseInt(dayOfWeek)));
			model.addAttribute("dayOfWeek",dayOfWeek);
			return "layout";
		}
	}
	

	@GetMapping(value ="/toondetail")
	public void toondetail(@RequestParam("toonId") int toonId, HttpServletRequest request, Model model) {
		log.info("toondetail 요청 toonId 값 : "+toonId);
		
		HttpSession session = request.getSession();
		
		// 웹툰 객체 찾기 & 세션에 저장
		WebToonDTO webtoon = webtoonservice.getWebToon(toonId);
		session.setAttribute("WebToonDTO", webtoon);
		
		// 웹툰id에 해당하는 에피소드 객체 찾기
		List<EpisodeDTO> episodes = episodeservice.findBytoonId(toonId);
		model.addAttribute("episodes", episodes);
		
		// 로그인 객체 찾기
		ToonUserDTO ToonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");
		// 로그인 한 상태일 때 구매한 웹툰Id를 jsp로 전달
		if(ToonUserDTO != null) {
		model.addAttribute("purchasedEpIds",purchaseservice.getPurchasedEpId(ToonUserDTO.getUserId()) ) ;
		}
		
	}

}
