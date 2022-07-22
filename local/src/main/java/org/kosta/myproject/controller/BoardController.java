package org.kosta.myproject.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.kosta.myproject.mapper.BoardMapper;
import org.kosta.myproject.service.BoardService;
import org.kosta.myproject.service.CommentService;
import org.kosta.myproject.service.MemberService;
import org.kosta.myproject.service.ReserveService;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.CommentVO;
import org.kosta.myproject.vo.JjimVO;
import org.kosta.myproject.vo.MemberVO;
import org.kosta.myproject.vo.Pagination;
import org.kosta.myproject.vo.ReservationVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	private final ReserveService reserveService;
	private final MemberService memberService;

	private final BoardMapper BoardMapper;
	private final CommentService commentService;
	
	public int getTotalPostCount() {
		return BoardMapper.getTotalPostCount();
		
	}
	
	@RequestMapping("guest/findAllBoardList")
	public String findAllBoardList(Model model, Pagination pagination, String pageNo) {
		
		if(pageNo==null) {
			pagination = new Pagination(getTotalPostCount());
		}else {
			pagination = new Pagination(getTotalPostCount(),Integer.parseInt(pageNo));
		}
		
		List<BoardVO> list = boardService.findAllBoardList(pagination);
		model.addAttribute("boardList", list);
		model.addAttribute("pagination", pagination);
		model.addAttribute("total", getTotalPostCount());
		return "guest/all-board-list";
	}
	@RequestMapping("guest/boardListByBoardCategori")
	public String findBoardListByBoardCategori(@AuthenticationPrincipal MemberVO membervo,String boardCategori, Model model) {
		List<BoardVO> list = boardService.findBoardListByBoardCategori(boardCategori);
		if(membervo !=null) {
			String authority = memberService.findAuthorityById(membervo.getId());
			model.addAttribute("authority", authority);
		}
		model.addAttribute("boardList", list);
		
		return "board/board-categori-list";
	}

	@RequestMapping("guest/boardListBySelectCategori")
	public String boardListBySelectCategori(@AuthenticationPrincipal MemberVO membervo,String boardCategori,String selectCategori, Model model) {
		List<BoardVO> list = boardService.findBoardListBySelectCategori(selectCategori);
		if(membervo !=null) {
			String authority = memberService.findAuthorityById(membervo.getId());
			model.addAttribute("authority", authority);
		}
		model.addAttribute("boardList", list);
		model.addAttribute("boardCategori", boardCategori);
		return "board/board-categori-list";
	}

	@RequestMapping("guest/boardListByRegion")
	public String findBoardListByRegion(String region, Model model) {
		List<BoardVO> list = boardService.findBoardListByRegion(region);
		model.addAttribute("boardList", list);
		return "board/board-categori-list";
	}
	
	//region list ajax 89-95
	@RequestMapping("guest/ajaxBoardListByRegion")
	@ResponseBody
	public List<BoardVO> ajaxBoardListByRegion(String region, Model model) {
		List<BoardVO> list = boardService.findBoardListByRegion(region);
		model.addAttribute("boardList", list);
		return boardService.findBoardListByRegion(region);
	}
	
	//findSearchBoardList 20220713 추가
	@GetMapping("guest/findSearchBoardList")
	public String findSearchBoardList(String searchOption,String searchContent,Model model) {
		List<BoardVO> list = boardService.findSearchBoardList(searchOption, searchContent);
		model.addAttribute("boardList", list);
		return "board/board-categori-list";
	}

	@GetMapping("registerPostForm")
	public String registerPostForm(String boardCategori) {
		String viewName = null;
		if(boardCategori.equals("농촌활동")) {
			viewName = "board/registerFarmPostForm";
		}else {
			viewName = "board/registerPostForm";
		}
		return viewName;
	}

	/*
	   데이터타입 메소드명(RedirectAttributes ra){
 	ra.addAttribute("전달할변수명", 전달할데이터);
 	ra.addFlashAttribute("전달할변수명", 전달할데이터);
 	return "redirect:/"
		}
		addAttribute는 get 방식으로 넘겨줄 때처럼 url 뒤에 "?변수명=데이터" 가 함께 붙어 전달된다.
	 */
	
	@PostMapping("registerPost")
	public String registerPost(@AuthenticationPrincipal MemberVO membervo, BoardVO bvo, String boardCategori,
			Model model, MultipartFile file, RedirectAttributes redirect) throws Exception {
		bvo.setId(membervo.getId());

		if (file.isEmpty()) {
			bvo.setFilename("");
			bvo.setFilepath("");

		} else {
			String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();

			File saveFile = new File(projectPath, fileName);

			file.transferTo(saveFile);

			bvo.setFilename(fileName);
			bvo.setFilepath("/files/" + fileName);
		}
		boardService.registerBoard(bvo);
		redirect.addAttribute("boardCategori", boardCategori);
		//return "redirect:/guest/boardListByBoardCategori?boardCategori=" + boardCategori;
		return "redirect:/guest/boardListByBoardCategori";
	}
	
	@GetMapping("deletePost")
	public String deletePost(String boardNo, Model model) {
		boardService.deletePost(boardNo);
		model.addAttribute("message", "게시글이 삭제되었습니다.");
		model.addAttribute("searchUrl", "guest/findAllBoardList");
		return "delete-message";

	}
	@RequestMapping("boardView")
	public String boardView(@AuthenticationPrincipal MemberVO membervo,String boardNo, Model model) throws Exception {
		String viewName = "board/boardView";
		BoardVO boardVO = boardService.boardView(boardNo);
		model.addAttribute("myId",membervo.getId());
		boardService.updateCount(boardNo);
		JjimVO jjimVO = new JjimVO();
		jjimVO.setBoardNo(boardVO.getBoardNo());
		jjimVO.setId(membervo.getId());
		String jjimCheck = boardService.findJjim(jjimVO);
		model.addAttribute("jjimCheck",jjimCheck);
		List<CommentVO> commentList = commentService.findCommentByBoardNo(boardNo);
		model.addAttribute("commentList",commentList);
		//농촌활동 뷰페이지 다름
		if(boardVO.getBoardCategori().equals("농촌활동")) {
			//예약 가능 날짜 불러와서 보내주기
			List<ReservationVO> rdateList = reserveService.findReservateDate(boardNo);
			int rdateListSize = rdateList.size();
			int checkCount = reserveService.checkReservationByBoardNo(boardNo);
			model.addAttribute("rdateList",rdateList);
			model.addAttribute("rdateListSize",rdateListSize);
			model.addAttribute("checkCount",checkCount);
			viewName = "board/boardFarmView";
		}
		model.addAttribute("boardVO", boardVO);
		return viewName;
	}

	
	
	@RequestMapping("guest/boardFarmingListByBoardCategori")
	public String findBoardFarmingListByBoardCategori(String farmingCategori, Model model) throws Exception {
		List<BoardVO> list = boardService.findBoardFarmingListByBoardCategori(farmingCategori);
		model.addAttribute("boardFarmingList",list);
		return "board/boardFarmingList";
	}
	
	@RequestMapping("mypage/findMyPostListById")
	public String findMyPostById(@AuthenticationPrincipal MemberVO membervo, Model model) {
		List<BoardVO> list = boardService.findMyPostListById(membervo.getId());
		model.addAttribute("boardList", list);
		return "mypage/findMyPostListById";
	}
	@RequestMapping("mypage/findMyJjimListById")
	public String findMyJjimListById(@AuthenticationPrincipal MemberVO membervo, Model model) {
		List<BoardVO> list = boardService.findMyJjimListById(membervo.getId());
		model.addAttribute("boardList", list);
		return "mypage/findMyJjimListById";
	}
	
	@GetMapping("updatePostForm")
	public String updatePostForm( String boardNo, Model model) {
		BoardVO boardVO = boardService.boardView(boardNo);
		model.addAttribute("boardVO", boardVO);
		return "board/updatePostForm";
	}
	
	//20220713 추가 농촌활동 게시물 수정
	@GetMapping("updateFarmPostForm")
	public String updateFarmPostForm( String boardNo, Model model) {
		BoardVO boardVO = boardService.boardView(boardNo);
		//예약 가능 날짜 불러와서 보내주기
		List<ReservationVO> rdateList = reserveService.findReservateDate(boardNo);
		int rdateListSize = rdateList.size();
		model.addAttribute("rdateList",rdateList);
		model.addAttribute("rdateListSize",rdateListSize);
		model.addAttribute("boardVO", boardVO);
		return "board/updateFarmPostForm";
	}
	
	@PostMapping("updatePost")
	public String updatePost(@AuthenticationPrincipal MemberVO membervo, BoardVO boardVO, String boardCategori,
			Model model, MultipartFile file, int boardNo, RedirectAttributes redirect) throws Exception {
		boardVO.setBoardNo(boardNo);
		
		boardService.updateBoard(boardVO);//이 boardVO안에는 title, content, boardNo만 들어있음
		
		model.addAttribute("message", "게시글이 수정되었습니다.");
		model.addAttribute("searchUrl", "guest/findAllBoardList");
		return "update-message";
		
	}
	
	//20220713 추가 농촌활동 게시물 수정
	@PostMapping("updateFarmPost")
	public String updateFarmPost(BoardVO boardVO, MultipartFile file, RedirectAttributes redirect) {
		boardService.updateFarmPost(boardVO);
		redirect.addAttribute("boardNo", boardVO.getBoardNo());
		return "redirect:/boardView";
	}
	
	@PostMapping("registerJjim")
	public String registerJjim(@AuthenticationPrincipal MemberVO membervo, BoardVO bvo, String boardCategori,
			Model model) {
		bvo.setId(membervo.getId());
		bvo.setBoardNo(bvo.getBoardNo());
		JjimVO jjimVO=new JjimVO();
		jjimVO.setBoardNo(bvo.getBoardNo());
		jjimVO.setId(membervo.getId());
		
		return boardCategori;
	}	
	@GetMapping("changeJjim")
	@ResponseBody
	public String changeJjim(@AuthenticationPrincipal MemberVO membervo,String jjimCheck,int boardNo,Model model) {
		JjimVO jjimVO = new JjimVO();
		jjimVO.setBoardNo(boardNo);
		jjimVO.setId(membervo.getId());
		if(jjimCheck.equals("0")) {
			boardService.registerJjim(jjimVO);
		}else {
			boardService.deleteJjim(jjimVO);
		}
		jjimCheck = boardService.findJjim(jjimVO);
		model.addAttribute("jjimCheck",jjimCheck);
		return jjimCheck;
	}
	
	//20220713 추가 농촌활동 게시물 삭제
	@GetMapping("deleteFarmPost")
	public String deleteFarmPost(String boardNo, Model model) {
		boardService.deleteFarmPost(boardNo);
		List<BoardVO> list = boardService.findBoardFarmingListByBoardCategori("농촌활동");
		model.addAttribute("boardFarmingList",list);
		model.addAttribute("message","삭제되었습니다.");
		return "board/boardFarmingList";
	}

}