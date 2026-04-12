package com.cocoa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.ChargeDTO;
import com.cocoa.exception.ChargeFailException;
import com.cocoa.mapper.ChargeMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class ChargeServiceImpl implements ChargeService {

	private final ChargeMapper chargemapper;
	
	@Transactional
	@Override
	public void charge(ChargeDTO charge, String userId) {

	    charge.setUserId(userId);

	    int insertResult = chargemapper.insert(charge);
	    int updateResult = chargemapper.update(charge);

	    if (insertResult != 1 || updateResult != 1) {
	        throw new ChargeFailException("충전 실패");
	    }
	}

}
