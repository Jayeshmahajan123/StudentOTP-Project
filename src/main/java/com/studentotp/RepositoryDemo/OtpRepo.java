package com.studentotp.RepositoryDemo;

import com.studentotp.Model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepo extends JpaRepository<Otp,Long> {

    @Query(nativeQuery = true,value = "select * from otp_table where std_opt=:Std_opt")
    public Otp findByStd_opt(String Std_opt);
}
