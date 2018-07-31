package com.spring.sse.demo.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.spring.sse.demo.model.SFMessage;
import com.spring.sse.demo.model.SFRequest;
import com.spring.sse.demo.model.SFResponse;
import com.spring.sse.demo.repository.SFMessageRepository;

import reactor.core.publisher.Flux;

/**
 * @author mkarki
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SFTransactionServiceTest {

	@TestConfiguration
	static class SFTransactionServiceTestConfiguration {
		@MockBean
		private SFMessageRepository sfMessageRepository;
		@Bean
		public SFTransactionService sfTransactionService() {
			return new SFTransactionService(sfMessageRepository);
		}
	}

	private static final Long WORKSPACE_ID = 1234L;
	private static final Long TENANT_ID = 5678L;
	public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";
	public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

	private UUID id;

	@Autowired
	private SFTransactionService sfTransactionService;

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setup() {
		id = UUID.randomUUID();
	}

	/*@BeforeClass
	public static void startCassandraEmbedded() throws InterruptedException, TTransportException,
			ConfigurationException, IOException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		Cluster cluster = Cluster.builder()
				.addContactPoints("127.0.0.1").withPort(9042).build();
		Session session = cluster.connect();
		session.execute(KEYSPACE_CREATION_QUERY);
		session.execute(KEYSPACE_ACTIVATE_QUERY);
		Thread.sleep(5000);
	}*/

	@Test
	public void testSavingToDbWithNullEntity() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("A null entity can't be saved");

		sfTransactionService.saveToDb(null, TENANT_ID, WORKSPACE_ID);
	}

	@Test
	public void testSavingToDbWithValidEntityButWithoutTenantId() {
		SFMessage sfMessage = new SFMessage(id, "take this value");

		SFRequest sfRequest = new SFRequest(new ArrayList<SFMessage>(){{add(sfMessage);}});

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Workspace or tenant cannot be null");

		sfTransactionService.saveToDb(sfRequest, null, WORKSPACE_ID);
	}

	@Test
	public void testSavingToDbWithValidEntity() {
		SFMessage sfMessage = new SFMessage(id, "take this value");

		SFRequest sfRequest = new SFRequest(new ArrayList<SFMessage>(){{add(sfMessage);}});
		Flux<SFResponse> response = sfTransactionService.saveToDb(sfRequest, TENANT_ID, WORKSPACE_ID);

//		assertThat(response.getId(), is(equalTo(sfMessage.getId())));
	}

//	@AfterClass
//	public static void stopCassandraEmbedded() {
//		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
//	}


}