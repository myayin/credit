package com.colendi.credit.repository;

import com.colendi.credit.model.Installment;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {

    Optional<Installment> findByIdAndStatus(Long id, String status);

    @Query("SELECT i FROM Installment i WHERE i.dueDate < :dueDate AND i.status = :status AND " +
            "(i.lateFeeCalculatedDate < :lateFeeCalculatedDate or i.lateFeeCalculatedDate is null ) ")
    List<Installment> findByDueDateBeforeAndStatusAndLateFeeCalculatedDateBefore(Instant dueDate, String status, Instant lateFeeCalculatedDate, Pageable pageable);

}
