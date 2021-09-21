package training.supportbank;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;


/*
################## order of event
read data line by line from file
for each line, break it apart and make a transaction object along with the prerequisites like accounts etc..

##################
my idea of failing gracefully is to ignore the bad input and continue processing all the good ones

##################  ideas about classes and methods
generateTransaction method - to create the accounts and make transaction objects

BookOfAccounts - links together accounts and name
Transaction - just to make instances of each transaction log
Account - personal account info
 */

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    private static BookOfAccounts book = new BookOfAccounts();
    private static String fileName = "DodgyTransactions2015.csv";

    public static void main(String args[]) throws IOException {
        LOGGER.log(Level.DEBUG, "Start of program");
        // Your code here!
        // System.out.println("Test!");

        extractData(fileName);

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
                LOGGER.log(Level.INFO, "Exited Program");
                break;
            }

            if (input.equals("List All")) {
                System.out.println(book);
                LOGGER.log(Level.INFO, "Successfully listed all accounts and balance");
            } else if (input.startsWith("List")) {

                String accountName = input.substring(4,input.length()).trim();

                if (book.getNames().contains(accountName)) {

                    LOGGER.log(Level.INFO, "Successful retrieve: " + accountName);
                    System.out.println(book.getAccount(accountName)); // to show current balance
                    book.getAccount(accountName).printLog(); // to print the log

                } else {
                    LOGGER.log(Level.WARN, "Invalid account: " + accountName);
                }
            }
        }
        // end of loop for user interaction
    }

    private static void extractData(String fileName) {
        try (Scanner scanner = new Scanner(Paths.get(fileName))) {
            LOGGER.log(Level.INFO, "Successfully opened: " + fileName);
            scanner.nextLine();
            // counter for current line on the data file
            int counter = 1;
            while (scanner.hasNextLine()) {
                counter++;       // increment line counter
                generateTransaction(scanner.nextLine(), counter);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Could not open " + fileName +": " +e.getMessage());
            System.out.println("Error : " + e.getMessage());
        }
    }

    // method to make a transaction object
    private static void generateTransaction(String initTransaction, int counter) {
        // split each line into parts
        // ---> [date, client, worker, narrative, amount]
        //        0,     1     ,  2    ,  3 ,      4
        String[] splitData = initTransaction.split(",");
        DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // ******************* set up *****************
            // start off checking date and amount
            Date date = myDateFormat.parse(splitData[0]);
            BigDecimal amount =new BigDecimal(splitData[4]);

            // once passed set up, proceed to create accounts
            for (String name: Arrays.copyOfRange(splitData,1,3)) {
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
            LOGGER.log(Level.ERROR,fileName + ", line " + counter +": "+ e.getMessage());
        }
        }
    }

