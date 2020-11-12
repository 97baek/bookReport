package com.library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.domain.LibraryDTO;

@Mapper // 기존의 스프링과 달리 @Mapper만 지정해주면 XML Mapper에서 메서드의 이름과 일치하는 SQL문을 찾아 실행. SQL 쿼리를 호출하는 것이 전부이며, 다른 로직은 필요하지 않음
public abstract interface LibraryMapper
{
  public abstract int insertLibrary(LibraryDTO paramLibraryDTO); // 게시글을 생성하는 INSERT 쿼리를 호출하는 메서드. paramLibraryDTO에는 게시글의 정보가 담기게 됨

  public abstract LibraryDTO selectLibraryDetail(Long paramLong); // 하나의 게시글을 조회하는 SELECT 쿼리를 호출하는 메서드. SELECT 쿼리가 실행되면, 각 컬럼에 해당하는 결과값이 리턴 타입으로 지정된 LibraryDTO 클래스의 멤버 변수에 매핑됨

  public abstract int updateLibrary(LibraryDTO paramLibraryDTO); // 게시글을 수정하는 UPDATE 쿼리를 호출하는 메서드. insertLibrary와 마찬가지로 paramLibraryDTO에는 게시글의 정보가 담기게 됨

  public abstract int deleteLibrary(Long paramLong); // 파라미터로 게시글 번호를 전달받으며, WHERE 조건으로 idx를 사용해 특정 게시글을 삭제(실제로는 삭제되지 않고 상태만 변경)함.

  public abstract List<LibraryDTO> selectLibraryList(LibraryDTO params); // 게시글 목록을 조회하는 SELECT 쿼리를 호출하는 메서드.

  public abstract int selectLibraryTotalCount(LibraryDTO params); // 삭제 여부(delete_yn)가 'N'으로 지정된 게시글의 개수를 조회하는 SELECT 쿼리를 호출하는 메서드
}