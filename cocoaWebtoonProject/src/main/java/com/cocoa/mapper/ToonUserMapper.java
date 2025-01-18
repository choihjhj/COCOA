package com.cocoa.mapper;

import java.util.List;

import com.cocoa.domain.CphistoryDTO;
import com.cocoa.domain.ToonUserDTO;

public interface ToonUserMapper {
	
	public int insert(ToonUserDTO toonUser);
	
	public ToonUserDTO selectUserById(String userId);
	
	public int deleteByUserId(String userId);
		
	public List<CphistoryDTO> selectCphistory(String userId);
	
	public int idDupCheck(String userId);
}
