package com.kelvSYC.wikia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;

/**
 * Page object representing the "Add Video Page" (Special:WikiaVideoAdd)
 * @author kelvSYC
 *
 */
public class AddVideoPage extends WikiPage {
	private final WikiPageFactory genericPageFactory;
	
	private final By videoInputDescription;
	private final By addVideoButtonDescription;
	
	@Inject
	public AddVideoPage(@Assisted WebDriver driver,
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
			@Named("Notification Banner") By notificationBannerDescription,
			@Named("Add Video Link") By addVideoDescription,
			@Named("Video URL Input") By videoInputDescription,
			@Named("Add Video Button") By addVideoButtonDescription) {
		super(driver, genericPageFactory, videoPageFactory, signInMenuDescription, loginDropdownDescription, usernameInputDescription, passwordInputDescription,
				loginButtonDescription, authLabelDescription, contributeButtonDescription, contributeMenuDescription, notificationBannerDescription,
				addVideoDescription);
		
		this.genericPageFactory = genericPageFactory;
		this.videoInputDescription = videoInputDescription;
		this.addVideoButtonDescription = addVideoButtonDescription;
	}
	
	public WikiPage inputVideo(String url) {
		WebElement inputElement = driver.findElement(videoInputDescription);
		inputElement.sendKeys(url);
		
		WebElement addVideoButton = driver.findElement(addVideoButtonDescription);
		addVideoButton.click();
		
		return genericPageFactory.build(driver);
	}
}
