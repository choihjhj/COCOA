package com.cocoa.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cocoa.domain.PurchaseDTO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class PurchaseMapperTests {

	@Setter(onMethod_ = @Autowired)
	private PurchaseMapper mapper;

	@Test
	@Transactional
	public void insertTest() {
		//given
        PurchaseDTO purchase = new PurchaseDTO();
        purchase.setUserId("aaa");
        purchase.setEpId(72);

        //when
        mapper.insert(purchase);

        //then
        log.info("Inserted Purchase ID: " + purchase.getPurchaseId());
		List<Integer> result = mapper.selectByUserId("aaa");
		result.forEach(var -> log.info(var));
	}
}
