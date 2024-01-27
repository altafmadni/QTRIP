package qtriptest.tests;

import qtriptest.DP;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestCase_01 {

    static RemoteWebDriver driver;

    public static void logStatus(String type, String message, String status) {
		System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	}

    // Iinitialize webdriver for our Unit Tests
	@BeforeSuite(alwaysRun = true, enabled = true)
	public static void createDriver() throws MalformedURLException {
		logStatus("driver", "Initializing driver", "Started");
		final DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(BrowserType.CHROME);
		driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
		logStatus("driver", "Initializing driver", "Success");
	}

    @Test(dataProvider = "Qtriptestdata",dataProviderClass = DP.class ,description = "TestCase01", enabled = true)
    public void TestCase01(String email, String password) throws InterruptedException{

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

    @AfterSuite(enabled = true)
	public static void quitDriver() throws MalformedURLException {
		driver.close();
		driver.quit();
		logStatus("driver", "Quitting driver", "Success");
	}
}

