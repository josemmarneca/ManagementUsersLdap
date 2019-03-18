package com.dxnet.ldap.database.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;

@Entity
@Table(name = "usex")
@AttributeOverride(name = "id", column = @Column(name = "usex_id",
        nullable = false, columnDefinition = "BIGINT"))
public class Usex extends BaseEntityActiveAudit {


    @Column(name = "secret")
    private String secret;

    @Column(name = "dn", nullable = false)
    private String dn;
    /**
     * LDAP Schema
     */
    @Column(name = "whenCreated")
    public String whenCreated;

    @Column(name = "objectCategory")
    public String objectCategory;

    @Column(name = "mail")
    public String mail;

    @Column(name = "memberOf")
    public String memberOf;

    @Column(name = "objectClass")
    public String[] objectClass;

    @Column(name = "distinguishedName")
    public String distinguishedName;

    @Column(name = "primaryGroupID")
    public String primaryGroupID;

    @Column(name = "sAMAccountName")
    public String sAMAccountName;

    @Column(name = "sAMAccountType")
    public String sAMAccountType;



    @NotNull
    @Column(name = "cn", nullable = false)
    public String cn;

    @Column(name = "sn")
    public String sn;

    @Column(name = "description")
    public String description;

    @Column(name = "givenName")
    public String givenName;


    /**
     * Phones
     */

    @Column(name = "telephoneNumber")
    public String telephoneNumber;

    @Column(name = "homePhone")
    public String homePhone;





    public Usex() {
    }




    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * GETTERS SETTERS
     */



    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(String whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



    public String[] getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String[] objectClass) {
        this.objectClass = objectClass;
    }


    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }


    public String getPrimaryGroupID() {
        return primaryGroupID;
    }

    public void setPrimaryGroupID(String primaryGroupID) {
        this.primaryGroupID = primaryGroupID;
    }

    public String getSAMAccountName() {
        return sAMAccountName;
    }

    public void setSAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public String getSAMAccountType() {
        return sAMAccountType;
    }

    public void setSAMAccountType(String sAMAccountType) {
        this.sAMAccountType = sAMAccountType;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String memberOf) {
        this.memberOf = memberOf;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }



    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
}
