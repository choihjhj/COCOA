package com.cocoa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cocoa.domain.PurchaseDTO;
import com.cocoa.domain.PurchaseVO;

public interface PurchaseMapper {
	
	public List<PurchaseVO> getPurchasedEpisodesByUserId (String userId); //purchasedate 순으로 구매회차목록 조회
	public List<Integer> getPurchasedEpisodeIdsByUserId(String userId); //구매epid 조회
	public int checkIfEpisodePurchasedByUser(PurchaseDTO p); //구매 여부 확인
	public int insertPurchase(PurchaseDTO p);
	public boolean updateCocoaBalanceAfterPurchase(@Param("p") PurchaseDTO p, @Param("price") int price);
}
