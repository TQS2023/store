package pt.ua.deti.store.integration;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import pt.ua.deti.store.pages.CartPage;
import pt.ua.deti.store.pages.HomePage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Glue {
    private final static String DOCKER_COMPOSE_LOCATION = "docker-compose.test.yml";

    private static DockerComposeContainer environment = new DockerComposeContainer(new java.io.File(DOCKER_COMPOSE_LOCATION))
            .waitingFor("frontend", Wait.forHealthcheck().withStartupTimeout(Duration.ofDays(2)));

    private RemoteWebDriver driver;

    private HomePage homePage;
    private CartPage cartPage;

    @Given("I start the browser")
    public void iStartTheBrowser() throws MalformedURLException {
        environment.stop();
        environment = new DockerComposeContainer(new java.io.File(DOCKER_COMPOSE_LOCATION))
                .waitingFor("frontend", Wait.forHealthcheck().withStartupTimeout(Duration.ofDays(2)));
        environment.start();

        URL url = new URL("http://localhost:4444/wd/hub");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setCapability("enableVNC", true);
        driver = new RemoteWebDriver(url, capabilities);
        driver.get("http://frontend:3000");

        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
    }

    @Then("I should see the list of books")
    public void iShouldSeeTheListOfBooks() {
        homePage.assertBooksList();
    }

    @When("I click on the book")
    public void iClickOnTheBook() {
        homePage.clickRandomBook();
    }

    @Then("I should see the details of the book")
    public void iShouldSeeTheDetailsOfTheBook() {
        homePage.assertBookDetails();
    }

    @And("I click on the add to cart button")
    public void iClickOnTheAddToCartButton() {
        homePage.clickAddToCart();
    }

    @Then("I should see the book in my cart")
    public void iShouldSeeTheBookInMyCart() {
        cartPage.assertCartLength();
    }

    @And("I click on the cart button")
    public void iClickOnTheCartButton() {
        homePage.navigateToCart();
    }

    @And("I click on the remove from cart button")
    public void iClickOnTheRemoveFromCartButton() {
        cartPage.clickRemoveFromCart();
    }

    @Then("I should not see the book in my cart")
    public void iShouldNotSeeTheBookInMyCart() {
        cartPage.assertCartEmpty();
    }

    @And("I close the modal")
    public void iCloseTheModal() {
        homePage.closeModal();
    }
}
