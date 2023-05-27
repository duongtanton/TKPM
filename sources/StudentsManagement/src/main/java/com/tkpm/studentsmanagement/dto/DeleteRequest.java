package com.tkpm.studentsmanagement.dto;

import java.util.List;

public class DeleteRequest {
    private List<Long> ids;


    public DeleteRequest() {
    }
    
    public List<Long> getIds() {
        return ids;
    }
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "{" +
            " ids='" + getIds() + "'" +
            "}";
    }

}
