package com.shopapotheke.api.github.repository;

import com.shopapotheke.api.github.entity.CodeRepo;
import com.shopapotheke.api.github.model.QueryFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CodeRepoRepositoryCustomImpl implements CodeRepoRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    // TODO: Handle date type of 'value' in better ways.
    public List<CodeRepo> search(List<String> textSearch, List<QueryFilter> queryFilters, String sort, String order, int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CodeRepo> query = cb.createQuery(CodeRepo.class);
        Root<CodeRepo> codeRepoRoot = query.from(CodeRepo.class);

        List<Predicate> predicates = new ArrayList<>();
        for (QueryFilter queryFilter : queryFilters) {
            String field = queryFilter.getField();
            String value = queryFilter.getValue();

            switch (queryFilter.getCompareType()) {
                case EQUAL -> predicates.add(cb.equal(codeRepoRoot.get(field), checkIfDate(value)));
                case LESSER -> predicates.add(cb.lessThan(codeRepoRoot.get(field), checkIfDate(value)));
                case GREATER -> predicates.add(cb.greaterThan(codeRepoRoot.get(field), checkIfDate(value)));
            }
        }

        Order orderBy = order.equals("desc") ? cb.desc(codeRepoRoot.get(sort)) : cb.asc(codeRepoRoot.get(sort));

        query.select(codeRepoRoot).where(cb.and(predicates.toArray(new Predicate[predicates.size()]))).orderBy(orderBy);

        return entityManager.createQuery(query).setMaxResults(limit).getResultList();
    }

    private String checkIfDate(String value) {
        String regex = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(value).matches()) {
            return String.valueOf(LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay().toEpochSecond(ZoneOffset.UTC));
        } else {
            return value;
        }
    }
}
