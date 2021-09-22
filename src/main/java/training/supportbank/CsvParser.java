package training.supportbank;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class CsvParser {
    private String fileName;
    private ArrayList<String> content;
    private static final Logger LOGGER = LogManager.getLogger();
    public CsvParser(String initFileName) {
        this.fileName = initFileName;
        this.content = new ArrayList<>();
    }

    private void convertToList() {

        try (Scanner scanner = new Scanner(Paths.get(fileName))) {
            LOGGER.log(Level.DEBUG, "Success open file: " + this.fileName);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                content.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            LOGGER.log(Level.DEBUG, "Fail open file: " + this.fileName);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<String> getContent() {
        convertToList();
        return content;
    }
}
