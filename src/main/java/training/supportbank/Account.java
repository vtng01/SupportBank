package training.supportbank;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Account {

    private BigDecimal balance;
    private String name;
    private ArrayList<Transaction> log;

    public Account(String initName) {
        this.name = initName;
        this.balance = new BigDecimal(0);
        this.log = new ArrayList<>();
    }

    public Account(String initName, BigDecimal initBalance) {
        this.name = initName;
        this.balance = initBalance;
    }

    public String getName() {
        return this.name;
    }

    public void makePayment(BigDecimal amount, Transaction transaction) {
        if (amount.compareTo(new BigDecimal(0)) >= 0) {
            // making payment means our balance decrease
            this.balance = this.balance.subtract(amount);
            this.log.add(transaction);
        }
    }

    public void receivePayment(BigDecimal amount, Transaction transaction) {
        if (amount.compareTo(new BigDecimal(0)) >= 0) {
            // receiving payment means our balance increase
            this.balance = this.balance.add(amount);
            this.log.add(transaction);
        }
    }

    public String toString() { return this.name + ": " + this.balance; }

    public BigDecimal getBalance() { return this.balance; }

    public ArrayList<Transaction> getLog() { return this.log; }

    public void printLog() { this.log.stream().forEach(System.out::println); }
}

