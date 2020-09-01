package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserHandler {
    private static final Logger LOGGER = Logger.getLogger(BrowserHandler.class.getName());
    private static final CredentialsManager CREDS = new CredentialsManager();

    private WebDriver driver = null;

    public BrowserHandler() {
        driver = new FirefoxDriver();
    }

    /**
     * Login to the Github.com Website at the webriver.
     * 
     */
    public void loginToGithub() {
        // Open github login page
        this.driver.get("https://github.com/login");
        // Write login name into field
        this.driver.findElement(By.id("login_field")).sendKeys(CREDS.username);
        // Wirte password into field
        this.driver.findElement(By.id("password")).sendKeys(CREDS.password);
        // Submit login form
        this.driver.findElement(By.name("commit")).click();
    }

    /**
     * Creates a project with the name if you are already logged in github.
     * 
     * @param name The name of the Repository wich will be created.
     */
    public String createGithubProject(final String name) {
        this.driver.get("https://github.com/new");
        this.driver.findElement(By.id("repository_name")).sendKeys(name);

        // before the button can get pressed wait for 3sec to recognize that the name is
        // accepted
        try {
            Thread.sleep(3000);
        } catch (final InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Interrupted during sleep", e);
            Thread.currentThread().interrupt();
        }
        // Clicks the create project button
        this.driver.findElement(By.xpath("//button[@class=\"btn btn-primary first-in-line\"]")).click();
        LOGGER.log(Level.INFO, "Project on github.com created.");
        // The remote url
        return driver.getCurrentUrl();
    }

    /**
     * Closes the browser.
     */
    public void colseBrowser() {
        this.driver.close();
    }
}