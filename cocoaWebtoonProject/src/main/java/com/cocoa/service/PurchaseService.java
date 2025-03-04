package com.cocoa.service;

import java.util.List;
import com.cocoa.domain.PurchaseDTO;
import com.cocoa.domain.PurchaseVO;

public interface PurchaseService {
	
	public List<PurchaseVO> getPurchasedEpToonId (String userId);
	
	public List<Integer> getPurchasedEpId (String userId);
	
	public int purchase(PurchaseDTO p, int price);	//구매 insert 후 toonuser cocoa 차감 업데이트
}
