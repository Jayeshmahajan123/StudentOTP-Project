package com.studentotp.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name = "std_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long studentId;

    private String fullName;

    private String address;

    private String email;
    private String mobileNumber;

    private boolean verifyStatus;

    private String password;


}
