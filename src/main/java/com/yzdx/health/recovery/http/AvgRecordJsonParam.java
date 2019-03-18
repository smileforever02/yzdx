package com.yzdx.health.recovery.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvgRecordJsonParam {
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
}
