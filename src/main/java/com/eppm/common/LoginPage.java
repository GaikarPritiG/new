package com.eppm.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;



public class LoginPage {
	
	
	
	public void login(){
		
		File file = new File("C://Users//dell//Downloads//Talenticks//Talenticks//src//main//java//resources//loginData.properties");
		  
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		
		//load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		MainDriver.webdriver.get(prop.getProperty("URL"));
		prop.get("searchTextBox");
		
	}

	
	
}
