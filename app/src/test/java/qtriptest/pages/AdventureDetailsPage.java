package qtriptest.pages;


import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {
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

    @FindBy(xpath ="//a[text()='Reservations']")
    WebElement reservationPageLink;
    
    public AdventureDetailsPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajx = new AjaxElementLocatorFactory(driver, 50);
        PageFactory.initElements(ajx, this);
    }

    SeleniumWrapper wrap = new SeleniumWrapper(driver);
    public void enterReservationDetails(String name, String date, String count) throws InterruptedException{
        Thread.sleep(2000);
        WebElement customerName = this.customerName;
        // customerName.sendKeys(name);
        wrap.sendKeys(customerName, name);

        WebElement registrationDate = this.registrationDate;
        // registrationDate.sendKeys(date);
        wrap.sendKeys(registrationDate, date);

        WebElement personCount = this.personCount;
        // personCount.clear();
        // personCount.sendKeys(count);
        wrap.sendKeys(personCount, count);

        WebElement reserveBtn = this.reserveBtn;
        // reserveBtn.click();
        wrap.click(reserveBtn,this.driver);
    }

    public boolean verifyReservation() throws InterruptedException{
        Thread.sleep(4000);
        WebElement reservationBanner = this.reservationBanner;
        
        if(reservationBanner.isDisplayed()){
            return true;
        }
        return false;
    }

    public void navigateToHistoryPage() throws InterruptedException{
        Thread.sleep(2000);
        WebElement reservationPageLink = this.reservationPageLink;
        // reservationPageLink.click(reservationPageLink);
        wrap.click(reservationPageLink,this.driver);
    }
}