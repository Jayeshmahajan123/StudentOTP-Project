package com.studentotp.RepositoryDemo;

import com.studentotp.Model.Otp;
import com.studentotp.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM token where token=:Token")
    public Token findByToken(@Param("Token") String Token);


    // public Token findByToken(String Token);

}
