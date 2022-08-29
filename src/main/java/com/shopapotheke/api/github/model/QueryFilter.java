package com.shopapotheke.api.github.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class QueryFilter {
    @Getter @Setter private String field;
    @Getter @Setter private CompareType compareType;
    @Getter @Setter private String value;

}
