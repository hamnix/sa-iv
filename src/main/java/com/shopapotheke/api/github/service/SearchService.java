package com.shopapotheke.api.github.service;

import com.shopapotheke.api.github.entity.CodeRepo;
import com.shopapotheke.api.github.model.CompareType;
import com.shopapotheke.api.github.model.QueryFilter;
import com.shopapotheke.api.github.repository.CodeRepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SearchService {

    private static final Logger LOGGER = Logger.getLogger(SearchService.class
            .getClass().getName());
    @Autowired
    CodeRepoRepository codeRepoRepository;

    public List<CodeRepo> getRepository(String queryString, String sort, String order, int limit){
        List<String> textSearch = new ArrayList<>();
        List<QueryFilter> queryFilters = new ArrayList<>();

        if (StringUtils.hasText(queryString)){
            String[] queryParts = queryString.split(" ");
            for(String queryPart: queryParts){
                if(queryPart.contains(":")){
                    queryFilters.add(getFilter(queryPart));      // created:>2019-01-10 -> field, compareType, value
                }else{
                    textSearch.add(queryPart);
                }
            }
        }

        return codeRepoRepository.search(textSearch, queryFilters, sort, order, limit);
    }

    private QueryFilter getFilter(String queryPart){
        List<Character> comparisonCharacters = List.of('=', '>', '<');
        int operatorIndex = queryPart.indexOf(":");

        String field =  queryPart.substring(0,operatorIndex);
        String compareType = queryPart.substring(operatorIndex, operatorIndex+1);
        String value = "";

        // TODO: use regex to get compareType
        if(comparisonCharacters.contains(queryPart.charAt(operatorIndex+1))){
            compareType = String.valueOf(queryPart.charAt(operatorIndex+1));
            value = queryPart.substring(operatorIndex+2);
        }else{
            value = queryPart.substring(operatorIndex+1);
        }

        LOGGER.info(String.format( "field - %s | compareType - %s | value - %s", field,compareType, value ));

        return new QueryFilter(field, CompareType.valueOfLabel(compareType), value);
    }
}
