package com.tkpm.studentsmanagement.util;

public class NotNullOrT {
    static public Object run(Object object, Object T){
        if (object != null) {
            return object;
        } else{
            return T;
        }
    }
}
