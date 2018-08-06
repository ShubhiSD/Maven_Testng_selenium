package QAITraining.Flipkart;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class checkOut {
	public WebDriver driver;
	checkOut(){
		driver=Login.driver;
	}
	
  @Test(priority=9,dependsOnGroups="Itemchoose",groups="cart")
  public void addtoCart() {
	  WebElement addCart=driver.findElement(By.xpath("//button[@class='_2AkmmA _2Npkh4 _2MWPVK']"));
	  addCart.click();
	  Assert.assertTrue((driver.getTitle().contains("Shopping Cart")));
  }
  @Test(priority=10,dependsOnGroups="Itemchoose",groups="cart")
  public void orderSummaryPage() {
	  WebElement placeOrder=driver.findElement(By.xpath("//*[text()='Place Order']/parent::button"));
	  placeOrder.click();
	
	  Assert.assertTrue((driver.findElement(By.xpath("//*[contains(text(),'Order Summary')]"))).isDisplayed());;
	  
  }
  @Test(priority=11,dependsOnGroups="Itemchoose",groups="cart",dependsOnMethods="orderSummaryPage")
  public void orderPayment() {
	  List<WebElement> dynamicElement =driver.findElements(By.xpath("//button"));
	  System.out.println("size "+dynamicElement.size());
	  int i=-1;
		while(true)  
		{ ++i;
			System.out.println("button "+dynamicElement.get(i).getText());
			if(dynamicElement.get(i).getText().contains("PAY"))
				{Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'PAY')]")).isDisplayed());
				
				break;}
			else
				if(dynamicElement.get(i).getText().contains("DELIVER") || dynamicElement.get(i).getText().contains("CONTINUE"))
				{
					if(dynamicElement.get(i).getText().contains("CONTINUE"))
					{
						List<WebElement> cvv =driver.findElements(By.xpath("//label[text()='CVV']"));
						if(cvv.size()!=0)
						{System.out.println("Going to break-continue");
						Assert.assertTrue(driver.findElement(By.xpath("//label[text()='CVV']")).isDisplayed());
							break;}
					}
					
					dynamicElement.get(i).click();
					dynamicElement =driver.findElements(By.xpath("//button"));
					i=-1;
				}
		}System.out.println("came out of  break");
		
	 
  }
}
