package com.yzdx.health.recovery.http;

import java.util.Date;

public class JsonParam {
    String userId;
    String bodyPart;
    // YYYY-MM-DD format
    String fromDate;
    // YYYY-MM-DD format
    String toDate;

    String gender;
    double fromAge;
    double toAge;
    String avgRecordName;
    String description;

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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}
