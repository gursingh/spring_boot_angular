package com.tarakshila.file.processor;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tarakshila.entity.Applicant;
import com.tarakshila.entity.EmailStatus;
import com.tarakshila.handler.email.EmailHandlerExecutor;
import com.tarakshila.handler.email.EmailInfoBean;
import com.tarakshila.service.ApplicantService;
import com.tarakshila.service.EmailStatusService;

public class FileProcessor implements Runnable {
	private static final Logger logger = LoggerFactory
			.getLogger(FileProcessor.class);
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
			logger.info("FILE Uploaded");
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
			int i = 0;
			List<EmailInfoBean> emailInfoBeans = new ArrayList<EmailInfoBean>();
			while (rowIterator.hasNext()) {
				logger.info("Row Processing " + (i++));
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
					logger.warn("applicant already exist");
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
							.setSubject("Monday Morning - An initiative for accomplished individuals");
					emailInfoBeans.add(emailInfoBean);
					EmailStatus emailStatus = new EmailStatus();
					emailStatus.setApplicant(applicant);
					emailStatus.setUniqueCode(userUniqueConfirmationLink);
					emailStatus.setCreationDate(new Date());
					emailStatusService.saveEmailStatus(emailStatus);

				}
			}

			EmailHandlerExecutor.getInstance().sendMessage(emailInfoBeans,
					emailStatusService);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			file.delete();
		}

	}

	private String createHtmlEmailBody(String confirmationLink, String name) {
		String content = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\""
				+ "class=\"m_-9144570498440269817subscribe-body\" width=\"550\""
				+ "style=\"width: 100%; padding: 10px\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td>"
				+

				"<div style=\"direction: ltr; max-width: 600px; margin: 0 auto\">"
				+

				"<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\""
				+ "class=\"m_-9144570498440269817subscribe-wrapper\""
				+ "style=\"width: 100%; background-color: #fff; text-align: left; margin: 0 auto; max-width: 1024px; min-width: 320px\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td>"
				+

				"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\""
				+ "height=\"8\""
				+ "background=\"https://ci3.googleusercontent.com/proxy/A--eNJbuWGcMvC9urmzoDZ6Juea6Xcg-ciQ9rlB5LbXLHtDZDz5AaJJ-Hy6I_SwzgwlKfL3M30nRmJSp=s0-d-e1-ft#https://s0.wp.com/i/emails/stripes.gif\""
				+ "class=\"m_-9144570498440269817subscribe-header-wrap\""
				+ "style=\"width: 100%; background-image: url('https://ci3.googleusercontent.com/proxy/A--eNJbuWGcMvC9urmzoDZ6Juea6Xcg-ciQ9rlB5LbXLHtDZDz5AaJJ-Hy6I_SwzgwlKfL3M30nRmJSp=s0-d-e1-ft#https://s0.wp.com/i/emails/stripes.gif'); background-repeat: repeat-x; background-color: #43a4d0; height: 8px\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td></td>"
				+ "</tr>"
				+ "</tbody>"
				+ "</table>"
				+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\""
				+ "class=\"m_-9144570498440269817subscribe-header\""
				+ "style=\"width: 100%; background-color: #efefef; padding: 0; border-bottom: 1px solid #ddd\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td></td>"
				+

				"<td style=\"vertical-align: middle\" height=\"32\" width=\"32\""
				+ "valign=\"middle\" align=\"right\"><a"
				+ "style=\"text-decoration: underline; color: #2585b2\""
				+ "href=\"http://antoniogoncalves.org\" target=\"_blank\""
				+ "data-saferedirecturl=\"https://www.google.com/url?hl=en&amp;q=http://antoniogoncalves.org&amp;source=gmail&amp;ust=1487260143448000&amp;usg=AFQjCNE605wT9-KkrR3FL3wWkgWCrZEnRA\">"
				+

				"</a></td>"
				+ "</tr>"
				+ "</tbody>"
				+ "</table>"
				+ "<table style=\"width: 100%\" width=\"100%\" border=\"0\""
				+ "cellspacing=\"0\" cellpadding=\"20\" bgcolor=\"#ffffff\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td>"
				+ "<table style=\"width: 100%\" border=\"0\" cellspacing=\"0\""
				+ "cellpadding=\"0\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td valign=\"top\">"
				+ "<p"
				+ "style=\"direction: ltr; font-size: 14px; line-height: 1.4em; color: #444444; font-family: &amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; quot; Helvetica Neue&amp;amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; quot; , Helvetica ,Arial,sans-serif; margin: 0 0 1em 0\">"
				+ "Dear {0},<br> Happy to connect with you again!<br>"
				+ "<br>"
				+ ""
				+ "<p>A. <b>Context</b><br /> We might have "
				+ "connected with you for a position with us in the "
				+ "past. It is possible that during our last "
				+ "conversation and through the evaluation process we "
				+ "could not bring you on board due to various reasons. "
				+ "However, we have identified a common platform that "
				+ "connects the potential leaders under one huge "
				+ "umbrella cutting across sectors, age and experience.<br>"
				+ "<ul>"
				+ "<li>To build the skills and intellectual capacity to the next level of the future leaders of this country we are introducing a program called \"<b>Monday Morning - An initiative for accomplished individuals</b>\"."
				+ "</li>"
				+ "<li>This program is conceived from the thought "
				+ "that India needs talent which is passionately "
				+ "aspired, multi-skilled across various disciplines, "
				+ "industries and functions and can hold a big picture "
				+ "view while evaluating and addressing situations.</li>"
				+ "</ul>"
				+ "</p>"
				+

				"<p>B. <b>Our Expertise</b><br />"
				+ "<ul>"
				+ "<li>As a firm, we have gathered the expertise "
				+ "in various industries, functions and situations "
				+ "that our clients have faced across time.</li>"
				+ "<li>In the process of helping clients stride "
				+ "through these situation, we have built an innate "
				+ "ability to manage and resolve complex problems and "
				+ "build the skills of our talent to adapt and quickly "
				+ "move up the curve.</li>"
				+ "<li>We have been able to build skills for "
				+ "consultants with just the right ingredients that "
				+ "will make you successful in the corporate "
				+ "ecosystem.</li>"
				+ "<li>Our experience of last 1000 projects with 1 "
				+ "Million man-hours of successful and impactful "
				+ "client work has helped us establish these methods, "
				+ "frameworks and content required to convert young "
				+ "elite MBA talent into accomplished individuals.</li>"
				+ "</ul>"
				+ "</p>"
				+

				"<p>C. <b>What is in it for you? (Build your "
				+ "skill anywhere, anytime)</b><br />"
				+ "<ul>"
				+ "<li>Through this program, we wish to share this "
				+ "skill and help the young elite MBA talent of today "
				+ "to be hands-on and aware of the ecosystem they "
				+ "exist in.</li>"
				+ "<li>This will be done through small pieces of to-dos that will bring change in you every Monday morning.</li>"
				+ "<li>This journey will help you accelerate your "
				+ "career by multiplying your knowledge through "
				+ "various innovative yet quick methods and "
				+ "challenging yourself to the hilt.</li>"
				+

				"</ul>"
				+ "</p>"
				+ ""
				+ "</p>"
				+ "<p>"
				+ "If you wish to join us  in this <b>Monday Morning program</b>, fill the form . We are doing a "
				+ "pre-registration for the first 1000 potential early "
				+ "birds. </p><p>We assure that you will have an enriching "
				+ "experience in this journey with us."
				+ "</p>"
				+

				"<p class=\"m_-9144570498440269817subscribe-action-links\""
				+ "style=\"direction: ltr; font-size: 14px; line-height: 1.4em; color: #444444; font-family: &amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; quot; Helvetica Neue&amp;amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; quot; , Helvetica ,Arial,sans-serif; margin: 0 0 1em 0; font-size: 14px; padding: 0; color: #666; padding-top: 1em; padding-bottom: 0em; margin-bottom: 0; margin-left: 0; padding-left: 0\">"
				+ "<a href={1} "
				+ " style=\"text-decoration: underline; color: #2585b2; border-radius: 10em; border: 1px solid #11729e; text-decoration: none; color: #fff; background-color: #2585b2; padding: 5px 15px; font-size: 16px; line-height: 1.4em; font-family: Helvetica Neue, Helvetica, Arial, sans-serif; font-weight: normal; margin-left: 0\""
				+ "target=\"_blank\">Click Here</a>"
				+ "</p> <br /> <br /> <br />"
				+ "<p"
				+ "style=\"direction: ltr; font-size: 14px; line-height: 1.4em; color: #444444; font-family: &amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; quot; Helvetica Neue&amp;amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; quot; , Helvetica ,Arial,sans-serif; margin: 0 0 1em 0\">"
				+ "<strong>Contact Us : </strong><a"
				+ "href=\"tkcgold@tkc.firm.in\">tkcgold@tkc.firm.in</a><br>"
				+ "<strong>"
				+ "</p>"
				+ "</td>"
				+ "</tr>"
				+ "</tbody>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</tbody>"
				+ "</table>"
				+ "<table border=\"0\" cellspacing=\"0\" width=\"550\" cellpadding=\"20\""
				+ "bgcolor=\"#efefef\""
				+ "class=\"m_-9144570498440269817subscribe-wrapper-sub\""
				+ "style=\"width: 100%; background-color: #efefef; text-align: left; border-top: 1px solid #dddddd\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td class=\"m_-9144570498440269817subscribe-content\""
				+ "	style=\"border-top: 1px solid #f3f3f3; color: #888; font-family: &amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; quot; Helvetica Neue&amp;amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; amp; quot; , Helvetica ,Arial,sans-serif; font-size: 14px; background: #efefef; margin: 0; padding: 10px 20px 0\">"
				+ "</td>"
				+ "</tr>"
				+ "</tbody>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</tbody>"
				+ "</table>"
				+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\""
				+ "height=\"3\""
				+ "background=\"https://ci3.googleusercontent.com/proxy/A--eNJbuWGcMvC9urmzoDZ6Juea6Xcg-ciQ9rlB5LbXLHtDZDz5AaJJ-Hy6I_SwzgwlKfL3M30nRmJSp=s0-d-e1-ft#https://s0.wp.com/i/emails/stripes.gif\""
				+ "class=\"m_-9144570498440269817subscribe-footer-wrap\""
				+ "style=\"width: 100%; background-image: url('https://ci3.googleusercontent.com/proxy/A--eNJbuWGcMvC9urmzoDZ6Juea6Xcg-ciQ9rlB5LbXLHtDZDz5AaJJ-Hy6I_SwzgwlKfL3M30nRmJSp=s0-d-e1-ft#https://s0.wp.com/i/emails/stripes.gif'); background-repeat: repeat-x; background-color: #43a4d0; height: 3px\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td></td>"
				+ "</tr>"
				+ "</tbody>"
				+ "</table>" + "</div>" +

				"</td>" + "</tr>" + "</tbody>" + "</table>";
		content = MessageFormat.format(content, name, confirmationLink);
		return content;
	}
}
