package com.colendi.credit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Entity
@Table(schema = "core", uniqueConstraints = @UniqueConstraint(name = "unique_key", columnNames = "key"))
@Getter
@Setter
@Accessors(chain = true)
public class Config {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    private BigDecimal value;
}
