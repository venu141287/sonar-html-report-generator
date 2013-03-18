package com.pramati;

import junit.framework.Assert;

import org.junit.Test;

public class EmailTest {
	Emailer emailer = new Emailer("test@gmail.com", "http://google.com/");
	@Test(expected = RuntimeException.class)
	public void testEmail() {
		emailer.mail();
	}
	@Test
	public void testgetServerHostPath() {
		emailer = new Emailer("test@gmail.com", "http://localhost:9000/dashboard/index/304");
		Assert.assertEquals("http://localhost:9000",emailer.getServerHostPath());
	}
	
}
