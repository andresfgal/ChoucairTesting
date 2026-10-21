package com.reto.responsiveness;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class ResponsiveLayoutTest {
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final Dimension[] VIEWPORTS = {
            new Dimension(360, 800),
            new Dimension(768, 1024),
            new Dimension(1440, 900)
    };

    @Test
    public void loginAndInventoryFitAcrossBrowsersAndViewports() {
        for (String browser : new String[]{"Chrome", "Edge", "Firefox"}) {
            WebDriver driver = createDriver(browser);
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                driver.get(BASE_URL);
                wait.until(currentDriver -> currentDriver.findElement(By.id("user-name")).isDisplayed());

                for (Dimension viewport : viewportsFor(browser)) {
                    setViewport(driver, viewport);
                    assertPageFits(driver, browser, "login", viewport);
                    assertVisible(driver, By.id("user-name"), browser, "login", viewport);
                    assertVisible(driver, By.id("password"), browser, "login", viewport);
                    assertVisible(driver, By.id("login-button"), browser, "login", viewport);
                }

                setViewport(driver, VIEWPORTS[2]);
                driver.findElement(By.id("user-name")).sendKeys(USERNAME);
                driver.findElement(By.id("password")).sendKeys(PASSWORD);
                driver.findElement(By.id("login-button")).click();
                wait.until(currentDriver -> currentDriver.findElement(By.id("inventory_container")).isDisplayed());

                for (Dimension viewport : viewportsFor(browser)) {
                    setViewport(driver, viewport);
                    assertPageFits(driver, browser, "inventario", viewport);
                    assertVisible(driver, By.id("inventory_container"), browser, "inventario", viewport);
                    assertVisible(driver, By.className("shopping_cart_link"), browser, "inventario", viewport);
                    assertTrue(context(browser, "inventario", viewport) + ": no se muestran productos",
                            !driver.findElements(By.className("inventory_item")).isEmpty());
                }
            } finally {
                driver.quit();
            }
        }
    }

    private WebDriver createDriver(String browser) {
        switch (browser) {
            case "Chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new", "--disable-gpu");
                return new ChromeDriver(chromeOptions);
            case "Edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless=new", "--disable-gpu");
                return new EdgeDriver(edgeOptions);
            case "Firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                return new FirefoxDriver(firefoxOptions);
            default:
                throw new IllegalArgumentException("Navegador no soportado: " + browser);
        }
    }

    private Dimension[] viewportsFor(String browser) {
        if ("Firefox".equals(browser)) {
            return new Dimension[]{new Dimension(500, 800), VIEWPORTS[1], VIEWPORTS[2]};
        }
        return VIEWPORTS;
    }

    private void setViewport(WebDriver driver, Dimension viewport) {
        if (driver instanceof ChromiumDriver) {
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("width", viewport.getWidth());
            metrics.put("height", viewport.getHeight());
            metrics.put("deviceScaleFactor", 1);
            metrics.put("mobile", viewport.getWidth() <= 480);
            ((ChromiumDriver) driver).executeCdpCommand("Emulation.setDeviceMetricsOverride", metrics);
        } else {
            driver.manage().window().setSize(viewport);
        }
    }

    private void assertPageFits(WebDriver driver, String browser, String page, Dimension viewport) {
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        long viewportWidth = ((Number) javascript.executeScript("return window.innerWidth")).longValue();
        long pageWidth = ((Number) javascript.executeScript(
                "return Math.max(document.documentElement.scrollWidth, document.body.scrollWidth)"
        )).longValue();

        assertTrue(context(browser, page, viewport) + ": viewport no aplicado (" + viewportWidth + " px)",
                Math.abs(viewportWidth - viewport.getWidth()) <= 20);
        assertTrue(context(browser, page, viewport) + ": desbordamiento horizontal (" + pageWidth
                + " px > " + viewportWidth + " px)", pageWidth <= viewportWidth);
    }

    private void assertVisible(WebDriver driver, By locator, String browser, String page, Dimension viewport) {
        WebElement element = driver.findElement(locator);
        assertTrue(context(browser, page, viewport) + ": elemento no visible " + locator, element.isDisplayed());
    }

    private String context(String browser, String page, Dimension viewport) {
        return browser + " / " + page + " / " + viewport.getWidth() + "x" + viewport.getHeight();
    }
}