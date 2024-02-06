package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class testCase_02 {
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
    
    @Test(priority=2, groups={"Search and Filter flow"},dataProvider="Qtriptestdata",dataProviderClass = DP.class,description="Testcase02", enabled = true)
    public void TestCase02(String CityName, String Category_Filter, String DurationFilter, String ExpectedFilteredResults, String ExpectedUnFilteredResults) throws InterruptedException, IOException{
        //Navigate to home page and search for the CityName
        test = ReportSingleton.createTest("TestCase02");
        HomePage home = new HomePage(driver);
        home.navigatetoHomePage();
        Thread.sleep(2000);
        logStatus("driver", "Navigate to Homepage", "Success");

        boolean status = home.searchInvalidCity("Nagpur");
        ReportSingleton.testLogger(status, "CityName not found",driver);
        Thread.sleep(2000);

        status = home.searchCity(CityName);
        ReportSingleton.testLogger(status, "City found",driver);
        
        
        //head over to adventure page
        AdventurePage adventure = new AdventurePage(driver);
        adventure.filterByDuration(DurationFilter);
        status = adventure.verifyDataByDuration(DurationFilter);
        ReportSingleton.testLogger(status,"Duration filter verified",driver);

        adventure.filterByCategory(Category_Filter);
        status = adventure.verifyDataByCategory(Category_Filter);
        ReportSingleton.testLogger(status,"Category filter verified",driver);

        status = adventure.filteredResults(ExpectedFilteredResults);
        ReportSingleton.testLogger(status,"Filtered results verification",driver);
        
        adventure.clearDurationFilter();
        logStatus("driver", "Duration filter cleared", "success");
        adventure.clearCategoryFilter();
        logStatus("driver", "Category filter cleared", "success");
        
        status = adventure.unfilteredResults(ExpectedUnFilteredResults);
        ReportSingleton.testLogger(status,"Unfiltered results verification",driver);

		ReportSingleton.captureFinalScreenshot(driver);
        ReportSingleton.endTest();
    }
}

