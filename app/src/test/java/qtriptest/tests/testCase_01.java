package qtriptest.tests;

// import qtriptest.BaseTest;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_01 {
	private static ExtentReports reports = ReportSingleton.getInstance();;
	private static ExtentTest test;
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
    public void TestCase01(String email, String password) throws InterruptedException, IOException{
		test = ReportSingleton.createTest("TestCase01");
		RegisterPage register = new RegisterPage(driver);
        register.navigatetoRegisterPage();
        Thread.sleep(3000);

       	boolean status= register.registerNewUser(email, password, true);
		ReportSingleton.testLogger(status, "New User registration", driver);
        String loginEmail = register.lastGeneratedEmail;
        LoginPage login = new LoginPage(driver);
        Thread.sleep(3000);

        status = login.loginUser(loginEmail,password);
		ReportSingleton.testLogger(status, "New User Login",driver);
        HomePage home = new HomePage(driver);
		
        status = home.logout();
		ReportSingleton.testLogger(status, "Logout user",driver);
		ReportSingleton.captureFinalScreenshot(driver);
		ReportSingleton.endTest();
    }

	@AfterSuite(alwaysRun = true, enabled = true)
	public static void closeDriver() throws MalformedURLException {
		ReportSingleton.closeExtentReport();
		driver.close();
		// driver.quit();
		logStatus("driver", "Quitting driver", "Success");
	}

}

