package com.pramati;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class HTMLFileCorrecterTest {
	HTMLFileCorrecter htmlCorrecter = new HTMLFileCorrecter(this.getClass().getClassLoader().getResource("com/pramati/sourcetest.html").getPath(), "http://localhost:9000"){
		protected void renameCorrectedFile() {
			// Do Nothing
		}
	};

	@Test
	public void testReplaceRelativeUrlPaths(){
		try{
			htmlCorrecter.replaceRelativeUrlPaths();
			BufferedReader outputFileReader = getFileReader("com/pramati/sourcetest.html.tmp");
			BufferedReader expectedFileReader = getFileReader("com/pramati/expectedoutputfile.html");
			StringBuilder expectedResult = getFileContent(expectedFileReader);
			StringBuilder correctedFileContent = 	getFileContent(outputFileReader);
			closeStream(outputFileReader);
			closeStream(expectedFileReader);
			Assert.assertEquals(correctedFileContent.toString(), expectedResult.toString());
			delete(this.getClass().getClassLoader().getResource("com/pramati/expectedoutputfile.html").getPath());
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}

	private void delete(String path) {
		new File(path).delete();		
	}

	private void closeStream(BufferedReader reader)
			throws IOException {
		reader.close();
	}

	private StringBuilder getFileContent(BufferedReader reader) throws IOException {
		String line;
		StringBuilder filecontent = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			filecontent.append(line);
		}
		return filecontent;
	}

	private BufferedReader getFileReader(String filePath) throws FileNotFoundException {
		return new BufferedReader(new FileReader(new File(this.getClass().getClassLoader().getResource(filePath).getPath())));
	}
}
