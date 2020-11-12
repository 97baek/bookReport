package com.library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.constant.Method;
import com.library.domain.LibraryDTO;
import com.library.service.LibraryService;
import com.library.util.UiUtils;

/** 모델과 뷰 영역을 연결해주는 컨트롤러 영역 */

@Controller	// 사용자의 요청과 응답을 처리하는, UI를 담당하는 컨트롤러 클래스임을 의미
public class LibraryController extends UiUtils{
	@Autowired
	private LibraryService libraryService;
	
	@GetMapping("/library/index.do")
	private String openIndex(Model model) {
		return "library/index";
	}
	
	@GetMapping("/library/profile.do")
	private String openProfile(Model model) {
		return "library/profile";
	}
	
	@GetMapping("/library/todolist.do")
	private String openTodoList(Model model) {
		return "library/todolist";
	}
	
	/** 게시글 등록 화면 처리
	 *  @RequestParam: 뷰에서 전달받은 파라미터를 처리하는 데 사용. list에서 write페이지로 이동 시 idx는 null 전송, view 페이지에서 수정하기 버튼 클릭 시 idx를 getBoardDetail 메서드의 인자로 전달
	 *  각각의 조건문의 addAttribute 메서드를 이용해 Library 객체를 "library"라는 이름으로 뷰로 전달
	 *  idx가 전송되지 않은 경우에는 비어있는 객체를, idx가 전송된 경우에는 getLibraryDetail 메서드의 실행 결과인 게시글 정보를 포함하고 있는 객체를 전달*/
	@GetMapping(value = "/library/write.do")
	public String openLibraryWrite(@ModelAttribute("params") LibraryDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) { // 파라미터로 지정된 Model 인터페이스는 데이터를 뷰로 전달하는 데 사용
		if (idx == null) {
			model.addAttribute("library", new LibraryDTO());
		} else {
			LibraryDTO library = libraryService.getLibraryDetail(idx);
			if (library == null || "Y".equals(library.getDeleteYn())) {
				return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/library/list.do", Method.GET, null, model);
			}
			model.addAttribute("library", library);
		}

		return "library/write";
	}

	/** 게시글 등록을 처리하는 메서드. ModelAttribute를 이용하면 파라미터로 전달받은 객체를 자동으로 뷰까지 전달 가능 */
	@PostMapping(value = "/library/register.do")
	public String registerLibrary(@ModelAttribute("params") final LibraryDTO params, Model model) { //params: 폼 엘리먼트의 사용자 입력 필드의 "name"속성 값을 통해 폼 데이터를 컨트롤러 메서드의 파라미터로 전송.
		Map<String, Object> pagingParams = getPagingParams(params);
		// 예외 발생 시 처리하기 부분
		try {
			boolean isRegistered = libraryService.registerLibrary(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/library/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/library/list.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/library/list.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/library/list.do", Method.GET, pagingParams, model);
	}
	
	
	@GetMapping(value="/library/list.do") // GET방식의 HTTP 요청 메서드를 의미
	public String openLibraryList(@ModelAttribute("params") LibraryDTO params, Model model) { // Model: 컨트롤러에서 뷰로 데이터를 전달하는 데 사용되는 인터페이스
		List<LibraryDTO> libraryList = libraryService.getLibraryList(params); // getLibraryList 메서드의 인자로 params를 전달
		model.addAttribute("libraryList",libraryList);
		return "library/list"; // 컨트롤러의 리턴 타입이 String이면 리턴문에 저장된 경로의 HTML이 화면에 출력
	}
	
	/** 게시글 조회 페이지 처리 */
	@GetMapping(value = "/library/view.do")
	// @RequestParam: 특정 게시글 조회에 필요한 idx를 파라미터로 전달받음. 파라미터가 넘어오지 않았을 경우 직접 처리할 것이기 때문에 required 속성값을 false로 지정
	public String openLibraryDetail(@ModelAttribute("params") LibraryDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) { // idx가 파라미터로 전달되지 않았다면
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/library/list.do", Method.GET, null, model); // 리스트로 리다이렉트
		}

		// 정상적인 경우, getLibraryDetail을 이용해 idx를 전달해 게시글 정보 조회
		LibraryDTO library = libraryService.getLibraryDetail(idx); 
		if (library == null || "Y".equals(library.getDeleteYn())) {
			return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/library/list.do", Method.GET, null, model);
		}
		model.addAttribute("library", library);

		return "library/view"; // 게시글 정보를 뷰로 전달하고, 게시글 상세 페이지를 리턴
	}
	
	/** 게시글 삭제 처리 메서드*/
	@PostMapping(value = "/library/delete.do")
	public String deleteLibrary(@ModelAttribute("params") LibraryDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) { // idx가 파라미터로 전달되지 않았다면 게시글 리스트로 리다이렉트
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/library/list.do", Method.GET, null, model);
		}
		
		Map<String, Object> pagingParams = getPagingParams(params); // UiUtils의 getPagingParams 메서드 호출. pagingParams에 담긴 이전 페이지 정보를 showMeesageRedirect 메서드의 인자로 전달
		// 정상적인 경우, 아래 try문 수행
		try {
			boolean isDeleted = libraryService.deleteLibrary(idx);
			if (isDeleted == false) {
				return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/library/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/library/list.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/library/list.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/library/list.do", Method.GET, pagingParams, model);
	}
}