package com.tkpm.studentsmanagement.service;

import com.tkpm.studentsmanagement.dto.ScoreBoardDTO;
import com.tkpm.studentsmanagement.dto.ScoreBoardSearchDTO;
import com.tkpm.studentsmanagement.dto.StaticAverageByClass;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/

public interface IScoreBoardService {
    ScoreBoardDTO create(ScoreBoardDTO scoreBoard);
    List<ScoreBoardDTO> create(List<ScoreBoardDTO> listScoreboard);
    List<ScoreBoardDTO> findAll(Pageable pageable);
    List<ScoreBoardDTO> findAll();
    ScoreBoardDTO findAllByStudentID(Long studentID);
    List<ScoreBoardDTO> findAllBySubject(Long subjectID);
    ScoreBoardDTO findByStudentAndSubject(Long studentID, Long subjectID);
    Integer totalPages(Pageable pageable);
    Boolean delete(List<Long> ids);
    Boolean delete(Long studentID, Long subjectID);
    Boolean update(ScoreBoardDTO scoreBoard);
    ScoreBoardDTO findByID(Long id);
    List<StaticAverageByClass> staticAverageByClass(Long classsId);
    List<ScoreBoardDTO> findAll(ScoreBoardSearchDTO scoreBoardSearchDTO, Pageable pageable);
    ScoreBoardDTO updateResult(Long scoreBoardId);

    void exportExcel(String scoreboardStr, HttpServletResponse httpServletResponse);
}
