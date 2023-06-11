package com.tkpm.studentsmanagement.dto;

import java.util.List;

public class DisableRequest {
    private List<Long> ids;

    public DisableRequest() {}

    public List<Long> getIds() {
        return ids;
    }
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
