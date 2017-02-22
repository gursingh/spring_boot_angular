package com.tarakshila.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarakshila.entity.EmailResponseDetail;
import com.tarakshila.entity.EmailStatus;
import com.tarakshila.response.Response;
import com.tarakshila.service.EmailResponseDetailService;
import com.tarakshila.service.EmailStatusService;

@RestController
public class EmailResponseController {
	@Autowired
	private EmailResponseDetailService emailResponseDetailService;
	@Autowired
	private EmailStatusService emailStatusService;

	@RequestMapping(value = "/responsedetail")
	public String saveEmailResponseDetail(@RequestParam("code") String code,
			@RequestBody EmailResponseDetail emailResponseDetail) {
		String responseString = emailResponseDetailService
				.updateEmailResponseDetail(emailResponseDetail, code);
		if (responseString != null) {

		}
		return null;

	}

	@RequestMapping(value = "/responsestatus")
	public List<EmailStatus> getEmailResponseDetail(
			@RequestParam("page") int page, @RequestParam("size") int size) {
		return emailStatusService.findAll(page - 1, size);
	}

	@RequestMapping(value = "/responsedetail", method = RequestMethod.POST)
	public Response saveEmailResponseDetail(
			@RequestBody EmailResponseDetail emailResponseDetail) {
		String response = emailResponseDetailService
				.saveEmailResponseDetail(emailResponseDetail);
		if (response == null) {
			response = "some problem in server side";
		}
		Response response2=new Response();
		response2.setMessage(response);
		return response2;
	}
}
