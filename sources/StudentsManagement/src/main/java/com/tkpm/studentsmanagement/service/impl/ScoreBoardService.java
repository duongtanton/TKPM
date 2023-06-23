package com.tkpm.studentsmanagement.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.constant.Regualation;
import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.entity.RoleEntity;
import com.tkpm.studentsmanagement.entity.UserEntity;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.entity.ScoreBoardEntity;
import com.tkpm.studentsmanagement.repository.ScoreBoardRepository;
import com.tkpm.studentsmanagement.service.IScoreBoardService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScoreBoardService implements IScoreBoardService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScoreBoardRepository scoreBoardRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ConfigurationService configurationService;

    public ScoreBoardDTO create(ScoreBoardDTO scoreBoard) {
        ScoreBoardEntity scoreBoardEntity = modelMapper.map(scoreBoard, ScoreBoardEntity.class);
        return modelMapper.map(scoreBoardRepository.save(scoreBoardEntity), ScoreBoardDTO.class);
    }

    @Override
    public List<ScoreBoardDTO> create(List<ScoreBoardDTO> listScoreboard) {
        List<ScoreBoardEntity> listScoreboardEntity = modelMapper.map(
                listScoreboard,
                new TypeToken<List<ScoreBoardEntity>>() {
                }.getType());
        List<ScoreBoardDTO> listScoreboardDTO = modelMapper.map(
                scoreBoardRepository.saveAll(listScoreboardEntity),
                new TypeToken<List<ScoreBoardDTO>>() {
                }.getType());
        return listScoreboardDTO;
    }

    @Override
    public List<ScoreBoardDTO> findAll(Pageable pageable) {
        List<ScoreBoardEntity> listScoreboardEntity = scoreBoardRepository.findAll(pageable);
        return modelMapper.map(listScoreboardEntity, new TypeToken<List<ScoreBoardDTO>>() {
        }.getType());
    }

    @Override
    public List<ScoreBoardDTO> findAll() {
        List<ScoreBoardEntity> listScoreboardEntity = scoreBoardRepository.findAll();
        return modelMapper.map(
                listScoreboardEntity,
                new TypeToken<List<ScoreBoardDTO>>() {
                }.getType());
    }

    @Override
    public ScoreBoardDTO findAllByStudentID(Long studentID) {
        return null;
    }

    @Override
    public List<ScoreBoardDTO> findAllBySubject(Long subjectID) {
        return null;
    }

    @Override
    public ScoreBoardDTO findByStudentAndSubject(Long studentID, Long subjectID) {
        return null;
    }

    @Override
    public Integer totalPages(Pageable pageable) {
        Long count = scoreBoardRepository.count();
        Integer totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return totalPages;
    }

    @Override
    public Boolean delete(List<Long> ids) {
        try {
            scoreBoardRepository.deleteAllById(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean delete(Long studentID, Long subjectID) {
        return null;
    }

    @Override
    public Boolean update(ScoreBoardDTO scoreBoard) {
        ScoreBoardEntity scoreBoardEntity = scoreBoardRepository.findById(scoreBoard.getId()).orElse(null);
        try {
            scoreBoardEntity.setAverageScore(scoreBoard.getAverageScore());
            scoreBoardEntity.setCompleted(scoreBoard.getIsCompleted());
            scoreBoardEntity.setExam15Min(scoreBoard.getExam15Min());
            scoreBoardEntity.setExam45Min(scoreBoard.getExam45Min());
            scoreBoardEntity.setExamMiddle(scoreBoard.getExamMiddle());
            scoreBoardEntity.setExamFinal(scoreBoard.getExamFinal());
            scoreBoardEntity.setSemester(scoreBoard.getSemester());

            scoreBoardRepository.save(scoreBoardEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ScoreBoardDTO findByID(Long id) {
        ScoreBoardEntity scoreBoardEntity = scoreBoardRepository.findById(id).orElse(null);
        return modelMapper.map(scoreBoardRepository.save(scoreBoardEntity), ScoreBoardDTO.class);
    }

    @Override
    public List<StaticAverageByClass> staticAverageByClass(Long classsId) {
        List<Object[]> objectResults = scoreBoardRepository.staticAverageByClass(classsId);
        List<StaticAverageByClass> staticResults = objectResults.stream().map(item -> {
            StaticAverageByClass staticRow = new StaticAverageByClass();

            staticRow.setStudent(modelMapper.map(item[0], StudentDTO.class));
            staticRow.setClasss(modelMapper.map(item[1], ClassDTO.class));
            staticRow.setScoreI((Double) item[2]);
            staticRow.setScoreII((Double) item[3]);
            return staticRow;
        }).toList();
        return staticResults;
    }

    @Override
    public List<ScoreBoardDTO> findAll(ScoreBoardSearchDTO scoreBoardSearchDTO, Pageable pageable) {
        Page<ScoreBoardEntity> pageScoreboardEntity = scoreBoardRepository.findAll(
                scoreBoardSearchDTO.getClasss(),
                scoreBoardSearchDTO.getStudent(),
                scoreBoardSearchDTO.getSubject(),
                scoreBoardSearchDTO.getYear(),
                scoreBoardSearchDTO.getSemester(),
                pageable);
        return modelMapper.map(pageScoreboardEntity.get().collect(Collectors.toList()), new TypeToken<List<ScoreBoardDTO>>() {
        }.getType());
    }

    @Override
    public ScoreBoardDTO updateResult(Long scoreBoardId) {
        System.out.println(scoreBoardId);
        ScoreBoardEntity scoreBoard = scoreBoardRepository.findById(scoreBoardId).orElse(null);
        if (scoreBoard != null) {
            scoreBoard.setAverageScore(
                    ((scoreBoard.getExam15Min() * 1 +
                            scoreBoard.getExam45Min() * 2 +
                            scoreBoard.getExamMiddle() * 3 +
                            scoreBoard.getExamFinal() * 4)/10));
            System.out.println(scoreBoard.getAverageScore());
            scoreBoard.setIsCompleted(scoreBoard.getAverageScore() >= Double.parseDouble(
                    configurationService.findByName(Regualation.REQUIRED_MARK.name()).getValue()));
            System.out.println(scoreBoard.getIsCompleted());
            scoreBoard = scoreBoardRepository.save(scoreBoard);
        }
        return modelMapper.map(scoreBoard, ScoreBoardDTO.class);
    }

    @Override
    public void exportExcel(String scoreboardStr, HttpServletResponse httpServletResponse) {
        ScoreBoardSearchDTO scoreBoardSearchDTO = new ScoreBoardSearchDTO();
        if (scoreboardStr != null) {
            try {
                scoreBoardSearchDTO = objectMapper.readValue(scoreboardStr, ScoreBoardSearchDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<ScoreBoardDTO> ScoreBoards = findAll(scoreBoardSearchDTO,null);
        httpServletResponse.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ScoreBoard_" + currentDateTime + ".xlsx";
        httpServletResponse.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ScoreBoard");
        int rowNum = 0;
        Row _row = sheet.createRow(rowNum++);
        _row.createCell(0).setCellValue(scoreBoardSearchDTO.getClasss() != null
                ? "Class: " + classService.findById(scoreBoardSearchDTO.getClasss()).getName() : "Class: " + "All");
        _row.createCell(1).setCellValue(scoreBoardSearchDTO.getSubject() != null
                ? "Subject: " + subjectService.findByID(scoreBoardSearchDTO.getSubject()).getName() : "Subject: " + "All");
        _row.createCell(2).setCellValue(scoreBoardSearchDTO.getYear() != null
                && scoreBoardSearchDTO.getSemester() != null
                ? "Semester-Year: " + scoreBoardSearchDTO.getSemester() + "-" +scoreBoardSearchDTO.getYear() : "Semester-Year: " + "All");
        _row = sheet.createRow(rowNum++);
        Cell _cell1 = _row.createCell(0);
        _cell1.setCellValue("Student ID");
        Cell _cell2 = _row.createCell(1);
        _cell2.setCellValue("Student Name");
        Cell _cell3 = _row.createCell(2);
        _cell3.setCellValue("15 Mins Score");
        Cell _cell4 = _row.createCell(3);
        _cell4.setCellValue("45 Mins Score");
        Cell _cell5 = _row.createCell(4);
        _cell5.setCellValue("Mid-exam Score");
        Cell _cell6 = _row.createCell(5);
        _cell6.setCellValue("Final-exam Score");
        Cell _cell7 = _row.createCell(6);
        _cell7.setCellValue("Average Score");
        Cell _cell8 = _row.createCell(7);
        _cell8.setCellValue("Passed");
        for (ScoreBoardDTO scoreBoard : ScoreBoards) {
            Row row = sheet.createRow(rowNum++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(scoreBoard.getStudent().getId());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(scoreBoard.getStudent().getName());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(scoreBoard.getExam15Min());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(scoreBoard.getExam45Min());
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(scoreBoard.getExamMiddle());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(scoreBoard.getExamFinal());
            Cell cell7 = row.createCell(6);
            cell7.setCellValue(scoreBoard.getAverageScore() != null ? scoreBoard.getAverageScore() : 0);
            Cell cell8 = row.createCell(7);
            cell8.setCellValue(scoreBoard.getIsCompleted() != null ? scoreBoard.getIsCompleted() : false);
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
