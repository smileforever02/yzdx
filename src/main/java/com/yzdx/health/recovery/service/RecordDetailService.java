package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.record.RecordDetail;
import com.yzdx.health.recovery.entity.record.RecordDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordDetailService {

    @Autowired
    RecordDetailRepository repository;

    public List<RecordDetail> createRecordDetail(List<RecordDetail> details) {
        return repository.saveAll(details);
    }

    public List<RecordDetail> getRecordDetail(String recordId) {
        return repository.getAllByRecordRecordId(recordId);
    }

}
