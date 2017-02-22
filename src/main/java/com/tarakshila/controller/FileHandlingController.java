package com.tarakshila.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tarakshila.file.processor.FileProcessor;
import com.tarakshila.service.ApplicantService;
import com.tarakshila.service.EmailStatusService;

@RestController
public class FileHandlingController {
	@Autowired
	private ApplicantService applicantService;
	@Autowired
	private EmailStatusService emailStatusService;

	@RequestMapping(value = "/uploadexcel", method = RequestMethod.POST)
	public String uploadAndProcessExcelFile(
			@RequestParam("file") MultipartFile file) {
		String name = file.getOriginalFilename();
		if (!file.isEmpty()) {
			try {
				String extension = "";
				int i = file.getName().lastIndexOf('.');
				if (i > 0) {
					extension = file.getName().substring(i + 1);
				}
				String basePath = System.getProperty("catalina.base");
				byte[] bytes = file.getBytes();
				String fileName = basePath + "/" + new ObjectId().toString()
						+ extension;
				File file2 = new File(fileName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(file2));
				stream.write(bytes);
				stream.close();
				FileProcessor fileProcessor = new FileProcessor(file2,
						applicantService, emailStatusService);
				Thread thread = new Thread(fileProcessor);
				thread.start();
				return "You successfully uploaded " + name + " into ";
			} catch (Exception e) {
				return "You failed to upload " + name + " file";
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}
}
