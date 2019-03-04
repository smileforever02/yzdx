package com.yzdx.health.recovery.entity.record;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(value={"record"})
@Entity
@Table(name = "record_detail")
public class RecordDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id", insertable = false, nullable = false, updatable = false)
    private Long detailId;

    @ManyToOne
    @JoinColumn(name = "record_id",referencedColumnName = "record_id", nullable = false)
    private Record record;

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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
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
