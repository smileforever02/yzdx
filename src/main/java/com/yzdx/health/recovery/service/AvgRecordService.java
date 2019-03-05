package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.record.avg.AvgRecord;
import com.yzdx.health.recovery.entity.record.avg.AvgRecordRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AvgRecordService {

    @Autowired
    AvgRecordRepository repository;

    public AvgRecord saveAvgRecord(AvgRecord avgRecord) {
        return repository.save(avgRecord);
    }

    public AvgRecord getAvgRecord(Long avgRecordId) {
        Optional<AvgRecord> avgRecord = repository.findById(avgRecordId);
        if (avgRecord.isPresent()) {
            return avgRecord.get();
        } else {
            return null;
        }
    }

    public List<AvgRecord> findAll(String userId, String bodyPart, String gender, String fromAge, String toAge,
                                   String fromDate, String toDate, String avgRecordName) {
        return repository.findAll(new Specification<AvgRecord>() {
            @Override
            public Predicate toPredicate(Root<AvgRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
                    predicates.add(criteriaBuilder.equal(root.get("age"), Double.parseDouble(fromAge)));
                }
                if (!StringUtils.isEmpty(toAge)) {
                    predicates.add(criteriaBuilder.equal(root.get("age"), Double.parseDouble(toAge)));
                }
                if (!StringUtils.isEmpty(fromDate)) {
                    try {
                        predicates.add(criteriaBuilder.equal(root.get("fromDate"), new SimpleDateFormat("YYYY-MM-DD").parse(fromDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!StringUtils.isEmpty(toDate)) {
                    try {
                        predicates.add(criteriaBuilder.equal(root.get("toDate"), new SimpleDateFormat("YYYY-MM-DD").parse(toDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(!StringUtils.isEmpty(avgRecordName)) {
                    predicates.add(criteriaBuilder.like(root.get("avgRecordName"), "%" + avgRecordName + "%"));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}