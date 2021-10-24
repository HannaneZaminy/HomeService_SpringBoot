package ir.homeService.service;

import ir.homeService.data.domain.Users;
import ir.homeService.data.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public UserDetailsServiceImpl(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            //if (user.getUserRole().equals(UserRole.CUSTOMER)) {
            return User.withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getUserRole().name()).build();
//            }
//            if (user.getUserRole().equals(UserRole.EXPERT)) {
//                return User.withUsername(user.getEmail())
//                        .password(user.getPassword())
//                        .roles("EXPERT").build();
//
//            }
        }
        throw new UsernameNotFoundException(messageSource.getMessage("not.found.user", null, new Locale("en_us")));
    }
}
