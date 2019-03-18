package com.yzdx.health.recovery.entity.record.delta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"deltaRecordDetails"})
@Entity
public class DeltaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delta_record_id", insertable = false, nullable = false, updatable = false)
    private Long deltaRecordId;

    private String userId;

    private Long recordId;

    private Long avgRecordId;

    @OneToMany(mappedBy = "deltaRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DeltaRecordDetail> deltaRecordDetails;

    private String deltaRecordName;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createdDate;
}
