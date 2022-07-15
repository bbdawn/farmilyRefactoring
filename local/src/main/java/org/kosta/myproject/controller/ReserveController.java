package org.kosta.myproject.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.kosta.myproject.service.BoardService;
import org.kosta.myproject.service.ReserveService;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.MemberVO;
import org.kosta.myproject.vo.ReservationVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReserveController {
	private final BoardService boardService;
	private final ReserveService reserveService;
	
	@PostMapping("registerFarmPost")
	public String registerFarmPost(@AuthenticationPrincipal MemberVO membervo,BoardVO boardVO,String boardCategori,String[] dateArray, 
			MultipartFile file,RedirectAttributes redirect ) throws Exception {
		boardVO.setId(membervo.getId());
		
		if (file.isEmpty()) {
			boardVO.setFilename("");
			boardVO.setFilepath("");

		} else {
			String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();

			File saveFile = new File(projectPath, fileName);

			file.transferTo(saveFile);

			boardVO.setFilename(fileName);
			boardVO.setFilepath("/files/" + fileName);
		}
		boardService.registerBoard(boardVO);
		
		for(int i=0;i<dateArray.length;i++) {
			if(dateArray[i].length()>1) {
				
				String boardNo = reserveService.getBoardNo(membervo.getId());
				ReservationVO reservationVO = new ReservationVO();
				reservationVO.setReservationDate(dateArray[i]);
				reservationVO.setBoardNo(boardNo);
				reserveService.availableReservation(reservationVO);	
			}else {
				continue;
			}
		}
		redirect.addAttribute("boardCategori", boardCategori);
		return "redirect:/guest/boardListByBoardCategori";
	}
	
	@GetMapping("reservationForm")
	public String reservationForm(@AuthenticationPrincipal MemberVO membervo,String boardNo, Model model) {
		List<ReservationVO> rdateList = reserveService.findReservateDate(boardNo);
		BoardVO boardVO = boardService.boardView(boardNo);
		model.addAttribute("rdateList",rdateList);
		model.addAttribute("boardVO",boardVO);
		model.addAttribute("memberVO", membervo); //20220714추가
		return "reserve/reservationForm";
	}
	
	@PostMapping("registerReservation")
	public String registerReservation(@AuthenticationPrincipal MemberVO membervo,String[] myCheckList,String boardNo, Model model) {
		BoardVO boardVO = boardService.boardView(boardNo);
		ReservationVO rvo = new ReservationVO();
		rvo.setBoardNo(boardNo);
		rvo.setId(membervo.getId());
		for(int i=0;i<myCheckList.length;i++) {
			rvo.setReservationDate(myCheckList[i]);
			reserveService.registerReservation(rvo);
		}
		model.addAttribute("myCheckList",myCheckList);
		model.addAttribute("boardVO",boardVO);
		model.addAttribute("memberVO",membervo);
		return "reserve/reservation-ok";
	}
	
	@RequestMapping("reservationList")
	public String reservationList(@AuthenticationPrincipal MemberVO membervo, Model model) {
		String id = membervo.getId();
		List<BoardVO> myReserList = reserveService.findReservationListById(id);
		List<BoardVO> myReserBoardNoList = reserveService.findReservationBoardNoDistinct(id);
		model.addAttribute("myReserList",myReserList);
		model.addAttribute("myReserBoardNoList",myReserBoardNoList);
		model.addAttribute("membervo",membervo);
		return "reserve/reservationList";
	}
	
	//관리자가 예약현황 조회하기 20220714 추가 
	@RequestMapping("reservationListForAdmin")
	public String reservationListForAdmin(@AuthenticationPrincipal MemberVO membervo, Model model) {
		String id = membervo.getId();
		List<BoardVO> reserList = reserveService.findReservationListForAdmin(id);
		List<BoardVO> reserBoardNoList = reserveService.findReserBoardNoDistinctForAdmin(id);
		model.addAttribute("myReserList",reserList);
		model.addAttribute("myReserBoardNoList",reserBoardNoList);
		model.addAttribute("membervo",membervo);
		return "reserve/reservationListForAdmin";
	}
	
	//예약취소 20220715추가
	@RequestMapping("deleteReservation")
	public String deleteReservation(@AuthenticationPrincipal MemberVO membervo, Model model,String boardNo, String[] myCheckList) {
		String id = membervo.getId();
		ReservationVO rvo = new ReservationVO();
		rvo.setId(id);
		rvo.setBoardNo(boardNo);
		List<ReservationVO> myReserDateList = reserveService.findReservationDateByBoardNoAndId(rvo);
		for(int i=0;i<myCheckList.length;i++) {
			String date = myCheckList[i];
			for(int j=0;j<myReserDateList.size();j++) {
				if(myReserDateList.get(j).getReservationDate().contains(date)) {
					rvo.setReservationDate(date);
					reserveService.deleteReservation(rvo);
					model.addAttribute("message","예약이 취소되었습니다.");
				}
			}
		}
		List<BoardVO> myReserList = reserveService.findReservationListById(id);
		List<BoardVO> myReserBoardNoList = reserveService.findReservationBoardNoDistinct(id);
		model.addAttribute("myReserList",myReserList);
		model.addAttribute("myReserBoardNoList",myReserBoardNoList);
		model.addAttribute("membervo",membervo);
		return "reserve/reservationList";
	}
	
}