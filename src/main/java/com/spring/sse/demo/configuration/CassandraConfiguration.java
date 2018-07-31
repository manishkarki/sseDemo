package com.spring.sse.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.cql.config.CassandraCqlClusterFactoryBean;

/**
 * @author mkarki
 */
@Configuration
@EnableCassandraRepositories(basePackages = {
		"com.spring.sse.demo.model",
})
public class CassandraConfiguration extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.username}")
	private String username;

	@Value("${spring.data.cassandra.password}")
	private String password;

	@Value("${spring.data.cassandra.contactpoints}")
	private String contactPoints;

	@Value("${spring.data.cassandra.port}")
	private int port;

	@Value("${spring.data.cassandra.keyspace-name}")
	private String keySpace;

	@Value("${spring.data.cassandra.basepackages}")
	private String basePackages;

	@Bean
	@Override
	public CassandraCqlClusterFactoryBean cluster() {
		CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();

		bean.setAddressTranslator(getAddressTranslator());
		bean.setAuthProvider(getAuthProvider());
		bean.setClusterBuilderConfigurer(getClusterBuilderConfigurer());
		bean.setClusterName(getClusterName());
		bean.setCompressionType(getCompressionType());
		bean.setContactPoints(getContactPoints());
		bean.setLoadBalancingPolicy(getLoadBalancingPolicy());
		bean.setMaxSchemaAgreementWaitSeconds(getMaxSchemaAgreementWaitSeconds());
		bean.setMetricsEnabled(getMetricsEnabled());
		bean.setNettyOptions(getNettyOptions());
		bean.setPoolingOptions(getPoolingOptions());
		bean.setPort(getPort());
		bean.setProtocolVersion(getProtocolVersion());
		bean.setQueryOptions(getQueryOptions());
		bean.setReconnectionPolicy(getReconnectionPolicy());
		bean.setRetryPolicy(getRetryPolicy());
		bean.setSpeculativeExecutionPolicy(getSpeculativeExecutionPolicy());
		bean.setSocketOptions(getSocketOptions());
		bean.setTimestampGenerator(getTimestampGenerator());

		bean.setKeyspaceCreations(getKeyspaceCreations());
		bean.setKeyspaceDrops(getKeyspaceDrops());
		bean.setStartupScripts(getStartupScripts());
		bean.setShutdownScripts(getShutdownScripts());
		bean.setUsername(username);
		bean.setPassword(password);

		return bean;
	}

	@Override
	protected String getKeyspaceName() {
		return keySpace;
	}

	@Override
	protected String getContactPoints() {
		return contactPoints;
	}

	@Override
	protected int getPort() {
		return port;
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] {basePackages};
	}

}
