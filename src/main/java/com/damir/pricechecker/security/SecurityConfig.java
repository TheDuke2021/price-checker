package com.damir.pricechecker.security;


import com.damir.pricechecker.data.AccountRepository;
import com.damir.pricechecker.models.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/profile").hasRole("USER")
                .requestMatchers("/register").anonymous()
                .requestMatchers("/login").anonymous()
                .requestMatchers("/**").permitAll()


                .and()
                .csrf().disable()

                .headers().frameOptions().disable()

                .and().formLogin().loginPage("/login").defaultSuccessUrl("/profile", true)

                .and().logout().logoutSuccessUrl("/")

                .and()
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }*/

    @Bean
    public UserDetailsService userDetailService(AccountRepository accountRepository) {
        return username -> {
            Account account = accountRepository.findByUsername(username);
            if(account != null) {
                return account;
            }
            throw new UsernameNotFoundException("User '" +
                    username + "' not found");
        };
    }

}