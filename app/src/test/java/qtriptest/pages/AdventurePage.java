package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

public class AdventurePage {
   WebDriver driver;
   String URL = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/";
   
   @FindBy(id="duration-select")
   WebElement durationDropdown;

   @FindBy(xpath="//h5[text()='Duration']/../p")
   List<WebElement> dataDuration;

   @FindBy(xpath="//div[@class='activity-card']")
   List<WebElement> activityCards;

   @FindBy(id="category-select")
   WebElement categoryDropdown;

   @FindBy(id="category-banner")
   List<WebElement> categoryBanner;

   @FindBy(xpath="//div[@onclick='clearDuration(event)']")
   WebElement clearDurationFilter;

   @FindBy(xpath="//div[@onclick='clearCategory(event)']")
   WebElement clearCategoryFilter;

   @FindBy(id="search-adventures")
   WebElement searchAdventure;

   @FindBy(xpath="(//div[@class='activity-card']//h5)[1]")
   WebElement adventureName;

   
   public AdventurePage(WebDriver driver2){
      this.driver = driver2;
      AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver2, 50);
      PageFactory.initElements(ajax, this);
   }

   public void navigateToAdventurePage(){
      if(driver.getCurrentUrl() != URL){
         driver.get(URL);
      }
   }

   public void filterByDuration(String hours) throws InterruptedException{
      Thread.sleep(2000); 
      WebElement durationDropdown = this.durationDropdown;
      durationDropdown.click();
      Select duration = new Select(durationDropdown);
      duration.selectByVisibleText(hours);
        
   }

   public boolean verifyDataByDuration(String duration) throws InterruptedException{
      Thread.sleep(2000); 
      List<WebElement> data = dataDuration;

      for(WebElement hours : data){
         if(duration.contains(hours.getText())){
            return true;
         }
      }
      return false;
   }

   public void clearDurationFilter() throws InterruptedException{
      Thread.sleep(2000); 
      WebElement clearDurationFilter = this.clearDurationFilter;
      clearDurationFilter.click();
      
   }

   
   public void filterByCategory(String category) throws InterruptedException{
      Thread.sleep(2000); 
      WebElement categoryDropdown = this.categoryDropdown;
      categoryDropdown.click();
      Select selectCategory = new Select(categoryDropdown);
      selectCategory.selectByVisibleText(category);
        
   }

   public boolean verifyDataByCategory(String category) throws InterruptedException{
      Thread.sleep(2000);
      List<WebElement> data = categoryBanner;
      for(WebElement fetchedCategory : data){
         if(fetchedCategory.getText().contains(category) ){
            System.out.println(fetchedCategory);
            return true;
         }
      }
      return false;
   }
   
   public void clearCategoryFilter() throws InterruptedException{
      Thread.sleep(2000); 
      WebElement clearCategoryFilter = this.clearCategoryFilter;
      clearCategoryFilter.click();
   }

   public boolean unfilteredResults(String unfilteredResult) throws InterruptedException{
      Thread.sleep(2000); 
      List<WebElement> acivityCards = this.activityCards;
      int resultSize = acivityCards.size();
      int unfiltered = Integer.parseInt(unfilteredResult);
      if(resultSize != unfiltered){
         return false;
      }
      return true;
   }


   public boolean filteredResults(String filteredResult) throws InterruptedException{
      Thread.sleep(2000); 
      List<WebElement> acivityCards = this.activityCards;
      int resultSize = acivityCards.size();
      int filtered = Integer.parseInt(filteredResult);
      if(resultSize != filtered){
         return false;
      }
      return true;
   }

   // @FindBy()
   
   
   public void searchAdventure(String adventureName) throws InterruptedException{
      Thread.sleep(2000);
      WebElement adventure = searchAdventure;
      adventure.sendKeys(adventureName);
      Thread.sleep(2000);
      Actions act = new Actions(driver);
      act.moveToElement(this.adventureName).click().perform();
      
   }


}