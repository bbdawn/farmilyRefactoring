package org.kosta.myproject;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.kosta.myproject.mapper.BoardMapper;
import org.kosta.myproject.mapper.MessageMapper;
import org.kosta.myproject.mapper.ReserveMapper;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.MessageVO;
import org.kosta.myproject.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinalprojectFarmilyApplicationTests {
	
	@Autowired
	BoardMapper boardMapper;
	
	@Autowired
	ReserveMapper reserveMapper;
	
	@Autowired
	MessageMapper messageMapper;

	@Test
	void contextLoads() {
	}
	
	@Test
	void findSearchBoardList() {
		String searchOption = "content";
		String searchContent = "a";
		List<BoardVO> list = boardMapper.findSearchBoardList(searchOption,searchContent);
		for(BoardVO bvo : list) {
			System.out.println(bvo);
		}
	}
	
	@Test
	void checkReservationByBoardNo() {
		String boardNo = "131";
		int result = reserveMapper.checkReservationByBoardNo(boardNo);
		System.out.println(result);
	}
	
	@Test
	void adminCheckReservationList() {
		String id = "ddww";
		List<BoardVO> list = reserveMapper.findReservationListForAdmin(id);
		for(BoardVO bvo : list) {
			System.out.println(bvo);
		}
	}
	
	@Test
	void deleteReservation() {
		ReservationVO rvo = new ReservationVO();
		rvo.setBoardNo("136");
		rvo.setReservationDate("2022-07-31");
		reserveMapper.deleteReservation(rvo);
		System.out.println("예약취소완료 ");
	}
	
	@Test
	void findMyMessageList() {
		String id = "iiiu";
		List<MessageVO> list = messageMapper.findMyMessageList(id);
		for(MessageVO mvo : list) {
			System.out.println(mvo);
		}
	}
	
	@Test
	void sendMessage() {
		String sender = "iiiu";
		String receiver = "ddww";
		String content = "5명 예약 가능한가요?";
		MessageVO mvo = new MessageVO();
		mvo.setSender(sender);
		mvo.setReceiver(receiver);
		mvo.setContent(content);
		messageMapper.sendMessage(mvo);
		System.out.println("메세지보내기완료");
	}
	
	@Test
	void getMessageList() {
		String id = "ddww";
		List<MessageVO> list = messageMapper.getMessageList(id);
		for(MessageVO mvo : list) {
			System.out.println(mvo);
		}
	}
	
	@Test
	void sendMessageList() {
		String id = "ddww";
		List<MessageVO> list = messageMapper.sendMessageList(id);
		for(MessageVO mvo : list) {
			System.out.println(mvo);
		}
	}
	
	@Test
	void messageDetail() {
		String messageNo = "1";
		MessageVO messageVO = messageMapper.messageDetail(messageNo);
		System.out.println(messageVO);
	}
	
	@Test
	void updateCheckMessage() {
		String messageNo = "1";
		messageMapper.updateCheckMessage(messageNo);
		System.out.println("업데이트완료");
	}
	

}
