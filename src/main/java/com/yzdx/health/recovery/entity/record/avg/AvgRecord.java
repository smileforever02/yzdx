package com.yzdx.health.recovery.entity.record.avg;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@JsonIgnoreProperties(value={"avgRecordDetails"})
@Entity
public class AvgRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avg_record_id", insertable = false, nullable = false, updatable = false)
    private Long avgRecordId;

    private String userId;

    private String bodyPart;

    private double fromAge;

    private double toAge;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date toDate;

    private long recordCount;

    @OneToMany(mappedBy = "avgRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AvgRecordDetail> avgRecordDetails;

    private String avgRecordName;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

    public String getAvgRecordName() {
        return avgRecordName;
    }

    public void setAvgRecordName(String avgRecordName) {
        this.avgRecordName = avgRecordName;
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
