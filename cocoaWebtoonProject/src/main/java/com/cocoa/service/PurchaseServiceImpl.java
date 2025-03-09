package com.cocoa.service;

import java.util.List;
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

		log.info("purchase");
		
		PurchaseDTO p = new PurchaseDTO();
		p.setUserId(userId);
		p.setEpId(epId);
		
		int price = episodeMapper.selectPriceByEpId(epId);
		
		if (purchaseMapper.checkIfEpisodePurchasedByUser(p) == 0) { //구매 여부 확인 후 insert & update 작업
			return (purchaseMapper.insertPurchase(p) == 1 && purchaseMapper.updateCocoaBalanceAfterPurchase(p, price)) ? 1 : 0;
		} else {
			return 0;
		}

	}

}
