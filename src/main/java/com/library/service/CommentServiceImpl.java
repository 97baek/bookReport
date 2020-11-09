package com.library.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.domain.CommentDTO;
import com.library.mapper.CommentMapper;

// CommentService 인터페이스 구현
@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired // @Autowired를 사용해 CommentMapper Bean 주입
	private CommentMapper commentMapper;
	
	@Override // 게시글과 마찬가지로 INSERT와 UPDATE를 하나의 메서드로 처리. idx가 포함되어 있지 않으면 INSERT를, 포함되어 있다면 UPDATE를 실행
	public boolean registerComment(CommentDTO params) {
		int queryResult = 0;

		if (params.getIdx() == null) {
			queryResult = commentMapper.insertComment(params);
		} else {
			queryResult = commentMapper.updateComment(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override // 댓글의 상세정보를 조회해 정상적으로 사용중인 댓글인 경우에만 삭제 진행
	public boolean deleteComment(Long idx) {
		int queryResult = 0;

		CommentDTO comment = commentMapper.selectCommentDetail(idx);

		if (comment != null && "N".equals(comment.getDeleteYn())) {
			queryResult = commentMapper.deleteComment(idx);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override // 특정 게시글에 포함된 댓글이 1개 이상이면 댓글 목록 반환
	public List<CommentDTO> getCommentList(CommentDTO params) {
		List<CommentDTO> commentList = Collections.emptyList();

		int commentTotalCount = commentMapper.selectCommentTotalCount(params);
		if (commentTotalCount > 0) {
			commentList = commentMapper.selectCommentList(params);
		}

		return commentList;
	}
}
