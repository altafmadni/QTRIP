package qtriptest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class ReportSingleton {
    private static ExtentReports extentReports;
    private static ExtentTest test;

    private ReportSingleton() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extentReports == null) {
            String reportFilePath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "qtripTestReport.html";
            extentReports = new ExtentReports(reportFilePath);
        }
        return extentReports;
    }

    public static synchronized ExtentTest createTest(String testName) {
        test = extentReports.startTest(testName);
        return test;
    }

    public static void testLogger(boolean status, String description, WebDriver driver) throws IOException {
        if (status == false) {
            test.log(LogStatus.FAIL, description+" :Failed.");
            test.log(LogStatus.FAIL, test.addScreenCapture(capture(driver)));
        } else {
            test.log(LogStatus.PASS, description);
        }
    }

    public static void endTest() {
        extentReports.endTest(test);
    }

    public static synchronized void closeExtentReport() {
        if (extentReports != null) {
            extentReports.flush();
            extentReports.close();
        }
    }

    public static String capture(WebDriver driver) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(System.getProperty("user.dir") + "/reports/" + System.currentTimeMillis() + ".png");
        String errFilePath = destFile.getAbsolutePath();
        FileUtils.copyFile(srcFile, destFile);
        return errFilePath;
    }

    public static void captureFinalScreenshot(WebDriver driver) throws IOException {
        String finalScreenshotPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "finalScreenshot.png";
        File finalScreenshotFile = new File(finalScreenshotPath);
        FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), finalScreenshotFile);
        test.log(LogStatus.INFO, "Final Page Screenshot: " + test.addScreenCapture(finalScreenshotFile.getAbsolutePath()));
    }
}
