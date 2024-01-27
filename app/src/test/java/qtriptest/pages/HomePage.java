package qtriptest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    WebDriver driver;
    String URL = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(xpath="//a[text()='Register']")
    WebElement registerButton;

    @FindBy(xpath="//div[@onclick='Logout()']")
    WebElement logoutButton;
    //a[text()='Login Here']

    @FindBy(xpath="//a[text()='Login Here']")
    WebElement loginHereBtn;

    public HomePage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 30);
        PageFactory.initElements(ajax, this);
    }

    public void navigatetoHomePage(){
        if(driver.getCurrentUrl() != URL){
            driver.get(URL);
        }
    }

    public boolean logout() throws InterruptedException{
        Thread.sleep(2000);
        logoutButton.click();
        
        if(loginHereBtn.isDisplayed()){
            return true;
        }else{
            return false;
        }
    }

}
