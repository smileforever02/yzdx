package com.yzdx.health.recovery.entity.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordDetailRepository extends JpaRepository<RecordDetail, Long> {
    @Query(value = "select time as time, avg(delta_cap_perc) as deltaCapPerc, avg(power) as power, count(1) as count from record_detail where record_id in(select record_id from record where user_id=:userId and body_part=:bodyPart and (test_date between :fromDate and :toDate)) group by time order by time", nativeQuery = true)
    List<Object> genAvgRecordDetailByUser(@Param("userId") String userId, @Param("bodyPart") String bodyPart, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "select time as time, avg(delta_cap_perc) as deltaCapPerc, avg(power) as power, count(1) as count from record_detail where record_id in(select record_id from record where body_part=:bodyPart and gender=:gender and (age between :fromAge and :toAge) and (test_date between :fromDate and :toDate)) group by time order by time", nativeQuery = true)
    List<Object> genAvgRecordDetail(@Param("bodyPart") String bodyPart, @Param("gender") String gender, @Param("fromAge") double fromAge, @Param("toAge") double toAge, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<RecordDetail> getAllByRecordRecordIdOrderByTime(Long recordId);
}
