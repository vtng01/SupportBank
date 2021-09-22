package training.supportbank;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    private static BookOfAccounts book = new BookOfAccounts();
    private static String fileName = "Transactions2013.json";

    public static void main(String args[]) throws IOException {
        LOGGER.log(Level.DEBUG, "Start of program");

        int extensionOption = checkExtension(fileName);
        System.out.println(extensionOption);

        if (extensionOption == 1) {

            String dateFormat = "dd/MM/yyyy";
            ArrayList<String> content = new CsvParser(fileName).getContent();
            extractData2(content, dateFormat);

        } else if (extensionOption == 2) {

            String dateFormat = "yyyy-MM-dd";
            ArrayList<String> content = new JsonParser(fileName).getContent();
            extractData2(content, dateFormat);

        }

        UserInterface();
        LOGGER.log(Level.INFO, "End of program");
    }


    private static void extractData2(ArrayList<String> initContent, String initDateFormat) {
        DateFormat myDateFormat = new SimpleDateFormat(initDateFormat);
        for (String line: initContent) {
            generateTransaction(line, initDateFormat);
        }

    }

    // check file extension
    private static int checkExtension(String fileName) {

        int indexOfDot = fileName.indexOf(".");

        String fileExt = fileName.substring(indexOfDot + 1, fileName.length()).toLowerCase();
        LOGGER.log(Level.INFO, "File extension: " + fileExt);
        if (fileExt.equals("csv")) {
            return 1;
        } else if (fileExt.equals("json")) {
            return 2;
        } else {
            return 0;
        }
    }

    private static void UserInterface() {
        Scanner sc = new Scanner(System.in);

        // loop to ask for user interactions
        while (true) {
            System.out.println("Please select options by typing the following:");
            System.out.println("'List All' - to print the account balance of everyone.");
            System.out.println("'List [Account]' - where [Account] is the name of the person you wish to see their log. ");
            System.out.println("Enter nothing to exit");
            String input = sc.nextLine();

            // break condition
            if (input.isEmpty()) {
                LOGGER.log(Level.INFO, "Exit Program");
                break;
            }

            if (input.equals("List All")) {
                System.out.println(book);
                LOGGER.log(Level.INFO, "Successfully listed all accounts and balance");
                continue;
            }

            if (input.startsWith("List")) {

                String accountName = input.substring(4,input.length()).trim();

                if (book.getNames().contains(accountName)) {

                    LOGGER.log(Level.INFO, "Successful retrieve: " + accountName);
                    System.out.println(book.getAccount(accountName)); // to show current balance
                    book.getAccount(accountName).printLog(); // to print the log

                } else {
                    LOGGER.log(Level.WARN, "Fail to retrieve: " + accountName);
                }

                continue;
            }
        }
        // end of loop for user interaction
    }

    // method to make a transaction object
    private static void generateTransaction(String initTransaction, String dateFormat) {
        // split each line into parts
        // ---> [date, client, worker, narrative, amount]
        //        0,     1     ,  2    ,  3 ,      4
        String[] splitData = initTransaction.split(",");
        DateFormat myDateFormat = new SimpleDateFormat(dateFormat);


        try {
            // ******************* set up *****************
            // start off checking date and amount
            Date date = myDateFormat.parse(splitData[0]);
            BigDecimal amount =new BigDecimal(splitData[4]);
            // once passed set up, proceed to create accounts
            for (String name: Arrays.copyOfRange(splitData,1,3)) {
                // if account not found then create new account
                if (!book.getNames().contains(name)) {
                    book.addAccount(name, new Account(name));
                }
            }
            // get accounts

            Account client = book.getAccount(splitData[1]);
            Account worker = book.getAccount(splitData[2]);

            // **************** making the transaction object ***********
            // make transaction
            Transaction transaction = new Transaction(date, client, worker, splitData[3], amount);


            client.makePayment(transaction.getAmount(), transaction);
            worker.receivePayment(transaction.getAmount(), transaction);

        } catch (Exception e) {
            LOGGER.log(Level.ERROR,fileName + ": "+ e.getMessage());
        }
        }
    }

