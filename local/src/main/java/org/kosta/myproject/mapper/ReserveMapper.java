package org.kosta.myproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.ReservationVO;

@Mapper
public interface ReserveMapper {
	
	//예약가능날짜 insert
	void availableReservation(ReservationVO reservationVO);

	//등록한 게시물 번호 가져오기
	String getBoardNo(String id);
	
	//예약가능날짜 가져오기
	List<ReservationVO> findReservateDate(String boardNo);
	
	//예약하기
	void registerReservation(ReservationVO rvo);
	
	//나의 예약 현황 조회
	List<BoardVO> findReservationListById(String id);

	//내가 예약한 게시물 조회
	List<String> findReservationBoardNo(String id);

	//내가 예약한 게시물 번호 중복없이 조회
	List<BoardVO> findReservationBoardNoDistinct(String id);

	//게시물 예약 여부 (예약이 있는지 없는지 확인) 20220713 추가 
	int checkReservationByBoardNo(String boardNo);

	//농촌활동 관리자의 예약 현황 조회 20220714추가
	List<BoardVO> findReservationListForAdmin(String id);

	//나(관리자)의 농촌활동 게시물 번호 중복없이 조회 20220714추가
	List<BoardVO> findReserBoardNoDistinctForAdmin(String id);


}
