package com.cocoa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.PurchaseDTO;
import com.cocoa.domain.PurchaseVO;
import com.cocoa.mapper.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Service
@Log4j
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseMapper purchaseMapper;

	@Override
	public List<PurchaseVO> getPurchasedEpToonId(String userId) {
		return purchaseMapper.selectEpToonIdByUserId(userId);
	}
	
	
	@Override
	public List<Integer> getPurchasedEpId(String userId) {
		log.info(userId);
		return purchaseMapper.selectByUserId(userId);
	}

	@Transactional
	@Override
	public int purchase(PurchaseDTO p, int price) {

		log.info("purchase");
		if (purchaseMapper.select(p) == 0) {
			return (purchaseMapper.insert(p) == 1 && purchaseMapper.update(p, price)) ? 1 : 0;
		} else {
			return 0;
		}

	}

}
