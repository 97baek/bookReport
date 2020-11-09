package com.library.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDTO extends CommonDTO
{
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