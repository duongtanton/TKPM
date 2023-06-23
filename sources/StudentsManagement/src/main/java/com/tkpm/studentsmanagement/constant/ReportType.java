package com.tkpm.studentsmanagement.constant;

import java.util.Arrays;

public enum ReportType {
    SUBJECT, SEMESTER;

    public static ReportType lookup(String typeStr) {
        for(ReportType type : ReportType.values()) {
            if(type.name().equalsIgnoreCase(typeStr)) {
                return type;
            }
        }
        throw new RuntimeException(String.format("Invalid type. It should be  %s",
                Arrays.asList(ReportType.values())));
    }
}
