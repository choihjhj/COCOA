package com.cocoa.service;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.PurchaseDTO;
import com.cocoa.domain.PurchaseVO;
import com.cocoa.mapper.EpisodeMapper;
import com.cocoa.mapper.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Service
@Log4j
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseMapper purchaseMapper;
	private final EpisodeMapper episodeMapper;

	@Override
	@Transactional(readOnly = true)
	public List<PurchaseVO> getPurchasedEpisodesByUserId(String userId) {
		return purchaseMapper.getPurchasedEpisodesByUserId(userId);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Integer> getPurchasedEpisodeIdsByUserId(String userId) {
		log.info(userId);
		return purchaseMapper.getPurchasedEpisodeIdsByUserId(userId);
	}

	@Transactional
	@Override
	public int insertPurchase(String userId,int epId) {

		int price = episodeMapper.selectPriceByEpId(epId);
		
		PurchaseDTO p = new PurchaseDTO();
		p.setUserId(userId);
		p.setEpId(epId);
		
		// 1. 먼저 돈 차감 
	    int updated = purchaseMapper.decreaseCocoa(userId, price);

	    if (updated == 0) {
	        return 0; // 잔액 부족
	    }

	    try {
	        // 2. 구매 insert
	        purchaseMapper.insertPurchase(p);
	        return 1;

	    } catch (DuplicateKeyException e) {
	        // 이미 구매 → rollback 발생 (자동으로 돈도 복구)
	        throw e;
	    }
		
		
	}

}
