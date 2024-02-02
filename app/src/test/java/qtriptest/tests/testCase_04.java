package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_04 {
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
        logStatus("driver", "Initializing driver", "Success");
    }

    @Test(priority=4, groups={"Reliability Flow"},dataProvider="Qtriptestdata",dataProviderClass = DP.class,description="Testcase04", enabled = true )
    public void TestCase04(String email, String password, String dataset, String dataset1, String dataset2) throws InterruptedException{
        String[] datasetValues = dataset.split(";");
        String[] datasetValues1 = dataset1.split(";");
        String[] datasetValues2 = dataset2.split(";");


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
        Thread.sleep(2000);
        Assert.assertTrue(home.searchCity(datasetValues[0]));
        logStatus("driver", "City found", "success");
        
        //Head over to adventure detail page
        AdventurePage adventure = new AdventurePage(driver);
        adventure.searchAdventure(datasetValues[1]);
        AdventureDetailsPage adDetail = new AdventureDetailsPage(driver);
        adDetail.enterReservationDetails(datasetValues[2], datasetValues[3], datasetValues[4]);
        logStatus("driver", "Entering reservation details", "success");
        Assert.assertTrue(adDetail.verifyReservation());
        logStatus("driver", "Reservation success message is displayed.", "success");

        home.navigatetoHomePage();
        Thread.sleep(2000);
        Assert.assertTrue(home.searchCity(datasetValues1[0]));
        logStatus("driver", "City found", "success");
        adventure.searchAdventure(datasetValues1[1]);
        adDetail.enterReservationDetails(datasetValues1[2], datasetValues1[3], datasetValues1[4]);
        logStatus("driver", "Entering reservation details", "success");
        Assert.assertTrue(adDetail.verifyReservation());
        logStatus("driver", "Reservation success message is displayed.", "success");

        home.navigatetoHomePage();
        Thread.sleep(2000);
        Assert.assertTrue(home.searchCity(datasetValues2[0]));
        logStatus("driver", "City found", "success");
        adventure.searchAdventure(datasetValues2[1]);
        adDetail.enterReservationDetails(datasetValues2[2], datasetValues2[3], datasetValues2[4]);
        logStatus("driver", "Entering reservation details", "success");
        Assert.assertTrue(adDetail.verifyReservation());
        logStatus("driver", "Reservation success message is displayed.", "success");


        adDetail.navigateToHistoryPage();
        logStatus("driver", "Navigating to history page", "success");
        HistoryPage history = new HistoryPage(driver);
        history.verifyMultipleReservation();
        
    }

    @AfterSuite
	public void quitDriver() throws MalformedURLException {
		driver.close();
		// driver.quit();
		logStatus("driver", "Quitting driver", "Success");
	}
}
