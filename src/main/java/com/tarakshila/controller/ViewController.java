package com.tarakshila.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
	@RequestMapping(value="/uploadfile")
	public String uploadPage(){
		return "index";
	}
}
