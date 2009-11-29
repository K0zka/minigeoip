package org.todomap.minigeoip.impl.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Interval implements Serializable {
	private static final long serialVersionUID = 2842004529786417869L;
	@Column
	long lowip;
	@Column
	long highip;

	public long getLowip() {
		return lowip;
	}

	public void setLowip(long lowip) {
		this.lowip = lowip;
	}

	public long getHighip() {
		return highip;
	}

	public void setHighip(long highip) {
		this.highip = highip;
	}
}
