package com.yzdx.health.recovery.entity.record.avg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(value={"avgRecord"})
@Entity
public class AvgRecordDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avg_detail_id", insertable = false, nullable = false, updatable = false)
    private Long avgDetailId;

    @ManyToOne
    @JoinColumn(name = "avg_record_id", referencedColumnName = "avg_record_id", nullable = false)
    private AvgRecord avgRecord;

    private double time;

    private double deltaCapPerc;

    private double power;

    private long recordCount;

    private Date createdDate;

    public Long getAvgDetailId() {
        return avgDetailId;
    }

    public void setAvgDetailId(Long avgDetailId) {
        this.avgDetailId = avgDetailId;
    }

    public AvgRecord getAvgRecord() {
        return avgRecord;
    }

    public void setAvgRecord(AvgRecord avgRecord) {
        this.avgRecord = avgRecord;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
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

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
