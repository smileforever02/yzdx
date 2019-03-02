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

    private Date createdDate;

}
