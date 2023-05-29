package com.tkpm.studentsmanagement.dto;

import java.util.List;

enum MESSAGE_CODE {
    ERROR,
    WARNING,
    SUCCESS
}

class Toast {
    private MESSAGE_CODE code = MESSAGE_CODE.SUCCESS;
    private String title = "Admin says";
    private String context = "";

    public void setCode(MESSAGE_CODE code) {
        this.code = code;
    }

    public MESSAGE_CODE getCode() {
        return code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

}

public class SimpleResponse<T> {

    private Toast toast;

    private Integer currentPage = 1;
    private Integer totalPages = 1;
    private Integer perPage = 10;
    private List<T> listT;

    public Toast getToast() {
        return this.toast;
    }

    public void setToast(Toast toast) {
        this.toast = toast;
    }

    public void setListT(List<T> listT) {
        this.listT = listT;
    }

    public List<T> getListT() {
        return listT;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPerPage() {
        return perPage;
    }
}
