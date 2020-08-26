package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * A utility wich create a folder at ~/Repos 
 * with the name passed as the first commandline argument.
 * It also creates a git repository and put it on github.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        WebDriver driver = new FirefoxDriver();
        loginToGithub(driver);
        // TODO
        //driver.close();
    }
    
    public static void loginToGithub(WebDriver driver){
        //Open github login page
        driver.get("https://github.com/login");
        //Write login name into field
        driver.findElement(By.id("login_field")).sendKeys("username");
        //Wirte password into field
        driver.findElement(By.id("password")).sendKeys("password");
        //Submit login form
        driver.findElement(By.name("commit")).click();
    }
}
