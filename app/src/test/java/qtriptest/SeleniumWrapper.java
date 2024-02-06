package qtriptest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {
    WebDriver driver;

    public SeleniumWrapper(WebDriver driver){
        this.driver = driver;
    }

    public boolean click(WebElement element, WebDriver driver) {
        try {
            Thread.sleep(2000);
            if (element != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
                return true;
            } else {
                System.out.println("Element not found");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return false;
        }
    }

    public void sendKeys(WebElement element, String keys) {
        try {
            element.clear();
            element.sendKeys(keys);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void navigateToUrl(String url, WebDriver driver) {
        if(driver.getCurrentUrl() != url){
            driver.get(url);
        }
    }

    public WebElement findElementWithRetry(By by, int maxRetry) {
        int retry = 0;
        WebElement element = null;
        while (retry < maxRetry) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 30);
                element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
                return element;
            } catch (NoSuchElementException e) {
                System.out.println("Element not found. Retrying...");
                retry++;
                try {
                    Thread.sleep(1000); // Wait before retrying
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("Element not found after retries");
        return null;
    }
}
