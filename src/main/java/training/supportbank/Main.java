package training.supportbank;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.nio.file.Files;
import java.util.ArrayList;


public class Main {
    public static void main(String args[]) throws IOException {
        // Your code here!
        // System.out.println("Test!");
        String fileName = "Transactions2014.csv";
        BookOfAccounts book = new BookOfAccounts();

        extractData(fileName, book);

        Scanner sc = new Scanner(System.in);

        // loop to ask for user interactions
        while (true) {
            System.out.println("Please select options by typing the following:");
            System.out.println("'List All' - to print the account balance of everyone.");
            System.out.println("'List [Account]' - where Account is the name of the person you wish to see their log. ");
            System.out.println("Enter nothing to exit");
            String input = sc.nextLine();

            // break condition
            if (input.isEmpty()) {
                break;
            }

            if (input.equals("List All")) {
                System.out.println(book);
            }

            if (input.contains("List")) {
                String temp = input.substring(4,input.length()).trim();
                if (book.getNames().contains(temp)) {
                    System.out.println(book.getAccount(temp));
                    System.out.println(book.getAccount(temp).getLog());
                }
            }
        }
        //System.out.println(book);
        //System.out.println(book.getAccount("Jon A").getLog());


    }

    private static void extractData(String fileName, BookOfAccounts book) {
        try (Scanner scanner = new Scanner(Paths.get(fileName))) {
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                decompose(scanner.nextLine(), book);
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private static void decompose(String transaction, BookOfAccounts book) {
        // split line of each into parts
        String[] parts = transaction.split(",");

        // money
        double amount = Double.valueOf(parts[4]);

        // if don't have an account then set up an account
        for (String name: Arrays.copyOfRange(parts,1,3)) {
            if (!book.getNames().contains(name)) {
                book.addAccount(name, new Account(name));
            }
        }

        Account client = book.getAccount(parts[1]);
        Account worker = book.getAccount(parts[2]);
        client.makePayment(amount, transaction);
        worker.receivePayment(amount, transaction);

        }

    }

