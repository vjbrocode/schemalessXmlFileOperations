package com.db.ems.controller;

import java.util.Set;

public class Validator {
	private static Set<String> supportedProperties = Set.of("name", "designation");
	
	public static boolean notValid(String property) {
		return !supportedProperties.contains(property);
	}

	public static String getSupportedProperties() {
		return supportedProperties.toString();
	}
}
