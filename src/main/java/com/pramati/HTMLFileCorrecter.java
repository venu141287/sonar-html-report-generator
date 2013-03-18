package com.pramati;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTMLFileCorrecter {
	private Logger logger = LoggerFactory.getLogger(HTMLFileCorrecter.class.getSimpleName());
	private String filename;
	private String serverHostPath;
	public HTMLFileCorrecter(String filename, String serverHos) {
		super();
		this.filename = filename;
		this.serverHostPath = serverHos;
	}
	public void replaceRelativeUrlPaths() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename+".tmp"));
			String line;
			while ((line = reader.readLine())!=null) {
				writer.write(line.replaceAll("src=\"", "src=\""+serverHostPath).replaceAll("href=\"", "href=\""+serverHostPath).replaceAll("href=\'", "href=\'"+serverHostPath));
			}
			reader.close();
			writer.close();
			renameCorrectedFile();
		}
		catch (IOException e) {
			logger.error("HTML Pull exception:"+e.getMessage());
			throw new HTMLReportException(e);
		}

	}
	protected void renameCorrectedFile() {
		new File(filename).delete();
		new File(filename+".tmp").renameTo(new File(filename));
	}
}
