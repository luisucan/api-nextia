/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.security;

import mx.nextia.entities.User;
import mx.nextia.repositories.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author luisucan
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private RepositoryUser repositoryUser;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = repositoryUser.findOneByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("usuario o contraseña incorrectos"));
        
        return new UserDetailsImpl(user);
    }
    
}
