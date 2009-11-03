package org.todomap.minigeoip.impl.jpa;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.todomap.minigeoip.Util;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

@SpringApplicationContext("org/todomap/minigeoip/impl/jpa/JpaGeoipResolverTestCtx.xml")
public class JpaGeoipResolverTest extends UnitilsJUnit4 {

	@SpringBean("resolver")
	JpaGeoipResolver resolver;

	@SpringBean("transactionManager")
	PlatformTransactionManager manager;

	@Test
	public void testGetCountryCode() throws IOException {
		new TransactionTemplate(manager).execute(new TransactionCallback() {
			
			@Override
			public Object doInTransaction(TransactionStatus status) {
				resolver.persist(Util.ipToLong("127.0.0.0"), Util.ipToLong("127.0.0.0") + 100, "WL"); //wonderland
				Assert.assertNotNull(resolver.getCountryCode("127.0.0.10"));
				Assert.assertEquals("WL", resolver.getCountryCode("127.0.0.10"));
				return null;
			}
		});
	}

	@Test
	public void testUpdate() throws IOException {
		resolver.update();
	}
	
}
