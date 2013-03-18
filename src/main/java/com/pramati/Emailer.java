
package com.pramati;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Emailer {
	private Logger logger = LoggerFactory.getLogger(Emailer.class.getSimpleName());
	private static final String SONAR_REPORT = "Sonar-Report";
	private static final String SONAR_REPORT_HTML = "Sonar-Report.html";
	private static Properties emailProperties = new Properties();
    private String emailId,url,filepath;
	public Emailer(String emailId, String url) {
		super();
		this.emailId = emailId;
		this.url = url;
	}
	static{
		try {
			emailProperties.load(Emailer.class.getResourceAsStream("/email.properties"));
		} catch (IOException e) {
			throw new HTMLReportException(e);
		}
	}
	public void mail(){
		try{
			pullSonarReport();
			replaceabsoluteUrlsInSonarReport();
			loadContextLoader();
			EmailAttachment attachment = createEmailAttachmentWithProperties();
			MultiPartEmail  email = createEmailWithSettings();
			email.attach(attachment);
			sendEmail(email);

		}catch(Exception e){
			logger.error("Email Error:"+e.getMessage());
			throw new HTMLReportException(e);
		}finally{
			deleteSonarReport();
		}

	}

	private void loadContextLoader() {
		Thread.currentThread().setContextClassLoader(Emailer.class.getClassLoader());
	}

	protected void sendEmail(MultiPartEmail email) throws EmailException {
		email.send();
	}

	private void deleteSonarReport() {
		new File(filepath).delete();		
	}


	private MultiPartEmail createEmailWithSettings() throws EmailException {
		MultiPartEmail  email = new MultiPartEmail();
		email.setSmtpPort(Integer.parseInt(emailProperties.getProperty("smtpPort")));
		email.setAuthenticator(new DefaultAuthenticator(emailProperties.getProperty("authuser"), emailProperties.getProperty("authpwd")));
		email.setDebug(Boolean.parseBoolean(emailProperties.getProperty("debug")));
		email.setHostName(emailProperties.getProperty("hostName"));
		email.setFrom(emailProperties.getProperty("from"), "SenderName");
		email.setSubject(emailProperties.getProperty("subject"));
		email.setMsg(emailProperties.getProperty("message"));
		email.addTo(emailId, emailId);
		email.setTLS(Boolean.parseBoolean(emailProperties.getProperty("tls")));
		return email;
	}


	private EmailAttachment createEmailAttachmentWithProperties() {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(filepath);
		attachment.setDescription(SONAR_REPORT);
		attachment.setName(SONAR_REPORT_HTML);
		return attachment;
	}


	private void replaceabsoluteUrlsInSonarReport() {
		new HTMLFileCorrecter(filepath, getServerHostPath()).replaceRelativeUrlPaths();
	}

	protected String getServerHostPath() {
		return url.substring(0,url.indexOf('/',url.indexOf('/')+2 ));
	}


	private void pullSonarReport() {
		try {
			filepath = new File(".").getCanonicalPath()+File.separator+UUID.randomUUID();
		}
		catch (IOException e) {
			logger.error("Email Error:"+e.getMessage());
			throw new HTMLReportException(e);		
		}
		new HTMLContentPuller(url,filepath).pull();
	}
}