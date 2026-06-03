package com.qaautomation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();
    private static final String env;

    static {
        env = System.getProperty("env", "tst");
        loadProperties();
    }

    private static void loadProperties() {
        String fileName = "config-" + env + ".properties";
        try (InputStream input = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException("No se encontró el archivo: " + fileName);
            }
            properties.load(input);
            System.out.println(">>> Ambiente cargado: " + env.toUpperCase());
        } catch (IOException e) {
            throw new RuntimeException("Error cargando configuración: " + e.getMessage());
        }
    }

    public static String getWebBaseUrl() {
        return properties.getProperty("web.base.url");
    }

    public static String getApiBaseUrl() {
        return properties.getProperty("api.base.url");
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static int getTimeout() {
        return Integer.parseInt(properties.getProperty("timeout"));
    }

    public static String getEnv() {
        return env;
    }

    public static String getSeleniumRemoteUrl() {
        // Primero busca en variable de entorno (más prioritario, para Docker)
        String envUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        // Si no, lee del properties
    return properties.getProperty("selenium.remote.url");
    }

}