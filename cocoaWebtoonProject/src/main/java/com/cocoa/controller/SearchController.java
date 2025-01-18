package com.cocoa.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cocoa.domain.WebToonDTO;
import com.cocoa.service.WebToonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
	
	private final WebToonService webtoonservice;
	
	@GetMapping
	public void search(@RequestParam(name="keyword", required = false) String keyword, Model model){
		log.info("Search keyword: " + keyword);
		
		if(keyword != null && !keyword.trim().isEmpty()) {		
			List<WebToonDTO> webtoons = webtoonservice.search(keyword);
			model.addAttribute("webtoons", webtoons);
		}			
	}

}
