package com.yzdx.chemistry.healthrecovery.record;

import com.yzdx.chemistry.healthrecovery.record.RecordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordDetailRepository extends JpaRepository<RecordDetail, Long> {

}
