package org.kosta.myproject;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.kosta.myproject.mapper.BoardMapper;
import org.kosta.myproject.mapper.ReserveMapper;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinalprojectFarmilyApplicationTests {
	
	@Autowired
	BoardMapper boardMapper;
	
	@Autowired
	ReserveMapper reserveMapper;

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
	


}
