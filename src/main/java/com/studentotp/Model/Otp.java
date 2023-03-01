package com.studentotp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "otp_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long otpId;

  private String std_opt;

  private Long createOn;

    @JoinColumn(name = "student")
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Student student;

}
