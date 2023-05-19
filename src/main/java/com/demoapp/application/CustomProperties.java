package com.demoapp.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:custom.properties")
@ConfigurationProperties(prefix="app")
public class CustomProperties {

	private Integer timeout;
	
	@Value("${app.refresh.token.timeout}")
	private Integer refreshTimeout;

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getRefreshTimeout() {
		return refreshTimeout;
	}

	public void setRefreshTimeout(Integer refreshTimeout) {
		this.refreshTimeout = refreshTimeout;
	}
	
	
}
