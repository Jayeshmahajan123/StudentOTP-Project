package com.studentotp.Service;

import com.studentotp.Model.*;
import com.studentotp.RepositoryDemo.OtpRepo;
import com.studentotp.RepositoryDemo.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;


@Service
@Slf4j
public class OtpService {

    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerfiedResponse verfiedResponse;

    @Autowired
    private StudentRepo studentRepo;

    private final Long OTP_VALIDITY_TIME;

    @Autowired
    public OtpService(@Value("${otpValidityTime}") Long OTP_VALIDITY_TIME) {
        this.OTP_VALIDITY_TIME = OTP_VALIDITY_TIME;
    }

    public Otp generateOtp (Student student) throws MessagingException, UnsupportedEncodingException {

        Otp otp = new Otp();
        String generateOTP = new DecimalFormat("000000").format(new Random().nextInt(999999));

        otp.setStd_opt(generateOTP);
        otp.setStudent(student);
        otp.setCreateOn(new Date().getTime());
        otpRepo.save(otp);

        emailService.sendOTPEmail(student, generateOTP);
        return otp;
    }

    public ValidateResponse validateOtp(ValidateRequest validateRequest, String otp) {
        log.info("userId = " + validateRequest + " otp = " + otp);
        Otp check = otpRepo.findByStd_opt(validateRequest.getOtpVerify());

        if (check != null) {

            log.info("Otp = " + check.toString());

                long otpTime = check.getCreateOn();

                //   log.info("Current time now : " + currentTimeNow.getTime());

                long validTime = otpTime + (OTP_VALIDITY_TIME * 60000);

                Date otpValidity = new Date(validTime);
                Date currentDate = new Date();
                if (currentDate.before(otpValidity)) {
                    //    otpRepository.deleteAllByOtp(otp);

                    Student student = check.getStudent();

                    student.setVerifyStatus(true);
                    studentRepo.save(student);

                    verfiedResponse.setFullName(student.getFullName());
                    verfiedResponse.setEmail(student.getEmail());
                    verfiedResponse.setAddress(student.getAddress());
                    verfiedResponse.setMobile_number(student.getMobileNumber());
                    verfiedResponse.setVerifyStatus(student.isVerifyStatus());

                    return new ValidateResponse("Otp Validated", verfiedResponse);
                } else {
                    return new ValidateResponse("OTP validity expired",null );
                }
            } else {
                return new ValidateResponse("Please enter correct OTP", null);
            }
        }


    }
