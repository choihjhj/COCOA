package com.cocoa.service;

import java.util.List;

import com.cocoa.domain.WebToonDTO;

public interface WebToonService {

	public List<WebToonDTO> findAll(int dayOfWeek);
	public List<WebToonDTO> findById(int toonId);
	public List<WebToonDTO> search(String searchBox);
	public WebToonDTO getWebToon(int toonId);
}
