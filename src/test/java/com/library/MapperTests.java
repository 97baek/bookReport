package com.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.library.domain.LibraryDTO;
import com.library.mapper.LibraryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class MapperTests {

	@Autowired
	private LibraryMapper libraryMapper; // @Autowired를 이용해 BoardMapper 인터페이스 Bean을 주입

	@Test
	/** 게시글 생성을 처리하는 메서드. LibraryDTO의 객체를 생성하고, set 메서드를 이용해 게시글 제목, 내용, 작성자를 지정 */
	public void testOfInsert() {
		LibraryDTO params = new LibraryDTO();
		params.setTitle("1번 게시글 제목");
		params.setContent("1번 게시글 내용");
		params.setAuthor("테스터");

		int result = this.libraryMapper.insertLibrary(params);
		System.out.println("결과는 " + result + "입니다.");
	}

	@Test
	/** 게시글 조회 기능을 하는 selectLibraryDetails 메서드. LibraryDTO 타입의 변수인 library에 selectLibraryDetail 메서드의 결과 저장
	 *  인자로 지정된 (long)은 앞에서 추가한 1번 게시글의 PK에 해당하는 idx를 의미 */
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
	/** 게시글의 정보를 수정하는 메서드
	 *  게시글 생성과 마찬가지로 게시글을 등록하는 것이지만 없었던 데이터의 생성인지 기존에 있던 데이터의 갱신인지 PK의 유무로 구분
	 * */
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
	/** 게시글의 삭제 처리
	 *  실제로는 데이터를 삭제하는 것이 아니라 삭제 여부(delete_yn) 컬럼의 상태 값만 변경하는 것임 */
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