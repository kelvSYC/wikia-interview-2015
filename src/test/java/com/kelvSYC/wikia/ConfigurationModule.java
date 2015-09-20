package com.kelvSYC.wikia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.inject.AbstractModule;
import com.google.inject.PrivateModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;

/**
 * Primary configuration module for integration testing.
 * @author kelvSYC
 *
 */
public class ConfigurationModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new PrivateModule() {
			@Override
			protected void configure() {
				// Scenario 1 bindings
				bind(By.class).annotatedWith(Names.named("Sign in Menu")).toInstance(By.id("AccountNavigation"));
				bind(By.class).annotatedWith(Names.named("Login Dropdown")).toInstance(By.id("UserLoginDropdown"));
				bind(By.class).annotatedWith(Names.named("Username Input")).toInstance(By.id("usernameInput"));
				bind(By.class).annotatedWith(Names.named("Password Input")).toInstance(By.id("passwordInput"));
				bind(By.class).annotatedWith(Names.named("Login Button")).toInstance(By.xpath("//input[@class=\"login-button\"]"));
				bind(By.class).annotatedWith(Names.named("Authentication Label")).toInstance(By.xpath("//a[@data-id=\"userpage\"]"));
				
				// Scenario 2 bindings
				bind(By.class).annotatedWith(Names.named("Contribute Button")).toInstance(By.cssSelector("nav.contribute"));
				bind(By.class).annotatedWith(Names.named("Contribute Menu")).toInstance(By.cssSelector("ul.WikiaMenuElement"));
				bind(By.class).annotatedWith(Names.named("Add Video Link")).toInstance(By.xpath("//a[@data-id=\"wikiavideoadd\"]"));
				bind(By.class).annotatedWith(Names.named("Notification Banner")).toInstance(By.cssSelector(".banner-notifications-wrapper"));

				install(new FactoryModuleBuilder().build(WikiPageFactory.class));
				expose(WikiPageFactory.class);
			}
		});
		
		install(new PrivateModule() {
			@Override
			protected void configure() {
				// Scenario 1 bindings
				bind(By.class).annotatedWith(Names.named("Sign in Menu")).toInstance(By.id("AccountNavigation"));
				bind(By.class).annotatedWith(Names.named("Login Dropdown")).toInstance(By.id("UserLoginDropdown"));
				bind(By.class).annotatedWith(Names.named("Username Input")).toInstance(By.id("usernameInput"));
				bind(By.class).annotatedWith(Names.named("Password Input")).toInstance(By.id("passwordInput"));
				bind(By.class).annotatedWith(Names.named("Login Button")).toInstance(By.xpath("//input[@class=\"login-button\"]"));
				bind(By.class).annotatedWith(Names.named("Authentication Label")).toInstance(By.xpath("//a[@data-id=\"userpage\"]"));
				
				// Scenario 2 bindings
				bind(By.class).annotatedWith(Names.named("Contribute Button")).toInstance(By.cssSelector("nav.contribute"));
				bind(By.class).annotatedWith(Names.named("Contribute Menu")).toInstance(By.cssSelector("ul.WikiaMenuElement"));
				bind(By.class).annotatedWith(Names.named("Add Video Link")).toInstance(By.xpath("//a[@data-id=\"wikiavideoadd\"]"));
				bind(By.class).annotatedWith(Names.named("Notification Banner")).toInstance(By.cssSelector(".banner-notifications-wrapper"));

				bind(By.class).annotatedWith(Names.named("Video URL Input")).toInstance(By.id("wpWikiaVideoAddUrl"));
				bind(By.class).annotatedWith(Names.named("Add Video Button")).toInstance(By.cssSelector(".submits input"));
				
				install(new FactoryModuleBuilder().build(AddVideoPageFactory.class));
				expose(AddVideoPageFactory.class);
			}
		});
		
		bind(WebDriver.class).to(ChromeDriver.class);
	}
}
