package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_03 {
    private static ExtentReports reports;
	private ExtentTest test;
    static WebDriver driver;    

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    // // Iinitialize webdriver for our Unit Tests
    @BeforeTest(alwaysRun = true, enabled = true)
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        driver = DriverSingleton.getDriver();
        reports = ReportSingleton.getInstance();
        logStatus("driver", "Initializing driver", "Success");
    }
    
    @Test(priority=3, groups={"Booking and Cancellation Flow"},dataProvider="Qtriptestdata",dataProviderClass = DP.class,description="Testcase03", enabled = true )
    public void TestCase03(String email, String password, String city, String adventureName, String guestName, String Date, String count) throws InterruptedException, IOException{
        test = ReportSingleton.createTest("TestCase03");
        RegisterPage register = new RegisterPage(driver);
        register.navigatetoRegisterPage();
        Thread.sleep(3000);

        boolean status = register.registerNewUser(email, password, true);
        ReportSingleton.testLogger(status, "New User registration ", driver);
        
        String loginEmail = register.lastGeneratedEmail;
        LoginPage login = new LoginPage(driver);
        Thread.sleep(3000);
       
        status = login.loginUser(loginEmail,password);
        ReportSingleton.testLogger(status, "New User login", driver);

        HomePage home = new HomePage(driver);
        Thread.sleep(2000);
        status = home.searchCity(city);
        ReportSingleton.testLogger(status, "City found.", driver);
        
        //head over to adventure detail page
        AdventurePage adventure = new AdventurePage(driver);
        adventure.searchAdventure(adventureName);
        AdventureDetailsPage adDetail = new AdventureDetailsPage(driver);
        adDetail.enterReservationDetails(guestName, Date, count);

        status = adDetail.verifyReservation();
        ReportSingleton.testLogger(status, "Reservation verified.", driver);
        adDetail.navigateToHistoryPage();
        
        HistoryPage history = new HistoryPage(driver);
        history.getTransactionID();
        history.cancelReservation();
        status = history.verifyReservationExistence();
        ReportSingleton.testLogger(!status, "Existence of reservation log verified.", driver);

		ReportSingleton.captureFinalScreenshot(driver);
        ReportSingleton.endTest();
    }
}

