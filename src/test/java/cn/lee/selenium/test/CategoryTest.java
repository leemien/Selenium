package cn.lee.selenium.test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.lee.selenium.SeleniumUtil;
import cn.lee.selenium.util.ConfigureUtil;
import cn.lee.selenium.util.GmUtil;

public class CategoryTest {
	private static Logger logger = LoggerFactory.getLogger(CategoryTest.class);
	private static WebDriver driver = null;
	private static String stationUrl = null;

	@BeforeClass
	public static void setUp() {
		stationUrl = ConfigureUtil.getValueByKey("stationUrl");
		driver = SeleniumUtil.newInstance().getChromeWebDriver();
		GmUtil.loginStation(driver);
	}

	@Test
	public void category1Test() {
		driver.get(stationUrl + "/station/new#/merchandise/manage/list/cate_manage");
		
		WebElement element = driver.findElement(By.xpath("//div[text()='一级分类']/parent::*/parent::*/parent::div"));
		List<WebElement> elementArray = element.findElements(By.xpath("./div[2]/div"));
		String category1_name = null;
		boolean exist = false;
		for (WebElement e : elementArray) {
			category1_name = e.findElement(By.xpath("./div/div/div[2]")).getText();
			if (category1_name.equals("蔬菜")) {
				exist = true;
				break;
			}
		}
		if (exist) {
			driver.findElement(By.xpath("//div[text()='一级分类']/following-sibling::div[1]")).click();
			driver.findElement(By.xpath("//label[text()='一级分类名称']/following-sibling::div[1]/input")).sendKeys("蔬菜");
			driver.findElement(By.xpath("//label[text()='选择图标']/following-sibling::div[1]/div/div/div[3]/img")).click();
			driver.findElement(By.xpath("//button[text()='保存']")).click();
			String tip_text = driver.findElement(By.xpath("//div[@class='panel-body']")).getText();
	        try {
				GmUtil.screenShot(driver, "category1Test.png");
			} catch (IOException e) {
				logger.error("截图遇到错误: " + e);
			}
			Assert.assertEquals(tip_text.contains("该分类已存在"), true,"新建一级分类,使用已经存在的名称,断言失败");
		}
	}

	@Test
	public static void tearDown() {
		// driver.close();
	}

}
