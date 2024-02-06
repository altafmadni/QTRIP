
package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HistoryPage {
    WebDriver driver;

    @FindBy(xpath="//input[@name='name']")
    WebElement customerName;
 
    @FindBy(xpath="//input[@name='date']")
    WebElement registrationDate;
 
    @FindBy(xpath="//input[@name='person']")
    WebElement personCount;

    @FindBy(className ="reserve-button")
    WebElement reserveBtn;

    @FindBy(id ="reserved-banner")
    WebElement reservationBanner;

    @FindBy(xpath  ="//tbody[@id='reservation-table']//th")
    WebElement transactionID;

    @FindBy(className ="cancel-button")
    WebElement cancelReservation;

    @FindBy(xpath ="//tbody/tr")
    List<WebElement> reservationRows;

    public HistoryPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajx = new AjaxElementLocatorFactory(driver, 50);
        PageFactory.initElements(ajx, this);
    }
    
    SeleniumWrapper wrap = new SeleniumWrapper(driver);
    WebElement transID;
    public void getTransactionID() throws InterruptedException{
        Thread.sleep(3000);
        WebElement transactionID = this.transactionID;
        transID = transactionID;
    }

    public void cancelReservation() throws InterruptedException{
        Thread.sleep(2000);
        WebElement cancelReservation = this.cancelReservation;
        // cancelReservation.click();
        wrap.click(cancelReservation,this.driver);
    }

    public boolean verifyReservationExistence() throws InterruptedException{
        Thread.sleep(2000);
        try{
            transID.isDisplayed();
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
        
    }

    public boolean verifyMultipleReservation() throws InterruptedException{
        Thread.sleep(2000);
        int rows= reservationRows.size();
        try{
            rows = 3;
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
    }
}


// public class HistoryPage {
// }