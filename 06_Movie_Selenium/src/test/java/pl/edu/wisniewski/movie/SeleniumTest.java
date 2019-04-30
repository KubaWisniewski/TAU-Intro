package pl.edu.wisniewski.movie;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import pl.edu.wisniewski.movie.utils.LoginPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.wisniewski.movie.utils.RegisterPage;
import pl.edu.wisniewski.movie.utils.StartPage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SeleniumTest {
    private static WebDriver driver;
    private StartPage startPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @BeforeClass
    public static void driverSetup() {
        System.setProperty("webdriver.chrome.driver", "webDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MICROSECONDS);
        driver.manage().window().setSize(new Dimension(300, 700));
    }

    @Before
    public void before() {
        startPage = new StartPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage();
    }

    @Test
    public void homePage() throws IOException {
        driver.get("http://automationpractice.com");
        driver.findElement(By.cssSelector(".login")).click();
        assertEquals("Sign in", driver.findElement(By.cssSelector("#SubmitLogin")).getText());
        if (driver instanceof TakesScreenshot) {
            File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(f, new File("screenshots/homePage.png"));
        }
    }

    @Test
    public void topProducts() {
        startPage.open();
        startPage.clickBestSellers();
        assertEquals(7, startPage.getProducts().size());
    }

    @Test
    public void loginFail() {
        loginPage.open();
        loginPage.login("jan", "");
        assertFalse(loginPage.loginStatus());
    }

    @Test
    public void loginSuccess() {
        loginPage.open();
        loginPage.login("s15662@pjwstk.edu.pl", "test123");
        assertTrue(loginPage.loginStatus());
    }

    @Test
    public void registerUser() throws IOException {
        assertEquals(true, registerPage.registerpage(driver));
    }

    @AfterClass
    public static void cleanp() {
        driver.quit();
    }
}