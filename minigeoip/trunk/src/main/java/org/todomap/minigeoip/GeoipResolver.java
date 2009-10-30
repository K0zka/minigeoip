package org.todomap.minigeoip;


public interface GeoipResolver {
	String getCountryCode(final String address);
}
