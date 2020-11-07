package com.library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.domain.LibraryDTO;
import com.library.paging.Criteria;

@Mapper
public abstract interface LibraryMapper
{
  public abstract int insertLibrary(LibraryDTO paramLibraryDTO);

  public abstract LibraryDTO selectLibraryDetail(Long paramLong);

  public abstract int updateLibrary(LibraryDTO paramLibraryDTO);

  public abstract int deleteLibrary(Long paramLong);

  public abstract List<LibraryDTO> selectLibraryList(LibraryDTO params);

  public abstract int selectLibraryTotalCount(LibraryDTO params);
}