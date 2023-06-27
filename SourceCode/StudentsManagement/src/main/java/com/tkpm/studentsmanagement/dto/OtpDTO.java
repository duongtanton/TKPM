package com.tkpm.studentsmanagement.dto;

public class OtpDTO extends AbstractDTO {
    
    private String content;
    
    private Boolean used;

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getUsed() {
        return this.used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

}
