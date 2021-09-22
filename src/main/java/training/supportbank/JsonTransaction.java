package training.supportbank;

import java.math.BigDecimal;

public class JsonTransaction {
    private String date;
    private String fromAccount;
    private String toAccount;
    private String narrative;
    private BigDecimal amount;

    public String getDate() {
        return date;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getNarrative() {
        return narrative;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s,%s", date,fromAccount,toAccount,narrative,amount);
    }
}
