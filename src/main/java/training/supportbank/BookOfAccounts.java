package training.supportbank;

import java.util.HashMap;
import java.util.Set;

public class BookOfAccounts {
    HashMap<String, Account> book;

    public BookOfAccounts() {
        this.book = new HashMap<>();
    }

    public void addAccount(String name, Account a) {
        book.put(name, a);
    }

    public HashMap<String, Account> getBook() { return this.book;}

    public Account getAccount(String name) {return book.get(name);}

    public Set<String> getNames() { return book.keySet(); }

    public String toString() {
        String report = "";
        for (Account a: book.values()) {
            report += a.toString() + "\n";
        }
        return report;
    }
}
