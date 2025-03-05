package com.cocoa.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.WebToonDTO;
import com.cocoa.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{
	private final SearchMapper mapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<WebToonDTO> search(String searchBox){
		log.info("검색값 : " + searchBox);
		return mapper.selectBySearchBox(searchBox);
	}

}
