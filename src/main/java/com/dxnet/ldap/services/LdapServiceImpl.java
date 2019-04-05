package com.dxnet.ldap.services;

import com.dxnet.ldap.commons.utils.FilterUtils;
import com.dxnet.ldap.commons.utils.LdapUtils;
import com.dxnet.ldap.commons.utils.UsexUtils;
import com.dxnet.ldap.configs.LdapApplicationProperties;
import com.dxnet.ldap.database.entities.Usex;
import com.dxnet.ldap.exception.LdapException;
import com.dxnet.ldap.mappers.UsexAttributesMapper;
import com.dxnet.ldap.services.properties.ServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.ldap.core.*;
import org.springframework.ldap.filter.AbstractFilter;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class LdapServiceImpl {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    private final ServiceProperties serviceProperties;

    @Autowired
    DirContext dirContext;

    @Autowired
    LdapTemplate ldapTemplate;

    @Autowired
    UsexService usexService;

    @Autowired
    LdapApplicationProperties ldapApplicationProperties;


    public LdapServiceImpl(ServiceProperties serviceProperties){
        this.serviceProperties=serviceProperties;
    }

    public String msg(){
        return this.serviceProperties.getMsg();
    }
    /**
     * Get all user model from lDPA
     *
     * @return
     */
    public List<Usex> getAllByFilter(String filter) {
        List<Usex> userList = new ArrayList<>();
        SearchControls searchCtls = new SearchControls();
        //All atributes = null
        searchCtls.setReturningAttributes(null);
        //Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        int totalResults = 0;
        try {
            // Search for objects using the filter
            NamingEnumeration<SearchResult> answer =
                    dirContext.search(ldapApplicationProperties.getBase(), filter, searchCtls);

            //Loop through the search results
            while (answer.hasMoreElements()) {
                totalResults++;
                SearchResult sr = (SearchResult) answer.next();
                Usex newUser = new UsexAttributesMapper("LdapServiceImpl").mapFromContext(sr);
                userList.add(newUser);
            }
            dirContext.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public boolean authenticate(Usex user) {
        return ldapTemplate.authenticate(user.getDn(), FilterUtils.getFilterEncode(user.getObjectClass()), user.getSecret());
    }

    public void create(Usex user) throws LdapException {
        DirContextAdapter context = null;
        context = new DirContextAdapter(user.getDn());
        LdapUtils.mapToContext(user, context);
        ldapTemplate.bind(context);

    }

    public void update(Usex person) {
        String dn = person.getDn();
        DirContextOperations context = ldapTemplate.lookupContext(dn);
        LdapUtils.mapToContext(person, context);
        ldapTemplate.modifyAttributes(context);
    }

    public void delete(Usex person) {
        ldapTemplate.unbind(person.getDn());
    }

    public Usex findByPrimaryKey(Usex user) {
        String dn = user.getDn();
        return (Usex) ldapTemplate.lookup(dn, getContextMapper(null));
    }

    public List findByName(String name) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "user")).and(new WhitespaceWildcardsFilter("cn", name));
        return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper(null));
    }

    /**
     * Need to spcefic the group to search only in group
     *
     * @param group
     * @return
     */
    public List<Usex> findAllByFilter(String group, String filterEncode, String from) {
        return ldapTemplate.search(group, filterEncode, getContextMapper(from));
    }


    protected ContextMapper getContextMapper(String from) {
        return new UsexAttributesMapper(from);
    }


}
