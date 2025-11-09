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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Searchflight {
    protected WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./software/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.get("https://groupy.live/travelplanbooker.com");
        //Assert.assertEquals(driver.getTitle(), "TravelPlanBooker");
    }

    @Test(priority = 1)
    public void selectFlightDetails() throws InterruptedException, AWTException {
        driver.findElement(By.xpath("//input[@id='airport_from']")).sendKeys("Mumbai");
        driver.findElement(By.xpath("//input[@id='airport_to']")).sendKeys("Dubai");
        Thread.sleep(2000);
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
    }

    @Test(priority = 2)
    public void selectDatesAndPassengers() throws InterruptedException {
        driver.findElement(By.id("flight-dates")).click();
        driver.findElement(By.xpath("(//td[text()='23'])[1]")).click();
        driver.findElement(By.xpath("(//td[text()='26'])[1]")).click();

        driver.findElement(By.id("f-guests")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@class='plus_btn btn-plus1'])[4]")).click();
        WebElement done = driver.findElement(By.xpath("//button[text()='Done']"));
        wait.until(ExpectedConditions.elementToBeClickable(done)).click();
    }

    @Test(priority = 3)
    public void searchFlightsAndValidateResults() throws IOException {
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        // rest of your validation logic...
    }



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
