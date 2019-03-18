package com.dxnet.ldap.controllers;


import com.dxnet.ldap.database.entities.Usex;
import com.dxnet.ldap.exception.LdapException;
import com.dxnet.ldap.exception.ResourceNotFoundException;
import com.dxnet.ldap.services.LdapServiceImpl;
import com.dxnet.ldap.services.UsexService;
import com.dxnet.ldap.commons.utils.UsexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    @Autowired
    LdapServiceImpl ldapService;

    @Autowired
    UsexService usexService;


    @PostMapping("login")
    public ResponseEntity<Boolean> login(@RequestBody Usex usex) throws LdapException, ResourceNotFoundException {
        Usex userDatabase = usexService.findByNameActive(usex.getCn());
        userDatabase.setSecret(usex.getSecret());
        return new ResponseEntity<>(ldapService.authenticate(userDatabase), HttpStatus.ACCEPTED);
    }


    @GetMapping("get/all")
    public ResponseEntity<List<Usex>> getAllUsers() {
        return new ResponseEntity<>(usexService.findAllActive(), HttpStatus.ACCEPTED);
    }

    @GetMapping("get/{userName}")
    public ResponseEntity<Usex> getUsers(@PathVariable String userName) throws ResourceNotFoundException {
        Usex usex = usexService.findByNameActive(userName);
        return new ResponseEntity<>(usex, HttpStatus.ACCEPTED);
    }


    @PostMapping("create")
    public ResponseEntity<Usex> createUser(@Valid @RequestBody Usex usex) throws LdapException {
        UsexUtils.setParametersToCreateDefaultUser(usex);
        ldapService.create(usex);
        Usex userCreated = usexService.save(usex);
        return new ResponseEntity<>(userCreated, HttpStatus.ACCEPTED);
    }


    @PutMapping("update")
    public ResponseEntity<Usex> update(@Valid @RequestBody Usex userParametersToUpdate) throws LdapException, ResourceNotFoundException {

        Usex usexDatabase = usexService.findByNameActive(userParametersToUpdate.getCn());
        UsexUtils.updateUserWithParameters(userParametersToUpdate, usexDatabase);

        ldapService.update(usexDatabase);
        Usex userUpdated = usexService.save(usexDatabase);

        return new ResponseEntity<>(userUpdated, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("delete")
    public ResponseEntity<Usex> delete(@RequestBody Usex usex) throws LdapException, ResourceNotFoundException {
        Usex usexDatabase = usexService.findByNameActive(usex.getCn());
        ldapService.delete(usexDatabase);
        usexService.delete(usexDatabase);
        return new ResponseEntity<>(usex, HttpStatus.ACCEPTED);
    }
}
