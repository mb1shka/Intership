package com.game.repository;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    private final List<Specification<T>> specifications;

    public SpecificationBuilder() {
        this.specifications = new ArrayList<>();
    }

    public final SpecificationBuilder<T> with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    public Specification<T> build() {
        Specification<T> result = null;
        if (!specifications.isEmpty()) {
            for (Specification<T> specification : specifications) {
                result = Specification.where(result).and(specification);
            }
        }
        return result;
    }
}
