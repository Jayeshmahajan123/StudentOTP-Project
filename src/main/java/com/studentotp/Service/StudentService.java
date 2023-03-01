package com.studentotp.Service;

import com.studentotp.Config.ScalarCrypter;
import com.studentotp.Model.*;
import com.studentotp.RepositoryDemo.OtpRepo;
import com.studentotp.RepositoryDemo.StudentRepo;
import com.studentotp.RepositoryDemo.TokenRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpRepo otpRepo;
    @Autowired
    private TokenRepo tokenRepo;


    public RegisterResponse createStudent(StudentRequest studentRequest) throws MessagingException, UnsupportedEncodingException {

        Student student = new Student();

        String password = ScalarCrypter.encrypt(studentRequest.getPassword());
        studentRequest.setPassword(password);

        student.setFullName(studentRequest.getFullName());
        student.setEmail(studentRequest.getEmail());
        student.setAddress(studentRequest.getAddress());
        student.setMobileNumber(studentRequest.getMobileNumber());
        student.setPassword(studentRequest.getPassword());
        student.setVerifyStatus(false);
        studentRepo.save(student);

        Otp otp = otpService.generateOtp(student);

        return new RegisterResponse(student, "otp sent to your registered Email-Id");
    }

    public ArrayList<Student> getAllStudents(String token) {

        log.info("Token = " + token);
        Token check = tokenRepo.findByToken(token);
        log.info(""+check);

        if (check != null) {

            ArrayList<Student> al = studentRepo.getStudents();
            log.info("check empty:" + al.isEmpty());

            return al;

        }
        return null;
    }

    public Student getStudent(String token) {

        log.info("Token = " + token);
        Token check = tokenRepo.findByToken(token);
        log.info(""+check);

        if (check != null) {

            Student student = studentRepo.getStudentByToken(check.getStudentId());

            return student;

        }
        return null;
    }
}
