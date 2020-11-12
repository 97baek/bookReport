package com.library.paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {

	// 페이징 계산에 필요한 파라미터들이 담긴 클래스
	private Criteria criteria;

	// 전체 데이터 개수
	private int totalRecordCount;

	// 전체 페이지 개수 
	private int totalPageCount;

	// 페이지 리스트의 첫 페이지 번호 (1)
	private int firstPage;

	// 페이지 리스트의 마지막 페이지 번호 (10)
	private int lastPage;

	// SQL의 조건절에 사용되는 첫 RNUM 
	private int firstRecordIndex;

	// SQL의 조건절에 사용되는 마지막 RNUM 
	private int lastRecordIndex;

	// 이전 페이지 존재 여부 
	private boolean hasPreviousPage;

	// 다음 페이지 존재 여부 
	private boolean hasNextPage;

	
	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}

	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}

	public int getLastRecordIndex() {
		return lastRecordIndex;
	}

	public void setLastRecordIndex(int lastRecordIndex) {
		this.lastRecordIndex = lastRecordIndex;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/** 잘못된 값이 들어왔을 때, 각각의 if문을 통해 기본값 지정*/
	public PaginationInfo(Criteria criteria) {
		if (criteria.getCurrentPageNo() < 1) {
			criteria.setCurrentPageNo(1);
		}
		if (criteria.getRecordsPerPage() < 1 || criteria.getRecordsPerPage() > 100) {
			criteria.setRecordsPerPage(9);
		}
		if (criteria.getPageSize() < 5 || criteria.getPageSize() > 20) {
			criteria.setPageSize(10);
		}

		this.criteria = criteria; // 페이지 번호 계산을 위해 사용자의 요청 파라미터 정보를 가진 criteria를 PaginationInfo 클래스의 criteria에 저장
	}

	/** 파라미터로 넘어온 전체 데이터 개수를 PaginationInfo 클래스의 전체 데이터 개수에 저장*/
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;

		if (totalRecordCount > 0) { // 전체 데이터 개수가 1개 이상이면 페이지 번호를 계산하는 calculation 메서드 실행
			calculation();
		}
	}

	/** 페이지 번호를 계산하는 calculation 메서드 실행*/
	private void calculation() {

		totalPageCount = ((totalRecordCount - 1) / criteria.getRecordsPerPage()) + 1; // 전체 페이지 수 
		if (criteria.getCurrentPageNo() > totalPageCount) { // 현재 페이지 번호가 전체 페이지의 개수의 값보다 크면
			criteria.setCurrentPageNo(totalPageCount); // 현재 페이지에 전체 페이지 개수를 저장
		}

		firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1; // 가장 좌측의 페이지 번호
		lastPage = firstPage + criteria.getPageSize() - 1; // 마지막 페이지 번호
		if (lastPage > totalPageCount) { // 마지막 페이지 번호가 전체 페이지 개수보다 크면
			lastPage = totalPageCount; // lastPage에 totalPageCount 값 저장
		}

		firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordsPerPage(); // Criteria 클래스의 getStartPage 메서드를 대신해 LIMIT 구문의 첫번째 값에 들어갈 데이터
		lastRecordIndex = criteria.getCurrentPageNo() * criteria.getRecordsPerPage();
		hasPreviousPage = firstPage != 1; // 첫 페이지 번호가 1이라면 false, 아니면 true 리턴
		hasNextPage = (lastPage * criteria.getRecordsPerPage()) < totalRecordCount; // (마지막 페이지 번호 * 페이지당 출력할 데이터의 개수)가 전체 데이터 개수보다 크거나 같으면 false, 작으면 true
	}

}