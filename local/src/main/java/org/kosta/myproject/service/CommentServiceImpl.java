package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.mapper.CommentMapper;
import org.kosta.myproject.vo.CommentVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	private final CommentMapper commentMapper;
	
	@Override
	public List<CommentVO> findCommentByBoardNo(String boardNo) {
		return commentMapper.findCommentByBoardNo(boardNo);
	}

	@Override
	public void registerComment(CommentVO cvo) {
		commentMapper.registerComment(cvo);
	}

	@Override
	public void deleteComment(int commentNo) {
		commentMapper.deleteComment(commentNo);
	}

	@Override
	public void updateComment(CommentVO cvo) {
		commentMapper.updateComment(cvo);
	}

	@Override
	public CommentVO findCommentByCommentNo(String CommentNo) {
		return commentMapper.findCommentByCommentNo(CommentNo);
	}

}
