package com.cocoa.service;

import java.util.List;
import com.cocoa.domain.WebToonDTO;

public interface WebToonService {

	public List<WebToonDTO> findAll(int dayOfWeek); //요일에 해당하는 웹툰목록 조회(HomeController /iayout)
	public WebToonDTO getWebToon(int toonId); //toonid에 해당하는 웹툰목록 조회(WebtoonController /toondetail)
}
