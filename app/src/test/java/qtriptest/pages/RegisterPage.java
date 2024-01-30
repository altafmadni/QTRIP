package qtriptest.pages;

import java.util.UUID;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegisterPage {
    WebDriver driver;
    String URL = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    @FindBy(id="floatingInput")
    WebElement emailTextbox;

    @FindBy(xpath="//input[@name='password']")
    WebElement passwordTextbox;

    @FindBy(xpath="//input[@name='confirmpassword']")
    WebElement confirmPasswordTextbox;
    
    @FindBy(xpath="//button[text()='Register Now']")
    WebElement registerNowButton;

    public RegisterPage(WebDriver driver2){
        this.driver = driver2;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver2, 50);
        PageFactory.initElements(ajax, this);
    }

    public void logStatus(String type, String message, String status) {
		System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	}

    public void navigatetoRegisterPage(){
        if(driver.getCurrentUrl() != URL){
        driver.get(URL);
        }
        driver.manage().window().maximize();
        logStatus("driver", "Navigate to register page", "Success");
    }

    public String lastGeneratedEmail;
    public boolean registerNewUser(String username, String password, boolean generateRandomUsername ) throws InterruptedException{
        if (generateRandomUsername){
         username = username+UUID.randomUUID().toString();
        }
        
        lastGeneratedEmail = username;
        System.out.println("Last generated dynamic username"+lastGeneratedEmail);
        
        WebElement usernameField = emailTextbox;
        usernameField.sendKeys(username);

        WebElement passwordField = passwordTextbox;
        passwordField.sendKeys(password);

        
        WebElement confirmPasswordField = confirmPasswordTextbox;
        confirmPasswordField.sendKeys(password);

        WebElement registerNow = registerNowButton;
        registerNow.click();
        
        Thread.sleep(2000);

        String loginURL = driver.getCurrentUrl();

        if(loginURL == URL){
            return false;
        }
        logStatus("driver", "New user registeration", "Success");
        return true;
    }
    
}
