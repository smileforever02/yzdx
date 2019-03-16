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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @PersistenceContext
    private EntityManager em;

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
    public AvgRecord genAvgRecordDetail(String userId, String bodyPart, String gender, double fromAge, double toAge,
                                        String fromDate, String toDate, String avgRecordName, String description) throws ParseException {
        StringBuffer sql = new StringBuffer("select time as time, avg(deltaCapPerc) as deltaCapPerc, avg(power) as power, count(1) as count from RecordDetail" +
                " where record in (select r from Record as r where 1=1");
        if (StringUtils.isNotBlank(userId)) {
            sql.append(" and userId='").append(userId).append("'");
        }
        if (StringUtils.isNotBlank(bodyPart) && !"ALL".equalsIgnoreCase(bodyPart)) {
            sql.append(" and bodyPart='").append(bodyPart).append("'");
        }
        if (StringUtils.isNotBlank(gender) && !"ALL".equalsIgnoreCase(bodyPart)) {
            sql.append(" and gender='").append(gender).append("'");
        }
        if (fromAge > 0) {
            sql.append(" and age>=").append(fromAge);
        }
        if (toAge > 0) {
            sql.append(" and age<=").append(toAge);
        }
        if (StringUtils.isNotBlank(fromDate)) {
            sql.append(" and testDate>='").append(fromDate).append("'");
        }
        if (StringUtils.isNotBlank(toDate)) {
            sql.append(" and testDate<='").append(toDate).append("'");
        }
        sql.append(") group by time order by time");

        Query query = em.createQuery(sql.toString());
        List list = query.getResultList();

        AvgRecord record = new AvgRecord();
        record.setUserId(userId);
        record.setBodyPart(bodyPart);
        record.setGender(gender);
        record.setFromAge(fromAge);
        record.setToAge(toAge);
        if (StringUtils.isNotBlank(fromDate)) {
            record.setFromDate(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
        }
        if (StringUtils.isNotBlank(toDate)) {
            record.setToDate(new SimpleDateFormat("yyyy-MM-dd").parse(toDate));
        }
        record.setAvgRecordName(avgRecordName);
        record.setDescription(description);
        record.setCreatedDate(new Date());

        long maxRecordCount = 0;
        Set<AvgRecordDetail> detailSet = new HashSet<>();
        for (Object o : list) {
            Object[] arr = (Object[]) o;
            AvgRecordDetail detail = new AvgRecordDetail();
            detail.setTime((double) arr[0]);
            detail.setDeltaCapPerc((double) arr[1]);
            detail.setPower((double) arr[2]);
            long count = (long) arr[3];
            if (count > maxRecordCount) {
                maxRecordCount = count;
            }
            detail.setRecordCount(count);
            detail.setCreatedDate(new Date());
            detail.setAvgRecord(record);
            detailSet.add(detail);
        }
        record.setRecordCount(maxRecordCount);
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
                if (!StringUtils.isEmpty(bodyPart) && !"ALL".equalsIgnoreCase(bodyPart)) {
                    predicates.add(criteriaBuilder.like(root.get("bodyPart"), "%" + bodyPart + "%"));
                }
                if (!StringUtils.isEmpty(gender) && !"ALL".equalsIgnoreCase(gender)) {
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
