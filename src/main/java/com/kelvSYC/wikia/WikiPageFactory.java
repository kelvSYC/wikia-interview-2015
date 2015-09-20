package com.kelvSYC.wikia;

import org.openqa.selenium.WebDriver;

/**
 * Assisted injection factory for a generic wiki page.
 * @author kelvSYC
 *
 */
public interface WikiPageFactory {
	WikiPage build(WebDriver driver);
}
