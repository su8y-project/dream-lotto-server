package com.su8y.bootstrap.batch;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamWithBatchSizeRepository extends JpaRepository<TeamWithBatchSize, Long> {
}
