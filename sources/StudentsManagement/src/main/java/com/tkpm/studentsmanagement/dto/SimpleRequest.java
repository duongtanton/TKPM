package com.tkpm.studentsmanagement.dto;

public class SimpleRequest {
    private Integer currentPage = 1;
    private Integer totalPages = 1;
    private Integer perPage = 10;

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

    @Override
    public String toString() {
        return "{" +
            " currentPage='" + getCurrentPage() + "'" +
            ", totalPages='" + getTotalPages() + "'" +
            ", perPage='" + getPerPage() + "'" +
            "}";
    }

}
