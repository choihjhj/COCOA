package com.cocoa.controller;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cocoa.service.WebToonService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final WebToonService webtoonservice;

	
	@GetMapping(value = "/")
	public String home() {
		return "redirect:/layout";
	}

	
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
	
}
