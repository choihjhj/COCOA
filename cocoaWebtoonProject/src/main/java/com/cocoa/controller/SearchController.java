package com.cocoa.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String search(){
		return "search"; //search.jsp 요청
	}
	
	// 검색 Ajax 요청 처리: JSON 반환
    @GetMapping("/ajax")
    @ResponseBody
    public List<WebToonDTO> searchAjax(@RequestParam(name="keyword", required = false) String keyword) {
        log.info("Search keyword (Ajax): " + keyword);

        if (keyword != null && !keyword.trim().isEmpty()) {
            return webtoonservice.search(keyword); // JSON으로 자동 변환
        }
        return List.of(); // 빈 리스트 반환
    }

}
