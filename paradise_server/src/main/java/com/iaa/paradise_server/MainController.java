package com.iaa.paradise_server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

	@GetMapping(value="/home",produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String index() {
		return "Hello from spring boot";
	}

}