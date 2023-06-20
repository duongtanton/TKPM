package com.tkpm.studentsmanagement;

import com.tkpm.studentsmanagement.dto.ScoreBoardDTO;
import com.tkpm.studentsmanagement.entity.ScoreBoardEntity;
import com.tkpm.studentsmanagement.entity.SubjectEntity;
import com.tkpm.studentsmanagement.repository.ScoreBoardRepository;
import com.tkpm.studentsmanagement.repository.SubjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ApplicationTests {
	@Autowired
	private ScoreBoardRepository scoreBoardRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Test
	void contextLoads() {
	}

	/*
    TEST APIs HANDLING SCOREBOARD
    */
	@Test
	void addNew() {
		// ScoreBoardEntity scoreBoard = new ScoreBoardEntity();
		// scoreBoard.setStudentID(2L);
		// scoreBoard.setClassID(2L);
		// scoreBoard.setSubjectID(2L);
		// scoreBoard.setYear(2023);
		// scoreBoard.setSemester(2D);
		// scoreBoard.setExam15Min(8.5D);
		// scoreBoard.setExam45Min(8.5D);
		// scoreBoard.setExamMiddle(8.5D);
		// scoreBoard.setExamFinal(8.5D);

		// scoreBoardRepository.save(scoreBoard);
	}

	@Test
	void findAll() {
		Iterable<ScoreBoardEntity> scoreBoardEntities = scoreBoardRepository.findAll();
		for(ScoreBoardEntity sb : scoreBoardEntities) {
			System.out.println(sb.toString());
		}
	}

	@Test
	void updateScoreboard() {
		ScoreBoardEntity scoreBoardEntity = scoreBoardRepository.findById(2L).orElse(null);
		if(scoreBoardEntity != null) {
			scoreBoardEntity.setExam15Min(15D);
			scoreBoardEntity.setExam45Min(15D);
			scoreBoardEntity.setExamMiddle(15D);
			scoreBoardEntity.setExamFinal(15D);

			scoreBoardRepository.save(scoreBoardEntity);
			System.out.println(scoreBoardEntity.toString());
		} else {
			System.out.println("Scoreboard not found !");
		}
	}

	@Test
	void deleteScoreboard() {

	}

	/*
    TEST APIs HANDLING SUBJECT
    */
	@Test
	void addNewSubject() {
		SubjectEntity subjectEntity = new SubjectEntity();
		subjectEntity.setId(2L);
		subjectEntity.setName("Ly");
		subjectEntity.setRequiredScore(5.0D);
		subjectRepository.save(subjectEntity);
	}

	@Test
	void findAllSubject() {
		Iterable<SubjectEntity> subjectEntities = subjectRepository.findAll();
		for(SubjectEntity sj : subjectEntities) {
			System.out.println(sj.toString());
		}
	}

	@Test
	void updateSubject() {
		SubjectEntity subjectEntity = subjectRepository.findById(1L).orElse(null);
		if(subjectEntity != null) {
			subjectEntity.setName("Hoa");
			subjectEntity.setRequiredScore(7.0D);

			subjectRepository.save(subjectEntity);
		} else {
			System.out.println("Can't find the subject !");
		}
	}

	@Test
	void deleteSubject() {
		subjectRepository.deleteById(1L);
	}

}
