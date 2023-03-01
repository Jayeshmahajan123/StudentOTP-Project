package com.studentotp.Service;

import com.studentotp.Model.Student;
import com.studentotp.Model.Token;
import com.studentotp.RepositoryDemo.TokenRepo;
import com.studentotp.Config.ScalarCrypter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class TokenService {

    private static final String ATTR_SEPARATOR = "&@";
    @Autowired
    private TokenRepo tokenRepo;

    public String generate(Long studentId, Map<Long, String> tokenAttributes) throws TransactionException {

        log.info("generate token");
        StringBuffer buffer = new StringBuffer();
        buffer.append("Student=");
        buffer.append(studentId);

        buffer.append(ATTR_SEPARATOR);
        buffer.append("createdOn=");
        buffer.append(new Date().getTime());
        for (Map.Entry<Long, String> attr : tokenAttributes.entrySet()) {
            buffer.append(ATTR_SEPARATOR);
            buffer.append(attr.getKey());
            buffer.append("=");
            buffer.append(attr.getValue());
        }

        String token = ScalarCrypter.encrypt(buffer.toString());

        tokenRepo.save(new Token(studentId,token,new Date().getTime()));

        return token;
    }

}
