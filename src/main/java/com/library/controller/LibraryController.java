package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.domain.LibraryDTO;
import com.library.service.LibraryService;

@Controller	// 사용자의 요청을 처리
public class LibraryController {
	@Autowired
	private LibraryService libraryService;
	
	@GetMapping(value = "/library/write.do")
	public String openLibraryWrite(@RequestParam(value="idx",required=false) Long idx, Model model) {
		if(idx==null) {
			model.addAttribute("library",new LibraryDTO());
		}else {
			LibraryDTO library=libraryService.getLibraryDetail(idx);
			if(library==null) {
				return "redirect:/library/list.do";
			}
			model.addAttribute("library",library);
		}
		return "library/write";
	}
	
	@PostMapping(value="/library/register.do")
	public String registerLibrary(final LibraryDTO params) {
		try {
			boolean isRegistered=libraryService.registerLibrary(params);
			if(isRegistered==false) {
				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
			}
		} catch(DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지
		} catch(Exception e) {
			// TODO => 시스템에 문제가 발생했다는 메시지를 전달
		}
		return "redirect:/library/list.do";
	}
	
	@GetMapping(value="/library/list.do")
	public String openLibraryList(Model model) {
		List<LibraryDTO> libraryList = libraryService.getLibraryList();
		model.addAttribute("libraryList",libraryList);
		return "library/list";
	}
}
