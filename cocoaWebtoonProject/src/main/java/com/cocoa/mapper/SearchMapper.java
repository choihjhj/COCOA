package com.cocoa.mapper;

import java.util.List;

import com.cocoa.domain.WebToonDTO;

public interface SearchMapper {
	public List<WebToonDTO> selectBySearchBox(String searchBox);
}
