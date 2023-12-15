package com.example.dw_huy.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class DBContext {
    // ... existing code ...

    private static void copyPropertiesFile(String sourceRelativePath, String destinationPath) {
        Path source = Paths.get(sourceRelativePath).toAbsolutePath();  // Convert to absolute path
        Path destination = new File(destinationPath).toPath();

        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Properties file copied successfully.");
        } catch (NoSuchFileException e) {
            System.err.println("Error: Source file not found. Please check the source path.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error copying properties file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Specify the relative source path and destination path
        String sourceRelativePath = "config.properties";
        String destinationPath = "src/main/resources/config.properties";

        // Copy the properties file to the resources folder
        copyPropertiesFile(sourceRelativePath, destinationPath);

    }
}
