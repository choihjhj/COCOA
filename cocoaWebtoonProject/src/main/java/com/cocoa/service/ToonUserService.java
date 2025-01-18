package com.cocoa.service;

import java.util.List;

import com.cocoa.domain.CphistoryDTO;
import com.cocoa.domain.ToonUserDTO;

public interface ToonUserService {

	public int signUp(ToonUserDTO user);
	
	public ToonUserDTO login(ToonUserDTO user);
	
	public int removeUser(String userId);
	
	public List<CphistoryDTO> findCphistory(String userId);
	
	public boolean idDupCheck(String userId);
}
