package com.library.util;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.constant.Method;

@Controller
public class UiUtils {
	public String showMessageWithRedirect(@RequestParam(value="message",required=false)String message,
			@RequestParam(value="redirectUri",required=false)String redirectUri, // 게시글이 작성되면 게시글 작성이 완성되었다는 메시지를 보내고 리스트로 리다이렉트
			@RequestParam(value="method",required=false)Method method, // /constant/method에 있는 메소드들 이용
			@RequestParam(value="params",required=false)Map<String,Object>params, Model model) { // 화면으로 전달해줄 파라미터
		model.addAttribute("message",message);
		model.addAttribute("redirectUri",redirectUri);
		model.addAttribute("method",method);
		model.addAttribute("params",params);
		
		return "utils/message-redirect";
	}
}
