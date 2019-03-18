package com.dxnet.ldap.mappers;

import com.dxnet.ldap.commons.utils.LdapUtils;
import com.dxnet.ldap.database.entities.Usex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UsexAttributesMapper extends AbstractContextMapper   {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    private String from;

    public UsexAttributesMapper(String from){
        super();
        this.from = from;

    }

    public Usex mapFromContext(SearchResult searchResult) throws NamingException {
        Usex usex = LdapUtils.mapAttributesFromLdapToUsex(searchResult.getAttributes());
        usex.setDn(searchResult.getName());
        return usex;
    }



    @Override
    protected Usex doMapFromContext(DirContextOperations dirContextOperations) {
        try {
            Usex usex = LdapUtils.mapAttributesFromLdapToUsex(dirContextOperations.getAttributes());
            usex.setDn(dirContextOperations.getDn().toString());
            if(from != null) {
                usex.setCreatedBy(from);
            }
            return usex;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
