package com.carros.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // Segurança por método
@EnableGlobalMethodSecurity(
        securedEnabled =
                true) // Desta forma, você habilita a visualização dos usuários ADM do Controller
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsService") // Encontra um identificador Service
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }

    @Override // Criação de método de segurança pra requisições
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Criação do algorítmo de encoder, se não o rest não funciona
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}

/*auth
.inMemoryAuthentication().passwordEncoder(encoder)
.withUser("root").password(encoder.encode("admin")).roles("USER") // senha de acesso
.and()
.withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN"); // Usuário ADMIN*/
