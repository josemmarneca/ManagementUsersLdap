package com.dxnet.ldap.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class LdapApplicationProperties {

    @Value("${ldap.url}")
    private String url;
    @Value("${ldap.principal}")
    private String principal;
    @Value("${ldap.password}")
    private String password;
    @Value("${ldap.base}")
    private String base;
    @Value("${ldap.referral}")
    private String referral;

    @Value("${ldap.load.groups}")
    private String loadGroups;
    @Value("${ldap.load.filter}")
    private String loadFilter;


    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String[] getLoadGroups() {
        return loadGroups.split(",");
    }

    public void setLoadGroups(String loadGroups) {
        this.loadGroups = loadGroups;
    }

    public String[] getLoadFilter() {
        return loadFilter.split(",");
    }

    public void setLoadFilter(String loadFilter) {
        this.loadFilter = loadFilter;
    }
}
