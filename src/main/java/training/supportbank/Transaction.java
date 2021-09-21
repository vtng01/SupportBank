package training.supportbank;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private Date date;
    private Account client;
    private Account worker;
    private String narrative;
    private BigDecimal amount;

    public Transaction(Date initDate, Account initClient, Account initWorker, String initNarrative, BigDecimal initAmount) {
        this.date = initDate;
        this.client = initClient;
        this.worker = initWorker;
        this.narrative = initNarrative;
        this.amount = initAmount;
    }

    public Account getWorker() {
        return worker;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getNarrative() {
        return narrative;
    }

    public Account getClient() {
        return client;
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tempDate = myDateFormat.format(this.date);
        return String.format("%s, %s, %s, %s, %s", tempDate, this.client.getName(), this.worker.getName(), this.narrative, this.amount);
    }
}
