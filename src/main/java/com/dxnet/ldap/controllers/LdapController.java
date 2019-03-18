/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.dxnet.ldap.controllers;

import com.dxnet.ldap.commons.utils.FilterUtils;
import com.dxnet.ldap.database.entities.Usex;
import com.dxnet.ldap.exception.LdapException;
import com.dxnet.ldap.exception.ResourceNotFoundException;
import com.dxnet.ldap.services.LdapServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * The type LdapSchema controllers.
 *
 * @author Givantha Kalansuriya
 */
@RestController
@RequestMapping("/api/v1/ldap/")
public class LdapController {


    @Autowired
    LdapServiceImpl ldapService;


    /**
     * @param group - String groupA = "CN=Users" or String groupB = "OU=Utilizadores";
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("get/all/group/{group}")
    public ResponseEntity<List<Usex>> getAllByGroup(@PathVariable String group) throws ResourceNotFoundException {
        return new ResponseEntity<>(ldapService.findAllByFilter(group, FilterUtils.getFilterEncode(new String[] {"person"}), null), HttpStatus.ACCEPTED);
    }

    @GetMapping("get/user/{userDn}")
    public ResponseEntity<Usex> getUsers(@PathVariable String userDn) throws ResourceNotFoundException {
        Usex usex = new Usex();
        usex.setDn(userDn);
        return new ResponseEntity<>(ldapService.findByPrimaryKey(usex), HttpStatus.ACCEPTED);
    }

    @PutMapping("update/user")
    public ResponseEntity<Usex> udpate(@RequestBody Usex usex) throws ResourceNotFoundException {
        ldapService.update(usex);
        return new ResponseEntity<>(usex, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/user")
    public ResponseEntity<Usex> delete(@RequestBody Usex usex) throws ResourceNotFoundException {
        ldapService.delete(usex);
        return new ResponseEntity<>(usex, HttpStatus.ACCEPTED);
    }


}
