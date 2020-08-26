package main;

import java.io.File;
import org.apache.commons.exec.OS;
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
    public static void main(String[] args) {
        String folderPath = getOsSpecificFolderPath();
        createFolder(folderPath, "test" /*args[1]*/); //TODO change if finished
        //TODO check git user and git init

        WebDriver driver = new FirefoxDriver();
        loginToGithub(driver);
        //driver.close(); //TODO use if everything browser related is done
    }

    /**
     * Creates and returns the Os Specific Path.
     * @return the Os Specific Path at wich the folder gets created
     */
    public static String getOsSpecificFolderPath(){
        if(OS.isFamilyWindows()) return "C:\\Users\\" + System.getProperty("user.name") + "\\documents\\repos\\";
        if(OS.isFamilyUnix()){
            //TODO get linx path.
        }
        System.err.println("OS is not suported.\nExit program..");
        System.exit(-1);
        return "";
        
    }

    /**
     * Creates a folder with a README.md in it at the path with the name.
     * @param path path at wich the folder gets created
     * @param name name of folder
     */
    public static void createFolder(String path, String name){
        File file =  new File(path + name);
        //If the folder exits you can't create a new project with this name
        if(file.exists()){
            System.err.println("Folder already exists.\nExit program...");
            System.exit(-1);
        }
        //Create the folder
        try {
            file.mkdir();
        } catch (Exception e) {
            System.out.println(e);
        }

        //Create the README.md file
        try {
            new File(path + name + "/README.md").createNewFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    /**
     * Login to the Github.com Website at the webriver.
     * @param driver the driver wich is used
     */
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
