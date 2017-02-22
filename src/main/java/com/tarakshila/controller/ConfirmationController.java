package com.tarakshila.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tarakshila.service.EmailStatusService;

@Controller
public class ConfirmationController {

	@Autowired
	private EmailStatusService emailStatusService;

	@RequestMapping(value = "/confirmation")
	public String confirmationPublicLink(@RequestParam("code") String code,
			@RequestParam("email") String email) {
		emailStatusService.emailConfirmationReply(code, email);
		return "confirmationindex";
	}
}
