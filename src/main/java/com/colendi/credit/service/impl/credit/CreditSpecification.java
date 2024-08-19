package com.colendi.credit.service.impl.credit;

import com.colendi.credit.dto.CreditFilter;
import com.colendi.credit.model.Credit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class CreditSpecification implements Specification<Credit> {

    private final CreditFilter filter;

    @Override
    public Predicate toPredicate(Root<Credit> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        filter.getStatus().ifPresent(status -> predicates.add(builder.equal(root.get("status"), status)));

        filter.getCreatedDate().ifPresent(createdDate -> predicates.add(builder.equal(root.get("created_date"), createdDate)));

        filter.getUserId().ifPresent(userId -> predicates.add(builder.equal(root.get("user").get("id"), userId)));

        if (query != null) {
            query.orderBy(builder.asc(root.get("createdDate")));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
