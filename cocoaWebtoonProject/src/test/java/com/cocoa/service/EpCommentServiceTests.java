package com.cocoa.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.cocoa.mapper.EpCommentMapper;

@RunWith(MockitoJUnitRunner.class)
public class EpCommentServiceTests {
	
	@Mock
    private EpCommentMapper epCommentMapper;

    @InjectMocks
    private EpCommentServiceImpl epCommentService;

    private final int THREAD_COUNT = 30; //30명 동시성 테스트
    
    @Before
    public void setup() {

        // INSERT: 첫 번째만 성공, 나머지는 예외
        when(epCommentMapper.likeInsertLikecomment(anyInt(), anyString()))
            .thenAnswer(new org.mockito.stubbing.Answer<Integer>() {
                private boolean first = true;

                @Override
                public Integer answer(org.mockito.invocation.InvocationOnMock invocation) {
                    if (first) {
                        first = false;
                        return 1; // 성공
                    }
                    throw new org.springframework.dao.DuplicateKeyException("중복");
                }
            });

        // UPDATE는 성공했다고 가정
        when(epCommentMapper.likeUpdateEpcomment(anyInt()))
            .thenReturn(1);
    }
    
    
    /*
     * (사용자가 좋아요 30번 요청)
     * INSERT 성공 1번만 허용
     * → DB UNIQUE로 방어
     * → 나머지 29개 실패
     * 
     * */
    @Test
    public void 좋아요_1개_성공_29개_실패_동시성_테스트() throws InterruptedException {

        int commentId = 1;

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < THREAD_COUNT; i++) {

            final String userId = "user" + i;

            executor.submit(() -> {
                try {
                    epCommentService.likeComment(commentId, userId);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println("성공 = " + successCount.get());
        System.out.println("실패 = " + failCount.get());

        assertEquals(1, successCount.get());
        assertEquals(THREAD_COUNT - 1, failCount.get());

        verify(epCommentMapper, times(1))
            .likeUpdateEpcomment(commentId);
    }
    

}
