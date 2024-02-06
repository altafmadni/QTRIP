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
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_04 {
    private static ExtentReports reports;
	private ExtentTest test;
    static WebDriver driver;

    
    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    // Iinitialize webdriver for our Unit Tests
    @BeforeTest(alwaysRun = true, enabled = true)
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        driver = DriverSingleton.getDriver();
        reports = ReportSingleton.getInstance();
        logStatus("driver", "Initializing driver", "Success");
    }

    @Test(priority=4, groups={"Reliability Flow"},dataProvider="Qtriptestdata",dataProviderClass = DP.class,description="Testcase04", enabled = true )
    public void TestCase04(String email, String password, String dataset, String dataset1, String dataset2) throws InterruptedException, IOException{
        String[] datasetValues = dataset.split(";");
        String[] datasetValues1 = dataset1.split(";");
        String[] datasetValues2 = dataset2.split(";");

        test = ReportSingleton.createTest("TestCase04");
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

        //headover to homepage
        HomePage home = new HomePage(driver);
        Thread.sleep(2000);
        status = home.searchCity(datasetValues[0]);
        ReportSingleton.testLogger(status, "City found", driver);
        
        //Head over to adventure detail page
        AdventurePage adventure = new AdventurePage(driver);
        adventure.searchAdventure(datasetValues[1]);

        //adventure detail page
        AdventureDetailsPage adDetail = new AdventureDetailsPage(driver);
        adDetail.enterReservationDetails(datasetValues[2], datasetValues[3], datasetValues[4]);
        test.log(LogStatus.PASS, "Entering reservation details");
        // logStatus("driver", "Entering reservation details", "success");
        
        status = adDetail.verifyReservation();
        ReportSingleton.testLogger(status, "Reservation success message is displayed.", driver);
        // logStatus("driver", "Reservation success message is displayed.", "success");

        //repeat above process 2 more times
        home.navigatetoHomePage();
        Thread.sleep(2000);

        status = home.searchCity((datasetValues1[0]));
        ReportSingleton.testLogger(status, "City found", driver);
        // logStatus("driver", "City found", "success");
        
        adventure.searchAdventure(datasetValues1[1]);
        adDetail.enterReservationDetails(datasetValues1[2], datasetValues1[3], datasetValues1[4]);
        test.log(LogStatus.PASS, "Entering reservation details");
        // logStatus("driver", "Entering reservation details", "success");

        status = adDetail.verifyReservation();
        ReportSingleton.testLogger(status, "Reservation success message is displayed.", driver);
        // logStatus("driver", "Reservation success message is displayed.", "success");

        home.navigatetoHomePage();
        Thread.sleep(2000);

        status = home.searchCity((datasetValues2[0]));
        ReportSingleton.testLogger(status, "City found", driver);
        // logStatus("driver", "City found", "success");

        adventure.searchAdventure(datasetValues2[1]);
        adDetail.enterReservationDetails(datasetValues2[2], datasetValues2[3], datasetValues2[4]);
        test.log(LogStatus.PASS, "Entering reservation details");
        // logStatus("driver", "Entering reservation details", "success");

        status = adDetail.verifyReservation();
        ReportSingleton.testLogger(status, "Reservation success message is displayed.", driver);

        adDetail.navigateToHistoryPage();
        // logStatus("driver", "Navigating to history page", "success");
        test.log(LogStatus.PASS, "Navigating to history page");

        HistoryPage history = new HistoryPage(driver);
        history.verifyMultipleReservation();
    }

    // @AfterSuite
	// public void quitDriver() throws MalformedURLException {
	// 	driver.close();
	// 	// driver.quit();
	// 	logStatus("driver", "Quitting driver", "Success");
	// }

}

