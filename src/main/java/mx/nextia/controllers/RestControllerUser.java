/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.controllers;

import mx.nextia.dto.CreateUserDTO;
import mx.nextia.entities.User;
import mx.nextia.repositories.RepositoryUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author luisucan
 */
@RestController
@RequestMapping("/users")
public class RestControllerUser {
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private RepositoryUser repositoryUser;
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> findOne(@PathVariable int userId){
        try {
            User user =  repositoryUser.findById(userId)
                    .orElseThrow(()-> new Exception("usuario no encontrado"));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody CreateUserDTO userDTO) {
        try {
            User user = modelMapper.map(userDTO, User.class);
            user = repositoryUser.save(user);
            
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
