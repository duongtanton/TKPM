package com.tkpm.studentsmanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.configuration.CustomUserAuth;
import com.tkpm.studentsmanagement.constant.Regualation;
import com.tkpm.studentsmanagement.constant.ReportType;
import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.entity.ReportEntity;
import com.tkpm.studentsmanagement.repository.*;
import com.tkpm.studentsmanagement.service.IReportService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService implements IReportService {
    @Autowired
    ScoreBoardRepository scoreBoardRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ClassRepository classRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ConfigurationRepository configurationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public SimpleResponse<?> findAll(ReportDTO reportDTO, Pageable pageable) {
        System.out.println(reportDTO.getType());
        if (reportDTO.getType() == null) {
            reportDTO.setType(ReportType.SEMESTER.name());
        }
        if (reportDTO.getType().equalsIgnoreCase(ReportType.SUBJECT.name())) {
            System.out.println("OK");
            SimpleResponse<SubjectReportDTO> simpleResponse = new SimpleResponse<>();
            Page<ReportEntity> reportEntityPage = reportRepository.findAll(
                    reportDTO.getClassId(),
                    reportDTO.getSubjectId(),
                    ReportType.lookup(reportDTO.getType()),
                    reportDTO.getYear(),
                    reportDTO.getSemester(),
                    pageable);
            System.out.println(reportEntityPage.getTotalElements());
            List<SubjectReportDTO> subjectReportDTOList = reportEntityPage.get().map(r -> {
                SubjectReportDTO subjectReportDTO = new SubjectReportDTO();
                BeanUtils.copyProperties(r, subjectReportDTO);
                subjectReportDTO.setClassId(r.getClasss().getId());
                subjectReportDTO.setClassName(r.getClasss().getName());
                subjectReportDTO.setSubjectId(r.getSubject().getId());
                subjectReportDTO.setSubjectName(r.getSubject().getName());
                return subjectReportDTO;
            }).toList();
            System.out.println(subjectReportDTOList.size());
            simpleResponse.setListT(subjectReportDTOList);
            simpleResponse.setPerPage(pageable.getPageSize());
            simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
            simpleResponse.setTotalPages((int) Math.ceil((double) subjectReportDTOList.size() / pageable.getPageSize()));
            return simpleResponse;
        } else {
            SimpleResponse<SemesterReportDTO> simpleResponse = new SimpleResponse<>();
            Page<ReportEntity> reportEntityPage = reportRepository.findAll(
                    reportDTO.getClassId(),
                    reportDTO.getSubjectId(),
                    ReportType.lookup(reportDTO.getType()),
                    reportDTO.getYear(),
                    reportDTO.getSemester(),
                    pageable);
            List<SemesterReportDTO> semesterReportDTOList = reportEntityPage.get().map(r -> {
                SemesterReportDTO semesterReportDTO = new SemesterReportDTO();
                BeanUtils.copyProperties(r, semesterReportDTO);
                semesterReportDTO.setClassId(r.getClasss().getId());
                semesterReportDTO.setClassName(r.getClasss().getName());
                return semesterReportDTO;
            }).toList();
            simpleResponse.setListT(semesterReportDTOList);
            simpleResponse.setPerPage(pageable.getPageSize());
            simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
            simpleResponse.setTotalPages((int) Math.ceil((double) semesterReportDTOList.size() / pageable.getPageSize()));
            return simpleResponse;
        }
    }

    @Override
    public void update(String type) {
        if (type == null) {
            type = ReportType.SEMESTER.name();
        }
        CustomUserAuth userPrincipal = (CustomUserAuth) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        List<Object[]> statistics;
        if (type.equalsIgnoreCase(ReportType.SUBJECT.name())) {
            statistics = scoreBoardRepository.statisticBySubject(
                    Double.parseDouble(configurationRepository.findByName(Regualation.REQUIRED_MARK.name()).getValue()));
            List<SubjectReportDTO> statisticBySubjects = new ArrayList<>();
            for (Object[] statistic : statistics) {
                SubjectReportDTO subjectReportDTO = new SubjectReportDTO();
                subjectReportDTO.setClassId(Long.parseLong(String.valueOf(statistic[0])));
                subjectReportDTO.setSubjectId(Long.parseLong(String.valueOf(statistic[1])));
                subjectReportDTO.setYear(Integer.parseInt(String.valueOf(statistic[2])));
                subjectReportDTO.setSemester(Integer.parseInt(String.valueOf(statistic[3])));
                subjectReportDTO.setAverageScore(Double.parseDouble(String.valueOf(statistic[4])));
                subjectReportDTO.setPassQuantity(Integer.parseInt(String.valueOf(statistic[5])));
                statisticBySubjects.add(subjectReportDTO);
            }
            for (SubjectReportDTO statisticBySubject : statisticBySubjects) {
                ReportEntity report = new ReportEntity();
                report.setType(ReportType.SUBJECT);
                report.setClasss(classRepository.findById(statisticBySubject.getClassId()).orElse(null));
                report.setSubject(subjectRepository.findById(statisticBySubject.getSubjectId()).orElse(null));
                report.setYear(statisticBySubject.getYear());
                report.setSemester(statisticBySubject.getSemester());
                report.setAverageScore(statisticBySubject.getAverageScore());
                report.setNumberOfPupils(report.getClasss().getNumberOfPupils());
                report.setPassQuantity(statisticBySubject.getPassQuantity());
                report.setPassPercent(((double) statisticBySubject.getPassQuantity()
                        / classRepository.findById(statisticBySubject.getClassId()).get().getNumberOfPupils()) * 100);
                report.setCreatedBy(userRepository.findById(userPrincipal.getId()).orElse(null));

                ReportEntity existedReport = reportRepository.findByStatistic(
                        report.getClasss().getId(),
                        report.getSubject().getId(),
                        report.getType(),
                        report.getYear(),
                        report.getSemester()
                );
                if (existedReport != null) {
                    existedReport.setAverageScore(report.getAverageScore());
                    existedReport.setPassQuantity(report.getPassQuantity());
                    existedReport.setPassPercent(report.getPassPercent());
                    existedReport.setUpdatedBy(userRepository.findById(userPrincipal.getId()).orElse(null));
                    reportRepository.save(existedReport);
                } else {
                    reportRepository.save(report);
                }
            }
        } else {
            statistics = scoreBoardRepository.statisticByStudent(
                    Double.parseDouble(configurationRepository.findByName(Regualation.REQUIRED_MARK.name()).getValue()));
            List<SemesterReportDTO> statisticBySemesters = new ArrayList<>();
            for (Object[] statistic : statistics) {
                SemesterReportDTO semesterReportDTO = new SemesterReportDTO();
                semesterReportDTO.setClassId(Long.parseLong(String.valueOf(statistic[0])));
                semesterReportDTO.setYear(Integer.parseInt(String.valueOf(statistic[1])));
                semesterReportDTO.setSemester(Integer.parseInt(String.valueOf(statistic[2])));
                semesterReportDTO.setPassQuantity(Integer.parseInt(String.valueOf(statistic[3])));
                semesterReportDTO.setAverageScore(Double.parseDouble(String.valueOf(statistic[4])));
                statisticBySemesters.add(semesterReportDTO);
            }
            for (SemesterReportDTO statisticBySemester : statisticBySemesters) {
                ReportEntity report = new ReportEntity();
                report.setType(ReportType.SEMESTER);
                report.setClasss(classRepository.findById(statisticBySemester.getClassId()).orElse(null));
                report.setYear(statisticBySemester.getYear());
                report.setSemester(statisticBySemester.getSemester());
                report.setAverageScore(statisticBySemester.getAverageScore());
                report.setNumberOfPupils(report.getClasss().getNumberOfPupils());
                report.setPassQuantity(statisticBySemester.getPassQuantity());
                report.setPassPercent(((double) statisticBySemester.getPassQuantity()
                        / classRepository.findById(statisticBySemester.getClassId()).get().getNumberOfPupils()) * 100);
                report.setCreatedBy(userRepository.findById(userPrincipal.getId()).orElse(null));

                ReportEntity existedReport = reportRepository.findByStatistic(
                        report.getClasss().getId(),
                        null,
                        report.getType(),
                        report.getYear(),
                        report.getSemester()
                );
                if (existedReport != null) {
                    existedReport.setAverageScore(report.getAverageScore());
                    existedReport.setPassQuantity(report.getPassQuantity());
                    existedReport.setPassPercent(report.getPassPercent());
                    existedReport.setUpdatedBy(userRepository.findById(userPrincipal.getId()).orElse(null));
                    reportRepository.save(existedReport);
                } else {
                    reportRepository.save(report);
                }
            }
        }
    }

    @Override
    public Boolean remove(DisableRequest disableRequest) {
        reportRepository.deleteAllById(disableRequest.getIds());
        return true;
    }

    @Override
    public void exportExcel(String reportStr, HttpServletResponse httpServletResponse) {
        System.out.println(reportStr);
        ReportDTO reportDTO = new ReportDTO();
        if (reportStr != null && !reportStr.isEmpty()) {
            try {
                reportDTO = objectMapper.readValue(reportStr, ReportDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Pageable pageable = PageRequest.of(0, 10,
                Sort.by("createdDate").descending());
        SimpleResponse<?> simpleResponse = findAll(reportDTO, pageable);
        List<ReportDTO> reportDTOList = modelMapper.map(simpleResponse.getListT(),
                new TypeToken<List<ReportDTO>>() {
                }.getType());
        httpServletResponse.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Report_" + currentDateTime + ".xlsx";
        httpServletResponse.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Report");
        int rowNum = 0;
        Row _row = sheet.createRow(rowNum++);
        if (reportDTO.getType().equalsIgnoreCase(ReportType.SUBJECT.name())) {
            _row.createCell(0).setCellValue(reportDTO.getSubjectId() != null
                    ? "Subject: " + subjectRepository.findById(reportDTO.getSubjectId()).get().getName() : "Subject: " + "All");
            _row.createCell(1).setCellValue(reportDTO.getYear() != null
                    && reportDTO.getSemester() != null
                    ? "Semester-Year: " + reportDTO.getSemester() + "-" + reportDTO.getYear() : "Semester-Year: " + "All");
        } else {
            _row.createCell(0).setCellValue(reportDTO.getYear() != null
                    && reportDTO.getSemester() != null
                    ? "Semester-Year: " + reportDTO.getSemester() + "-" + reportDTO.getYear() : "Semester-Year: " + "All");
        }
        _row = sheet.createRow(rowNum++);
        Cell _cell1 = _row.createCell(0);
        _cell1.setCellValue("Class");
        Cell _cell2 = _row.createCell(1);
        _cell2.setCellValue("Year");
        Cell _cell3 = _row.createCell(2);
        _cell3.setCellValue("Semester");
        Cell _cell4 = _row.createCell(3);
        _cell4.setCellValue("Number Of Pupils");
        Cell _cell5 = _row.createCell(4);
        _cell5.setCellValue("Pass Quantity");
        Cell _cell6 = _row.createCell(5);
        _cell6.setCellValue("Pass Percent");
        Cell _cell7 = _row.createCell(6);
        _cell7.setCellValue("Average Score");
        for (ReportDTO report : reportDTOList) {
            Row row = sheet.createRow(rowNum++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(classRepository.findById(report.getClassId()).get().getName());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(report.getYear());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(report.getSemester());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(report.getNumberOfPupils());
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(report.getPassQuantity());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(report.getPassPercent());
            Cell cell7 = row.createCell(6);
            cell7.setCellValue(report.getAverageScore());
        }

        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
