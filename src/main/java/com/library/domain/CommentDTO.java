package com.library.domain;

// 댓글 테이블을 구조화
public class CommentDTO extends CommonDTO{
	private Long idx;
	private Long libraryIdx;
	private String content;
	private String writer;
	
	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public Long getLibraryIdx() {
		return libraryIdx;
	}
	public void setLibraryIdx(Long libraryIdx) {
		this.libraryIdx = libraryIdx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
}
