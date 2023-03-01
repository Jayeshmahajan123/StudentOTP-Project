package com.studentotp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerfiedResponse {

    private String fullName;
    private String address;

    private  String email;

    private String mobile_number;

    private  boolean verifyStatus;
}
