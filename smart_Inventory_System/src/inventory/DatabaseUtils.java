package inventory;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DatabaseUtils {
    private static final String DB_NAME = "inventory_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Rakshitha$26"; // replace with your MySQL password

    public static void backupDatabase(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Backup File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            String backupCommand = String.format("mysqldump -u%s -p%s %s -r \"%s\"",
                    DB_USER, DB_PASSWORD, DB_NAME, file.getAbsolutePath());

            try {
                Process process = Runtime.getRuntime().exec(backupCommand);
                int processComplete = process.waitFor();

                if (processComplete == 0) {
                    System.out.println("✅ Backup successful.");
                } else {
                    System.out.println("❌ Backup failed.");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("❌ Error during backup.");
            }
        }
    }

    public static void restoreDatabase(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Backup File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String[] restoreCommand = new String[]{
                    "mysql", "-u" + DB_USER, "-p" + DB_PASSWORD, DB_NAME,
                    "-e", " source " + file.getAbsolutePath()
            };

            try {
                Process process = Runtime.getRuntime().exec(restoreCommand);
                int processComplete = process.waitFor();

                if (processComplete == 0) {
                    System.out.println("✅ Restore successful.");
                } else {
                    System.out.println("❌ Restore failed.");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("❌ Error during restore.");
            }
        }
    }
}
