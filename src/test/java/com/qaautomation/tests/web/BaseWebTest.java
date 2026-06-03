package com.qaautomation.tests.web;

import com.qaautomation.driver.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseWebTest {

    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.getDriver();
    }

    @AfterEach
    void tearDown() {
        DriverFactory.quitDriver();
    }
}