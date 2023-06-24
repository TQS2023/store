package pt.ua.deti.store.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

public class HomePage {
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(webDriver -> webDriver.findElement(By.id("topShelf")).findElements(By.tagName("div")).size() > 0);
    }

    @FindBy(id = "topShelf")
    private WebElement topShelf;

    @FindBy(id = "bottomShelf")
    private WebElement bottomShelf;

    @FindBy(id = "cart")
    private WebElement cart;

    @FindBy(id = "signin")
    private WebElement signin;

    @FindBy(id = "bookModal")
    private WebElement bookModal;

    @FindBy(id = "addToCart")
    private WebElement addToCart;

    @FindBy(id = "closeModal")
    private WebElement closeModal;

    public void assertBooksList() {
        assertThat(topShelf.findElements(By.tagName("div")).size(), is(greaterThan(0)));
        assertThat(bottomShelf.findElements(By.tagName("div")).size(), is(greaterThan(0)));
    }

    public void navigateToCart() {
        cart.click();
    }

    public void navigateToSignin() {
        signin.click();
    }

    public void clickRandomBook() {
        List<WebElement> books = topShelf.findElements(By.tagName("div"));
        books.get(0).click();
    }

    public void assertBookDetails() {
        assertThat(bookModal.isDisplayed(), is(true));
    }

    public void clickAddToCart() {
        addToCart.click();
    }

    public void closeModal() {
        closeModal.click();
    }
}