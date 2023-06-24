package com.stori.datamodel.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author tantianyi
 * Date: 6/23/23
 * Time: 9:18 PM
 * Project Name: MiniCoreBank
 * Package Name: com.stori.datamodel.model
 */
@MappedSuperclass
public class BasicObj {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", updatable = false, nullable = false)
    public Date createTime;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="update_time", nullable = false)
    public Date updateTime;

    public BasicObj() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }
}
