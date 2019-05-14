package com.eppm.common;


import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;



	




public class MainDriver {

	LoginPage loginPage = new LoginPage();
	public static WebDriver webdriver;
	public Properties prop;
	public static void main(String[] args) {
		MainDriver m = new MainDriver();
		try {
			m.initialioseBrowser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeSuite
	public void initialioseBrowser() throws Exception {
		//prop = new Properties();
		//FileInputStream fis = new FileInputStream("/home/meetcsserver/eclipse-workspace/Talenticks/src/main/java/resources/loginData.properties");
	    System.setProperty("webdriver.chrome.driver","C:\\Users\\dell\\git\\EPPM\\src\\main\\resources\\chromedriver.exe");
		webdriver = new ChromeDriver();
		Thread.sleep(1000);
		//webdriver.get(url);
		Thread.sleep(1000);
		webdriver.manage().window().maximize();
		Thread.sleep(4000);
		loginPage.login();

	}

	@AfterSuite
	public void closeBrowser() {

		webdriver.quit();

	}
}

