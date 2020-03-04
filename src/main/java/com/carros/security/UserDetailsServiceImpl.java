package com.carros.security;

import com.carros.domain.User;
import com.carros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userRepo.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException(" user not found");
        } // acessando a senha do banco
        return user;
    }
}

/*        ACESSO NÃO DINÂMICO                                                                  //acessando a senha do banco
return org.springframework.security.core.userdetails.User.withUsername(username).password(user.getSenha()).roles("USER").build();// senha de acesso*/

/*if (username.equals("user")){
    return User.withUsername(username).password(encoder.encode("admin")).roles("USER").build();// senha de acesso
}else if (username.equals("admin")){
    return User.withUsername(username).password(encoder.encode("admin")).roles("USER", "ADMIN").build();
}*/

/*  .withUser("root").password
.and()
.withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN"); // Usuário ADMIN

return null;*/
