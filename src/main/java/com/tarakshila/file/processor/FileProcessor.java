package com.tarakshila.file.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;

import com.tarakshila.entity.Applicant;
import com.tarakshila.entity.EmailStatus;
import com.tarakshila.handler.email.EmailHandlerExecutor;
import com.tarakshila.handler.email.EmailInfoBean;
import com.tarakshila.service.ApplicantService;
import com.tarakshila.service.EmailStatusService;

public class FileProcessor implements Runnable {
	private File file;
	private ApplicantService applicantService;
	private EmailStatusService emailStatusService;

	public FileProcessor(File file, ApplicantService applicantService,
			EmailStatusService emailStatusService) {
		this.file = file;
		this.applicantService = applicantService;
		this.emailStatusService = emailStatusService;
	}

	public void run() {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			Iterator<Row> rowIterator = null;
			if (file.getName().endsWith(".xls")) {
				HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
				HSSFSheet sheet = workbook.getSheetAt(0);
				rowIterator = sheet.iterator();
			} else {
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
				XSSFSheet sheet = workbook.getSheetAt(0);
				rowIterator = sheet.iterator();
			}

			while (rowIterator.hasNext()) {
				Applicant applicant = new Applicant();
				Row row = rowIterator.next();
				Cell applicantNameCell = row.getCell(0);
				applicantNameCell.setCellType(Cell.CELL_TYPE_STRING);
				Cell applicantEmailCell = row.getCell(1);
				applicantEmailCell.setCellType(Cell.CELL_TYPE_STRING);
				Cell applicantPhoneCell = row.getCell(2);
				applicantPhoneCell.setCellType(Cell.CELL_TYPE_STRING);
				applicant.setName(applicantNameCell.getStringCellValue());
				applicant.setEmailId(applicantEmailCell.getStringCellValue());
				applicant.setPhoneNumber(applicantPhoneCell
						.getStringCellValue());
				try {
					Applicant temp = applicantService.findByEmailId(applicant
							.getEmailId());
					if (temp != null) {
						applicant.setId(temp.getId());
					}
					applicantService.saveApplicant(applicant);

				} catch (Exception e) {
					applicant = applicantService.findByEmailId(applicant
							.getEmailId());
				}
				if (applicant.getId() != null) {
					String userUniqueConfirmationLink = new ObjectId() + ""
							+ UUID.randomUUID();
					String confirmationLink = "http://gold.tkc.firm.in/confirmation?code="
							+ userUniqueConfirmationLink
							+ "&email="
							+ applicant.getEmailId();
					EmailInfoBean emailInfoBean = new EmailInfoBean();
					emailInfoBean.setMessageBody(createHtmlEmailBody(
							confirmationLink, applicant.getName()));
					emailInfoBean.setReceiverEmailId(applicant.getEmailId());
					emailInfoBean
							.setSubject("CXO Factory - a TKC knowledge initiative");
					EmailHandlerExecutor.getInstance().sendMessage(
							emailInfoBean);
					EmailStatus emailStatus = new EmailStatus();
					emailStatus.setApplicant(applicant);
					emailStatus.setUniqueCode(userUniqueConfirmationLink);
					emailStatus.setCreationDate(new Date());
					emailStatusService.saveEmailStatus(emailStatus);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file.delete();
		}

	}

	private String createHtmlEmailBody(String confirmationLink, String name) {
		;
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(getClass()
					.getResource("temp.html").getFile()));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		content = MessageFormat.format(content, name, confirmationLink);
		return content;
	}
}
