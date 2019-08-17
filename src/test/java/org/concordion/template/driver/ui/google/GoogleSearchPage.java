package org.concordion.template.driver.ui.google;

import java.util.List;

import org.concordion.cubano.driver.BrowserBasedTest;
import org.concordion.template.AppConfig;
import org.concordion.template.driver.ui.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GoogleSearchPage extends PageObject<GoogleSearchPage> {
    public GoogleSearchPage(BrowserBasedTest test) {
        super(test);
    }

    @FindBy(name = "q")
    WebElement searchBox;

    // @FindBy(css = "#gs_lc0 input")
    // WebElement searchField;

    @FindBy(name = "btnK")
    List<WebElement> searchButtons;

    private By resultListSelector = By.cssSelector("div.rc");

    // @FindBy(name = "btnG")
    // WebElement searchIconButton;

    // @FindBy(name = "div.g")
    // List<SearchResult> searchResults;

    @Override
    public ExpectedCondition<?> pageIsLoaded(Object... params) {
        return ExpectedConditions.visibilityOf(searchBox);
    }

    public static GoogleSearchPage open(BrowserBasedTest test) {
        test.getBrowser().getDriver().navigate().to(AppConfig.getInstance().getSearchUrl());

        return new GoogleSearchPage(test);
    }

    public GoogleSearchPage searchFor(String term) {
        searchBox.sendKeys(term);

        WebElement button = null;

        for (WebElement searchButton : searchButtons) {
            try {
                waitForElementToClickable(searchButton, 2);
                button = searchButton;
                break;
            } catch (Exception e) {
                // Ignore
            }
        }

        if (button == null) {
            throw new RuntimeException("Search button not found");
        }

        capturePage(button);
        button.click();

        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(resultListSelector, 2), 3);

        return this;
    }

    public String getSearchResult(String link) {
        List<SearchResult> searchResults = getBrowser().getHtmlElementsLoader(this).findElements(SearchResult.class, resultListSelector);

        for (SearchResult searchResult : searchResults) {
            String url = searchResult.url.getText();

            if (url.contains(link)) {
                return url;
            }
        }

        return null;
    }
}
