package QAITraining.Flipkart;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Login {
	public static WebDriver driver=null;
  //constructor
	Login()
	{
		System.setProperty("webdriver.firefox.marionette","C:\\geckodriver.exe"); 
	  //create new instance of FireFoxDriver
	  driver=new FirefoxDriver();
	 }
	@BeforeSuite(alwaysRun=true)
  public void driverConnection() {
	  
	  // Implicit wait
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  //open the url
	  driver.get("http://www.flipkart.com");
  }
  
  @AfterSuite(alwaysRun=true)
  public void closeConnection() {
	//Close the browser
     driver.quit();
  }
  
  @BeforeTest(alwaysRun=true)
  public void openLoginWindows() {
	//This method will execute before test and ensure if login window is not open then it opens it
	//if login window not displayed by class for username field
	  if(!(driver.findElement(By.cssSelector("._2zrpKA")).isDisplayed()))
		{
		  if(driver.findElement(By.xpath("//div[text()='My Account']")).isDisplayed())
			  logout();//if already logged in then first logout 
		  //launch login screen from main flipkart page
		  WebElement loginBttn=driver.findElement(By.xpath("//a[text()= 'Login & Signup']"));
		  loginBttn.click();
		  
		}
  }
 
  @Test(priority=1,groups="registration")
  public void resgisterUser() {
	  //find any string contain SignUp and then find parent node of the string which is link to registration page
	  WebElement signUpBttn = driver.findElement(By.xpath("//*[contains(text(),'Sign up')]/parent::a"));		
	  signUpBttn.click();
	  Assert.assertTrue((driver.findElement(By.xpath("//*[contains(text(),'not share your personal')]"))).isDisplayed(),"Registration window open");
	}
  //don't run this test of user has not passed resgisterUser test because user is not in registration screen
@Test(dependsOnMethods= {"resgisterUser"},alwaysRun=false,groups="registration", priority=2)
  public void registerUserAlreadyExist() {
	  WebElement usernamElement = driver.findElement(By.cssSelector("._2zrpKA"));
	  usernamElement.sendKeys("9004676496");
	  WebElement continueBttn = driver.findElement(By.xpath("//*[text()='CONTINUE']/parent::button"));		
	  continueBttn.click();
	  Assert.assertTrue((driver.findElement(By.xpath("//*[contains(text(),'You are already registered')]"))).isDisplayed(),"Already regsiter Testcase passed with correct notification");
  }
@Test(dependsOnMethods= {"resgisterUser"},alwaysRun=false,groups="registration",priority=3)
public void registerNewUser() {
	  WebElement usernamElement = driver.findElement(By.cssSelector("._2zrpKA"));
	  usernamElement.clear();
	  usernamElement.sendKeys("9004676400");
	  WebElement continueBttn = driver.findElement(By.xpath("//*[text()='CONTINUE']/parent::button"));		
	  continueBttn.click();
	  Assert.assertTrue((driver.findElement(By.xpath("//*[contains(text(),'OTP sent to Mobile')]"))).isDisplayed(),"New regsiter Testcase passed");
	
	  //closeRegistrationWindow();
}

@AfterGroups(groups="registration",alwaysRun=true)
public void closeRegistrationWindow() {
	//switch to login window 
	Set <String> handles = driver.getWindowHandles();
	String  WindowHandlerID = handles.iterator().next();
	 driver.switchTo().window(WindowHandlerID);
	//click on close button after switching to login window
	 WebElement closeBttn=driver.findElement(By.cssSelector("button._2AkmmA._29YdH8"));
	closeBttn.click();
}
  @Test(priority=4,groups="login")
  public void checkLogin_email() {
	  login("shubhi.shukla.07@gmail.com");
	  Assert.assertTrue((driver.findElement(By.xpath("//div[text()='My Account']"))).isDisplayed(), "Login through email id is successful");
	 // logout();
  }
  /*@Test(priority=5,groups="login")
  public void checkLogin_mobile() {
	  login("9004676496");
	  Assert.assertTrue((driver.findElement(By.xpath("//div[text()='My Account']"))).isDisplayed(), "Login through mobile id is successful");
  }*/
  public void login(String username) {
		//find element by CSS selector by class for username field
		//if(!(driver.findElement(By.cssSelector("._2zrpKA")).isDisplayed()))
	  //above will give error because this element dont exist so ca  check it is displayed or not
	  //findelements dont throw exception and return list or empty list
		List<WebElement> dynamicElement =driver.findElements(By.cssSelector("._2zrpKA"));
			 
		if(dynamicElement.size()==0)
		{//click on login&singup in main window
			WebElement loginBttn=driver.findElement(By.xpath("//a[text()= 'Login & Signup']"));
			loginBttn.click();
		}
		WebElement usernamElement = driver.findElement(By.cssSelector("._2zrpKA"));
		//enter the text
		usernamElement.sendKeys(username);	
		WebElement psswd = driver.findElement(By.cssSelector("input[type='password']"));
		//enter the text
		psswd.sendKeys("aashi@143");
		WebElement bttn = driver.findElement(By.xpath("//*[text()='Login']/parent::button"));
		bttn.submit();
		//if sometime button not clickable we do by action class
		/*System.out.println(bttn.isEnabled());
		Actions action = new Actions(driver);
		action.moveToElement(bttn).click().perform();*/
		
		}
	  public void logout() {
		  WebElement myAccount = driver.findElement(By.xpath("//div[text()='My Account']"));		
		  myAccount.click();
		  WebElement logOut = driver.findElement(By.xpath("//div[text()='Logout']"));		
		  logOut.click();

	  }
}
