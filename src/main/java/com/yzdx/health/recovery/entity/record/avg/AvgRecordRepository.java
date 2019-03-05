package com.yzdx.health.recovery.entity.record.avg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvgRecordRepository extends JpaRepository<AvgRecord, Long>, JpaSpecificationExecutor<AvgRecord> {
    List<AvgRecord> findAllByUserId(String userId);
}
