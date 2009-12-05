package org.todomap.minigeoip;

/**
 * GEO Ip resolver interface.
 * 
 * @author kocka
 *
 */
public interface GeoipResolver {
	/**
	 * Resolve the country code based in IP address.
	 * @param address stringified IP-address
	 * @return	2-letter country code
	 */
	String getCountryCode(final String address);

	/**
	 * Resolve the country code based in IP address.
	 * @param address stringified IP-address
	 * @return	2-letter country code
	 */
	String getCountryName(final String address);

	/**
	 * Update the datasource. Do nothing if not required.
	 * 
	 * @throws Exception
	 */
	void update() throws Exception;
}
