package com.yzdx.health.recovery.entity.record.delta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@JsonIgnoreProperties(value = {"deltaRecord"})
@Entity
public class DeltaRecordDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delta_detail_id", insertable = false, nullable = false, updatable = false)
    private Long deltaDetailId;

    @ManyToOne
    @JoinColumn(name = "delta_record_id", referencedColumnName = "delta_record_id", nullable = false)
    private DeltaRecord deltaRecord;
    private double time;
    private double deltaCapPerc;
    private double power;
}
