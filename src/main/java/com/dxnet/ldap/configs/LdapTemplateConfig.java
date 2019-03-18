package com.dxnet.ldap.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

@Configuration
public class LdapTemplateConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LdapApplicationProperties ldapApplicationProperties;

    @Bean(name = "ldapTemplate")
    public LdapTemplate ldapTemplate() {
        LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource());
        return ldapTemplate;
    }

    @Bean(name = "contextSource")
    public LdapContextSource ldapContextSource() {

        if (isConfigurationValid(ldapApplicationProperties.getUrl(), ldapApplicationProperties.getBase())) {
            LdapContextSource ldapContextSource = new LdapContextSource();
            ldapContextSource.setUrl(ldapApplicationProperties.getUrl());
            ldapContextSource.setBase(ldapApplicationProperties.getBase());
            ldapContextSource.setUserDn(ldapApplicationProperties.getPrincipal() + ","+ ldapApplicationProperties.getBase());
            ldapContextSource.setPassword(ldapApplicationProperties.getPassword());
            ldapContextSource.setReferral(ldapApplicationProperties.getReferral());
            // lcs.setPooled(false);
            // lcs.setDirObjectFactory(DefaultDirObjectFactory.class);
            ldapContextSource.afterPropertiesSet();

            return ldapContextSource;
        }
        return null;
    }

    @Bean(name = "dirContext")
    public DirContext dirContext() throws NamingException {
        DirContext ldapContext = new InitialDirContext(ldapEnv());
        return ldapContext;
    }

    @Bean(name = "ldapEnv")
    public Hashtable<String, String> ldapEnv() {
        Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);

        ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ldapEnv.put(Context.PROVIDER_URL, ldapApplicationProperties.getUrl());
        ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        ldapEnv.put(Context.SECURITY_PRINCIPAL, ldapApplicationProperties.getPrincipal() + ","+ ldapApplicationProperties.getBase());
        ldapEnv.put(Context.SECURITY_CREDENTIALS, ldapApplicationProperties.getPassword());
        //ldapEnv.put(Context.SECURITY_PROTOCOL, "ssl");
        //ldapEnv.put(Context.SECURITY_PROTOCOL, "simple");
        return ldapEnv;
    }

    public boolean isConfigurationValid(String url, String base) {
        if ((url == null) || url.isEmpty() || (base == null) || base.isEmpty()) {
            log.error("Warning! Your LDAP server is not configured.");
            log.info("Did you configure your LDAP settings in your application.yml?");
            return false;
        } else {
            return true;
        }
    }
}

