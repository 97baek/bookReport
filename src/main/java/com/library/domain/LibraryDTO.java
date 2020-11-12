package com.library.domain;

/** 게시판 테이블(tb_library)의 구조화 역할을 하는 클래스. 입력받은 데이터를 저장 및 전송 */
public class LibraryDTO extends CommonDTO
{
	/* 멤버 변수의 순서는 게시판 테이블의 컬럼 순서와 동일해야함 */
  private Long idx;
  private String title;
  private String content;
  private String author;
  private int viewCnt;
  private String noticeYn;
  private String secretYn;

  private String thumbImg;

  public Long getIdx()
  {
    return this.idx;
  }

  public void setIdx(Long idx) {
    this.idx = idx;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getViewCnt() {
    return this.viewCnt;
  }

  public void setViewCnt(int viewCnt) {
    this.viewCnt = viewCnt;
  }

  public String getNoticeYn() {
    return this.noticeYn;
  }

  public void setNoticeYn(String noticeYn) {
    this.noticeYn = noticeYn;
  }

  public String getSecretYn() {
    return this.secretYn;
  }

  public void setSecretYn(String secretYn) {
    this.secretYn = secretYn;
  }


  public String getThumbImg() {
  	return thumbImg;
  }
  
  public void setThumbImg(String thumbImg) {
  	this.thumbImg=thumbImg;
  }
}