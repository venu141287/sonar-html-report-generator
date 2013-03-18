package com.pramati;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class HTMLContentPullerTest {
	@Test
	public void testPull(){
		try {
			String pullFileLocation = new File(".").getCanonicalPath()+File.separator+"tmp";
			HTMLContentPuller htmlContentPuller = new HTMLContentPuller("http://google.com",pullFileLocation);; 
			htmlContentPuller.pull();
			Assert.assertTrue(new File(pullFileLocation).exists());
			delete(pullFileLocation);
			
		} catch (IOException e) {
			Assert.fail();		
		}

	}
	private void delete(String path) {
		new File(path).delete();		
	}

}
