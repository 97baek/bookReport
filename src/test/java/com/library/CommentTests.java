package com.library;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.library.domain.CommentDTO;
import com.library.service.CommentService;

@SpringBootTest
public class CommentTests {
	
	@Autowired
	private CommentService commentService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void registerComments() {
		int number=20;
		
		for(int i = 1; i <= number; i++) {
			CommentDTO params = new CommentDTO();
			params.setLibraryIdx((long)52);	// 댓글을 추가할 게시글 idx
			params.setContent(i+"번 댓글 추가");
			params.setWriter(i+"번 회원");
			commentService.registerComment(params);
		}
		logger.debug("댓글 "+ number + "개 등록");
	}
	
	@Test
	public void deleteComment() {
		commentService.deleteComment((long)10);	// 삭제할 댓글 번호
		getCommentList();
	}
	
	@Test
	public void getCommentList() {
		CommentDTO params = new CommentDTO();
		params.setLibraryIdx((long)52);	// 댓글을 추가할 게시글 번호
		commentService.getCommentList(params);
	}
}
