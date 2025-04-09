package com.cocoa.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cocoa.domain.WebToonDTO;
import com.cocoa.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
	
	private final SearchService searchservice;
	
	/*
     * 검색 페이지 요청
     * GET /search
     * return "search"
     * */
	@GetMapping
	public String search(){
		return "search"; //search.jsp 요청
	}
	
	/*
     * 검색어 기반 웹툰 목록 조회
     * GET /search/ajax?keyword={keyword}
     * return List<WebToonDTO> //JSON 반환
     * */
    @GetMapping("/ajax") // search 페이지요청 URL과 구분하기 위해 요청명 이렇게 함
    @ResponseBody
    public List<WebToonDTO> searchAjax(@RequestParam(name="keyword") String keyword) {
        log.info("Search keyword (Ajax): '" + keyword + "'");
        return searchservice.search(keyword);
    }

}
