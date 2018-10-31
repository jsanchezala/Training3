package springBoot.mvc.practica3.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import wecc.examples.springboot.security.advanced.model.User;
import wecc.examples.springboot.security.advanced.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		User user = userRepository.findById(username).orElseThrow(() -> new BadCredentialsException("User not found"));
		if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}

		return new UsernamePasswordAuthenticationToken(username, password, user.getRoles());

	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}

