package com.yzdx.health.recovery.entity.record.delta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeltaRecordRepository extends JpaRepository<DeltaRecord, Long> {
    List<DeltaRecord> findAllByUserIdOrderByCreatedDateDesc(String userId);

    @Query(value = "select a.time as time, (a.delta_cap_perc-b.delta_cap_perc) as delta_cap_perc, (a.power-b.power) as power from record_detail a, avg_record_detail b where a.record_id=:recordId and b.avg_record_id=:avgRecordId and a.time=b.time order by a.time", nativeQuery = true)
    List<Object> genDeltaRecordDetail(@Param("recordId") Long recordId, @Param("avgRecordId") Long avgRecordId);
}
