package com.shopapotheke.api.github.repository;

import com.shopapotheke.api.github.entity.CodeRepo;
import com.shopapotheke.api.github.model.QueryFilter;

import java.util.List;

public interface CodeRepoRepositoryCustom {
    public List<CodeRepo> search(List<String> textSearch, List<QueryFilter> queryFilters, String sort, String order, int limit);
}
