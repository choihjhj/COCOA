package com.cocoa.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

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

	@Override
	public int signUp(ToonUserDTO user) {
		try {
			log.info(user);
			// 정상적으로 회원가입 되면 1을 반환
			return toonUserMapper.insert(user);
		} catch (Exception e) {
			log.info("중복된 키값이 발견되었습니다.");
			// 중복된 키값이 발견되면 0을 반환
			return 0;
		}
	}

	@Override
	public ToonUserDTO login(ToonUserDTO user) {
		try {
			ToonUserDTO dbUser = toonUserMapper.selectUserById(user.getUserId());
			if (dbUser.getPwd().equals(user.getPwd())) {
				// 로그인 성공
				return dbUser;
			} else {
				// 로그인 실패
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int removeUser(String userId) {
		// 삭제되면 1 삭제되지 않으면 0 반환
		return toonUserMapper.deleteByUserId(userId);
	}

	@Override
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

	@Override
	public boolean idDupCheck(String userId) {
		int result = toonUserMapper.idDupCheck(userId);

		return result == 0;
	}

}
