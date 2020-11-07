package com.library.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.library.constant.Method;
import com.library.domain.LibraryDTO;
import com.library.paging.Criteria;
import com.library.service.LibraryService;
import com.library.util.UiUtils;

@Controller	// 사용자의 요청을 처리
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
	
	@GetMapping(value = "/library/write.do")
	public String openLibraryWrite(@ModelAttribute("params") LibraryDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
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

	@PostMapping(value = "/library/register.do")
	public String registerLibrary(@ModelAttribute("params") final LibraryDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
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
	
	@GetMapping(value="/library/list.do")
	public String openLibraryList(@ModelAttribute("params") LibraryDTO params, Model model) {
		List<LibraryDTO> libraryList = libraryService.getLibraryList(params);
		model.addAttribute("libraryList",libraryList);
		return "library/list";
	}
	
	@GetMapping(value="/library/list2.do")
	public String openLibraryListTwo(@ModelAttribute("params") LibraryDTO params, Model model) {
		List<LibraryDTO> libraryList = libraryService.getLibraryList(params);
		model.addAttribute("libraryList",libraryList);
		return "library/list2";
	}
	
	@GetMapping(value = "/library/view.do")
	public String openLibraryDetail(@ModelAttribute("params") LibraryDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/library/list.do", Method.GET, null, model);
		}

		LibraryDTO library = libraryService.getLibraryDetail(idx);
		if (library == null || "Y".equals(library.getDeleteYn())) {
			return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/library/list.do", Method.GET, null, model);
		}
		model.addAttribute("library", library);

		return "library/view";
	}
	
	@PostMapping(value = "/library/delete.do")
	public String deleteLibrary(@ModelAttribute("params") LibraryDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/library/list.do", Method.GET, null, model);
		}

		Map<String, Object> pagingParams = getPagingParams(params);
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