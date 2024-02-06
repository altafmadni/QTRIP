package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage {
    WebDriver driver;
    String URL = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

    @FindBy(id="floatingInput")
    WebElement emailTextbox;

    @FindBy(id="floatingPassword")
    WebElement passwordTextbox;
    
    @FindBy(xpath="//button[@type='submit']")
    WebElement loginButton;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 50);
        PageFactory.initElements(ajax, this);
    }


    
    SeleniumWrapper wrap = new SeleniumWrapper(driver);
    public void navigatetoLoginPage(){
        // if(driver.getCurrentUrl() != URL){
        //     driver.get(URL);
        // }
        wrap.navigateToUrl(URL, this.driver);
    }

    public boolean loginUser(String email, String password) throws InterruptedException{
        WebElement inputEmail = emailTextbox;
        // inputEmail.sendKeys(email);
        wrap.sendKeys(inputEmail, email);

        WebElement inputPassword = passwordTextbox;
        // inputPassword.sendKeys(password);
        wrap.sendKeys(inputPassword, password);
        
        Thread.sleep(2000);
        WebElement clickLogin = loginButton;
        // clickLogin.click();
        wrap.click(clickLogin,this.driver);

        Thread.sleep(2000);

        if(driver.getCurrentUrl() == URL){
            return false;
        }

        return true;
    }

}
