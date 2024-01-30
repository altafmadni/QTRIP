package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class testCase_02 {
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
    
    @Test(priority=2, groups={"Search and Filter flow"},dataProvider="Qtriptestdata",dataProviderClass = DP.class,description="Testcase02", enabled = true)
    public void TestCase02(String CityName, String Category_Filter, String DurationFilter, String ExpectedFilteredResults, String ExpectedUnFilteredResults) throws InterruptedException, MalformedURLException{
        //Navigate to home page and search for the CityName
        HomePage home = new HomePage(driver);
        home.navigatetoHomePage();
        Thread.sleep(2000);
        logStatus("driver", "Navigate to Homepage", "success");
        Assert.assertTrue(home.searchInvalidCity("Nagpur"));
        Thread.sleep(2000);
        logStatus("driver", "CityName not found", "success");
        Assert.assertTrue(home.searchCity(CityName));
        logStatus("driver", "City found", "success");
        
        //head over to adventure page
        AdventurePage adventure = new AdventurePage(driver);
        adventure.filterByDuration(DurationFilter);
        adventure.verifyDataByDuration(DurationFilter);
        logStatus("driver", "Duration filter verified", "success");
        adventure.filterByCategory(Category_Filter);
        adventure.verifyDataByCategory(Category_Filter);
        logStatus("driver", "Category filter verified", "success");
        Assert.assertTrue(adventure.filteredResults(ExpectedFilteredResults));
        logStatus("driver", "Filtered results verification", "success");
        adventure.clearDurationFilter();
        logStatus("driver", "Duration filter cleared", "success");
        adventure.clearCategoryFilter();
        logStatus("driver", "Category filter cleared", "success");
        Assert.assertTrue(adventure.unfilteredResults(ExpectedUnFilteredResults));
        logStatus("driver", "Unfiltered results verification", "success");


    }

}
