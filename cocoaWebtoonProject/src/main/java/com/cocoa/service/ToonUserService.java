package com.cocoa.service;

import java.util.List;

import com.cocoa.domain.CphistoryDTO;
import com.cocoa.domain.ToonUserDTO;

public interface ToonUserService {

	public int signUp(ToonUserDTO user);					//회원가입
	
	public ToonUserDTO login(ToonUserDTO user);				//로그인
	
	public int removeUser(String userId); 					//회원탈퇴
	
	public List<CphistoryDTO> findCphistory(String userId);	//포인트사용내역조회
	
}
