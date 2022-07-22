package org.kosta.myproject.controller;

import java.util.List;

import org.kosta.myproject.service.CommentService;
import org.kosta.myproject.vo.CommentVO;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	
	@RequestMapping("registerComment")
	public String registerComment(@AuthenticationPrincipal MemberVO membervo, String boardNo, String commentContent,Model model){
		CommentVO commentVO = new CommentVO();
		commentVO.setBoardNo(boardNo);
		commentVO.setId(membervo.getId());
		commentVO.setCommentContent(commentContent);
		commentService.registerComment(commentVO);
		List<CommentVO> commentList = commentService.findCommentByBoardNo(boardNo);
		model.addAttribute("commentList", commentList);
		return "board/boardFarmView :: #commentTable";
	}

	@RequestMapping("updateComment")
	public String updateComment(@AuthenticationPrincipal MemberVO membervo,String boardNo, String commentNo, String commentContent,Model model){
		CommentVO commentVO = commentService.findCommentByCommentNo(commentNo);
		commentVO.setCommentContent(commentContent);
		commentService.updateComment(commentVO);
		List<CommentVO> commentList = commentService.findCommentByBoardNo(boardNo);
		model.addAttribute("commentList", commentList);
		return "board/boardFarmView :: #commentTable";
	}
	
	@RequestMapping("deleteComment")
	public String deleteComment(int commentNo, String boardNo, Model model) {
		System.out.println(commentNo);
		commentService.deleteComment(commentNo);
		List<CommentVO> commentList = commentService.findCommentByBoardNo(boardNo);
		model.addAttribute("commentList", commentList);
		return "board/boardFarmView :: #commentTable";
	}
}
