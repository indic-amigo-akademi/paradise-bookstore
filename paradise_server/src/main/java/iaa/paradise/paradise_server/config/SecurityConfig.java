package iaa.paradise.paradise_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

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

    /**
     * Configures the InMemoryUserDetailsManager bean with the provided user details service.
     *
     * @param userDetailsService the user details service to be used by the InMemoryUserDetailsManager
     * @return the configured InMemoryUserDetailsManager bean
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

    /**
     * Configures the message source bean with the provided base name and default encoding.
     *
     * @return the configured message source bean
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:validation");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Configures the validator factory bean with the provided message source.
     *
     * @param messageSource the message source bean to be used by the validator factory
     * @return the configured validator factory bean
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }
}
