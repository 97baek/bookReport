package com.library.domain;

import java.time.LocalDateTime;

import com.library.paging.Criteria;
import com.library.paging.PaginationInfo;

import lombok.Getter;
import lombok.Setter;

/** 공통으로 사용될 DTO 클래스 */
public class CommonDTO extends Criteria {

	/** 페이징 정보 */
	private PaginationInfo paginationInfo;
  private String deleteYn;
  private LocalDateTime insertTime;
  private LocalDateTime updateTime;
  private LocalDateTime deleteTime;
  
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

  // 페이징 정보
	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}
}