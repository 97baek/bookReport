package com.library.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDTO
{
  private Long idx;
  private String title;
  private String content;
  private String author;
  private int viewCnt;
  private String noticeYn;
  private String secretYn;
  private String deleteYn;
  private LocalDateTime insertTime;
  private LocalDateTime updateTime;
  private LocalDateTime deleteTime;

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

  public String getDeleteYn() {
    return this.deleteYn;
  }

  public void setDeleteYn(String deleteYn) {
    this.deleteYn = deleteYn;
  }

  public LocalDateTime getInsertTime() {
    return this.insertTime;
  }

  public void setInsertTime(LocalDateTime insertTime) {
    this.insertTime = insertTime;
  }

  public LocalDateTime getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public LocalDateTime getDeleteTime() {
    return this.deleteTime;
  }

  public void setDeleteTime(LocalDateTime deleteTime) {
    this.deleteTime = deleteTime;
  }

  public String toString()
  {
    return "LibraryDTO [idx=" + this.idx + ", title=" + this.title + ", content=" + this.content + ", author=" + this.author + 
      ", viewCnt=" + this.viewCnt + ", noticeYn=" + this.noticeYn + ", secretYn=" + this.secretYn + ", deleteYn=" + 
      this.deleteYn + ", insertTime=" + this.insertTime + ", updateTime=" + this.updateTime + ", deleteTime=" + this.deleteTime + 
      "]";
  }
}