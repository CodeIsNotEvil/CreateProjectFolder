package main;

import java.io.File;

import org.apache.commons.exec.OS;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * A utility wich create a folder at ~/Repos with the name passed as the first
 * commandline argument. It also creates a git repository and put it on github.
 *
 */
public class App {
    public static void main(String[] args) {
        String folderPath = getOsSpecificFolderPath();
        createFolder(folderPath, "test" /* args[1] */); // TODO change if finished
        // TODO check git user
        initGitInsideFolder(folderPath + "test" /* args[1] */);
        

        WebDriver driver = new FirefoxDriver();
        loginToGithub(driver, "test");
        //driver.close(); //TODO use if everything browser related is done
    }

    /**
     * Initilizes a git repository and commit the README file.
     * 
     * @param path fullpath to the folder.
     */
    private static void initGitInsideFolder(String path) {
        try {
            Git git = Git.init().setDirectory(new File(path)).call();
            git.add().addFilepattern("README.md").call();
            git.commit().setMessage("Initial commit").call();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates and returns the Os Specific Path.
     * 
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
            e.printStackTrace();
        }

        //Create the README.md file
        try {
            new File(path + name + "/README.md").createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Login to the Github.com Website at the webriver.
     * 
     * @param driver the driver wich is used
     * @throws InterruptedException
     */
    public static void loginToGithub(WebDriver driver, String repositoryName) {
        //Open github login page
        driver.get("https://github.com/login");
        //Write login name into field
        driver.findElement(By.id("login_field")).sendKeys("codeisnotevil");
        //Wirte password into field
        driver.findElement(By.id("password")).sendKeys("z4T9Zolj9e!uj^8C");
        //Submit login form
        driver.findElement(By.name("commit")).click();

        driver.get("https://github.com/new");
        driver.findElement(By.id("repository_name")).sendKeys(repositoryName);

        //before the button can get pressed wait for 3sec to recognize that the name is accepted
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("\n\nElement: " + driver.findElements(By.xpath("//button[@class=\"btn btn-primary first-in-line\"]")).size() + "\n\n");
        driver.findElement(By.xpath("//button[@class=\"btn btn-primary first-in-line\"]")).click();
        //driver.findElement(By.xpath("/html/body/div/main/div/form/div/button")).click();

        
    }
}
