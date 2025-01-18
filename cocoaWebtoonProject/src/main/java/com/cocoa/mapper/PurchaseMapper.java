package com.cocoa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cocoa.domain.PurchaseDTO;
import com.cocoa.domain.PurchaseVO;

public interface PurchaseMapper {
	
	public List<PurchaseVO> selectEpToonIdByUserId (String userId);
	public List<Integer> selectByUserId(String userId);
	public int select(PurchaseDTO p);
	public int insert(PurchaseDTO p);
	public boolean update(@Param("p") PurchaseDTO p, @Param("price") int price);
}
