package com.tkpm.studentsmanagement.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.dto.ScoreBoardDTO;
import com.tkpm.studentsmanagement.entity.ScoreBoardEntity;
import com.tkpm.studentsmanagement.repository.ScoreBoardRepository;
import com.tkpm.studentsmanagement.service.IScoreBoardService;

import jakarta.transaction.Transactional;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/

@Service
@Transactional
public class ScoreBoardService implements IScoreBoardService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ScoreBoardRepository scoreBoardRepository;

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
}
