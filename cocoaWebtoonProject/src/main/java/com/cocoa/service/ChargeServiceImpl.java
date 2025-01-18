package com.cocoa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocoa.domain.ChargeDTO;
import com.cocoa.mapper.ChargeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Service
@Log4j
public class ChargeServiceImpl implements ChargeService {

	private final ChargeMapper chargemapper;
	
	@Transactional
	@Override
	public int charge(ChargeDTO charge) {
		log.info("charge 정보: "+ charge);
		// 트랜잭션 결과가 성공이면 1 실패하면 0 반환
		return chargemapper.insert(charge)== 1 && chargemapper.update(charge) ? 1 : 0;
	}

}
