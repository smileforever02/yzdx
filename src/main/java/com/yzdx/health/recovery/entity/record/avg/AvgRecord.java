package com.yzdx.health.recovery.entity.record.avg;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class AvgRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "avg_record_id", insertable = false, nullable = false, updatable = false)
    private Long avgRecordId;

    private String userId;

    private String bodyPart;

    private double fromAge;

    private double toAge;

    private String gender;

    private Date fromDate;

    private Date toDate;

    private long recordCount;

    @OneToMany(mappedBy = "avgRecordId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AvgRecordDetail> avgRecordDetails;

    private String description;

    private Date createdDate;

    public Long getAvgRecordId() {
        return avgRecordId;
    }

    public void setAvgRecordId(Long avgRecordId) {
        this.avgRecordId = avgRecordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public double getFromAge() {
        return fromAge;
    }

    public void setFromAge(double fromAge) {
        this.fromAge = fromAge;
    }

    public double getToAge() {
        return toAge;
    }

    public void setToAge(double toAge) {
        this.toAge = toAge;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public Set<AvgRecordDetail> getAvgRecordDetails() {
        return avgRecordDetails;
    }

    public void setAvgRecordDetails(Set<AvgRecordDetail> avgRecordDetails) {
        this.avgRecordDetails = avgRecordDetails;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
