package com.colendi.credit.model.enums;

import java.util.HashSet;
import java.util.Set;

public enum CreditTypeEnum {
    HOME, CAR;


    public static Set<String> getCreditTypes() {
        HashSet<String> creditTypes = new HashSet<>();
        for (CreditTypeEnum type : CreditTypeEnum.values()) {
            creditTypes.add(type.name());
        }
        return creditTypes;
    }
}
