package sec.project.domain;

import java.util.UUID;
import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> {

    private double amount;
    private String accountCode;

    public Account() {
        this.amount = 100.0;
        this.accountCode = UUID.randomUUID().toString();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

}
