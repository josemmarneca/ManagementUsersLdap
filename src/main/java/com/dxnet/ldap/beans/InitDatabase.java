package com.dxnet.ldap.beans;

import com.dxnet.ldap.commons.utils.FilterUtils;
import com.dxnet.ldap.configs.LdapApplicationProperties;
import com.dxnet.ldap.database.entities.Usex;
import com.dxnet.ldap.services.LdapServiceImpl;
import com.dxnet.ldap.services.UsexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitDatabase implements CommandLineRunner {


    @Autowired
    LdapServiceImpl ldapService;


    @Autowired
    UsexService usexService;

    @Autowired
    LdapApplicationProperties ldapApplicationProperties;

    @Override
    public void run(String... args) throws Exception {
        //activeDirectoryService.create(newUsex);
        String filterEncode = FilterUtils.getFilterEncode(ldapApplicationProperties.getLoadFilter());
        List<Usex> usexList = new ArrayList<>();
        for (String group : ldapApplicationProperties.getLoadGroups()) {
            usexList.addAll(ldapService.findAllByFilter(group, filterEncode, "INIT_DATABASE"));
        }


/*
    List<Usex> usexList1 = ldapService.getAllByFilter(filterEncode);
        for (Usex user : usexList1) {
            user.setCreatedBy("InitDatabase");
        }
        */

        usexService.saveAll(usexList);
    }
}
