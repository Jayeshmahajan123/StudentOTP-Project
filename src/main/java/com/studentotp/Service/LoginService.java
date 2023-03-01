package com.studentotp.Service;

import com.studentotp.Config.ScalarCrypter;
import com.studentotp.Model.*;
import com.studentotp.RepositoryDemo.StudentRepo;
import com.studentotp.RepositoryDemo.TokenRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class LoginService {
    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private OtpService otpService;

    @Autowired
    private TokenService tokenService;

    public TokenInfo getLogin(Login login) {

        log.info("login" + login);

        Optional<Student> student = studentRepo.findById(login.getStudentId());

        String password = ScalarCrypter.encrypt(login.getPassword());
        login.setPassword(password);

        if (student.isPresent()) {
            if (student.get().getPassword().equals(password)){

                if(student.get().isVerifyStatus()==true){

                    Map<Long, String> tokenAttributes = new HashMap<>();
                    String token = tokenService.generate(student.get().getStudentId(), tokenAttributes);

                    Token token1 = new Token();
                    token1.setStudentId(student.get().getStudentId());
                    token1.setToken(token);
                    token1.setCreateOn(new Date().getTime());
                    tokenRepo.save(token1);

                    TokenInfo tokenInfo = new TokenInfo();

                    tokenInfo.setStudentId(student.get().getStudentId());
                    tokenInfo.setToken(token);

                    log.info("password" + password);

                    return new TokenInfo(tokenInfo.getStudentId(), tokenInfo.getToken());
                }
                else{
                    System.out.println("Student Not Verified");
                }

            } else {
                System.out.println("Password Not Match...");
            }


        }
        return null;
    }
}
