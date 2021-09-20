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
        int counter = 1;

        try (Scanner scanner = new Scanner(Paths.get(fileName))) {
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                decompose(scanner.nextLine(), book);
                counter++;
                System.out.println(counter);
                System.out.println(book.getAccount("Jon A"));
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }




        System.out.println(book);
        System.out.println(book.getAccount("Jon A").getLog());


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

