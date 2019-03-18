package com.yzdx.health.recovery.entity.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"recordDetails"})
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", insertable = false, nullable = false, updatable = false)
    private Long recordId;

    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date testDate;

    private double age;

    private String gender;

    private String bodyPart;

    private double frequency;

    private String fileName;

    private String fileDownloadUri;

    private long size;

    @OneToMany(mappedBy = "record", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecordDetail> recordDetails;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createdDate;
}
