package cn.lee.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumUtil {
	private static volatile SeleniumUtil seleniumUtil = null;
	private static WebDriver driver = null;

	private SeleniumUtil() {
	}

	public static SeleniumUtil newInstance() {
		if (seleniumUtil == null) {
			synchronized (SeleniumUtil.class) {
				if (seleniumUtil == null) {
					seleniumUtil = new SeleniumUtil();
				}
			}
		}
		return seleniumUtil;
	}

	public WebDriver getChromeWebDriver() {
		if (driver == null) {
			synchronized (WebDriver.class) {
				if (driver == null) {
					ChromeOptions option = new ChromeOptions();
					option.addArguments("disable-infobars");
					driver = new ChromeDriver(option);
					driver.manage().window().maximize();
				}
			}
		}
		return driver;
	}

}
