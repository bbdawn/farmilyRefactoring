package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.vo.CommentVO;

public interface CommentService {
	
	//게시물 댓글 조회
	List<CommentVO> findCommentByBoardNo(String boardNo);
	
	//댓글조회 
	CommentVO findCommentByCommentNo(String CommentNo);

	//댓글작성
	void registerComment(CommentVO cvo);

	//댓글삭제
	void deleteComment(int commentNo);
	
	//댓글수정
	void updateComment(CommentVO cvo);
}
