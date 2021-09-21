package training.supportbank;

public class Account {
    private double balance;
    private String name;
    private String log;

    public Account(String initName) {
        this.name = initName;
        this.balance = 0;
        this.log = "";
    }

    public Account(String initName, double initBalance) {
        this.name = initName;
        this.balance = initBalance;
        this.log = "";
    }

    public String getName() {
        return this.name;
    }

    public void makePayment(double amount, String transaction) {
        if (amount > 0) {
            // making payment means our balance decrease
            this.balance -= amount;
            this.log += transaction + "\n";
        }

    }

    public void receivePayment(double amount, String transaction) {
        if (amount > 0) {
            // receiving payment means our balance increase
            this.balance += amount;
            this.log += transaction + "\n";
        }
    }

    public String toString() {
        double temp = Math.round(this.balance * 100.0) / 100.0; // this is to round to 2 dp
        return this.name + ": " + temp;
    }

    public String getLog() {
        return this.log;
    }




}
