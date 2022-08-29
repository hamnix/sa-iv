package com.shopapotheke.api.github.model;

public enum CompareType {
    EQUAL(":"),
    GREATER(">"),
    LESSER("<");

    private final String value;

    CompareType(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static CompareType valueOfLabel(String label) {
        for (CompareType e : values()) {
            if (e.value.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
