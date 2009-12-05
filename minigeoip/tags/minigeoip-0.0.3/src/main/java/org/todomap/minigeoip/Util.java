package org.todomap.minigeoip;

public class Util {
	
	public static String longToIp(final long number) {
		final short[] ip = new short[4];
		ip[0] = (short) (number >> 24);
		ip[1] = (short) ((number >> 16) & 0xff);
		ip[2] = (short) ((number >> 8) & 0xff);
		ip[3] = (short) ((number) & 0xff);
		final StringBuilder builder = new StringBuilder();
		builder.append(ip[0]).append('.').append(ip[1]).append('.').append(ip[2]).append('.').append(ip[3]);
		return builder.toString();
	}

	public static long ipToLong(String ip) {
		short[] addr = getAddrBytes(ip);
		return ((long) addr[0] << 24) | (long) (addr[1] << 16)
				| (long) (addr[2] << 8) | (long) (addr[3]);
	}

	static short[] getAddrBytes(String ip) {
		String[] split = ip.split("\\.");
		short[] ret = new short[4];
		for (short i = 0; i < 4; i++) {
			ret[i] = Short.parseShort(split[i]);
		}
		return ret;
	}
}
