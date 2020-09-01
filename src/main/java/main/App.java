package main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility wich create a folder at ~/Repos with the name passed as the first
 * commandline argument. It also creates a git repository and put it on github.
 *
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(final String[] args) {
        LOGGER.setLevel(Level.INFO);
        String repositoryName = "test"; /* args[1] */ // TODO change if finished
        // create Folder
        FileHandler fileHandler = new FileHandler();
        fileHandler.createFolder(repositoryName);

        // create local git repository
        GitManager gitManager = new GitManager(fileHandler.getOsSpecificFolderPath() + repositoryName);
        gitManager.checkGitConfig();
        gitManager.initGitInsideFolder();

        // create online git repository
        BrowserHandler browserHandler = new BrowserHandler();
        browserHandler.loginToGithub();
        String remoteUrl = browserHandler.createGithubProject(repositoryName);

        // Add remote to local repo
        gitManager.addRemote(remoteUrl);
    }

}
