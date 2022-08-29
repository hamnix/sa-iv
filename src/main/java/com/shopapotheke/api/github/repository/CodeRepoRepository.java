package com.shopapotheke.api.github.repository;

import com.shopapotheke.api.github.entity.CodeRepo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepoRepository extends CrudRepository  <CodeRepo, Long>, CodeRepoRepositoryCustom {

}
