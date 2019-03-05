package com.yzdx.health.recovery.entity.record.avg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvgRecordDetailRepository extends JpaRepository<AvgRecordDetail, Long> {

    List<AvgRecordDetail> getAllByAvgRecordAvgRecordId(String avgRecordId);
}
