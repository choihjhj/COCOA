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
		
		// 양쪽 끝의 공백 제거
        String trimmedKeyword = searchBox.trim();
        log.info("searchService trimmedKeyword : '"+trimmedKeyword+"'");
        
        if (trimmedKeyword != null && !trimmedKeyword.isEmpty()) {
            return mapper.selectBySearchBox(trimmedKeyword); 
        }
 
        return List.of(); // 빈 리스트 반환

	}

}
