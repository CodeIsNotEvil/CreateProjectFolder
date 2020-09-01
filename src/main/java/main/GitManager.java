package main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitManager {

    private static final Logger LOGGER = Logger.getLogger(GitManager.class.getName());

    private static final CredentialsManager CREDS = new CredentialsManager();
    private Repository repo = null;
    private StoredConfig cfg = null;
    private String localRepositoryPath = null;

    public GitManager(String localRepositoryPath) {
        this.localRepositoryPath = localRepositoryPath;
        try {
            this.repo = new FileRepositoryBuilder().setGitDir(new File(localRepositoryPath + "/.git")).build();
        } catch (IOException e) {
            LOGGER.severe("Could not get local repository.\nExeting...");
            System.exit(0);
            e.printStackTrace();
        }
        this.cfg = repo.getConfig();
    }

    /**
     * Checks the git config with the help of the credentials manager
     * 
     * @param path
     */
    public void checkGitConfig() {
        final String name = cfg.getString("user", null, "name");
        final String email = cfg.getString("user", null, "email");
        if (!(name.equals(CREDS.username) && email.equals(CREDS.email))) {
            LOGGER.severe("Email and/or name do not match with the git config.\nExeting...");
            System.exit(0);
            // TODO call the CredentialManager, let the user type in username and password.
        }
    }

    /**
     * Initilizes a git repository and commit the README file.
     */
    public void initGitInsideFolder() {
        try {
            final Git git = Git.init().setDirectory(new File(this.localRepositoryPath)).call();
            git.add().addFilepattern("README.md").call();
            git.commit().setMessage("Initial commit").call();
            LOGGER.log(Level.INFO, () -> "Git initialized and README.md commited");
        } catch (IllegalStateException | GitAPIException e) {
            LOGGER.log(Level.SEVERE, "Failed to create local git repository.");
            e.printStackTrace();
        }
    }

    /**
     * Adds the remote url as origin to the repository.
     * 
     * @param remoteURL
     */
    public void addRemote(String remoteURL) {
        String originURL = remoteURL + ".git";
        // TODO use it.
        String originURLSSH = "git@github.com:" + remoteURL.substring(19) + ".git";
        cfg.setString("remote", "origin", "url", originURL);
        try {
            cfg.save();
            LOGGER.info(() -> "Added " + originURL + " as origin remote url to the local repository.");
        } catch (IOException e) {
            LOGGER.severe("Could not add the remote url to the local repository.\nExeting...");
            System.exit(0);
            e.printStackTrace();
        }

    }
}