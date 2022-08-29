package com.shopapotheke.api.github.controller;

import com.shopapotheke.api.github.entity.CodeRepo;
import com.shopapotheke.api.github.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/search/repositories")
public class RepositoryController {

    private static final Logger LOGGER = Logger.getLogger(RestController.class.getClass().getName());
    @Autowired
    SearchService searchService;

    @GetMapping
    public List<CodeRepo> getRepository(@RequestParam(required = false) String q,
                                        @RequestParam(required = false, defaultValue = "stars") String sort,
                                        @RequestParam(required = false, defaultValue = "desc") String order,
                                        @RequestParam(required = false, defaultValue = "100") int limit) {
        LOGGER.info(String.format("q: %s sort: %s order: %s limit: %d", q, sort, order, limit));
        List<CodeRepo> result = searchService.getRepository(q, sort, order, limit);

        return result;
    }
}
