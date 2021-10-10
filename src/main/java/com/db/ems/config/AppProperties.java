package com.db.ems.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppProperties {
	private Environment env;

	public AppProperties(Environment env) {
		this.env = env;
	}

	public String getProperty(String pPropertyKey) {
		return env.getProperty(pPropertyKey);
	}
}