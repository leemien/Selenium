package cn.lee.selenium.util;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class GmUtil {
	public static void loginStation(WebDriver driver) {
		String urlStr = ConfigureUtil.getValueByKey("stationUrl") + "/station/";
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.get(urlStr);
		driver.findElement(By.id("username")).sendKeys("");
		driver.findElement(By.id("password")).sendKeys("");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
	}
	
	/**
	 * 截图保存
	 * @param driver
	 * @param file_name
	 * @throws IOException
	 */
	public static void screenShot(WebDriver driver,String file_name) throws IOException{
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); //讲截取的图片以文件的形式返回  
		FileUtils.copyFile(srcFile, new File("./image/" + file_name));  //使用copyFile()方法保存获取到的截图文件  
	}

}
