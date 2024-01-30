package qtriptest.pages;

import org.openqa.selenium.Keys;
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

    @FindBy(xpath="//a[text()='Login Here']")
    WebElement loginHereBtn;

    @FindBy(id="autocomplete")
    WebElement searchBox;

    @FindBy(xpath="//ul[@id='results']/h5")
    WebElement autoSuggestion;

    @FindBy(xpath="//ul[@id='results']//li")
    WebElement searchedCity;

    
    public HomePage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 50);
        PageFactory.initElements(ajax, this);
    }

    public void navigatetoHomePage(){
        
        if(driver.getCurrentUrl() != URL){
            driver.get(URL);
        }
        driver.manage().window().maximize();
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

    public boolean searchCity(String cityName) throws InterruptedException{
        searchBox.clear();
        WebElement searchBox = this.searchBox;
        searchBox.sendKeys(cityName);
        Thread.sleep(2000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        
        System.out.println(searchedCity.getText()+" city is found.");
        searchedCity.click();
        return true;      
    }

    public boolean searchInvalidCity(String cityName) throws InterruptedException{
        searchBox.clear();
        WebElement searchBox = this.searchBox;
        searchBox.sendKeys(cityName);
        Thread.sleep(2000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        
        WebElement autoSuggestion = this.autoSuggestion;
        String noRecords = autoSuggestion.getText();
        
        if(noRecords == "No City found"){
            System.out.println(noRecords);
        }
        return true;

    }

}
