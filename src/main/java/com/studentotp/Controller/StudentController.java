package com.studentotp.Controller;

import com.studentotp.Model.*;
import com.studentotp.Service.LoginService;
import com.studentotp.Service.OtpService;
import com.studentotp.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/createStudent")
    public ResponseEntity<RegisterResponse> createStudent(@RequestBody StudentRequest studentRequest) throws MessagingException, UnsupportedEncodingException {

        RegisterResponse registerResponse = studentService.createStudent(studentRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);

    }

    @PostMapping("/validityLogin")
    public ResponseEntity<ValidateResponse> validityLogin(@RequestBody ValidateRequest loginRequest, String otp) throws MessagingException, UnsupportedEncodingException {

        ValidateResponse loginResponse = otpService.validateOtp(loginRequest,otp);
        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);

    }

    @PostMapping("/tokenLogin")
    public ResponseEntity<TokenInfo> tokenLogin(@RequestBody Login login)  {

        TokenInfo loginInfo = loginService.getLogin(login);
        return new ResponseEntity<>(loginInfo, HttpStatus.CREATED);

    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<ArrayList<Student>> getAllStudents(@RequestHeader("token") String token) throws MessagingException, UnsupportedEncodingException {

        ArrayList<Student> students =  studentService.getAllStudents(token);
        return new ResponseEntity<ArrayList<Student>>(students,HttpStatus.CREATED);

    }

    @GetMapping("/getStudent")
    public ResponseEntity<Student> getStudent(@RequestHeader("token") String token) throws MessagingException, UnsupportedEncodingException {

        Student student =  studentService.getStudent(token);
        return new ResponseEntity<Student>(student,HttpStatus.CREATED);

    }

}
