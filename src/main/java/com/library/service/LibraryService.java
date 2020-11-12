package com.library.service;

import java.util.List;

import com.library.domain.LibraryDTO;

/** 비즈니스 로직을 담당하는 서비스 영역. */
public interface LibraryService {
	public boolean registerLibrary(LibraryDTO params);
	public LibraryDTO getLibraryDetail(Long idx);
	public boolean deleteLibrary(Long idx);
	public List<LibraryDTO> getLibraryList(LibraryDTO params);
}