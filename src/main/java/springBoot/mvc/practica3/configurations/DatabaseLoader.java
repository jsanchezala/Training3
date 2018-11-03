package springBoot.mvc.practica3.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import springBoot.mvc.practica3.model.User;
import springBoot.mvc.practica3.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseLoader {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initDatabase(){
        List<GrantedAuthority> adminRoles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN"));
        List<GrantedAuthority> userRoles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        userRepository.save(new User("root", "root1", adminRoles));
        userRepository.save(new User("user", "user1", userRoles));

    }

}
