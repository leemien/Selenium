package cn.lee.selenium.test.category;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
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

	/**
	 * 使用已经用过的名称添加一级分类,断言失败
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void category1Test01() throws InterruptedException {
		Reporter.log("创建一级分类使用重复名称");
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
			Thread.sleep(500);

			exist = GmUtil.checkTipExist(driver, "该分类已存在");

			try {
				GmUtil.screenShot(driver, "category1Test.png");
			} catch (IOException e) {
				logger.error("截图遇到错误: " + e);
			}
			driver.findElement(By.xpath("//button[text()='取消']")).click();
			Assert.assertEquals(exist, true, "新建一级分类,使用已经存在的名称,断言失败");
		}
	}

	/**
	 * 添加一个一级分类,断言成功
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void category1Test02() throws InterruptedException {
		driver.get(stationUrl + "/station/new#/merchandise/manage/list/cate_manage");
		String category1_name = GmUtil.getRandomString(8);

		driver.findElement(By.xpath("//div[text()='一级分类']/following-sibling::div[1]")).click();
		driver.findElement(By.xpath("//label[text()='一级分类名称']/following-sibling::div[1]/input"))
				.sendKeys(category1_name);
		driver.findElement(By.xpath("//label[text()='选择图标']/following-sibling::div[1]/div/div/div[3]/img")).click();
		driver.findElement(By.xpath("//button[text()='保存']")).click();

		Thread.sleep(500);
		boolean exist = GmUtil.checkTipExist(driver, "成功增加");

		try {
			GmUtil.screenShot(driver, "category1Test.png");
			Assert.assertEquals(exist, true, "新建一级分类,使用已经存在的名称,断言失败");
		} catch (IOException e) {
			logger.error("截图遇到错误: " + e);
		} finally {
			// 找到它对应的删除按钮
			driver.findElement(By.xpath("//div[text()='" + category1_name + "']/../following-sibling::div[1]/span[2]"))
					.click();
			driver.findElement(By.xpath("//button[text()='确定']")).click();
		}

	}

	@Test
	public void searchSalemenu() {
		driver.get(stationUrl + "/station/new#/merchandise/manage/sale");
		WebElement timeElement = driver.findElements(By.xpath("//div[text()='全部运营时间']")).get(0);
		timeElement.click();
		List<WebElement> elements = driver.findElements(By.xpath(
				"//div[text()='全部运营时间' and @class='gm-select-option selected'][last()]/following-sibling::div"));
        System.out.println(elements.size());
		for(WebElement element:elements){
			System.out.println(element.getText());
			element.click();
			timeElement.click();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@AfterClass
	public static void tearDown() {
		//driver.close();
	}

}
