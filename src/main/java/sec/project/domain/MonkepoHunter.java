package sec.project.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class MonkepoHunter extends AbstractPersistable<Long> {

    private String username;
    private String password;
    private String authority;
    private String idCode;

    @OneToOne
    private Account account;

    public MonkepoHunter() {
        super();
    }

    public MonkepoHunter(String username, String password) {
        this();
        this.username = username;
        this.password = password;
        this.authority = "USER";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
