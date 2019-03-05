package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.record.avg.AvgRecordDetail;
import com.yzdx.health.recovery.entity.record.avg.AvgRecordDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvgRecordDetailService {

    @Autowired
    AvgRecordDetailRepository repository;

    public List<AvgRecordDetail> getAllByAvgRecordAvgRecordId(Long avgRecordId) {
        return repository.getAllByAvgRecordAvgRecordId(avgRecordId);
    }
}
