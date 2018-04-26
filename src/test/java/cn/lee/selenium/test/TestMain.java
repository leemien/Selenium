package cn.lee.selenium.test;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestMain {
	public static void main(String[] args) {
		TestNG testng = new TestNG();
		List<String> suites = new ArrayList<String>();
		suites.add("./testngxml/testng-category.xml");
		testng.setTestSuites(suites);
		testng.run();
	}
}