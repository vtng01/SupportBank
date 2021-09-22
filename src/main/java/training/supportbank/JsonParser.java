package training.supportbank;

import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileReader;
import java.util.ArrayList;

public class JsonParser {
    private String fileName;
    private ArrayList<String> content;
    private JsonTransaction[] myTransactions;
    private static final Logger LOGGER = LogManager.getLogger();

    public JsonParser(String initFileName) {
        this.content = new ArrayList<>();
        this.fileName = initFileName;
    }

    private void convertToList() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            LOGGER.log(Level.DEBUG, "Success open file: " + this.fileName);
            // this is a list of JsonTransaction objects
            myTransactions = gson.fromJson(reader, JsonTransaction[].class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.log(Level.DEBUG, "Fail open file: " + this.fileName);
        }

        // obtain the arraylist
        for (JsonTransaction transaction: myTransactions) {
            content.add(transaction.toString());
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
