package com.cocoa.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.WebToonDTO;
import com.cocoa.mapper.WebToonMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class WebToonServiceImpl implements WebToonService {
	
	@Setter(onMethod_=@Autowired)
	private WebToonMapper mapper;


	@Override
	public List<WebToonDTO> findAll(int dayOfWeek) {
		log.info("요일 : " + dayOfWeek);
		return mapper.selectAll(dayOfWeek);
	}
	
	@Override
	public List<WebToonDTO> findById(int toonId){
		log.info("툰아이디 : " + toonId);
		return mapper.selectByToonId(toonId);
	}
	
	@Override
	public List<WebToonDTO> search(String searchBox){
		log.info("검색값 : " + searchBox);
		return mapper.selectBySearchBox(searchBox);
	}
	
	@Override
	public WebToonDTO getWebToon(int toonId) {
		log.info("toonId : " + toonId);
		return mapper.getWebToon(toonId);
	}
}
