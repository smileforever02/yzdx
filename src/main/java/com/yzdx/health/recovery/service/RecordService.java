package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.record.Record;
import com.yzdx.health.recovery.entity.record.RecordDetailRepository;
import com.yzdx.health.recovery.entity.record.RecordRepository;
import com.yzdx.health.recovery.entity.record.avg.AvgRecord;
import com.yzdx.health.recovery.entity.record.avg.AvgRecordDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    @Autowired
    EntityManager entityManager;

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

    public List<Record> findAll(String userId, String bodyPart, String gender, String fromAge, String toAge, String fromDate, String toDate) {
        return repository.findAll(new Specification<Record>() {
            @Override
            public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new LinkedList<>();

                if (!StringUtils.isEmpty(userId)) {
                    predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
                }
                if (!StringUtils.isEmpty(bodyPart)) {
                    predicates.add(criteriaBuilder.like(root.get("bodyPart"), "%" + bodyPart + "%"));
                }
                if (!StringUtils.isEmpty(gender)) {
                    predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
                }
                if (!StringUtils.isEmpty(fromAge)) {
                    predicates.add(criteriaBuilder.ge(root.get("age"), Double.parseDouble(fromAge)));
                }
                if (!StringUtils.isEmpty(toAge)) {
                    predicates.add(criteriaBuilder.le(root.get("age"), Double.parseDouble(toAge)));
                }
                if (!StringUtils.isEmpty(fromDate)) {
                    try {
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("testDate"), new SimpleDateFormat("YYYY-MM-DD").parse(fromDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!StringUtils.isEmpty(toDate)) {
                    try {
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("testDate"), new SimpleDateFormat("YYYY-MM-DD").parse(toDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
