package cn.lee.selenium.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmUtil {
	public static void loginStation(WebDriver driver) {
		String userName = ConfigureUtil.getValueByKey("stationName");
		String password = ConfigureUtil.getValueByKey("stationPwd");
		String urlStr = ConfigureUtil.getValueByKey("stationUrl") + "/station/";
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get(urlStr);
		driver.findElement(By.id("username")).sendKeys(userName);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button[type='submit']")).click();
	}

	/**
	 * 退出Station
	 * 
	 * @param driver
	 */
	public static void logoutStation(WebDriver driver) {
		driver.navigate().refresh();
		WebElement element = driver.findElement(By.xpath("//div[contains(.,'你好')]/following-sibling::div[1]"));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//a[text()='退出']"));

			}
		}).click();
	}

	/**
	 * 截图保存
	 * 
	 * @param driver
	 * @param file_name
	 * @throws IOException
	 */
	public static void screenShot(WebDriver driver, String file_name) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); // 讲截取的图片以文件的形式返回
		FileUtils.copyFile(srcFile, new File("./image/" + file_name)); // 使用copyFile()方法保存获取到的截图文件
	}

	/***
	 * 生成指定长度的字符串
	 * 
	 * @param length
	 * @return str
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int number = 0;
		for (int i = 0; i < length; i++) {
			number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param scl
	 *            小数最大位数
	 * @return
	 */
	public static double getRandomDouble(int min, int max, int scl) {
		int pow = (int) Math.pow(10, scl);// 指定小数位
		return Math.floor((Math.random() * (max - min) + min) * pow) / pow;
	}

	
	/**
	 * 
	 * 检测提示是否出现
	 * @param driver
	 * @param tipText
	 * @return
	 */
	public static boolean checkTipExist(WebDriver driver, String tipText) {
		List<WebElement> tipElements = driver.findElements(By.xpath("//div[@class='panel-body']"));
		boolean exist = false;
		for (WebElement tipElement : tipElements) {
			String tip_text = tipElement.getText();
			if (tip_text.contains(tipText)) {
				exist = true;
				break;
			}
		}
		return exist;
	}
}
