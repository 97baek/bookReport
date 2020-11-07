package com.library;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.library.domain.LibraryDTO;
import com.library.mapper.LibraryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class MapperTests {

	@Autowired
	private LibraryMapper libraryMapper;

	@Test
	public void testOfInsert() {
		LibraryDTO params = new LibraryDTO();
		params.setTitle("1번 게시글 제목");
		params.setContent("1번 게시글 내용");
		params.setAuthor("테스터");

		int result = this.libraryMapper.insertLibrary(params);
		System.out.println("결과는 " + result + "입니다.");
	}

	@Test
	public void testOfSelectDetail() {
		LibraryDTO library = this.libraryMapper.selectLibraryDetail(Long.valueOf(1));
		try {
			String libraryJson = new ObjectMapper().writeValueAsString(library);
			System.out.println("=====================");
			System.out.println(libraryJson);
			System.out.println("=====================");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testOfUpdate() {
		LibraryDTO params = new LibraryDTO();
		params.setTitle("1번 게시글 제목을 수정합니다.");
		params.setContent("1번 게시글 내용을 수정합니다.");
		params.setAuthor("홍길동");
		params.setIdx(Long.valueOf(1));

		int result = this.libraryMapper.updateLibrary(params);
		if (result == 1) {
			LibraryDTO library = this.libraryMapper.selectLibraryDetail(Long.valueOf(1));
			try {
				String libraryJson = new ObjectMapper().writeValueAsString(library);
				System.out.println("====================");
				System.out.println(libraryJson);
				System.out.println("====================");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testOfDelete() {
		int result = this.libraryMapper.deleteLibrary(Long.valueOf(1));
		if (result == 1) {
			LibraryDTO library = this.libraryMapper.selectLibraryDetail(Long.valueOf(1));
			try {
				String libraryJson = new ObjectMapper().writeValueAsString(library);
				System.out.println("====================");
				System.out.println(libraryJson);
				System.out.println("====================");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testMultipleInsert() {
		for (int i = 10; i <= 50; i++) {
			LibraryDTO params = new LibraryDTO();
			params.setTitle(i + "번 게시글 제목");
			params.setContent(i + "번 게시글 내용");
			params.setAuthor(i + "번 게시글 작성자");
			this.libraryMapper.insertLibrary(params);
		}
	}

//	@Test
//	public void testSelectList() {
//		int libraryTotalCount = libraryMapper.selectLibraryTotalCount();
//		if (libraryTotalCount > 0) {
//			List<LibraryDTO> libraryList = libraryMapper.selectLibraryList();
//			if (CollectionUtils.isEmpty(libraryList) == false) {
//				for (LibraryDTO library : libraryList) {
//					System.out.println("=========================");
//					System.out.println(library.getTitle());
//					System.out.println(library.getContent());
//					System.out.println(library.getAuthor());
//					System.out.println("=========================");
//				}
//			}
//		}
//	}
}