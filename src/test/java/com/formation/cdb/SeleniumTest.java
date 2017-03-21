package com.formation.cdb;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;


public class SeleniumTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
     
    driver = new PhantomJSDriver();
    baseUrl = "http://127.0.0.1:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }


  public void testSelenium() throws Exception {
    driver.get(baseUrl + "/cdb/database");
    driver.findElement(By.id("searchbox")).clear();
    driver.findElement(By.id("searchbox")).sendKeys("123456789");
    driver.findElement(By.id("searchsubmit")).click();
    assertFalse(isElementPresent(By.xpath("//tbody[@id='results']/tr")));
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys("123456789");
    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("2010-10-20");
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("2012-01-10");
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Apple Inc.");
    driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
    driver.findElement(By.id("searchbox")).clear();
    driver.findElement(By.id("searchbox")).sendKeys("123456789");
    driver.findElement(By.id("searchsubmit")).click();
    assertTrue(isElementPresent(By.xpath("//tbody[@id='results']/tr[1]")));
    driver.findElement(By.linkText("123456789")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys("123456789_edited");
    driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
    driver.findElement(By.id("searchbox")).clear();
    driver.findElement(By.id("searchbox")).sendKeys("123456789_edited");
    driver.findElement(By.id("searchsubmit")).click();
   // assertTrue(isElementPresent(By.xpath("//tbody[@id='results']/tr[1]")));
    driver.findElement(By.id("editComputer")).click();
    driver.findElement(By.name("cb")).click();
    driver.findElement(By.xpath("//a[@id='deleteSelected']/i")).click();
    assertFalse(isElementPresent(By.xpath("//tbody[@id='results']/tr")));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
