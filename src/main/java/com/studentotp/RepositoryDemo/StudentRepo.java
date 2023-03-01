package com.studentotp.RepositoryDemo;

import com.studentotp.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    @Query(nativeQuery = true, value = "select * from std_table")
    public ArrayList<Student> getStudents();

    @Query(nativeQuery = true, value = "select * from std_table where student_id=:id")
    public Student getStudentByToken(Long id);

}
