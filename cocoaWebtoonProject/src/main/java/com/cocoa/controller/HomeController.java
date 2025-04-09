package com.cocoa.controller;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cocoa.service.WebToonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final WebToonService webtoonservice;

	/*
     * 홈페이지 요청
     * GET /
     * return "redirect:/layout"
     * */
	@GetMapping(value = "/")
	public String home() {
		return "redirect:/layout";
	}

	
	/*
     * 특정 요일에 맞는 웹툰 목록 조회
     * GET /layout
     * return "layout"
     * */
	@GetMapping(value = "/layout")
	public String layout(@RequestParam(required = false) String dayOfWeek, Model model) {
	    // 현재 날짜를 기반으로 요일 계산
	    int numericDayOfWeek = (dayOfWeek == null)
	            ? LocalDate.now().getDayOfWeek().getValue()  // dayOfWeek가 null인 경우 현재 날짜의 요일을 사용
	            : Integer.parseInt(dayOfWeek);  // dayOfWeek가 있을 경우 그 값을 사용

	    // 해당 요일에 맞는 웹툰 객체 찾기
	    model.addAttribute("webtoons", webtoonservice.findAll(numericDayOfWeek));
	    model.addAttribute("dayOfWeek", numericDayOfWeek);

	    return "layout";
	}

	
	/*
     * 에러 페이지 요청
     * GET /errorPage
     * return "errorPage"
     * */
	@GetMapping(value = "/errorPage")
	public String error(@RequestParam("errorMessage") String message, Model model) {
		log.info("errorMessage : "+message);
		
		model.addAttribute("errorMessage", message);
		return "errorPage";
	}
	
}
