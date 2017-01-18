package sec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.domain.MonkepoHunter;
import sec.project.repository.AccountRepository;
import sec.project.repository.MonkepoHunterRepository;

@Service
public class MonkepoHunterService {

    @Autowired
    private MonkepoHunterRepository monkepoHunterRepository;

    @Autowired
    private AccountRepository accountRepository;

    public MonkepoHunterService() {

    }

    public MonkepoHunter getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            MonkepoHunter authenticatedUser = monkepoHunterRepository.findByUsername(auth.getName());
            return authenticatedUser;
        }
        return null;
    }

    //check if the page user wants to redirect is allowed
    public boolean whiteList(String url) {
        if (url.equals("/") || url.equals("/monkepo") || url.equals("/hunters")
                || url.contains("/monkepoHunter/")) {
            return true;
        }
        return false;
    }

    public void transfer(Account accountFrom, Account accountTo, double amount) {
        accountFrom.setAmount(accountFrom.getAmount() - amount);
        accountTo.setAmount(accountTo.getAmount() + amount);

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }
}
