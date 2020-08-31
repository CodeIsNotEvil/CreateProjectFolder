package main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.exec.OS;

public class FileHandler {
    private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());

    /**
     * Creates and returns the Os Specific Path.
     * 
     * @return the Os Specific Path at wich the folder gets created
     */
    public String getOsSpecificFolderPath() {
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
    public void createFolder(final String name) {
        final String path = getOsSpecificFolderPath();
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
}