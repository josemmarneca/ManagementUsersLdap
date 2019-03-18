package com.dxnet.ldap.database.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntityActiveAudit extends BaseEntityAudit {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delete_at", nullable = true)
    protected Date deletedAt;

    @Column(name = "delete_by", nullable = true)
    protected String deletedBy;

    @Column(name = "active", nullable = false)
    protected Boolean active = true;

}