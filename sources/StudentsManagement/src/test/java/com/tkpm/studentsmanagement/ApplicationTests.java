package com.tkpm.studentsmanagement;

import com.tkpm.studentsmanagement.dto.ScoreBoardDTO;
import com.tkpm.studentsmanagement.entity.ScoreBoardEntity;
import com.tkpm.studentsmanagement.repository.ScoreBoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ApplicationTests {
	@Autowired
	private ScoreBoardRepository scoreBoardRepository;
	@Test
	void contextLoads() {
	}

	@Test
	void addNew() {
		ScoreBoardEntity scoreBoard = new ScoreBoardEntity();
		scoreBoard.setStudentID(2L);
		scoreBoard.setClassID(2L);
		scoreBoard.setSubjectID(2L);
		scoreBoard.setYear(2023);
		scoreBoard.setSemester(2D);
		scoreBoard.setExam15Min(8.5D);
		scoreBoard.setExam45Min(8.5D);
		scoreBoard.setExamMiddle(8.5D);
		scoreBoard.setExamFinal(8.5D);

		scoreBoardRepository.save(scoreBoard);
	}

	@Test
	void findAll() {
		Iterable<ScoreBoardEntity> scoreBoardEntities = scoreBoardRepository.findAll();
		for(ScoreBoardEntity sb : scoreBoardEntities) {
			System.out.println(sb.toString());
		}
	}

}
