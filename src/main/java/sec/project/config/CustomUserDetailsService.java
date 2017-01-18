package sec.project.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sec.project.domain.MonkepoHunter;
import sec.project.repository.MonkepoHunterRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MonkepoHunterRepository monkepoHunterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MonkepoHunter monkepoHunter = monkepoHunterRepository.findByUsername(username);
        if (monkepoHunter == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        String authority = monkepoHunter.getAuthority();
        String password = monkepoHunter.getPassword();

        return new org.springframework.security.core.userdetails.User(
                username,
                password,
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority(authority)));
    }
}
