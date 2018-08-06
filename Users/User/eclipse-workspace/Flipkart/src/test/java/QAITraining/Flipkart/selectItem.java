package QAITraining.Flipkart;

import java.util.List;

import org.apache.http.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class selectItem {
	public WebDriver driver;
	String parentHandle;
	//constructor to initialize driver
	selectItem(){
	driver=QAITraining.Flipkart.Login.driver;
}
@Test(groups="Itemchoose",alwaysRun=true,priority=6)
  public void serachItem() {
	  WebElement searchedtext =driver.findElement(By.cssSelector("input[name='q']"));
	  searchedtext.sendKeys("Samsung Galaxy On6");
	  searchedtext.sendKeys(Keys.ENTER);//to stimulate search
	  List<WebElement> itemInList =driver.findElements(By.xpath("//div[contains (text(),'Samsung Galaxy On6')]"));
	  Assert.assertTrue((itemInList.size()!=0));
	  //save parent window id
	  parentHandle = driver.getWindowHandle();
	  itemInList.get(0).click(); // click on first item in search result
		
  }
@Test(groups="Itemchoose", dependsOnMethods="serachItem" ,alwaysRun=false,priority=7)
  public void itemPageOpen() {
	 //this method check correct page open
	//this piece of code switch to newly open window
	for (String winHandle : driver.getWindowHandles()) {
	    driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
	}
	WebDriverWait webWait=new WebDriverWait(driver, 20);
	webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("*//h1/span[contains(text(),'Samsung Galaxy')]")));
	System.out.println(driver.getTitle());
	  Assert.assertTrue((driver.getTitle().contains("Samsung Galaxy On6")));
	  //Assert.assertTrue(Login.driver.findElement(By.xpath("//div[@class='_1HmYoV _35HD7C col-5-12 _3KsTU0']//button[@class='_2AkmmA _2Npkh4 _2MWPVK']")),"Correct item page opened");
  }
}
