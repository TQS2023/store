package pt.ua.deti.store.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class CartPage {
    private final WebDriver driver;
    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

        this.driver = driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> webDriver.findElement(By.id("cart")).isDisplayed());
    }

    @FindBy(id = "cart")
    private WebElement cart;

    @FindBy(className = "removeFromCart")
    private List<WebElement> removeFromCart;

    public void assertCartLength() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> removeFromCart.size() > 0);
        assertThat(removeFromCart.size(), is(greaterThan(0)));
    }

    public void clickRemoveFromCart() {
        removeFromCart.get(0).click();
    }

    public void assertCartEmpty() {
        assertThat(cart.findElements(By.tagName("li")).size(), is(0));
    }
}
