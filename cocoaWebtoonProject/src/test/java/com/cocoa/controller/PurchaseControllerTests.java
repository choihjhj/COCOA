package com.cocoa.controller;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cocoa.domain.ToonUserDTO;
import com.cocoa.mapper.ToonUserMapper;
import com.cocoa.service.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})

public class PurchaseControllerTests {

	@Autowired
    private PurchaseService purchaseService;
	
	@Autowired
    private ToonUserMapper toonUserMapper;
	

    private final int THREAD_COUNT = 10;
    
    @Before
    public void setup() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(2000, Calendar.JANUARY, 1);

    	toonUserMapper.insert(ToonUserDTO.builder().userId("testUser")
    			.pwd("testUser")
    			.birthday(cal.getTime())
    			.phone("010-1234-5678")
    			.userName("testUser")
    			.cocoa(10000)
    			.build());


    }

    @Test
    public void 동시_구매_요청_1건성공_9번실패_처리() throws Exception {
    	ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();

        int epId = 4;
        final String userId = "testUser"; 

        for (int i = 0; i < THREAD_COUNT; i++) {

            executor.submit(() -> {
                try {
                    int result = purchaseService.insertPurchase(userId, epId);

                    if (result == 1) {
                        success.incrementAndGet();
                    } else {
                        fail.incrementAndGet();
                    }

                } catch (Exception e) {
                    fail.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println("성공 = " + success.get());
        System.out.println("실패 = " + fail.get());

        assertEquals(1, success.get());
    	
    }
}
