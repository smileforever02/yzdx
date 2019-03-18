package com.yzdx.health.recovery.entity.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(value = {"record"})
@Entity
@Table(name = "record_detail")
public class RecordDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id", insertable = false, nullable = false, updatable = false)
    private Long detailId;

    @ManyToOne
    @JoinColumn(name = "record_id", referencedColumnName = "record_id", nullable = false)
    private Record record;

    private double time;

    private double initCap;

    private double cap;

    private double deltaCapPerc;

    private double power;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createdDate;
}
