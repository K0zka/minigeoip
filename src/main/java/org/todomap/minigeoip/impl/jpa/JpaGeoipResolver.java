package org.todomap.minigeoip.impl.jpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.todomap.minigeoip.GeoipResolver;
import org.todomap.minigeoip.Util;

public class JpaGeoipResolver extends JpaDaoSupport implements GeoipResolver {

	PlatformTransactionManager txManager;

	public PlatformTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(PlatformTransactionManager txManager) {
		this.txManager = txManager;
	}

	final static String[] list = new String[] {
			"ftp://ftp.afrinic.net/pub/stats/afrinic/delegated-afrinic-latest",
			"ftp://ftp.apnic.net/pub/stats/apnic/delegated-apnic-latest",
			"ftp://ftp.arin.net/pub/stats/arin/delegated-arin-latest",
			"ftp://ftp.lacnic.net/pub/stats/lacnic/delegated-lacnic-latest",
			"ftp://ftp.ripe.net/ripe/stats/delegated-ripencc-latest",
			"ftp://ftp.apnic.net/pub/stats/iana/delegated-iana-latest" };

	@Override
	public String getCountryCode(String address) {
		@SuppressWarnings("unchecked")
		final List<IpDomain> resultList = getJpaTemplate()
				.find(
						"select OBJECT(o) from "
								+ IpDomain.class.getName()
								+ " o where ? between o.ipInterval.lowip and o.ipInterval.highip",
						Util.ipToLong(address));
		switch (resultList.size()) {
		case 1:
			return resultList.get(0).getCountryCode();
		case 0:
			return null;
		default:
			// TODO: PANIC!
			return null;
		}
	}

	public void update() throws IOException {
		getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				em.createQuery("delete from " + IpDomain.class.getName())
						.executeUpdate();
				return null;
			}
		});
		for (final String source : list) {
			updateFromSource(source);
		}
	}

	private void updateFromSource(final String source) throws IOException {
		final URL url = new URL(source);
		final InputStream stream = url.openStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				stream));
		String record = reader.readLine();
		while (record != null) {
			String[] fields = record.split("\\|");
			if (fields.length == 7 && "ipv4".equals(fields[2])) {
				long lowerIp = Util.ipToLong(fields[3]);
				long higherIp = lowerIp + Long.parseLong(fields[4]);
				persist(lowerIp, higherIp, fields[1]);
			}
			record = reader.readLine();
		}
	}

	void persist(final long lowerIp, final long higherIp,
			final String countryCode) {
		final Interval interval = new Interval();
		interval.setHighip(higherIp);
		interval.setLowip(lowerIp);
		final IpDomain domain = new IpDomain();
		domain.setIpInterval(interval);
		domain.setCountryCode(countryCode);
		final IpDomain find = getJpaTemplate().find(IpDomain.class, interval);
		if (find == null) {
			getJpaTemplate().persist(domain);
		}
	}

}
