package com.su8y.bootstrap.batch;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberWithBatchSizeRepository extends JpaRepository<MemberWithBatchSize, Long> {
}
