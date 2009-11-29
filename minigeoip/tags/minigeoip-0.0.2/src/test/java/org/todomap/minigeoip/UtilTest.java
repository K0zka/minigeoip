package org.todomap.minigeoip;

import junit.framework.Assert;

import org.junit.Test;

public class UtilTest {
	@Test
	public void testIp2long() {
		check("127.0.0.1", 2130706433l);
		check("192.168.0.1", 3232235521l);
		check("195.228.252.138", 3286563978l);
	}
	
	@Test
	public void testLong2Ip() {
		check(2130706433l, "127.0.0.1");
		check(3232235521l, "192.168.0.1");
	}
	
	private void check(long number, String ip) {
		Assert.assertEquals(ip, Util.longToIp(number));
	}

	public void check(String ip, long expected) {
		Assert.assertEquals(expected, Util.ipToLong(ip));
	}
	
}
