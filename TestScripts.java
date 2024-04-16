
package com.AutomationScripts;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestScripts {
	public static WebDriver driver;

	@Test
	public void searchGoogleResults() {
		// Setup WebDriver
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		try {
			// Navigate to Google
			driver.get("https://www.google.com");

			// Find search bar and enter query
			WebElement searchBar = driver.findElement(By.name("q"));
			searchBar.sendKeys("YouTube" + Keys.ENTER);

			// Get and display search results count
			WebElement resultStats = driver.findElement(By.id("result-stats"));
			String noOfResults = resultStats.getText();
			System.out.println("No of Results Found: " + noOfResults);

			// Iterate through search results
			List<WebElement> searchResults = driver.findElements(By.cssSelector("div.g cite"));
			for (WebElement result : searchResults) {
				String onlineVideoPlatform = result.getText();
				System.out.println(onlineVideoPlatform);

				// Click on the YouTube link
				if (onlineVideoPlatform.contains("https://www.youtube.com")) {
					result.click();
					break;
				}
			}

			// Search for a channel
			WebElement searchChannelName = driver.findElement(By.xpath("//input[@id='search']"));
			searchChannelName.clear();

			searchChannelName.sendKeys("HackerEarth");

			// Click Search Button
			WebElement Search = driver
					.findElement(By.xpath("//button[@id=\"search-icon-legacy\"]/yt-icon/yt-icon-shape/icon-shape/div"));
			Search.click();

			// Click on the channel
			WebElement channelName = driver.findElement(By.xpath("(//img[@id='img'])[4]"));
			channelName.click();

			// Click on the videos section
			WebElement videos = driver.findElement(By.xpath("(//div[.='Videos'])"));
			videos.click();

			// Wait for the page to fully load
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
					.executeScript("return document.readyState").equals("complete"));

			// Create JavaScriptExecutor instance
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// Scroll the page to the bottom
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

			// Click on a specific video
			WebElement findOldVideos = driver
					.findElement(By.xpath("//a[.='HackerEarth | Be a better programmer | Code.Build.Hack']"));
			findOldVideos.click();

		} finally {
			// Quit the WebDriver
			driver.quit();
		}
	}
}
