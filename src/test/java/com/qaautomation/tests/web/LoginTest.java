package com.qaautomation.tests.web;

import com.qaautomation.config.ConfigManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Web Tests")
@Feature("Login")
public class LoginTest extends BaseWebTest {

    @Test
    @DisplayName("Login exitoso con credenciales válidas")
    @Description("Verifica que un usuario pueda loguearse con usuario y contraseña correctos")
    void loginWithValidCredentials_shouldSucceed() {
        // Ir a la página de login
        driver.get(ConfigManager.getWebBaseUrl() + "/login");

        // Completar usuario y contraseña
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        // Click en login
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verificar mensaje de éxito
        WebElement flashMessage = driver.findElement(By.id("flash"));
        assertTrue(flashMessage.getText().contains("You logged into a secure area!"),
            "El mensaje de éxito no apareció. Mensaje actual: " + flashMessage.getText());
    }

    @Test
    @DisplayName("Login fallido con credenciales inválidas")
    @Description("Verifica que aparezca un error al ingresar credenciales incorrectas")
    void loginWithInvalidCredentials_shouldFail() {
        driver.get(ConfigManager.getWebBaseUrl() + "/login");

        driver.findElement(By.id("username")).sendKeys("usuarioMalo");
        driver.findElement(By.id("password")).sendKeys("passwordMala");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement flashMessage = driver.findElement(By.id("flash"));
        assertTrue(flashMessage.getText().contains("Your username is invalid!"),
            "No apareció el error esperado. Mensaje actual: " + flashMessage.getText());
    }
}