package sec.project.controller;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.MonkepoHunter;
import sec.project.repository.AccountRepository;
import sec.project.repository.MonkepoHunterRepository;
import sec.project.service.MonkepoHunterService;

@Controller
public class MonkepoHunterController {

    @Autowired
    private MonkepoHunterRepository monkepoHunterRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MonkepoHunterService monkepoHunterService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/monkepo";
    }

    //For testing, one account is created from the beginning
    @PostConstruct
    public void init() {
        MonkepoHunter evilUser = new MonkepoHunter("evilUser", "1234");
        evilUser.setPassword(this.passwordEncoder.encode(evilUser.getPassword()));
        evilUser.setIdCode(UUID.randomUUID().toString());

        Account account = new Account();
        account.setAccountCode("1337");
        evilUser.setAccount(account);

        accountRepository.save(account);
        monkepoHunterRepository.save(evilUser);
    }

    @RequestMapping(value = "/monkepo", method = RequestMethod.GET)
    public String frontPage() {
        return "index";
    }

    @RequestMapping(value = "/hunters", method = RequestMethod.GET)
    public String monkepoHunters(Model model, HttpSession session) {
        MonkepoHunter authenticatedUser = monkepoHunterService.getAuthenticatedUser();
        authenticatedUser.setIdCode(session.getId());
        monkepoHunterRepository.save(authenticatedUser);
        model.addAttribute("hunters", monkepoHunterRepository.findAll());
        model.addAttribute("idCode", authenticatedUser.getIdCode());
        return "hunters";
    }

    @RequestMapping(value = "/monkepo", method = RequestMethod.POST)
    public String register(@RequestParam String username, String password, HttpSession session) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        MonkepoHunter monkepoHunter = new MonkepoHunter(username, password);
        monkepoHunter.setPassword(this.passwordEncoder.encode(monkepoHunter.getPassword()));
        monkepoHunter.setAuthority("USER");
//        monkepoHunter.setIdCode(UUID.randomUUID().toString());
        monkepoHunter.setIdCode(session.getId());

        Account account = new Account();
        monkepoHunter.setAccount(account);

        accountRepository.save(account);
        monkepoHunterRepository.save(monkepoHunter);

        Authentication auth = this.authManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/monkepo";
    }

    @RequestMapping(value = "/monkepoHunter/{idCode}", method = RequestMethod.POST)
    public String redirectToUserPage(@PathVariable String idCode) {

        return "redirect:/monkepoHunter/" + idCode;
    }

    @RequestMapping(value = "/monkepoHunter/{idCode}", method = RequestMethod.GET)
    public String profilePage(Model model, @PathVariable String idCode) {
//        MonkepoHunter authenticatedUser = monkepoHunterService.getAuthenticatedUser();
//        if (!authenticatedUser.getIdCode().equals(idCode)) {
//            return "redirect:/hunters";
//        }
        model.addAttribute("monkepoHunter", monkepoHunterRepository.findByIdCode(idCode));
        return "monkepoHunter";
    }

    //Only users with authority ADMIN should be allowed to access here
    @RequestMapping(value = "/adminPage", method = RequestMethod.GET)
    public String adminPage() {

        return "adminPage";
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirect(@RequestParam String where) {

//        if (!monkepoHunterService.whiteList(where)) {
//            return "redirect:/";
//        }
        return "redirect:" + where;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transfer(@RequestParam double amount, String toAccount) {
        MonkepoHunter authenticatedUser = monkepoHunterService.getAuthenticatedUser();
        Account accountFrom = authenticatedUser.getAccount();
        Account accountTo = accountRepository.findByAccountCode(toAccount);

        monkepoHunterService.transfer(accountFrom, accountTo, amount);
        return "redirect:/transfer";
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public String transferSuccessfull() {

        return "transfer";
    }

    @RequestMapping(value = "/youWon", method = RequestMethod.GET)
    public String youWon() {

        return "youWon";
    }

    @RequestMapping(value = "/evilUser", method = RequestMethod.GET)
    public String evilUser(Model model) {

        model.addAttribute("evilUser", monkepoHunterRepository.findByUsername("evilUser"));
        return "evilUser";
    }

}
