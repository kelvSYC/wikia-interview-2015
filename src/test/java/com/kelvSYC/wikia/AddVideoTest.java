package com.kelvSYC.wikia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Scenario 2: Adding a video
 * @author kelvSYC
 *
 */
public class AddVideoTest {
	private WebDriver driver;
	private WikiPageFactory factory;
	
	@Before
	public void setupDriver() {
		Injector injector = Guice.createInjector(new ConfigurationModule());
		driver = injector.getInstance(WebDriver.class);
		factory = injector.getInstance(WikiPageFactory.class);
	}
	
	@After
	public void closeDriver() {
		driver.close();
	}
	
	@Test
	public void addVideo() {
		// Step 1: Navigate to home page
		driver.get("http://qm-homework.wikia.com");
		
		assertEquals("Redirect URL mismatch", "http://qm-homework.wikia.com/wiki/QM_HomeWork_Wikia", driver.getCurrentUrl());
		
		// Ensure precondition
		String username = System.getProperty("username");
		String password = System.getProperty("password");
		WikiPage homepage = factory.build(driver);
		homepage = homepage.login(username, password);
		
		// Step 2: Click contribute
		homepage.clickContributeButton();
		
		assertTrue("Contribute menu not visible", homepage.isContributeMenuVisible());
		
		// Step 3: Click "Add video" link
		AddVideoPage videoPage = homepage.clickAddVideoLink();
		
		assertEquals("Destination URL mismatch", "http://qm-homework.wikia.com/wiki/Special:WikiaVideoAdd", driver.getCurrentUrl());
		
		// Step 4: Upload video
		WikiPage videosPage = videoPage.inputVideo("http://www.youtube.com/watch?v=h9tRIZyTXTI");
		
		String message = videosPage.getBannerNotificationMessage();
		assertTrue("Message mismatch", message.endsWith("was successfully added."));
		
		// Step 5 and 6: Click link in notification and verify
		WikiPage filePage = videosPage.clickNotificationLink();
		String pageName = message.substring(11, message.length() - 24);	// TODO remove hardcode
		
		assertEquals("File page URL mismatch", "http://qm-homework.wikia.com/wiki/" + pageName.replace(' ', '_'), driver.getCurrentUrl());
	}
}
