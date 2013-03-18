package com.pramati;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HTMLContentPuller {
	private Logger logger = LoggerFactory.getLogger(HTMLContentPuller.class.getSimpleName());
	private String pullUrl;
	private String pullFileLocation;

	public HTMLContentPuller(String pullUrl,String pullFileLocation) {
		super();
		this.pullUrl = pullUrl;
		this.pullFileLocation = pullFileLocation;
	}

	public void pull() {
		try {
			URL url = new URL(pullUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			File file = new File(pullFileLocation);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(fw);
			while ((inputLine = reader.readLine()) != null) {
				writer.write(inputLine);
			}
			writer.close();
			reader.close();

		} catch (Exception e) {
			logger.error("HTML Pull exception:"+e.getMessage());
			throw new HTMLReportException(e);
		}
	}
}