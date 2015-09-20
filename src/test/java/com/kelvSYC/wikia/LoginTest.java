package com.kelvSYC.wikia;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Scenario 1 - Login
 * @author kelvSYC
 *
 */
public class LoginTest {
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
	public void login() {
		String username = System.getProperty("username");
		String password = System.getProperty("password");
		
		// Step 1: Navigate to home page
		driver.get("http://qm-homework.wikia.com");
		
		assertEquals("Redirect URL mismatch", "http://qm-homework.wikia.com/wiki/QM_HomeWork_Wikia", driver.getCurrentUrl());
		
		// Step 2: Sign in
		WikiPage homepage = factory.build(driver);
		homepage.hoverOverSignInMenu();
		sleepUninterruptibly(1L, TimeUnit.SECONDS);		// TODO There might be a propagation delay; see if we can work around it
		
		assertTrue("Login dropdown remains invisible", homepage.isLoginDropdownVisible());
		
		// Step 3: Enter credentials
		homepage = homepage.login(username, password);
		
		String label = homepage.getAuthenticationLabel();	// This should be a capitalized username
		assertEquals("Username not displayed", capitalize(username), label);
	}
}
