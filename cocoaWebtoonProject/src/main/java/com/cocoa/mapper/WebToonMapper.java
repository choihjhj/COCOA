package com.cocoa.mapper;

import java.util.List;

import com.cocoa.domain.WebToonDTO;

public interface WebToonMapper {
	public List<WebToonDTO> selectAll(int dayOfWeek);
	
	public WebToonDTO getWebToon(int toodId);
	
	public List<WebToonDTO> selectByToonId(int toonId);
	
	public List<WebToonDTO> selectBySearchBox(String searchBox);
	
}
