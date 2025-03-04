package com.cocoa.service;

import java.util.List;
import com.cocoa.domain.PurchaseDTO;
import com.cocoa.domain.PurchaseVO;

public interface PurchaseService {
	
	public List<PurchaseVO> getPurchasedEpisodesByUserId (String userId); //mystorage에서 구매일순으로 구매회차목록 조회
	
	public List<Integer> getPurchasedEpisodeIdsByUserId (String userId); //toondetail에서 구매epid목록 조회
	
	public int insertPurchase(PurchaseDTO p, int price);	//구매 insert
}
