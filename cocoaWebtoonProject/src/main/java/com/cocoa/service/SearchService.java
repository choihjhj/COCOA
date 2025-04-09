package com.cocoa.service;

import java.util.List;

import com.cocoa.domain.WebToonDTO;

public interface SearchService {
	public List<WebToonDTO> search(String searchBox); //검색 조회

}
