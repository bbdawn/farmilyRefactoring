package org.kosta.myproject;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.kosta.myproject.mapper.BoardMapper;
import org.kosta.myproject.mapper.ReserveMapper;
import org.kosta.myproject.vo.BoardVO;
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
	


}
