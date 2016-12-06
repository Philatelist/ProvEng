package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.AbstractView;
import com.provectus.proveng.utils.view.EventView;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@JsonIdentityInfo(generator = JSOGGenerator.class)
public abstract class BaseEntity {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AbstractView.BasicInfoLevel.class)
    private long id;

    @Column(name = "sys_status", columnDefinition = "0")
    private int sysStatus;

    @Column(name = "create_dtm")
    private Date createDtm = new Date();

    @Column(name = "modify_dtm")
    @JsonView(EventView.BasicInfoLevel.class)
    private Date modifyDtm;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(int sysStatus) {
        this.sysStatus = sysStatus;
    }

    public Date getCreateDtm() {
        return createDtm;
    }

    public void setCreateDtm(Date createDtm) {
        this.createDtm = createDtm;
    }

    public Date getModifyDtm() {
        return modifyDtm;
    }

    public void setModifyDtm(Date modifyDtm) {
        this.modifyDtm = modifyDtm;
    }

    @PrePersist
    public void putDate() {
        if (createDtm == null) {
            createDtm = new Date();
        } else if (new Date().getTime() > (createDtm.getTime() + 10000))
            modifyDtm = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (id != that.id) return false;
        if (sysStatus != that.sysStatus) return false;
        if (createDtm != null ? !createDtm.equals(that.createDtm) : that.createDtm != null) return false;
        return modifyDtm != null ? modifyDtm.equals(that.modifyDtm) : that.modifyDtm == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + sysStatus;
        result = 31 * result + (createDtm != null ? createDtm.hashCode() : 0);
        result = 31 * result + (modifyDtm != null ? modifyDtm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", sysStatus=" + sysStatus +
                ", createDtm=" + createDtm +
                ", modifyDtm=" + modifyDtm +
                '}';
    }

}
