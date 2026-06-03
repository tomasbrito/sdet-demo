package com.qaautomation.driver;

import com.qaautomation.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
        }
        return driver.get();
    }

    private static WebDriver createDriver() {
        String browser = ConfigManager.getBrowser();
        String remoteUrl = ConfigManager.getSeleniumRemoteUrl();
        WebDriver webDriver;

        MutableCapabilities options = buildOptions(browser);

        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            // Modo remoto: Selenium Grid (Docker)
            System.out.println(">>> Conectando a Selenium Grid en: " + remoteUrl);
            try {
                webDriver = new RemoteWebDriver(new URL(remoteUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("URL de Selenium inválida: " + remoteUrl, e);
            }
        } else {
            // Modo local: Chrome instalado en la máquina
            System.out.println(">>> Usando Chrome local");
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver((ChromeOptions) options);
        }

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getTimeout()));
        return webDriver;
    }

    private static MutableCapabilities buildOptions(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--no-sandbox");        // necesario en Docker
                chromeOptions.addArguments("--disable-dev-shm-usage"); // necesario en Docker
                return chromeOptions;
            default:
                throw new RuntimeException("Browser no soportado: " + browser);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}