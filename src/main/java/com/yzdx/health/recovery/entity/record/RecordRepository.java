package com.yzdx.health.recovery.entity.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByUserId(String userId);

    List<Record> findAllByUserIdAndBodyPart(String userId, String bodyPart);

    List<Record> findAllByUserIdAndBodyPartAndTestDateBetween(String userId, String bodyPart, Date fromDate, Date toDate);

    List<Record> findAllByBodyPartAndGenderAndAgeBetween(String bodyPart, String gender, double fromAge, double toAge);

    List<Record> findAllByBodyPartAndGenderAndAgeBetweenAndTestDateBetween(String bodyPart, String gender, double fromAge, double toAge, Date fromDate, Date toDate);
}