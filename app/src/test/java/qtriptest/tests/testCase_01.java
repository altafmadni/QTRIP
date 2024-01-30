package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_01{

    static WebDriver driver;
	public static void logStatus(String type, String message, String status) {
		System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	}

	// Iinitialize webdriver for our Unit Tests
	@BeforeSuite(alwaysRun = true, enabled = true)
	public static void createDriver() throws MalformedURLException {
		logStatus("driver", "Initializing driver", "Started");
		driver = DriverSingleton.getDriver();
		logStatus("driver", "Initializing driver", "Success");
	}

    @Test(priority=1, groups={"Login Flow"}, dataProvider = "Qtriptestdata",dataProviderClass = DP.class ,description = "TestCase01", enabled = true)
    public void TestCase01(String email, String password) throws InterruptedException, MalformedURLException{
		
		RegisterPage register = new RegisterPage(driver);
        register.navigatetoRegisterPage();
        Thread.sleep(3000);
        Assert.assertTrue(register.registerNewUser(email, password, true));
        String loginEmail = register.lastGeneratedEmail;
        LoginPage login = new LoginPage(driver);
        Thread.sleep(3000);
        Assert.assertTrue(login.loginUser(loginEmail,password));
		logStatus("driver", "New User login", "Success");
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.logout());
		logStatus("driver", "Logout user", "Success");

        
    }

}

