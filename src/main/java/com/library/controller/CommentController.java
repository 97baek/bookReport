package com.library.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.library.adapter.GsonLocalDateTimeAdapter;
import com.library.domain.CommentDTO;
import com.library.service.CommentService;

@RestController // @RestController가 선언된 컨트롤러의 모든 메서드는 화면이 아닌 리턴타입에 해당하는 데이터 자체를 리턴
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	/* 게시글의 경우, 하나의 URI로 생성과 수정이 가능하지만 REST API는 설계 규칙에 따라 URI가 분리되어야 함. */	
	/* "comments": 새로운 댓글 등록, "/comments/{idx}": 댓글 테이블의 PK인 idx에 해당하는 댓글 수정 의미 / POST: @PostMapping과 유사, HTTP 요청 메서드 중 POST를 의미, PATHCH: @PatchMapping과 유사, PATCH 의미*/
	@RequestMapping(value = { "/comments", "/comments/{idx}" }, method = { RequestMethod.POST, RequestMethod.PATCH }) 
	public JsonObject registerComment(@PathVariable(value="idx", required=false) Long idx, @RequestBody final CommentDTO params) {
		JsonObject jsonObj = new JsonObject();
		try {
			if (idx != null) { // 이미 댓글이 생성되어 있다면
				params.setIdx(idx); // 댓글 수정
			}

			boolean isRegistered = commentService.registerComment(params); // 댓글이 생성 또는 수정되면 true, 실행되지 않으면 false 저장
			jsonObj.addProperty("result", isRegistered); // 메서드의 실행 결과를 "result"라는 이름의 프로퍼티로 JSON 객체에 추가해서 리턴


		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		return jsonObj;
	}
	
	@GetMapping(value="/comments/{libraryIdx}")
	//@PathVariable은 @RequestParam과 비슷한 기능을 하며, REST방식에서 리소스를 표현하는데 사용. "/comments/{libraryIdx}" URI의 {libraryIdx}와 @PathVariable의 libraryIdx를 매핑. @ModelAttribute는 파라미터로 전달받은 객체를 자동으로 화면(뷰)로 전달.
	public JsonObject getCommentList(@PathVariable("libraryIdx") Long libraryIdx, @ModelAttribute("params") CommentDTO params) { 
		JsonObject jsonObj = new JsonObject();

		List<CommentDTO> commentList = commentService.getCommentList(params);
		if (CollectionUtils.isEmpty(commentList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create(); // Gson 객체의 기본 생성자를 이용하지 않고, 어댑터 클래스를 포함해서 객체를 따로 생성
			JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
			jsonObj.add("commentList", jsonArr);
		}

		return jsonObj;
	}
}