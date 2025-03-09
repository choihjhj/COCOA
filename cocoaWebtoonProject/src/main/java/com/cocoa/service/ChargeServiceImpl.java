package com.cocoa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.ChargeDTO;
import com.cocoa.mapper.ChargeMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class ChargeServiceImpl implements ChargeService {

	private final ChargeMapper chargemapper;
	
	@Transactional
	@Override
	public int charge(ChargeDTO charge, String userId) {
		
		charge.setUserId(userId);

		// 트랜잭션 결과가 성공이면 1 실패하면 0 반환
		return chargemapper.insert(charge)== 1 && chargemapper.update(charge) ? 1 : 0;
	}

}
