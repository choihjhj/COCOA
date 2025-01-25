package com.cocoa.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.CphistoryDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.mapper.ToonUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
public class ToonUserServiceImpl implements ToonUserService {

	private final ToonUserMapper toonUserMapper;
	
	// 중복 아이디 조회 메서드 추출
	private ToonUserDTO checkUserExistsById(String userId) {
		return toonUserMapper.selectUserById(userId);
	}

	@Override
	@Transactional
	public int signUp(ToonUserDTO user) {	
		log.info(user);		
		ToonUserDTO existingUser = checkUserExistsById(user.getUserId());
		if (existingUser != null) { //이미 userid 존재
	        return 0;
	    }
		
		// 중복되지 않으면 insert 실행
		return toonUserMapper.insert(user);

	}

	@Override
	@Transactional(readOnly = true)
	public ToonUserDTO login(ToonUserDTO user) {
		ToonUserDTO existingUser = checkUserExistsById(user.getUserId());
		return (existingUser != null && existingUser.getPwd().equals(user.getPwd())) ? existingUser : null;
	}

	@Override
	@Transactional
	public int removeUser(String userId) {
		// 삭제되면 1 삭제되지 않으면 0 반환
		return toonUserMapper.deleteByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CphistoryDTO> findCphistory(String userId) {
		List<CphistoryDTO> list = toonUserMapper.selectCphistory(userId);
		Collections.reverse(list);
		int sum = 0;
		for (CphistoryDTO CphistoryDTO : list) {
			if (CphistoryDTO.getCpType().equals("충전")) {
				CphistoryDTO.setBalance(sum += CphistoryDTO.getCocoa());
			} else if (CphistoryDTO.getCpType().equals("결제")) {
				CphistoryDTO.setBalance(sum -= CphistoryDTO.getCocoa());
			}
		}
		Collections.reverse(list);
		return list;
	}


}
