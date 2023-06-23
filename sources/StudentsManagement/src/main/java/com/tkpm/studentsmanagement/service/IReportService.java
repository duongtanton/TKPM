package com.tkpm.studentsmanagement.service;

import com.tkpm.studentsmanagement.dto.DisableRequest;
import com.tkpm.studentsmanagement.dto.ReportDTO;
import com.tkpm.studentsmanagement.dto.SimpleResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

public interface IReportService {
    SimpleResponse<?> findAll(ReportDTO reportDTO, Pageable pageable);

    void update(String type);

    Boolean remove(DisableRequest disableRequest);

    void exportExcel(String reportStr, HttpServletResponse httpServletResponse);
}
