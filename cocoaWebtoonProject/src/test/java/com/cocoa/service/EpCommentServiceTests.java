//package com.cocoa.service;
//
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.cocoa.exception.NotFoundException;
//import com.cocoa.mapper.EpCommentMapper;
//
//import lombok.extern.log4j.Log4j;
//
//@RunWith(MockitoJUnitRunner.class)
////@RunWith(MockitoExtension.class)
//public class EpCommentServiceTests {
//
//	@Mock
//    private EpCommentMapper epCommentMapper;
//
//    @InjectMocks
//    private EpCommentServiceImpl service;  // Service 실제 객체 생성 (Mock 객체들 주입)
//
//    private ToonUserDTO loggedInUser;
//    private EpCommentDTO epCommentDTO;
//
//    @Before
//    public void setUp() {
//        // 테스트용 사용자 객체 생성
//        loggedInUser = new ToonUserDTO();
//        loggedInUser.setUserId("user1"); // 예시 사용자 ID
//
//        // 테스트용 댓글 객체 생성
//        epCommentDTO = new EpCommentDTO();
//        epCommentDTO.setCommentId(1);  // 댓글 ID 설정
//        epCommentDTO.setCommentBody("수정된 댓글 내용");
//        epCommentDTO.setUserId(loggedInUser.getUserId()); // 사용자 ID 설정
//    }
//
//    @Test
//    public void testModifyComment_Success() {
//        // 댓글이 존재한다고 가정하여, mock을 설정
//        Mockito.when(epCommentMapper.checkIfEpComment(epCommentDTO)).thenReturn(1);  // 댓글 존재
//        Mockito.when(epCommentMapper.updateComment(epCommentDTO)).thenReturn(1);  // 댓글 수정 성공
//
//        ResponseEntity<String> response = service.modifyComment(epCommentDTO, loggedInUser);
//
//        // 수정 성공에 대한 검증
//        assertEquals("success", response.getBody());
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void testModifyComment_CommentNotFound() {
//        // 댓글이 존재하지 않는 경우
//        Mockito.when(epCommentMapper.checkIfEpComment(epCommentDTO)).thenReturn(0);  // 댓글 없음
//
//        // 댓글 수정 시 NotFoundException 예외가 발생하는지 확인
//        service.modifyComment(epCommentDTO, loggedInUser);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testModifyComment_Failure() {
//        // 댓글이 존재한다고 가정하되, 수정이 실패한 경우
//        Mockito.when(epCommentMapper.checkIfEpComment(epCommentDTO)).thenReturn(1);  // 댓글 존재
//        Mockito.when(epCommentMapper.updateComment(epCommentDTO)).thenReturn(0);  // 댓글 수정 실패
//
//        // 댓글 수정 실패 시 IllegalArgumentException이 발생하는지 확인
//        service.modifyComment(epCommentDTO, loggedInUser);
//    }
//
//
//
//}
