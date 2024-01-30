package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {
    public static WebDriver driver;

    private  DriverSingleton(){
        //private constructor to prevent instantiation
    }

    public static WebDriver getDriver(){
        if(driver == null){
            synchronized (DriverSingleton.class){
                if(driver == null){
                    try{
                        final DesiredCapabilities capabilities = new DesiredCapabilities();
                        capabilities.setBrowserName(BrowserType.CHROME);
                        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
                    }catch(MalformedURLException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return driver;
    }
}



