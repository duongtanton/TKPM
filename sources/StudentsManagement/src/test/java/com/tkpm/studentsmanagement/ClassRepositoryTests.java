package com.tkpm.studentsmanagement;

import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.repository.ClassRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ClassRepositoryTests {
    // @Autowired private ClassRepository repo;

    // @Test
    // public void testAddNew(){
    //     ClassEntity classEntity = new ClassEntity();
    //     classEntity.setName("20CTT5");
    //     classEntity.setNumberOfPupils(75);

    //     ClassEntity savedClass = repo.save(classEntity);

    //     Assertions.assertNotNull(savedClass);
    //     Assertions.assertNotEquals(0, savedClass.getId());

    // }

    // @Test
    // public void testGetListAllClasses(){
    //     Iterable<ClassEntity> classesList = repo.findAll();

    //    for (ClassEntity classEntity: classesList) {
    //        System.out.println(classEntity.getName());
    //    }
    // }

    // @Test
    // public void testUpdateClass() {
    //     Long classId = Long.valueOf(1);
    //     Optional<ClassEntity> optionalClassEntity = repo.findById(classId);
    //     ClassEntity classEntity = optionalClassEntity.get();
    //     String newName = "Tkpm updated";
    //     classEntity.setName(newName);
    //     repo.save(classEntity);

    //    Optional<ClassEntity>  updatedClassEntity = repo.findById(classId);
    //     Assertions.assertEquals(newName, updatedClassEntity.get().getName());
    // }

    // @Test
    // public void testGetClassById() {
    //     Long classId = Long.valueOf(1);
    //     Optional<ClassEntity> optionalClassEntity = repo.findById(classId);
    //     Assertions.assertNotNull(optionalClassEntity);

    //     System.out.println(optionalClassEntity.get());
    // }
}
