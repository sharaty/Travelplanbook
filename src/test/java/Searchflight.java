import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class Searchflight {
	protected WebDriver driver;
	private WebDriverWait wait;

	@Test(priority = 1)
	public void launchHomePage() {
		System.setProperty("webdriver.chrome.driver", "./software/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://groupy.live/travelplanbooker.com");
		Assert.assertEquals(driver.getTitle(), "TravelPlanBooker");
	}

	@Test(priority = 2)
	public void selectFlightDetails() throws InterruptedException, AWTException {
		driver.findElement(By.xpath("//input[@id='airport_from']")).sendKeys("Mumbai");
		driver.findElement(By.xpath("//input[@id='airport_to']")).sendKeys("Dubai");
		Thread.sleep(2000);
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}

	@Test(priority = 3)
	public void selectDatesAndPassengers() throws InterruptedException {
		driver.findElement(By.id("flight-dates")).click();
		driver.findElement(By.xpath("(//td[text()='23'])[1]")).click();
		driver.findElement(By.xpath("(//td[text()='26'])[1]")).click();

		driver.findElement(By.id("f-guests")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//button[@class='plus_btn btn-plus1'])[4]")).click();
		WebElement done = driver.findElement(By.xpath("//button[text()='Done']"));
		wait.until(ExpectedConditions.elementToBeClickable(done)).click();

	}

	@Test(priority = 4)
	public void searchFlightsAndValidateResults() throws IOException {
		driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();

		try {
			Thread.sleep(3000);
			WebElement noResultsMsg = driver.findElement(By.xpath("//strong[contains(text(),'Please try selecting different dates or different locations')]"));

			if (noResultsMsg.isDisplayed()) {
				System.out.println(" No results found — capturing screenshot...");
				takeScreenshot("NoResultsFound.png");
				Assert.fail("No results were found. Failing the test.");
			}
		} catch (Exception e) {
			System.out.println(" Flights found or error element not displayed.");
		}
	}

	@Test(priority = 5)
	public void onewayserach() throws IOException, InterruptedException {
		driver.findElement(By.xpath("//label[normalize-space()='One Way']")).click();
		driver.findElement(By.id("flight-dates2")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//a[text()='23'])[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();

		try {
			Thread.sleep(3000);
			WebElement noResultsMsg = driver.findElement(By.xpath("//strong[contains(text(),'Please try selecting different dates or different locations')]"));

			if (noResultsMsg.isDisplayed()) {


				System.out.println(" No results found — capturing screenshot...");
				takeScreenshot("NoResultsFound.png");
				Assert.fail("No results were found. Failing the test.");
			}
		} catch (Exception e) {
			System.out.println(" Flights found or error element not displayed.");
		}
	}


	@Test(priority = 6)
	public void multiCityFlightSearch() throws InterruptedException, AWTException, IOException {
		//driver.get("https://groupy.live/travelplanbooker.com");

		// Select Multi-City tab
		driver.findElement(By.xpath("//label[text()='Multi City']")).click();

		// Enter first flight leg
		WebElement from1 = driver.findElement(By.xpath("(//input[@id='airport_from'])[1]"));
		from1.clear();
		from1.sendKeys("Mumbai");
		Thread.sleep(1000);
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

		WebElement to1 = driver.findElement(By.xpath("(//input[@id='airport_to'])[1]"));
		to1.clear();
		to1.sendKeys("Dubai");
		Thread.sleep(1000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

		driver.findElement(By.xpath("//input([@placeholder='From: Destination/Station'])[2]")).click();//input[@placeholder='From: Destination/Station'])[2]
		driver.findElement(By.xpath("//input([@placeholder='From: Destination/Station'])[2]")).sendKeys("Dubai");
		Thread.sleep(1000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

		WebElement to2 = driver.findElement(By.xpath("//input[@name='airport_to_m[]'])[2]"));
		to2.sendKeys("Mumbai");
		Thread.sleep(1000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		// Set first date
		driver.findElement(By.id("flight-dates2")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//a[text()='23'])[1]")).click();
		driver.findElement(By.xpath("input[@id='dp1752914754142']")).click();
		// Enter second flight leg
		WebElement from21 = driver.findElement(By.xpath("(//input[@id='airport_from'])[2]"));
		from21.clear();
		from21.sendKeys("Mumbai");
		Thread.sleep(1000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

		WebElement to21 = driver.findElement(By.xpath("(//input[@id='airport_to'])[2]"));
		to21.clear();
		to21.sendKeys("Dubai");
		Thread.sleep(1000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

		// Set second date
		WebElement date2 = driver.findElement(By.xpath("(//input[contains(@placeholder,'Select')])[2]"));
		date2.click();
		driver.findElement(By.xpath("//td[text()='24']")).click();

		// Click Search
		driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();

		Thread.sleep(3000);

		// Check for No Result or proceed
		try {
			WebElement noResult = driver.findElement(By.xpath("//strong[contains(text(),'Please try selecting different dates')]"));
			if (noResult.isDisplayed()) {
				takeScreenshot("MultiCity_NoResults.png");
				System.out.println("No flights found in multi-city search.");
			}
		} catch (Exception e) {
			System.out.println("Multi-city flights found or no error message present.");
		}
	}

	//    @Test(priority = 6)
	//    public void oneWayFlightSearch() throws InterruptedException, AWTException, IOException {
	//        driver.get("https://groupy.live/travelplanbooker.com");
	//
	//        // Select One Way
	//        driver.findElement(By.xpath("//label[text()='One Way']")).click();
	//
	//        WebElement from = driver.findElement(By.id("airport_from"));
	//        from.clear();
	//        from.sendKeys("Mumbai");
	//        Thread.sleep(1000);
	//        Robot r = new Robot();
	//        r.keyPress(KeyEvent.VK_ENTER);
	//        r.keyRelease(KeyEvent.VK_ENTER);
	//
	//        WebElement to = driver.findElement(By.id("airport_to"));
	//        to.clear();
	//        to.sendKeys("Dubai");
	//        Thread.sleep(1000);
	//        r.keyPress(KeyEvent.VK_ENTER);
	//        r.keyRelease(KeyEvent.VK_ENTER);
	//
	//        WebElement date = driver.findElement(By.id("oneway_date"));
	//        date.click();
	//        driver.findElement(By.xpath("//td[text()='25']")).click();
	//
	//        WebElement passengers = driver.findElement(By.id("f-guests"));
	//        passengers.click();
	//        Thread.sleep(1000);
	//        driver.findElement(By.xpath("(//button[@class='plus_btn btn-plus1'])[4]")).click();
	//        driver.findElement(By.xpath("//button[text()='Done']")).click();
	//
	//        driver.findElement(By.xpath("//button[@class='search-btn']")).click();
	//
	//        Thread.sleep(3000);
	//
	//        try {
	//            WebElement noResult = driver.findElement(By.xpath("//strong[contains(text(),'Please try selecting different dates')]"));
	//            if (noResult.isDisplayed()) {
	//                takeScreenshot("OneWay_NoResults.png");
	//                System.out.println("No flights found in one-way search.");
	//            }
	//        } catch (Exception e) {
	//            System.out.println("One-way flights found or no error message.");
	//        }
	//    }

	public void takeScreenshot(String baseFileName) throws IOException {
		String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File("screenshots/" + baseFileName + "_" + timestamp + ".png");
		dest.getParentFile().mkdirs(); // Create folder if not exists
		Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		System.out.println("Screenshot saved: " + dest.getAbsolutePath());
	}


	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}
}
