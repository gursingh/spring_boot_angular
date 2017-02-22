package com.tarakshila.service;

import com.tarakshila.entity.EmailResponseDetail;

public interface EmailResponseDetailService {
	public String updateEmailResponseDetail(
			EmailResponseDetail emailResponseDetail, String token);

	public String saveEmailResponseDetail(
			EmailResponseDetail emailResponseDetail);
}
