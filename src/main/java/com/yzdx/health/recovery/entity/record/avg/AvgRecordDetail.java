package com.yzdx.health.recovery.entity.record.avg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(value = {"avgRecord"})
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
}
