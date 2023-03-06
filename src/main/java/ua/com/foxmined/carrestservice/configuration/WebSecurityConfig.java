package ua.com.foxmined.carrestservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.foxmined.carrestservice.service.userdetailsservice.UserDetailsServiceImpl;
import ua.com.foxmined.carrestservice.utils.EndPoints;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        List<String> endPoints = EndPoints.getEndpointForAllUsers();

        for (var currentEndpoints : endPoints) {
            http.authorizeRequests().mvcMatchers(currentEndpoints).permitAll();
        }

        List<String> endPointsForAuthorizedUsers = EndPoints.getEndpointForAuthorizedUsers();

        for (var currentEndpoints : endPointsForAuthorizedUsers) {
            http.authorizeRequests().mvcMatchers(currentEndpoints).access("hasAnyRole('ROLE_USER')");
        }

        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

}
