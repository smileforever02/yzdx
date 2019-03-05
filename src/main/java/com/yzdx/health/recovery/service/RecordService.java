package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.record.Record;
import com.yzdx.health.recovery.entity.record.RecordDetailRepository;
import com.yzdx.health.recovery.entity.record.RecordRepository;
import com.yzdx.health.recovery.entity.record.avg.AvgRecord;
import com.yzdx.health.recovery.entity.record.avg.AvgRecordDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecordService {

    @Autowired
    RecordRepository repository;

    @Autowired
    RecordDetailRepository detailRepository;

    @Autowired
    AvgRecordService avgRecordService;

    public Record createRecord(Record record) {
        return repository.save(record);
    }

    public Record updateRecord(Record record) {
        return repository.save(record);
    }

    public void deleteRecord(Record record) {
        repository.delete(record);
    }

    public Optional<Record> findById(Long recordId) {
        return repository.findById(recordId);
    }

    public List<Record> findAllByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Record> findAllByUserIdAndBodyPart(String userId, String bodyPart) {
        return repository.findAllByUserIdAndBodyPart(userId, bodyPart);
    }

    public List<Record> findAllByUserIdAndBodyPartAndTestDateBetween(String userId, String bodyPart, Date fromDate, Date toDate) {
        return repository.findAllByUserIdAndBodyPartAndTestDateBetween(userId, bodyPart, fromDate, toDate);
    }

    public List<Record> findAllByBodyPartAndGenderAndAgeBetween(String bodyPart, String gender, double fromAge, double toAge) {
        return repository.findAllByBodyPartAndGenderAndAgeBetween(bodyPart, gender, fromAge, toAge);
    }

    public List<Record> findAllByBodyPartAndGenderAndAgeBetweenAndTestDateBetween(String bodyPart, String gender, double fromAge, double toAge, Date fromDate, Date toDate) {
        return repository.findAllByBodyPartAndGenderAndAgeBetweenAndTestDateBetween(bodyPart, gender, fromAge, toAge, fromDate, toDate);
    }

    // fromDate and toDate format should be YYYY-MM-DD
    public AvgRecord genAvgRecordDetailByUser(String userId, String bodyPart, String fromDate, String toDate,
                                              String avgRecordName, String description) throws ParseException {
        List<Object> list = detailRepository.genAvgRecordDetailByUser(userId, bodyPart, fromDate, toDate);
        AvgRecord record = new AvgRecord();
        record.setUserId(userId);
        record.setBodyPart(bodyPart);
        record.setFromDate(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
        record.setToDate(new SimpleDateFormat("yyyy-MM-dd").parse(toDate));
        record.setAvgRecordName(avgRecordName);
        record.setDescription(description);
        record.setCreatedDate(new Date());

        Set<AvgRecordDetail> detailSet = new HashSet<>();
        long maxCount = 0;
        for (Object o : list) {
            Object[] arr = (Object[]) o;
            AvgRecordDetail detail = new AvgRecordDetail();
            detail.setTime((double) arr[0]);
            detail.setDeltaCapPerc((double) arr[1]);
            detail.setPower((double) arr[2]);
            detail.setRecordCount(((BigInteger) arr[3]).intValue());
            detail.setCreatedDate(new Date());

            detail.setAvgRecord(record);
            detailSet.add(detail);

            if (detail.getRecordCount() > maxCount) {
                maxCount = detail.getRecordCount();
            }
        }

        record.setAvgRecordDetails(detailSet);
        record.setRecordCount(maxCount);
        return avgRecordService.saveAvgRecord(record);

    }

    // fromDate and toDate format should be YYYY-MM-DD
    public AvgRecord genAvgRecordDetail(String bodyPart, String gender, double fromAge, double toAge,
                                        String fromDate, String toDate, String avgRecordName, String description) throws ParseException {
        List<Object> list = detailRepository.genAvgRecordDetail(bodyPart, gender, fromAge, toAge, fromDate, toDate);

        AvgRecord record = new AvgRecord();
        record.setBodyPart(bodyPart);
        record.setGender(gender);
        record.setFromAge(fromAge);
        record.setToAge(toAge);
        record.setFromDate(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
        record.setToDate(new SimpleDateFormat("yyyy-MM-dd").parse(toDate));
        record.setAvgRecordName(avgRecordName);
        record.setDescription(description);
        record.setCreatedDate(new Date());

        Set<AvgRecordDetail> detailSet = new HashSet<>();
        for (Object o : list) {
            Object[] arr = (Object[]) o;
            AvgRecordDetail detail = new AvgRecordDetail();
            detail.setTime((double) arr[0]);
            detail.setDeltaCapPerc((double) arr[1]);
            detail.setPower((double) arr[2]);
            detail.setRecordCount(((BigInteger) arr[3]).intValue());
            detail.setCreatedDate(new Date());

            detail.setAvgRecord(record);
            detailSet.add(detail);
        }

        record.setAvgRecordDetails(detailSet);
        return avgRecordService.saveAvgRecord(record);
    }

    public Optional<Record> findByFileName(String fileName) {
        return repository.findAllByFileName(fileName);
    }
}
