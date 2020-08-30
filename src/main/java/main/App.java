package main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.exec.OS;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * A utility wich create a folder at ~/Repos with the name passed as the first
 * commandline argument. It also creates a git repository and put it on github.
 *
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private static final CredentialsManager CREDS = new CredentialsManager();

    public static void main(final String[] args) {
        LOGGER.setLevel(Level.INFO);
        final String folderPath = getOsSpecificFolderPath();
        createFolder(folderPath, "test" /* args[1] */); // TODO change if finished
        checkGitConfig(folderPath + "test" /* args[1] */);
        initGitInsideFolder(folderPath + "test" /* args[1] */);

        final WebDriver driver = new FirefoxDriver();
        loginToGithub(driver, "test");
        // driver.close(); //TODO use if everything browser related is done
    }

    /**
     * Initilizes a git repository and commit the README file.
     * 
     * @param path fullpath to the folder.
     */
    private static void initGitInsideFolder(final String path) {
        try {
            final Git git = Git.init().setDirectory(new File(path)).call();
            git.add().addFilepattern("README.md").call();
            git.commit().setMessage("Initial commit").call();
            LOGGER.log(Level.INFO, () -> "Git initialized and README.md commited");
        } catch (IllegalStateException | GitAPIException e) {
            LOGGER.log(Level.SEVERE, "Failed to create local git repository.");
            e.printStackTrace();
        }
    }

    /**
     * Checks the git config with the help of the credentials manager
     * @param path
     */
    private static void checkGitConfig(final String path) {
        // Open an existing repository
        try {
            final Repository repo = new FileRepositoryBuilder().setGitDir(new File(path)).build();
            final StoredConfig cfg = repo.getConfig();
            final String name = cfg.getString("user", null, "name");
            final String email = cfg.getString("user", null, "email");
            if (!(name.equals(CREDS.username) && email.equals(CREDS.email))) {
                LOGGER.severe("Email and/or name do not match with the git config.\nExeting...");
                System.exit(0);
                // TODO call the CredentialManager, let the user type in username and password.
            }

        } catch (final IOException e) {
            LOGGER.severe("Could not read from git config.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Creates and returns the Os Specific Path.
     * 
     * @return the Os Specific Path at wich the folder gets created
     */
    public static String getOsSpecificFolderPath() {
        if (OS.isFamilyWindows())
            return "C:\\Users\\" + System.getProperty("user.name") + "\\documents\\repos\\";
        if (OS.isFamilyUnix()) {
            return "/home/" + System.getProperty("user.name") + "/repos/";
        }
        LOGGER.log(Level.SEVERE, "OS is not suported.\nExit program..");
        System.exit(-1);
        return "";

    }

    /**
     * Creates a folder with a README.md in it at the path with the name.
     * 
     * @param path path at wich the folder gets created
     * @param name name of folder
     */
    public static void createFolder(final String path, final String name) {
        final File file = new File(path + name);
        // If the folder exits you can't create a new project with this name
        if (file.exists()) {
            LOGGER.log(Level.SEVERE, () -> "Folder already exists.\nExit program... " + path + name);
            System.exit(-1);
        }
        // Create the folder
        try {
            if (file.mkdir()) {
                LOGGER.log(Level.INFO, () -> file.toString() + " got created.\n");
            } else {
                LOGGER.log(Level.SEVERE, () -> "Failed to create " + file.toString() + "\n");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        // Create the README.md file
        try {
            if (new File(path + name + "/README.md").createNewFile()) {
                LOGGER.log(Level.INFO, () -> "README.md created at" + path + name);
            } else {
                LOGGER.log(Level.SEVERE, () -> "Could not create README.md at" + path + name);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Login to the Github.com Website at the webriver.
     * 
     * @param driver the driver wich is used
     * @throws InterruptedException
     */
    public static void loginToGithub(final WebDriver driver, final String repositoryName) {
        // Open github login page
        driver.get("https://github.com/login");
        // Write login name into field

        driver.findElement(By.id("login_field")).sendKeys(CREDS.username);
        // Wirte password into field
        driver.findElement(By.id("password")).sendKeys(CREDS.password);
        // Submit login form
        driver.findElement(By.name("commit")).click();

        driver.get("https://github.com/new");
        driver.findElement(By.id("repository_name")).sendKeys(repositoryName);

        // before the button can get pressed wait for 3sec to recognize that the name is
        // accepted
        try {
            Thread.sleep(3000);
        } catch (final InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Interrupted during sleep", e);
            Thread.currentThread().interrupt();
        }
        driver.findElement(By.xpath("//button[@class=\"btn btn-primary first-in-line\"]")).click();
        LOGGER.log(Level.INFO, "Project on github.com created.");
    }
}
