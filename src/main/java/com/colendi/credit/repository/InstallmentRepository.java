package com.colendi.credit.repository;

import com.colendi.credit.model.Installment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {

    Optional<Installment> findByIdAndStatus(Long id, String status);

}
