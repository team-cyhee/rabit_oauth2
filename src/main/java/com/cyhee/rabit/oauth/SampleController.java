package com.cyhee.rabit.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {
	@RequestMapping("/abc")
	@ResponseBody
	String abc() {
		return "hi";
	}

	@RequestMapping("/abcd")
	@ResponseBody
	String abcd() {
		return "hi abcd!";
	}
}
