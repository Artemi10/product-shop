package devanmejia.productshop.security;

import devanmejia.productshop.security.jwt.JWTProvider;
import devanmejia.productshop.security.jwt.JWTSecurityConfig;
import devanmejia.productshop.security.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@ComponentScan("devanmejia")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/shop/admin/**").hasAuthority(Role.ROLE_ADMIN.name())
                .antMatchers("/shop/order/*/cartProducts").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name())
                .antMatchers("/shop/stockProducts/**", "/shop/account/**").permitAll()
                .anyRequest().hasAuthority(Role.ROLE_USER.name())
                .and().apply(new JWTSecurityConfig(jwtProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
