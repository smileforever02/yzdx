package com.yzdx.health.recovery.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeltaRecordJsonParam {
    private Long recordId;
    private Long avgRecordId;
    private String deltaRecordName;
    private String description;
}
