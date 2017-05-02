package org.concordion.cubano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.concordion.cubano.utils.Config;

public class AppConfig extends Config {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

	private static String baseUrl;
	private static String searchUrl;
	private static String dbSchema;
	private static String dbUrl;

	static {
		synchronized (AppConfig.class) {
			loadProperties();
			releaseProperties();
		}
	}

	private AppConfig() { }

	public static void logSettings() {
		LOGGER.info("Environment:        " + getEnvironment());
		LOGGER.info("url:                " + baseUrl);
		LOGGER.info("Browser:            " + getBrowser());

		if (!getBrowserSize().isEmpty()) {
			LOGGER.info("browserSize:        " + getBrowserSize());
		}
	}

	private static void loadProperties() {
		baseUrl = getProperty("baseUrl");
		searchUrl = getProperty("searchUrl");

		// dbUrl = getProperty("database.url");
		// dbSchema = getProperty("database.schema");

	}

	// Application properties
	public static String getBaseUrl() {
		return baseUrl;
	}

	public static String getSearchUrl() {
		return searchUrl;
	}

	public static String getDatabaseUrl() {
		return dbUrl;
	}

	public static String getDatabaseSchema() {
		return dbSchema;
	}
}

