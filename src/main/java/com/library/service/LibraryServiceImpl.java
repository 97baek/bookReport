package com.library.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.domain.LibraryDTO;
import com.library.mapper.LibraryMapper;

@Service
public class LibraryServiceImpl implements LibraryService{
	@Autowired
	private LibraryMapper libraryMapper;
	
	// 게시글 번호의 유무에 따라 삽입인지 변경인지 판단
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
	
	public LibraryDTO getLibraryDetail(Long idx) {
		return libraryMapper.selectLibraryDetail(idx);
	}
	
	@Override
	public boolean deleteLibrary(Long idx) {
		int queryResult = 0;
		LibraryDTO library = libraryMapper.selectLibraryDetail(idx);
		if(library != null && "N".equals(library.getDeleteYn())) {
			queryResult = libraryMapper.deleteLibrary(idx);
		}
		return(queryResult == 1) ? true : false;
	}
	
	@Override
	public List<LibraryDTO> getLibraryList(){
		List<LibraryDTO> libraryList = Collections.emptyList();
		int libraryTotalCount = libraryMapper.selectLibraryTotalCount();
		if(libraryTotalCount > 0) {
			libraryList = libraryMapper.selectLibraryList();
		}
		return libraryList;
	}
}
