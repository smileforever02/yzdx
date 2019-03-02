package com.yzdx.chemistry.healthrecovery.record;


import javax.persistence.*;
import java.util.Date;

@Entity
public class RecordDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "detailId", insertable = false, nullable = false, updatable = false)
    private Long detailId;

    private Long recordId;

    private double time;

    private double initCap;

    private double cap;

    private double deltaCapPerc;

    private double power;

    private Date createdDate;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getInitCap() {
        return initCap;
    }

    public void setInitCap(double initCap) {
        this.initCap = initCap;
    }

    public double getCap() {
        return cap;
    }

    public void setCap(double cap) {
        this.cap = cap;
    }

    public double getDeltaCapPerc() {
        return deltaCapPerc;
    }

    public void setDeltaCapPerc(double deltaCapPerc) {
        this.deltaCapPerc = deltaCapPerc;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
