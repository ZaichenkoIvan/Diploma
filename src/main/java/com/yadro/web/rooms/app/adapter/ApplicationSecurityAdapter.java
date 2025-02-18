package com.yadro.web.rooms.app.adapter;

import com.yadro.web.rooms.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService accountService;

    @Value("${app.key}")
    private String applicationSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/account/autologin").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/account/delete").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/account/register").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/account/list").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/account/list/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/university").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/university/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/hostel").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/hostel/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/room").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/room/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/event/list/all").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/calendar/room").permitAll()
                .antMatchers("/calendar/room/**").permitAll()
                .antMatchers("/calendar/events").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/welcome").failureUrl("/welcome?errorl").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/welcome")
                //     .and()

                .and()
                .rememberMe().key(applicationSecret)
                .tokenValiditySeconds(31536000);

        http.csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
