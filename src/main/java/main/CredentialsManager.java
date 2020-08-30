package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CredentialsManager {
    private static final Logger LOGGER = Logger.getLogger(CredentialsManager.class.getName());
    public String username = null;
    public String password = null;
    public String email = null;

    public CredentialsManager() {
        File credFile = new File(".credentials");
        if (credFile.exists() && credFile.canRead()) {
            readCredentialFile(credFile);
        } else {
            askUserForCredentials();
            createCredentialFile(credFile);
            writeToCredFile(credFile);
        }
    }

    /**
     * Creates the credentials file.
     * 
     * @param credFile
     */
    private void createCredentialFile(File credFile) {
        try {
            if (credFile.createNewFile()) {
                LOGGER.log(Level.INFO, "Credentials file created.");
            }
            LOGGER.log(Level.SEVERE, "Credentials file could not be created.");

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Credentials file could not be created.");
            e.printStackTrace();
        }
    }

    /**
     * Writes the username and password to The credentials file
     * 
     * @param credFile
     */
    private void writeToCredFile(File credFile) {
        try (FileWriter wirteToCredFile = new FileWriter(credFile)) {
            wirteToCredFile.write(this.username + "\n" + this.password + "\n" + this.email);
            LOGGER.log(Level.INFO, "Username, password and email written to the credentials file.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not wirte to the credentials file");
            e.printStackTrace();
        }
    }

    /**
     * Asks the user for credentials. git config and github username must match.
     */
    private void askUserForCredentials() {
        LOGGER.log(Level.SEVERE, "Please enter your username: ");
        Scanner stdin = new Scanner(System.in);
        this.username = stdin.nextLine();
        LOGGER.log(Level.SEVERE, "and your password: ");
        this.password = stdin.nextLine();
        LOGGER.log(Level.SEVERE, "and your email: ");
        this.email = stdin.nextLine();
        stdin.close();
    }

    /**
     * Trys to read credentials from file, if it fails it asks credentials.
     * 
     * @param credFile
     */
    private void readCredentialFile(File credFile) {
        try (Scanner read = new Scanner(credFile)) {
            if (read.hasNext()) {
                this.username = read.nextLine();
                if (read.hasNextLine()) {
                    this.password = read.nextLine();
                    if (read.hasNextLine()) {
                        this.email = read.nextLine();
                    } else {
                        // email missing
                        askUserForCredentials();
                    }
                } else {
                    // password missing
                    askUserForCredentials();
                }
            } else {
                // username missing
                askUserForCredentials();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not read from credential file");
            askUserForCredentials();
            e.printStackTrace();
        }

    }
}