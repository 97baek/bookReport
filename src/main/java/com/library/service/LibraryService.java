package com.library.service;

import java.util.List;

import com.library.domain.LibraryDTO;
import com.library.paging.Criteria;

public interface LibraryService {
	public boolean registerLibrary(LibraryDTO params);
	public LibraryDTO getLibraryDetail(Long idx);
	public boolean deleteLibrary(Long idx);
	public List<LibraryDTO> getLibraryList(LibraryDTO params);
}