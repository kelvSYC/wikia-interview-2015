package com.kelvSYC.wikia;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;

/**
 * Basic wiki page.
 * @author kelvSYC
 *
 */
public class WikiPage {
	protected final WebDriver driver;
	private final WikiPageFactory genericPageFactory;
	
	// Scenario 1
	private final By signInMenuDescription;
	private final By loginDropdownDescription;
	private final By usernameInputDescription;
	private final By passwordInputDescription;
	private final By loginButtonDescription;
	private final By authLabelDescription;
	
	// Scenario 2
	private final AddVideoPageFactory videoPageFactory;
	private final By contributeButtonDescription;
	private final By contributeMenuDescription;
	private final By addVideoDescription;
	private final By notificationBannerDescription;
	
	@Inject
	public WikiPage(@Assisted WebDriver driver,
			WikiPageFactory genericPageFactory,
			AddVideoPageFactory videoPageFactory,
			@Named("Sign in Menu") By signInMenuDescription,
			@Named("Login Dropdown") By loginDropdownDescription,
			@Named("Username Input") By usernameInputDescription,
			@Named("Password Input") By passwordInputDescription,
			@Named("Login Button") By loginButtonDescription,
			@Named("Authentication Label") By authLabelDescription,
			@Named("Contribute Button") By contributeButtonDescription,
			@Named("Contribute Menu") By contributeMenuDescription,
			@Named("Add Video Link") By addVideoDescription,
			@Named("Notification Banner") By notificationBannerDescription) {
		this.driver = driver;
		this.genericPageFactory = genericPageFactory;
		this.videoPageFactory = videoPageFactory;
		this.signInMenuDescription = signInMenuDescription;
		this.loginDropdownDescription = loginDropdownDescription;
		this.usernameInputDescription = usernameInputDescription;
		this.passwordInputDescription = passwordInputDescription;
		this.loginButtonDescription = loginButtonDescription;
		this.authLabelDescription = authLabelDescription;
		this.contributeButtonDescription = contributeButtonDescription;
		this.contributeMenuDescription = contributeMenuDescription;
		this.addVideoDescription = addVideoDescription;
		this.notificationBannerDescription = notificationBannerDescription;
	}
	
	public String getCurrentUrl() { return driver.getCurrentUrl(); }
	
	/* Action methods */
	
	public void hoverOverSignInMenu() {
		WebElement element = driver.findElement(signInMenuDescription);
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();
	}
	
	public void clickContributeButton() {
		WebElement element = driver.findElement(contributeButtonDescription);
		element.click();
	}
	
	public AddVideoPage clickAddVideoLink() {
		WebElement element = driver.findElement(addVideoDescription);
		element.click();
		
		return videoPageFactory.build(driver);
	}
	
	public WikiPage login(String username, String password) {
		if (!isLoginDropdownVisible()) { hoverOverSignInMenu(); }	// Just in case
		
		WebElement usernameElement = driver.findElement(usernameInputDescription);
		WebElement passwordElement = driver.findElement(passwordInputDescription);
		WebElement loginButton = driver.findElement(loginButtonDescription);
		usernameElement.sendKeys(username);
		passwordElement.sendKeys(password);
		loginButton.click();
		
		return genericPageFactory.build(driver);
	}
	
	public WikiPage clickNotificationLink() {
		try {
			WebElement element = driver.findElement(notificationBannerDescription);
			WebElement link = element.findElement(By.cssSelector(".msg a"));	// TODO refactor
			link.click();
			
			return genericPageFactory.build(driver);
		} catch (NoSuchElementException e) {
			// No notification
			return this;
		}
	}
	
	/* Verification methods */
	
	public boolean isLoginDropdownVisible() {
		WebElement element = driver.findElement(loginDropdownDescription);
		return element.isDisplayed();
	}
	
	public boolean isContributeMenuVisible() {
		WebElement element = driver.findElement(contributeMenuDescription);
		return element.isDisplayed();
	}
	
	public String getAuthenticationLabel() {
		try {
			WebElement element = driver.findElement(authLabelDescription);
			// Unfortunately, the username doesn't appear anywhere, exception through a tooltip, so we'll have to hack it...
			String title = element.getAttribute("title");
			return title.substring(0, title.length() - 10);
		} catch (NoSuchElementException e) {
			// Not logged in
			return null;
		}
	}
	
	// Special:Videos
	
	public String getBannerNotificationMessage() {
		try {
			WebElement element = driver.findElement(notificationBannerDescription);
			WebElement messageElement = element.findElement(By.cssSelector(".msg"));	// TODO refactor
			return messageElement.getText();
		} catch (NoSuchElementException e) {
			// No banner
			return null;
		}
	}
}
