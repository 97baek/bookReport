package com.library.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.domain.LibraryDTO;
import com.library.paging.Criteria;
import com.library.paging.PaginationInfo;
import com.library.mapper.LibraryMapper;

@Service // @Mapper와 유사하며, 해당 클래스가 비즈니스 로직을 담당하는 서비스 클래스임을 의미
public class LibraryServiceImpl implements LibraryService{
	@Autowired
	private LibraryMapper libraryMapper; // @Autowired를 이용해 LibraryMapper 인터페이스 Bean 주입
	
	/** 게시글 번호의 유무에 따라 삽입인지 변경인지 판단
	 *  1. queryResult 변수에 insertLibrary 또는 updateLibrary 메서드의 실행 결과를 저장
	 *     각 메서드가 호출한 쿼리가 정상적으로 실행되면, 쿼리를 실행한 횟수(1) 저장
	 *  2. if else 문에서는 params의 idx가 null이면, MySQL의 AUTO_INCREMENT 속성에 의해
	 *     PK(idx)가 자동 증가되어 게시글을 생성하고, idx가 포함되어 있으면 게시글 수정
	 *  3. return 문에서는 쿼리의 실행 결과를 기준으로 true 또는 false 반환 */
	@Override
	public boolean registerLibrary(LibraryDTO params) {	
		int queryResult = 0;
		if(params.getIdx() == null) {
			queryResult = libraryMapper.insertLibrary(params);
		}else {
			queryResult = libraryMapper.updateLibrary(params);
		}
		return(queryResult == 1) ? true : false;
	}
	
	/** 하나의 게시글을 조회하는 selectLibrary 메서드의 결괏값을 반환*/
	public LibraryDTO getLibraryDetail(Long idx) {
		return libraryMapper.selectLibraryDetail(idx);
	}
	
	/** 특정 게시글을 조회하고, 사용 중인 상태의 게시글인 경우에만 게시글을 삭제
	 *  없는 게시글이거나, 삭제 여부 컬럼의 상태값이 'Y'인 경우에는 삭제가 진행되지 않으며, 
	 *  쿼리의 실행 결과를 기준으로 true / false의 결과 반환*/
	@Override
	public boolean deleteLibrary(Long idx) {
		int queryResult = 0;
		LibraryDTO library = libraryMapper.selectLibraryDetail(idx);
		if(library != null && "N".equals(library.getDeleteYn())) {
			queryResult = libraryMapper.deleteLibrary(idx);
		}
		return(queryResult == 1) ? true : false;
	}
	
	/** 삭제되지 않은 전체 게시글 조회 */
	@Override
	public List<LibraryDTO> getLibraryList(LibraryDTO params){
		List<LibraryDTO> libraryList = Collections.emptyList();

		int libraryTotalCount = libraryMapper.selectLibraryTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params); // PaginationInfo 클래스의 객체 생성
		paginationInfo.setTotalRecordCount(libraryTotalCount); // 전체 게시글 수에 담아 페이징 정보 계산

		params.setPaginationInfo(paginationInfo); // 페이징 정보를 가진 paginationInfo를 params에 담아 SELECT한 다음

		if (libraryTotalCount > 0) {
			libraryList = libraryMapper.selectLibraryList(params);
		}

		return libraryList; // 게시글 리스트 반환
	}
}
