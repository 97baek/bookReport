package com.library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.domain.CommentDTO;

// DB와 통신역할을 하는 Mapper 인터페이스
@Mapper
public interface CommentMapper {
	public int insertComment(CommentDTO params); // 댓글을 삽입하는 INSERT 쿼리 호출	
	public CommentDTO selectCommentDetail(Long idx); // 파라미터로 전달받은 idx에 해당하는 댓글의 내용 조회. 댓글 삭제 처리시 사용
	public int updateComment(CommentDTO params); // 댓글을 수정하는 UPDATE 쿼리 호출
	public int deleteComment(Long idx); // 댓글 삭제 메서드
	public List<CommentDTO> selectCommentList(CommentDTO params); // 특정 게시글에 포함된 댓글 목록을 조회하는 SELECT 쿼리 호출
	public int selectCommentTotalCount(CommentDTO params); // 특정 게시글에 포함된 댓글 갯수를 조회하는 SELECT 쿼리 호출
}
