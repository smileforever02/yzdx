package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.record.delta.DeltaRecord;
import com.yzdx.health.recovery.entity.record.delta.DeltaRecordDetail;
import com.yzdx.health.recovery.entity.record.delta.DeltaRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class DeltaRecordService {
    @Autowired
    DeltaRecordRepository repository;

    @Autowired
    RecordService recordService;

    public List<DeltaRecord> getDeltaRecords(String userId) {
        return repository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    public List<DeltaRecordDetail> getDeltaRecordDetail(Long deltaRecordId) {
        return repository.findById(deltaRecordId).get().getDeltaRecordDetails();
    }

    public DeltaRecord genDeltaRecordDetail(Long recordId, Long avgRecordId, String deltaRecordName, String description) {
        List list = repository.genDeltaRecordDetail(recordId, avgRecordId);

        DeltaRecord deltaRecord = new DeltaRecord();
        deltaRecord.setUserId(recordService.getRecord(recordId).get().getUserId());
        deltaRecord.setRecordId(recordId);
        deltaRecord.setAvgRecordId(avgRecordId);
        deltaRecord.setDeltaRecordName(deltaRecordName);
        deltaRecord.setDescription(description);
        deltaRecord.setCreatedDate(new Date());

        List<DeltaRecordDetail> details = new LinkedList<>();
        for (Object o : list) {
            Object[] arr = (Object[]) o;
            DeltaRecordDetail detail = new DeltaRecordDetail();
            detail.setDeltaRecord(deltaRecord);
            detail.setTime((double) arr[0]);
            detail.setDeltaCapPerc((double) arr[1]);
            detail.setPower((double) arr[2]);

            details.add(detail);
        }

        deltaRecord.setDeltaRecordDetails(details);

        return repository.save(deltaRecord);
    }
}
