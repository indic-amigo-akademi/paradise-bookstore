package iaa.paradise.paradise_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import iaa.paradise.paradise_server.service.UserService;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http
        // .csrf(csrf -> csrf.disable())
        // .authorizeHttpRequests((requests) -> requests
        // .requestMatchers("/", "/assets/**").permitAll()
        // .anyRequest().authenticated())
        // .formLogin((form) -> form
        // .loginProcessingUrl("/auth/login")
        // .permitAll())
        // .logout((logout) -> logout
        // .invalidateHttpSession(true)
        // .clearAuthentication(true)
        // .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
        // .logoutSuccessUrl("/")
        // .deleteCookies("JSESSIONID")
        // .permitAll());
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
