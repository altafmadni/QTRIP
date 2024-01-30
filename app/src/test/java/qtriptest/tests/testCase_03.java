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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_03 {
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
    
    @Test(priority=3, groups={"Booking and Cancellation Flow"},dataProvider="Qtriptestdata",dataProviderClass = DP.class,description="Testcase03", enabled = true )
    public void TestCase03(String email, String password, String city, String adventureName, String guestName, String Date, String count) throws InterruptedException{
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
        Assert.assertTrue(home.searchCity(city));
        logStatus("driver", "City found", "success");
        
        //head over to adventure detail page
        AdventurePage adventure = new AdventurePage(driver);
        adventure.searchAdventure(adventureName);
        AdventureDetailsPage adDetail = new AdventureDetailsPage(driver);
        adDetail.enterReservationDetails(guestName, Date, count);
        logStatus("driver", "Entering reservation details", "success");
        Assert.assertTrue(adDetail.verifyReservation());
        logStatus("driver", "Reservation success message is displayed.", "success");
        adDetail.navigateToHistoryPage();
        logStatus("driver", "Navigating to history page", "success");
        
        HistoryPage history = new HistoryPage(driver);
        history.getTransactionID();
        history.cancelReservation();
        logStatus("driver", "Canceling Reservation", "success");
        Assert.assertFalse(history.verifyReservationExistence());
        logStatus("driver", "Transaction ID not found", "success");
    }

}
