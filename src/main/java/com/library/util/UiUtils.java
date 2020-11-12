package com.library.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.constant.Method;
import com.library.paging.Criteria;

/** 사용자에게 메시지를 전달하기 위한 클래스 */
@Controller
public class UiUtils {
	public String showMessageWithRedirect(@RequestParam(value = "message", required = false) String message, // message: 사용자에게 전달할 메시지
		  @RequestParam(value = "redirectUri", required = false) String redirectUri, // redirect할 URI를 의미, 메시지 전달 후 리다이렉트 할 uri
		  @RequestParam(value = "method", required = false) Method method, // Method Enum 클래스에 선언한 HTTP 요청 메서드
		  @RequestParam(value = "params", required = false) Map<String, Object> params, Model model) { // 뷰로 전달할 파라미터. 파라미터의 개수가 페이지마다 달라질 수 있으므로 여러가지 데이터를 담을 수 있는 Map을 사용. Model은 뷰로 파라미터를 전달하는 데 사용
				model.addAttribute("message", message);
				model.addAttribute("redirectUri", redirectUri);
				model.addAttribute("method", method);
				model.addAttribute("params", params);
		
				return "utils/message-redirect";
	}
	
	/** Criteria 클래스의 모든 멤버변수(이전 페이지 정보)를 리턴하는 메서드. POST방식의 처리에서만 사용됨*/
	public Map<String, Object> getPagingParams(Criteria criteria) {
		Map<String, Object> params = new LinkedHashMap<>();
		
		params.put("currentPageNo", criteria.getCurrentPageNo());
		params.put("recordsPerPage", criteria.getRecordsPerPage());
		params.put("pageSize", criteria.getPageSize());
		params.put("searchType", criteria.getSearchType());
		params.put("searchKeyword", criteria.getSearchKeyword());

		return params;
	}

}