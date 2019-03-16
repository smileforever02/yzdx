package com.yzdx.health.recovery.entity.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {
    List<Record> findAllByUserIdOrderByTestDateDesc(String userId);
    Optional<Record> findAllByFileName(String fileName);
}