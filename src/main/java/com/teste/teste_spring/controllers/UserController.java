package com.teste.teste_spring.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.teste.teste_spring.models.User;
import com.teste.teste_spring.models.User.CreateUser;
import com.teste.teste_spring.models.User.UpdateUser;
import com.teste.teste_spring.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    
    //Substitui Construtor/ auto inicia a Classe
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity <User>findById(@PathVariable Long id){
        User obj = this.userService.findById(id);

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Validated(CreateUser.class)
    public ResponseEntity<Void>create(@Valid @RequestBody User obj){
        this.userService.create(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/id").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(UpdateUser.class)
    public ResponseEntity<Void> updade(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        this.userService.update(obj);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
