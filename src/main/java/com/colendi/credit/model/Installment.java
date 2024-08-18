package com.colendi.credit.model;

import com.colendi.credit.model.base.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(schema = "credit", indexes = {
        @Index(name = "idx_due_date_status_late_fee", columnList = "due_date, status, late_fee_calculated_date"),
        @Index(name = "idx_installment_id_status", columnList = "id, status")
})
@Getter
@Setter
@Accessors(chain = true)
public class Installment extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Credit credit;

    @OneToMany(mappedBy = "installment", fetch = FetchType.LAZY)
    private List<Payment> payment;

    @Column(name = "accumulated_late_fee")
    private BigDecimal accumulatedLateFee = BigDecimal.ZERO;

    @Column(name = "late_fee_calculated_date")
    private Instant lateFeeCalculatedDate;

    @Version
    private int version;


    public BigDecimal getRemainingAmount() {
        return amount.add(accumulatedLateFee).subtract(paidAmount);
    }
}
