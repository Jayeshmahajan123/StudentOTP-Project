package com.studentotp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ValidateResponse {
    private String msg;
    private VerfiedResponse verfiedResponse;


}
