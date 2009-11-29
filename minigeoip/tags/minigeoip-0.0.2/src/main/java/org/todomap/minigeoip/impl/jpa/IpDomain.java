package org.todomap.minigeoip.impl.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ip_domains")
public class IpDomain implements Serializable {
	private static final long serialVersionUID = -3098684358013841341L;
	@EmbeddedId
	Interval ipInterval;
	@Column
	String countryCode;

	public Interval getIpInterval() {
		return ipInterval;
	}

	public void setIpInterval(Interval ipInterval) {
		this.ipInterval = ipInterval;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
