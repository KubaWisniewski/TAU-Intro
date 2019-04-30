package pl.edu.wisniewski.movie.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;
    @FindBy(id = "email")
    private WebElement loginInput;

    @FindBy(css = "#passwd")
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"SubmitLogin\"]")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        loginInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public boolean loginStatus() {
        return driver.getCurrentUrl().equals("http://automationpractice.com/index.php?controller=my-account");
    }
}