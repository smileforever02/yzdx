package com.yzdx.health.recovery.entity.record.avg;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AvgRecordDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "avg_detail_id", insertable = false, nullable = false, updatable = false)
    private Long avgDetailId;

    private Long avgRecordId;

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

    public Long getAvgRecordId() {
        return avgRecordId;
    }

    public void setAvgRecordId(Long avgRecordId) {
        this.avgRecordId = avgRecordId;
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
