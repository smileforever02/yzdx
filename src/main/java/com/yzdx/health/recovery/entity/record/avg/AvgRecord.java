package com.yzdx.health.recovery.entity.record.avg;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"avgRecordDetails"})
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
    private List<AvgRecordDetail> avgRecordDetails;

    private String avgRecordName;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createdDate;
}
