package com.cocoa.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.WebToonDTO;
import com.cocoa.mapper.WebToonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class WebToonServiceImpl implements WebToonService {
		
	private final WebToonMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public List<WebToonDTO> findAll(int dayOfWeek) {
		log.info("요일 : " + dayOfWeek);
		return mapper.selectAll(dayOfWeek);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<WebToonDTO> findById(int toonId){
		log.info("툰아이디 : " + toonId);
		return mapper.selectByToonId(toonId);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public WebToonDTO getWebToon(int toonId) {
		log.info("toonId : " + toonId);
		return mapper.getWebToon(toonId);
	}
}
